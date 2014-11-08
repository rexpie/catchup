/**
 * 
 */
package tokenTest.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Constants;
import tokenTest.Util.SMSUtil;
import tokenTest.Util.Status;
import tokenTest.bo.ComplaintBo;
import tokenTest.bo.PictureBo;
import tokenTest.bo.ReportBo;
import tokenTest.bo.TagBo;
import tokenTest.bo.UserBo;
import tokenTest.bo.ValidationCodeBo;
import tokenTest.exception.PictureNotFoundException;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.Complaint;
import tokenTest.model.Picture;
import tokenTest.model.Report;
import tokenTest.model.Tag;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;
import tokenTest.response.BlacklistResponse;
import tokenTest.response.LikeUsersResponse;
import tokenTest.response.LoginResponse;
import tokenTest.response.PhotoListResponse;
import tokenTest.response.PicResponse;
import tokenTest.response.StatusResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.response.ValidatePhoneResponse;
import tokenTest.response.ViewersResponse;
import tokenTest.service.IUserService;

import com.google.common.collect.Sets;

/**
 * @author pengtao
 * 
 */
@RestController
@RequestMapping("/user")
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserBo userBo;

	@Autowired
	private PictureBo pictureBo;

	@Autowired
	private ValidationCodeBo validationCodeBo;

	@Autowired
	private ServletContext servletContext = null;

	@Autowired
	private ComplaintBo complaintBo;

	@Autowired
	private ReportBo reportBo;

	@Autowired
	private TagBo tagBo;

	private SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyyMMdd");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#userRegister(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Date,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = { "/userRegister**" }, method = RequestMethod.GET)
	public LoginResponse userRegister(
			@RequestParam(required = true) String nickName,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String phoneNum,
			@RequestParam(required = false) String building,
			@RequestParam(required = false) String gender,
			@RequestParam(required = false) Date birthday,
			@RequestParam(required = false) String company) {
		/* 必填信息 */
		User user = new User(password, nickName, gender, building, phoneNum);
		user.setLogin_attempts(0);

		/* 非必填信息 */
		if (birthday != null)
			user.setBirthday(birthday);
		if (company != null)
			user.setCompany(company);

		/* 生成令牌 */
		user.setToken(RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH));

		/* 注册用户 */
		try {
			userBo.save(user);
		} catch (DataIntegrityViolationException e) {
			// 不满足唯一性约束，phonenum或nickname重复,占用token做错误信息。
			e.printStackTrace();
			return new LoginResponse(Status.ERR_DUPLICATE_ENTRY, null);
		}
		return new LoginResponse(Status.OK, user.getId(), user.getToken());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#userLogin(java.lang.Long,
	 * java.lang.String)
	 */
	/*
	 * @RequestMapping(value = { "/userLogin**" }, method = RequestMethod.GET)
	 * public LoginResponse userLogin(@RequestParam(required = true) Long id,
	 * 
	 * @RequestParam(required = true) String password) { // TODO Auto-generated
	 * method stub User user = null;
	 * 
	 * 查找用户 try { user = userBo.findByUserId(id); } catch (Exception e) { //
	 * TODO: handle exception // 不知道啥错 return new
	 * LoginResponse(Status.SERVICE_NOT_AVAILABLE, "服务器不可用"); }
	 * 
	 * 用户不存在或密码不对 if (user == null || !password.equals(user.getPassword()))
	 * return new LoginResponse(Status.ERROR_USER_NOT_FOUND, "用户名或密码错误");
	 * 
	 * 生产、更新令牌 user.setToken(RandomStringUtils.randomAlphanumeric(30)); try {
	 * userBo.update(user); } catch (Exception e) { // TODO: handle exception
	 * return null; } return new LoginResponse(Status.OK, user.getId(),
	 * user.getToken()); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#userLogin(java.lang.Long,
	 * java.lang.String)
	 */
	@RequestMapping(value = { "/userLogin**" }, method = RequestMethod.GET)
	public LoginResponse userLogin(
			@RequestParam(required = true) String nickorphone,
			@RequestParam(required = true) String password) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByNickOrPhone(nickorphone);
		} catch (Exception e) {
			// 不知道啥错
			e.printStackTrace();
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, null);
		}

		/* 用昵称查找用户不存在 */

		if (user == null)
			return new LoginResponse(
					Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD, null);

		int attempts = 0;
		attempts = user.getLogin_attempts();
		// 用户存在密码不对
		if (user != null && !password.equals(user.getPassword())) {
			// password wrong
			attempts++;
			user.setLogin_attempts(attempts);
			userBo.update(user);
		}

		if (attempts >= Constants.MAX_LOGIN_ATTEMPTS) {
			return new LoginResponse(Status.ERR_MAX_LOGIN_ATTEMPTS, null);
		}

		/* 用昵称查找用户不存在 */

		if (user == null || !password.equals(user.getPassword()))
			return new LoginResponse(
					Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD, null);

		/* 生产、更新令牌 */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		user.setLogin_attempts(0); // clear login attempts
		try {
			userBo.update(user);
		} catch (Exception e) {
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, null);
		}
		return new LoginResponse(Status.OK, user.getId(), user.getToken());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#userLogout(java.lang.Long,
	 * java.lang.String)
	 */
	@RequestMapping(value = { "/userLogout**" }, method = RequestMethod.GET)
	public LoginResponse userLogout(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// 不管什么错，用户不用知道。
			return LoginResponse.OK;
		}

		/* 用户不存在或者令牌不正确 ，不管什么错，用户不用知道。 */
		if (user == null || !user.getToken().equals(token))
			return LoginResponse.OK;

		/* 清除令牌 */
		user.setToken("");
		try {
			userBo.update(user);
		} catch (Exception e) {
			// 不管什么错，用户不用知道。
			return LoginResponse.OK;
		}
		return LoginResponse.OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#getUserDetail(java.lang.Long,
	 * java.lang.String, java.lang.Long) 查找用户信息，queriedid为空表示查找自己的信息，显示内容更多。
	 */
	@RequestMapping(value = { "/getUserDetail**" }, method = RequestMethod.GET)
	public UserDetailResponse getUserDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) Long target) {
		User user = null;

		/* 查找用户 */
		try {
			if (target == null || target == id) {
				user = userBo.validateUserWithDetail(id, token,
						Constants.USER_LOAD_TAGS | Constants.USER_LOAD_VIEWERS
								| Constants.USER_LOAD_LIKES);
			} else {
				user = userBo.validateUserWithDetail(id, token,
						Constants.USER_LOAD_LIKES);
			}

		} catch (UserNotFoundException e) {
			return UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return UserDetailResponse.getError(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
			return UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
		}

		User theTarget = null;
		/* targetId==null表示查看自己信息，否则为查看别人信息 */
		if (target == null || target == user.getId()) {
			return new UserDetailResponse(user, true);
		} else {
			try {
				theTarget = userBo.findByUserIdWithDetail(target,
						Constants.USER_LOAD_VIEWERS | Constants.USER_LOAD_TAGS
								| Constants.USER_LOAD_LIKES);
			} catch (Exception e) {
				UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
			}
			if (theTarget == null)
				UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
			boolean added = theTarget.getViewers().add(user);
			if (added) {
				userBo.update(theTarget);
			}
			UserDetailResponse response = new UserDetailResponse(theTarget,
					false);
			response.setAlreadyLiked(user.getLikes().contains(theTarget));
			return response;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#updateUserProfile(java.lang.Long,
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Date,
	 * java.lang.String, java.lang.String, java.lang.String) 修改用户信息
	 */
	@Override
	@RequestMapping(value = { "/updateUserProfile**" }, method = RequestMethod.GET)
	public StatusResponse updateUserProfile(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) String nickname,
			@RequestParam(required = false) String building,
			@RequestParam(required = false) String birthday,
			@RequestParam(required = false) String sex,
			@RequestParam(required = false) String job,
			@RequestParam(required = false) String industry,
			@RequestParam(required = false) String company) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new StatusResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new StatusResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new StatusResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 设置新的 用户信息 */
		if (nickname != null)
			user.setNickname(nickname);
		if (building != null)
			user.setBuilding(building);
		if (birthday != null) {
			try {
				user.setBirthday(birthdayFormat.parse(birthday));
			} catch (ParseException e) {
				e.printStackTrace();
				return new StatusResponse(Status.ERR_INVALID_DATE_FORMAT);
			}
		}
		if (sex != null) {
			if (isValidSex(sex))
				user.setSex(sex);
			else
				return new StatusResponse(Status.ERR_INVALID_GENDER);

		}
		if (job != null)
			user.setJob(job);
		if (industry != null)
			user.setIndustry(industry);
		if (company != null)
			user.setCompany(company);

		/* 更新用户信息 */
		try {
			userBo.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new StatusResponse(Status.ERR_GENERIC);
		}
		return new StatusResponse(Status.OK);
	}

	private boolean isValidSex(String sex) {
		if (StringUtils.equalsIgnoreCase("F", sex)
				|| StringUtils.equalsIgnoreCase("M", sex))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#changePassWord(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = { "/changePassword**" }, method = RequestMethod.GET)
	public LoginResponse changePassword(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String oldpassword,
			@RequestParam(required = true) String newpassword) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return new LoginResponse(
					Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD);
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getPassword().equals(oldpassword))
			return new LoginResponse(
					Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD);

		/* 设置新的 用户密码 */
		user.setPassword(newpassword);
		user.setLogin_attempts(0);

		/* 更新用户密码 */
		try {
			userBo.update(user);
		} catch (Exception e) {
			return new LoginResponse(Status.ERR_GENERIC);
		}

		return new LoginResponse(Status.OK);
	}

	@RequestMapping(value = { "/validatePhone**" }, method = RequestMethod.GET)
	public ValidatePhoneResponse validatePhone(
			@RequestParam(required = true) String phoneNum) {

		return _doValidate(phoneNum, ValidationCodeStatus.NEW);

	}

	@RequestMapping(value = { "/validateReset**" }, method = RequestMethod.GET)
	public ValidatePhoneResponse validateReset(
			@RequestParam(required = true) String nickorphone) {

		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByNickOrPhone(nickorphone);
		} catch (Exception e) {
			// 不知道啥错
			e.printStackTrace();
			return new ValidatePhoneResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 用昵称查找用户不存在 */

		if (user == null)
			return new ValidatePhoneResponse(Status.ERR_USER_NOT_FOUND);

		return _doValidate(user.getPhone_number(),
				ValidationCodeStatus.PASSWD_RESET);

	}

	private ValidatePhoneResponse _doValidate(String phoneNum,
			ValidationCodeStatus inputStatus) {
		/* 随机验证码 */
		ValidationCode validationCode = null;
		try {
			validationCode = validationCodeBo.findByPhoneNum(phoneNum);
		} catch (Exception e) {
			return new ValidatePhoneResponse(Status.ERR_GENERIC);
		}

		Date time = new Date();
		boolean isNewNumber = false;
		if (validationCode == null) {
			if (inputStatus == ValidationCodeStatus.NEW) {
				isNewNumber = true;
				validationCode = new ValidationCode();
				validationCode.setPhoneNum(phoneNum);
				validationCode.setCreateTime(time);
			} else {
				// no valid phone num, cannot reset password
				return new ValidatePhoneResponse(Status.ERR_PHONE_NUM_NOT_FOUND);
			}
		}

		if (inputStatus == ValidationCodeStatus.NEW
				&& validationCode.getStatus() == inputStatus.getValue()) {
			// no need to validate twice? Or maybe not getting the SMS so doing
			// it again
		}

		if (inputStatus == ValidationCodeStatus.NEW
				&& validationCode.getStatus() == ValidationCodeStatus.PASSWD_RESET
						.getValue()) {
			// why validate the new phone num after reset?
			return new ValidatePhoneResponse(Status.ERR_PHONE_VALIDATION_FAIL);
		}

		String secret = SMSUtil.genCode();
		int retval = SMSUtil.doValidate(phoneNum, secret);
		if (retval < 0) {
			return new ValidatePhoneResponse(Status.ERR_PHONE_VALIDATION_FAIL);
		}
		validationCode.setCode(secret);
		validationCode.setUpdateTime(time);
		validationCode.setStatus(inputStatus);

		if (isNewNumber) {
			validationCodeBo.save(validationCode);
		} else {
			validationCodeBo.update(validationCode);
		}

		return new ValidatePhoneResponse(Status.OK);

	}

	/* 默认增的不是头像 */
	@RequestMapping(value = { "/addPhoto**" }, method = RequestMethod.POST)
	public PicResponse addPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) MultipartFile file,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Boolean isProfile) {

		/* 验证图片 */
		if (!StringUtils.equals(file.getContentType(), "image/png")) {
			/* 文件格式错误 */
			return new PicResponse(Status.ERR_PIC_FORMAT);
		}

		String path = servletContext.getRealPath("/") + File.separator
				+ Constants.PICTURE_ROOT_PATH;
		User user = null;
		/* 验证用户 */
		try {
			if (isProfile != null && isProfile){
				user = userBo.validateUserWithDetail(id, token, Constants.USER_LOAD_PHOTO);
			}else{
				user = userBo.validateUserWithDetail(id, token, Constants.USER_LOAD_PICTURES);
			}
		} catch (UserNotFoundException e) {
			return new PicResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new PicResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 新建图片 */

		Picture picture = new Picture(new Date());
		if (description != null)
			picture.setDescription(description);

		try {
			if (isProfile != null && isProfile) {
				pictureBo.save(user, file, picture, path, true);
			} else {
				pictureBo.save(user, file, picture, path, false);
			}

		} catch (IOException e) {
			/* 文件IO */
			e.printStackTrace();
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		}
		if (isProfile) {
			return new PicResponse(Status.OK, user.getPic().getId());
		} else {
			return new PicResponse(Status.OK);
		}
	}

	@RequestMapping(value = { "/deletePhoto**" }, method = RequestMethod.GET)
	public PicResponse deletePhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) String picid) {
		String path = servletContext.getRealPath("/") + File.separator
				+ Constants.PICTURE_ROOT_PATH;
		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_PICTURES | Constants.USER_LOAD_PHOTO);
		} catch (UserNotFoundException e) {
			return new PicResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new PicResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 查找图片 */
		Picture picture = null;
		try {
			picture = pictureBo.findById(picid);
		} catch (Exception e) {
			// TODO: handle exception
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		}
		if (picture == null) {
			// 图片不存在
			return new PicResponse(Status.ERR_PIC_NOT_FOUND);
		}

		/* 不是自己的图片，不能删除 */
		if (!user.getPicture().contains(picture)) {
			return new PicResponse(Status.ERR_BANNED);
		}

		/* 是头像，不能删除 */
		if (user.getPic() != null && user.getPic().equals(picture)) {
			return new PicResponse(Status.ERR_CANNOT_DELETE_PROFILE_PHOTO);
		}

		/* 删除图片数据和文件,头像不能删除 */
		try {
			/* 删除图片数据和文件 */
			pictureBo.delete(user, picture, path);
		} catch (Exception e) {
			e.printStackTrace();
			return new PicResponse(Status.SERVICE_NOT_AVAILABLE);
		}
		return new PicResponse(Status.OK);
	}

	/* 默认原图 */
	@RequestMapping(value = { "/getPhoto**" }, method = RequestMethod.GET)
	public void getPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) String picid,
			@RequestParam(required = false) Integer isThumb,
			HttpServletResponse response) {
		String path = servletContext.getRealPath("/") + File.separator
				+ Constants.PICTURE_ROOT_PATH;
		/* 验证用户 */
		try {
			userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			response.setStatus(404);
			try {
				new PrintStream(response.getOutputStream()).println("x");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
			// TODO: handle exception
			// return Status.ERR_USER_NOT_FOUND;
		} catch (WrongTokenException e) {
			return;
			// TODO: handle exception
			// return Status.ERR_WRONG_TOKEN;
		} catch (Exception e) {
			return;
			// TODO: handle exception
			// return Status.SERVICE_NOT_AVAILABLE;
		}
		/* 查找图片 */
		Picture picture = null;
		try {
			picture = pictureBo.findById(picid);
		} catch (PictureNotFoundException e) {
			System.out.println("PictureNotFoundException:" + picid + "user:"
					+ id);
			response.setStatus(404);
			return;
		} catch (Exception e) {
			System.out.println("UnknownException:" + picid + "user:" + id);
			response.setStatus(500);
			e.printStackTrace();
			return;
		}

		/* 返回原图或者小图 */
		if (isThumb == null || isThumb != 1) {
			path += File.separator + Constants.RIGIN_PICTURE_PATH;
		} else {
			path += File.separator + Constants.THUMB_PICTURE_PATH;
		}
		File file = new File(path + File.separator + picture.getFilename());
		if (file.exists()) {
			try {
				FileInputStream is = new FileInputStream(file);
				IOUtils.copy(is, response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	@RequestMapping(value = { "/listPhoto**" }, method = RequestMethod.GET)
	public PhotoListResponse listPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
		User user = null;
		PhotoListResponse response = new PhotoListResponse(Status.OK);
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_PICTURES);
		} catch (UserNotFoundException e) {
			return new PhotoListResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new PhotoListResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new PhotoListResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		for (Picture pic : user.getPicture()) {
			response.ids.add(pic.getId());
		}
		// TODO
		return response;
	}

	@RequestMapping(value = { "/getPicture**" }, method = RequestMethod.GET)
	public void getPicture(
			@RequestParam(required = true) Long id,
			@RequestParam(required = false, defaultValue = "0") Integer isThumb,
			HttpServletResponse response) {
		String path = servletContext.getRealPath("/") + File.separator
				+ Constants.PICTURE_ROOT_PATH;
		/* 验证用户 */
		User user = null;
		try {
			user = userBo.findByUserIdWithDetail(id, Constants.USER_LOAD_PHOTO);
		} catch (Exception e) {
			return;
		}

		if (user != null) {
			/* 返回原图或者小图 */
			if (isThumb == null || isThumb != 1) {
				path += File.separator + Constants.RIGIN_PICTURE_PATH;
			} else {
				path += File.separator + Constants.THUMB_PICTURE_PATH;
			}
			if (user.getPic() == null) {
				return;
			}
			File file = new File(path + File.separator
					+ user.getPic().getFilename());
			if (file.exists()) {
				try {
					FileInputStream is = new FileInputStream(file);
					IOUtils.copy(is, response.getOutputStream());
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	@RequestMapping(value = { "/resetPassword**" }, method = RequestMethod.GET)
	public LoginResponse resetPassword(
			@RequestParam(required = true) String nickorphone,
			@RequestParam(required = true) String newPassword,
			@RequestParam(required = true) String code) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByNickOrPhone(nickorphone);
		} catch (Exception e) {
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null)
			return new LoginResponse(Status.ERR_USER_NOT_FOUND);

		String phoneNum = user.getPhone_number();

		ValidationCode validationCode = null;
		try {
			validationCode = validationCodeBo.findByPhoneNum(phoneNum);
		} catch (Exception e) {
			return new LoginResponse(Status.ERR_GENERIC);
		}

		if (validationCode == null) {
			// no validation code found, notify to validate code first
			return new LoginResponse(Status.ERR_WRONG_VALIDATION_CODE);
		}

		// code found, check status
		if (validationCode.getStatus() == ValidationCodeStatus.PASSWD_RESET
				.getValue()) {
			// valid status
			if (StringUtils.equals(validationCode.getCode(), code)) {
				// matching code, clear validatino status
				validationCode.setStatus(ValidationCodeStatus.INVALID);
				validationCodeBo.update(validationCode);

				// reset password
				user.setPassword(newPassword);
				// get user new token
				user.setToken(RandomStringUtils
						.randomAlphanumeric(Constants.TOKEN_LENGTH));
				userBo.update(user);
				return new LoginResponse(Status.OK, user.getId(),
						user.getToken());

			}
		}
		return new LoginResponse(Status.ERR_PHONE_VALIDATION_FAIL);

	}

	@Override
	@RequestMapping(value = { "/blacklist**" }, method = RequestMethod.GET)
	public BlacklistResponse blacklist(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_BLACKLIST);
		} catch (UserNotFoundException e) {
			return new BlacklistResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new BlacklistResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new BlacklistResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		BlacklistResponse response = new BlacklistResponse(Status.OK);

		Collection<User> others = user.getBlacklist();

		for (User other : others) {
			response.ids.add(other.getId());
		}

		return response;
	}

	@Override
	@Transactional
	@RequestMapping(value = { "/block**" }, method = RequestMethod.GET)
	public StatusResponse block(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long target) {
		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_BLACKLIST);
		} catch (UserNotFoundException e) {
			return new BlacklistResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new BlacklistResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new BlacklistResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		StatusResponse response = new StatusResponse(Status.OK);

		User other = null;

		try {
			other = userBo.findByUserId(target);
		} catch (Exception e) {
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		if (other == null) {
			response.setStatus(Status.ERR_USER_NOT_FOUND);
			return response;
		}

		user.getBlacklist().add(other);

		userBo.update(user);

		return response;
	}

	@Override
	@RequestMapping(value = { "/unblock**" }, method = RequestMethod.GET)
	public StatusResponse unblock(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long target) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_BLACKLIST);
		} catch (UserNotFoundException e) {
			return new BlacklistResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new BlacklistResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new BlacklistResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		StatusResponse response = new StatusResponse(Status.OK);

		User other = null;

		try {
			other = userBo.findByUserId(target);
		} catch (Exception e) {
			response.setStatus(Status.ERR_USER_NOT_FOUND);
			return response;
		}

		Collection<User> others = user.getBlacklist();

		boolean removed = false;

		Iterator<User> otherIterator = others.iterator();
		while (otherIterator.hasNext()) {
			User blocked = otherIterator.next();
			if (blocked.getId() == target) {
				removed = true;
				otherIterator.remove();
			}
		}

		if (removed) {
			userBo.update(user);
		} else {
			response.setStatus(Status.ERR_NOT_BLACKLISTED);
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/complain**" }, method = RequestMethod.GET)
	public StatusResponse complain(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long target,
			@RequestParam(required = true) String reason) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new BlacklistResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new BlacklistResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new BlacklistResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		StatusResponse response = new StatusResponse(Status.OK);

		User other = null;

		try {
			other = userBo.findByUserId(target);
		} catch (Exception e) {
			response.setStatus(Status.ERR_USER_NOT_FOUND);
			return response;
		}

		Complaint complaint = new Complaint();
		complaint.setOwner(user);
		complaint.setTarget(other);
		complaint.setReason(reason);
		complaint.setCreate_time(new Date());

		try {
			complaintBo.save(complaint);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/setTags**" }, method = RequestMethod.GET)
	public StatusResponse setTags(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) String tags) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new StatusResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new StatusResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new StatusResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		StatusResponse response = new StatusResponse(Status.OK);

		Set<Tag> newTags = Sets.newHashSet();

		if (tags.length() > 0) {
			for (String tag : tags.split(",")) {
				Tag newTag;
				newTag = tagBo.findByTagName(tag);
				if (newTag == null) {
					newTag = new Tag(tag);
				}
				newTags.add(newTag);
			}
		}

		user.setTags(newTags);

		try {
			userBo.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/getLikeUsers**" }, method = RequestMethod.GET)
	public LikeUsersResponse getLikeUsers(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_LIKES);
		} catch (UserNotFoundException e) {
			return new LikeUsersResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new LikeUsersResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new LikeUsersResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		LikeUsersResponse response = new LikeUsersResponse(Status.OK);

		for (User other : user.getLikes()) {
			response.ids.add(other.getId());
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/visitors**" }, method = RequestMethod.GET)
	public ViewersResponse getViewers(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUserWithDetail(id, token,
					Constants.USER_LOAD_VIEWERS);
		} catch (UserNotFoundException e) {
			return new ViewersResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new ViewersResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new ViewersResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		ViewersResponse response = new ViewersResponse(Status.OK);

		for (User other : user.getViewers()) {
			response.ids.add(other.getId());
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/like**" }, method = RequestMethod.GET)
	public StatusResponse like(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long target) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new StatusResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new StatusResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new StatusResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		User theTarget = null;
		/* targetId==null表示查看自己信息，否则为查看别人信息 */
		if (target == null || target == user.getId()) {
			return new StatusResponse(Status.ERR_THAT_IS_SO_PATHETIC);
		} else {
			try {
				theTarget = userBo.findByUserIdWithDetail(target,
						Constants.USER_LOAD_LIKES);
			} catch (Exception e) {
				UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
			}
			if (theTarget == null)
				UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
			boolean added = theTarget.getLikes().add(user);
			if (added) {
				userBo.update(theTarget);
			}
			return new StatusResponse(Status.OK);
		}
	}

	@Override
	@RequestMapping(value = { "/cancelLike**" }, method = RequestMethod.GET)
	public StatusResponse cancelLike(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long target) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new StatusResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new StatusResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new StatusResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		User theTarget = null;
		/* targetId==null表示查看自己信息，否则为查看别人信息 */
		if (target == null || target == user.getId()) {
			return new StatusResponse(Status.ERR_THAT_IS_SO_PATHETIC);
		} else {
			try {
				theTarget = userBo.findByUserIdWithDetail(target,
						Constants.USER_LOAD_LIKES);
			} catch (Exception e) {
				UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
			}
			if (theTarget == null)
				UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
			boolean removed = theTarget.getLikes().remove(user);
			if (removed) {
				userBo.update(theTarget);
			}
			return new StatusResponse(Status.OK);
		}
	}

	@Override
	@RequestMapping(value = { "/report**" }, method = RequestMethod.GET)
	public StatusResponse report(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) String content) {

		User user = null;
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new BlacklistResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new BlacklistResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new BlacklistResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		StatusResponse response = new StatusResponse(Status.OK);

		Report report = new Report();
		report.setOwner(user);
		report.setContent(content);
		report.setCreate_time(new Date());
		report.setStatus(Constants.REPORT_STATUS_NEW);
		try {
			reportBo.save(report);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
		}
		return response;
	}

}

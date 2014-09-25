/**
 * 
 */
package tokenTest.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Constants;
import tokenTest.Util.SMSUtil;
import tokenTest.Util.Status;
import tokenTest.bo.PictureBo;
import tokenTest.bo.UserBo;
import tokenTest.bo.ValidationCodeBo;
import tokenTest.model.Picture;
import tokenTest.model.User;
import tokenTest.model.ValidationCode;
import tokenTest.response.LoginResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.response.ValidatePhoneResponse;
import tokenTest.service.UserServiceInterface;

/**
 * @author pengtao
 * 
 */
@RestController
@RequestMapping("/user")
public class UserServiceImpl implements UserServiceInterface {
	@Autowired
	private UserBo userBo;

	@Autowired
	private PictureBo pictureBo;

	@Autowired
	private ValidationCodeBo validationCodeBo;

	@Autowired
	private ServletContext servletContext = null;

	private static String DIR = "picture";

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
			@RequestParam(required = true) String building,
			@RequestParam(required = true) String phoneNum,
			@RequestParam(required = true) String gender,
			@RequestParam(required = false) Date birthday,
			@RequestParam(required = false) String emailaddress,
			@RequestParam(required = false) String company) {
		/* 必填信息 */
		User user = new User(password, nickName, gender, building, phoneNum);

		/* 非必填信息 */
		if (birthday != null)
			user.setBirthday(birthday);
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);

		/* 生成令牌 */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));

		/* 注册用户 */
		try {
			userBo.save(user);
		} catch (DataIntegrityViolationException e) {
			// 不满足唯一性约束，phonenum或nickname重复,占用token做错误信息。
			return new LoginResponse(Status.ERR_GENERIC, "phonenum或nickname重复");
		}
		return new LoginResponse(Status.OK, user.getToken());
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
			user = userBo.findByUserNickName(nickorphone);
		} catch (Exception e) {
			// 不知道啥错
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "服务器不可用");
		}

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
			return new LoginResponse(Status.ERR_MAX_LOGIN_ATTEMPTS,
					"密码输入错误过多，请重置密码");
		}

		/* 用昵称查找用户不存在 */

		if (user == null || !password.equals(user.getPassword()))
			return new LoginResponse(Status.ERR_USER_NOT_FOUND, "用户不存在或者密码错误");

		/* 生产、更新令牌 */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		user.setLogin_attempts(0); // clear login attempts
		try {
			userBo.update(user);
		} catch (Exception e) {
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "服务器不可用");
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
	public Enum<Status> userLogout(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// 不管什么错，用户不用知道。
			return Status.OK;
		}

		/* 用户不存在或者令牌不正确 ，不管什么错，用户不用知道。 */
		if (user == null || !user.getToken().equals(token))
			return Status.OK;

		/* 清除令牌 */
		user.setToken("");
		try {
			userBo.update(user);
		} catch (Exception e) {
			// 不管什么错，用户不用知道。
			return Status.OK;
		}
		return Status.OK;
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
			@RequestParam(required = false) Long targetId) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null)
			return UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);

		if (!StringUtils.equals(user.getToken(), token)) {
			return UserDetailResponse.getError(Status.ERR_WRONG_TOKEN);
		}

		/* targetId==null表示查看自己信息，否则为查看别人信息 */
		if (targetId == null || targetId == user.getId()) {
			return new UserDetailResponse(user, true);
		} else {
			try {
				user = userBo.findByUserId(targetId);
			} catch (Exception e) {
				UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
			}
			if (user == null)
				UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
			return new UserDetailResponse(user, false);
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
	@RequestMapping(value = { "/updateUserProfile**" }, method = RequestMethod.GET)
	public Enum<Status> updateUserProfile(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) String nickname,
			@RequestParam(required = false) String building,
			@RequestParam(required = false) Date birthday,
			@RequestParam(required = false) String sex,
			@RequestParam(required = false) String emailaddress,
			@RequestParam(required = false) String company) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return Status.ERR_USER_NOT_FOUND;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getToken().equals(token))
			return null;

		/* 设置新的 用户信息 */
		if (nickname != null)
			user.setNickname(nickname);
		if (building != null)
			user.setBuilding(building);
		if (birthday != null)
			user.setBirthday(birthday);
		if (sex != null)
			user.setSex(sex);
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);

		/* 更新用户信息 */
		try {
			userBo.update(user);
		} catch (Exception e) {
			return Status.ERR_GENERIC;
		}
		return Status.OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#changePassWord(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = { "/changePassword**" }, method = RequestMethod.GET)
	public LoginResponse changePassWord(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String oldpassword,
			@RequestParam(required = true) String newpassword) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return new LoginResponse(Status.ERR_USER_NOT_FOUND);
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getPassword().equals(oldpassword))
			return new LoginResponse(Status.ERR_USER_NOT_FOUND);

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
			@RequestParam(required = true) String phoneNum) {

		return _doValidate(phoneNum, ValidationCodeStatus.PASSWD_RESET);

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

		String secret = SMSUtil.doValidate();
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
	public Enum<Status> addPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) MultipartFile picture,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Boolean isProfile) {
		String path = servletContext.getRealPath("/") + File.pathSeparator
				+ DIR;
		User user = null;
		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null)
			return Status.ERR_USER_NOT_FOUND;

		if (!StringUtils.equals(user.getToken(), token)) {
			return Status.ERR_WRONG_TOKEN;
		}

		/* 验证图片 */
		if (!StringUtils.equals(picture.getContentType(), "image/png")) {
			/* 文件格式错误 */
			return Status.ERR_GENERIC;
		}

		/* 保存图片文件 */
		Picture pic = new Picture(new Date(), description);
		try {
			/* 保存文件 */
			pictureBo.save(picture, pic, path);
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}

		/* 将图片添加到用户 */
		if (isProfile != null && isProfile) {
			user.setPic(pic);
		} else {
			Set<Picture> pictures = user.getPicture();
			if (pictures == null)
				pictures = new HashSet<Picture>();
			pictures.add(pic);
			user.setPicture(pictures);
			try {
				userBo.update(user);
			} catch (Exception e) {
				// 保存失败，没做处理
				return Status.SERVICE_NOT_AVAILABLE;
			}
		}
		return Status.OK;
	}

	public Enum<Status> deletePhoto(Long id, String token, Long picId) {
		String path = servletContext.getRealPath("/") + File.pathSeparator
				+ DIR;
		User user = null;
		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null)
			return Status.ERR_USER_NOT_FOUND;

		if (!StringUtils.equals(user.getToken(), token)) {
			return Status.ERR_WRONG_TOKEN;
		}

		/* 查找图片 */
		Picture picture = null;
		try {
			picture = pictureBo.findById(picId);
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}
		if (picture == null) {
			// 图片不存在
			return Status.ERR_GENERIC;
		}

		Set<Picture> pictures = user.getPicture();
		if (pictures == null)
			return Status.ERR_GENERIC;
		pictures.remove(picture);
		user.setPicture(pictures);
		try {
			userBo.update(user);
		} catch (Exception e) {
			// 保存失败，没做处理
			return Status.SERVICE_NOT_AVAILABLE;
		}

		try {
			/* 删除图片数据和文件 */
			pictureBo.delete(picture, path);
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}
		return Status.OK;
	}

	@RequestMapping(value = { "/resetPassword**" }, method = RequestMethod.GET)
	public LoginResponse resetPassword(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String newPassword,
			@RequestParam(required = true) String code) {
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
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
				user.setToken(RandomStringUtils.randomAlphanumeric(30));
				userBo.update(user);
				return new LoginResponse(Status.OK, user.getId(),
						user.getToken());

			}
		}
		return new LoginResponse(Status.ERR_PHONE_VALIDATION_FAIL);

	}
}

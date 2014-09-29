/**
 * 
 */
package tokenTest.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
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
		/* ������Ϣ */
		User user = new User(password, nickName, gender, building, phoneNum);

		/* �Ǳ�����Ϣ */
		if (birthday != null)
			user.setBirthday(birthday);
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);
		
		user.setLogin_attempts(0);

		/* ������� */
		user.setToken(RandomStringUtils
				.randomAlphanumeric(Constants.TOKEN_LENGTH));

		/* ע���û� */
		try {
			userBo.save(user);
		} catch (DataIntegrityViolationException e) {
			// ������Ψһ��Լ��phonenum��nickname�ظ�,ռ��token��������Ϣ��
			return new LoginResponse(Status.ERR_GENERIC, "phonenum��nickname�ظ�");
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
	 * �����û� try { user = userBo.findByUserId(id); } catch (Exception e) { //
	 * TODO: handle exception // ��֪��ɶ�� return new
	 * LoginResponse(Status.SERVICE_NOT_AVAILABLE, "������������"); }
	 * 
	 * �û������ڻ����벻�� if (user == null || !password.equals(user.getPassword()))
	 * return new LoginResponse(Status.ERROR_USER_NOT_FOUND, "�û�����������");
	 * 
	 * ���������� user.setToken(RandomStringUtils.randomAlphanumeric(30)); try {
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

		/* �����û� */
		try {
			user = userBo.findByUserNickName(nickorphone);
		} catch (Exception e) {
			// ��֪��ɶ��
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "������������");
		}

		// find the user first
		if (user == null)
			return new LoginResponse(Status.ERR_USER_NOT_FOUND,
					"�û������ڻ����������");

		int attempts = 0;
		attempts = user.getLogin_attempts();
		// �û��������벻��
		if (user != null && !password.equals(user.getPassword())) {
			// password wrong
			attempts++;
			user.setLogin_attempts(attempts);
			userBo.update(user);
		}

		if (attempts >= Constants.MAX_LOGIN_ATTEMPTS) {
			return new LoginResponse(Status.ERR_MAX_LOGIN_ATTEMPTS,
					"������������࣬����������");
		}

		/* ���ǳƲ����û������� */

		if (user == null || !password.equals(user.getPassword()))
			return new LoginResponse(Status.ERR_USER_NOT_FOUND, "�û������ڻ����������");

		/* ���������� */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		user.setLogin_attempts(0); // clear login attempts
		try {
			userBo.update(user);
		} catch (Exception e) {
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "������������");
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

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// ����ʲô�?�û�����֪����
			return Status.OK;
		}

		/* �û������ڻ������Ʋ���ȷ ������ʲô�?�û�����֪���� */
		if (user == null || !user.getToken().equals(token))
			return Status.OK;

		/* ������� */
		user.setToken("");
		try {
			userBo.update(user);
		} catch (Exception e) {
			// ����ʲô�?�û�����֪����
			return Status.OK;
		}
		return Status.OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#getUserDetail(java.lang.Long,
	 * java.lang.String, java.lang.Long) �����û���Ϣ��queriedidΪ�ձ�ʾ�����Լ�����Ϣ����ʾ���ݸ�ࡣ
	 */
	@RequestMapping(value = { "/getUserDetail**" }, method = RequestMethod.GET)
	public UserDetailResponse getUserDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) Long targetId) {
		User user = null;

		/* �����û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return UserDetailResponse.getError(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return UserDetailResponse.getError(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
		}

		/* targetId==null��ʾ�鿴�Լ���Ϣ������Ϊ�鿴������Ϣ */
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
	 * java.lang.String, java.lang.String, java.lang.String) �޸��û���Ϣ
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

		/* �����û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return Status.ERR_USER_NOT_FOUND;
		} catch (WrongTokenException e) {
			return Status.ERR_WRONG_TOKEN;
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}

		/* �����µ� �û���Ϣ */
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

		/* �����û���Ϣ */
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

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return new LoginResponse(Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD);
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null || !user.getPassword().equals(oldpassword))
			return new LoginResponse(Status.ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD);

		/* �����µ� �û����� */
		user.setPassword(newpassword);
		user.setLogin_attempts(0);

		/* �����û����� */
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
		/* �����֤�� */
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

	/* Ĭ�����Ĳ���ͷ�� */
	@RequestMapping(value = { "/addPhoto**" }, method = RequestMethod.POST)
	public Enum<Status> addPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) MultipartFile file,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Boolean isProfile) {

		/* ��֤ͼƬ */
		if (!StringUtils.equals(file.getContentType(), "image/png")) {
			/* �ļ���ʽ���� */
			return Status.ERR_PIC_FORMAT;
		}

		String path = servletContext.getRealPath("/") + File.separator + DIR;
		User user = null;
		/* ��֤�û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return Status.ERR_USER_NOT_FOUND;
		} catch (WrongTokenException e) {
			return Status.ERR_WRONG_TOKEN;
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}
		
		/* �½�ͼƬ */
		Picture picture = null;
		if (description != null)
			picture = new Picture(new Date(), description);
		else
			picture = new Picture(new Date());

		try {
			if (isProfile != null && isProfile) {
				pictureBo.save(user, file, picture, path, true);
			} else {
				pictureBo.save(user, file, picture, path, false);
			}

		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}
		return Status.OK;
	}

	@RequestMapping(value = { "/deletePhoto**" }, method = RequestMethod.GET)
	public Enum<Status> deletePhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long picId) {
		// TODO Auto-generated method stub
		String path = servletContext.getRealPath("/") + File.separator + DIR;
		User user = null;
		/* ��֤�û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return Status.ERR_USER_NOT_FOUND;
		} catch (WrongTokenException e) {
			return Status.ERR_WRONG_TOKEN;
		} catch (Exception e) {
			return Status.SERVICE_NOT_AVAILABLE;
		}

		/* ����ͼƬ */
		Picture picture = null;
		try {
			picture = pictureBo.findById(picId);
		} catch (Exception e) {
			// TODO: handle exception
			return Status.SERVICE_NOT_AVAILABLE;
		}
		if (picture == null) {
			// ͼƬ������
			return Status.ERR_PIC_NOT_FOUND;
		}

		/* ��ͷ�񣬲���ɾ�� */
		if (user.getPic().equals(picture)) {
			return Status.ERR_CANNOT_DELETE_PROFILE_PHOTO;
		}

		/* �����Լ���ͼƬ������ɾ�� */
		if (!user.getPicture().contains(picture)) {
			return Status.ERR_BANNED;
		}

		/* ɾ��ͼƬ��ݺ��ļ�,ͷ����ɾ�� */
		try {
			/* ɾ��ͼƬ��ݺ��ļ� */
			pictureBo.delete(user, picture, path);
		} catch (Exception e) {
			// TODO: handle exception
			return Status.SERVICE_NOT_AVAILABLE;
		}
		return Status.OK;
	}

	/* Ĭ��ԭͼ */
	@RequestMapping(value = { "/getPhoto**" }, method = RequestMethod.GET)
	public void getPhoto(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long picId,
			@RequestParam(required = false) Integer isThumb,
			HttpServletResponse response) {
		String path = servletContext.getRealPath("/") + File.separator + DIR;
		User user = null;
		/* ��֤�û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return;
			// TODO: handle exception
//			return Status.ERR_USER_NOT_FOUND;
		} catch (WrongTokenException e) {
			return;
			// TODO: handle exception
//			return Status.ERR_WRONG_TOKEN;
		} catch (Exception e) {
			return;
			// TODO: handle exception
//			return Status.SERVICE_NOT_AVAILABLE;
		}
		/* ����ͼƬ */
		Picture picture = null;
		try {
			picture = pictureBo.findById(picId);
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		if (picture == null)
			return;

		File file = new File(path + File.separator + picture.getFilename());
		if (file.exists()) {
			if (isThumb == null || isThumb == 0) {
				/* ����ԭͼ */
				try {
					FileInputStream is = new FileInputStream(file);
					IOUtils.copy(is, response.getOutputStream());
				} catch (Exception e) {
					// TODO: handle exception
					return;
				}
			} else {
				/* ����Сͼ */
				try {
					BufferedImage originalImage = ImageIO
							.read(new FileInputStream(file));
					originalImage = pictureBo.zoomOutImage(originalImage, 100);
					IOUtils.copy((InputStream) ImageIO
							.createImageInputStream(originalImage), response
							.getOutputStream());
				} catch (Exception e) {
					// TODO: handle exception
					return;
				}
			}
		}
	}

	@RequestMapping(value = { "/resetPassword**" }, method = RequestMethod.GET)
	public LoginResponse resetPassword(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String newPassword,
			@RequestParam(required = true) String code) {
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		/* �û������ڻ������Ʋ���ȷ */
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
}

/**
 * 
 */
package tokenTest.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import tokenTest.Util.Status;
import tokenTest.bo.PictureBo;
import tokenTest.bo.UserBo;
import tokenTest.model.Picture;
import tokenTest.model.User;
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
		// TODO Auto-generated method stub
		/* ������Ϣ */
		User user = new User(password, nickName, gender, building, phoneNum);

		/* �Ǳ�����Ϣ */
		if (birthday != null)
			user.setBirthday(birthday);
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);

		/* �������� */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));

		/* ע���û� */
		try {
			userBo.save(user);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			// ������Ψһ��Լ����phonenum��nickname�ظ�,ռ��token��������Ϣ��
			return new LoginResponse(Status.ERROR_GENERIC,
					"phonenum��nickname�ظ�");
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
	 * return new LoginResponse(Status.ERROR_USER_NOT_FOUND, "�û������������");
	 * 
	 * �������������� user.setToken(RandomStringUtils.randomAlphanumeric(30)); try {
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
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserNickName(nickorphone);
		} catch (Exception e) {
			// TODO: handle exception
			// ��֪��ɶ��
			return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "������������");
		}

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
			return new LoginResponse(Status.ERROR_MAX_LOGIN_ATTEMPTS,
					"�������������࣬����������");
		}

		/* ���ǳƲ����û������� */
		if (user == null || !password.equals(user.getPassword())) {
			try {
				user = userBo.findByUserPhoneNum(nickorphone);
			} catch (Exception e) {
				// TODO: handle exception
				// ��֪��ɶ��
				return new LoginResponse(Status.SERVICE_NOT_AVAILABLE, "������������");
			}

			if (user == null || !password.equals(user.getPassword()))
				return new LoginResponse(Status.ERROR_USER_NOT_FOUND,
						"�û������ڻ����������");
		}

		/* �������������� */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		user.setLogin_attempts(0); // clear login attempts
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
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
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			// ����ʲô���û�����֪����
			return Status.OK;
		}

		/* �û������ڻ������Ʋ���ȷ ������ʲô���û�����֪���� */
		if (user == null || !user.getToken().equals(token))
			return Status.OK;

		/* ������� */
		user.setToken("");
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			// ����ʲô���û�����֪����
			return Status.OK;
		}
		return Status.OK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#getUserDetail(java.lang.Long,
	 * java.lang.String, java.lang.Long) �����û���Ϣ��queriedidΪ�ձ�ʾ�����Լ�����Ϣ����ʾ���ݸ��ࡣ
	 */
	@RequestMapping(value = { "/getUserDetail**" }, method = RequestMethod.GET)
	public UserDetailResponse getUserDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) Long targetId) {
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null)
			return UserDetailResponse.getError(Status.ERROR_USER_NOT_FOUND);

		if (!StringUtils.equals(user.getToken(), token)) {
			return UserDetailResponse.getError(Status.ERROR_WRONG_TOKEN);
		}

		/* targetId==null��ʾ�鿴�Լ���Ϣ������Ϊ�鿴������Ϣ */
		if (targetId == null) {
			return new UserDetailResponse(user, true);
		} else {
			try {
				user = userBo.findByUserId(targetId);
			} catch (Exception e) {
				UserDetailResponse.getError(Status.SERVICE_NOT_AVAILABLE);
			}
			if (user == null)
				UserDetailResponse.getError(Status.ERROR_USER_NOT_FOUND);
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
	public String updateUserProfile(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) String nickname,
			@RequestParam(required = false) String building,
			@RequestParam(required = false) Date birthday,
			@RequestParam(required = false) String sex,
			@RequestParam(required = false) String emailaddress,
			@RequestParam(required = false) String company) {
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null || !user.getToken().equals(token))
			return null;

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
			// TODO: handle exception
			return null;
		}
		return "Successed";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#changePassWord(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = { "/changePassWord**" }, method = RequestMethod.GET)
	public LoginResponse changePassWord(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String oldpassword,
			@RequestParam(required = true) String newpassword) {
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null || !user.getPassword().equals(oldpassword))
			return null;

		/* �����µ� �û����� */
		user.setPassword(newpassword);
		user.setLogin_attempts(0);

		/* �����û����� */
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return new LoginResponse(Status.OK);
	}

	@RequestMapping(value = { "/validatePhone**" }, method = RequestMethod.GET)
	public ValidatePhoneResponse validatePhone(
			@RequestParam(required = true) String phoneNum) {
		// TODO Auto-generated method stub
		/*
		 * User user = null; �����û� try { user =
		 * userBo.findByUserPhoneNum(phoneNum); } catch (Exception e) { // TODO:
		 * handle exception // ��֪��ɶ�� return new
		 * ValidatePhoneResponse(Status.SERVICE_NOT_AVAILABLE, "������������"); }
		 * 
		 * �ֻ������Ѿ���ע��,�������� if (user != null) { // TODO }
		 */

		/* �����֤�� */
		String code = RandomStringUtils.randomNumeric(6);
		// TODO ������֤��

		return new ValidatePhoneResponse(Status.OK, code);
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
			return Status.ERROR_BANNED;
		}

		String path = servletContext.getRealPath("/") + File.separator + DIR;
		User user = null;
		/* ��֤�û� */
		try {
			user = userBo.validateUser(id, token);
		} catch (Exception e) {
			// TODO: handle exception
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null)
			return Status.ERROR_USER_NOT_FOUND;

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
			// TODO: handle exception
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
		} catch (Exception e) {
			// TODO: handle exception
		}

		/* �û������ڻ������Ʋ���ȷ */
		if (user == null)
			return Status.ERROR_USER_NOT_FOUND;

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
			return Status.ERROR_PIC_NOT_FOUND;
		}

		/* ��ͷ�񣬲���ɾ�� */
		if (user.getPic().equals(picture)) {
			return Status.ERROR_CANNOT_DELETE_PROFILE_PHOTO;
		}

		/* �����Լ���ͼƬ������ɾ�� */
		if (!user.getPicture().contains(picture)) {
			return Status.ERROR_BANNED;
		}

		/* ɾ��ͼƬ���ݺ��ļ�,ͷ����ɾ�� */
		try {
			/* ɾ��ͼƬ���ݺ��ļ� */
			pictureBo.delete(user, picture, path);
		} catch (Exception e) {
			// TODO: handle exception
			return Status.SERVICE_NOT_AVAILABLE;
		}
		return Status.OK;
	}

	/* Ĭ��ԭͼ */
	@RequestMapping(value = { "/getPhoto**" }, method = RequestMethod.GET)
	public void getPhoto(@RequestParam(required = true) Long picId,
			@RequestParam(required = false) Integer size,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String path = servletContext.getRealPath("/") + File.separator + DIR;

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
			if (size == null || size == 0) {
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
}

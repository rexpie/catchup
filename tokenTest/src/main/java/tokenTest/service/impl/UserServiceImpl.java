/**
 * 
 */
package tokenTest.service.impl;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.bo.UserBo;
import tokenTest.model.User;
import tokenTest.response.UserDetailResponse;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * tokenTest.service.UserServiceInterface#userRegister(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Date,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = { "/userRegister**" }, method = RequestMethod.GET)
	public String userRegister(@RequestParam(required = true) String nickname,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String building,
			@RequestParam(required = true) String phonenumber,
			@RequestParam(required = true) Date birthday,
			@RequestParam(required = true) String sex,
			@RequestParam(required = false) String emailaddress,
			@RequestParam(required = false) String company) {
		// TODO Auto-generated method stub
		/* ������Ϣ */
		User user = new User(password, nickname, sex, building, birthday,
				phonenumber);

		/* �Ǳ�����Ϣ */
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);

		/* �������� */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));

		/* ע���û� */
		try {
			userBo.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return user.getToken();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#userLogin(java.lang.Long,
	 * java.lang.String)
	 */
	@RequestMapping(value = { "/userLogin**" }, method = RequestMethod.GET)
	public String userLogin(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String password) {
		// TODO Auto-generated method stub
		User user = null;

		/* �����û� */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* �û������ڻ����벻�� */
		if (user == null || !password.equals(user.getPassword()))
			return null;

		/* �������������� */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return user.getToken();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tokenTest.service.UserServiceInterface#userLogout(java.lang.Long,
	 * java.lang.String)
	 */
	@RequestMapping(value = { "/userLogout**" }, method = RequestMethod.GET)
	public String userLogout(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token) {
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

		/* ������� */
		user.setToken("");
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
	 * @see tokenTest.service.UserServiceInterface#getUserDetail(java.lang.Long,
	 * java.lang.String, java.lang.Long) �����û���Ϣ��queriedidΪ�ձ�ʾ�����Լ�����Ϣ����ʾ���ݸ��ࡣ
	 */
	@RequestMapping(value = { "/getUserDetail**" }, method = RequestMethod.GET)
	public UserDetailResponse getUserDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) Long queriedid) {
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

		/* queriedid==null��ʾ�鿴�Լ���Ϣ������Ϊ�鿴������Ϣ */
		if (queriedid == null) {
			return new UserDetailResponse(user);
		} else {
			try {
				user = userBo.findByUserId(queriedid);
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
			if (user == null)
				return null;
			return new UserDetailResponse(user.getNickname(), user.getSex(),
					user.getBuilding(), user.getZan_count(), user.getBirthday());
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
	public String changePassWord(@RequestParam(required = true) Long id,
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

		/* �����û����� */
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return "Successed";
	}

}

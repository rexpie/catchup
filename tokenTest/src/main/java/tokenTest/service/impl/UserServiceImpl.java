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
		/* 必填信息 */
		User user = new User(password, nickname, sex, building, birthday,
				phonenumber);

		/* 非必填信息 */
		if (emailaddress != null)
			user.setEmail_address(emailaddress);
		if (company != null)
			user.setCompany(company);

		/* 生成令牌 */
		user.setToken(RandomStringUtils.randomAlphanumeric(30));

		/* 注册用户 */
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

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* 用户不存在或密码不对 */
		if (user == null || !password.equals(user.getPassword()))
			return null;

		/* 生产、更新令牌 */
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

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getToken().equals(token))
			return null;

		/* 清除令牌 */
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
	 * java.lang.String, java.lang.Long) 查找用户信息，queriedid为空表示查找自己的信息，显示内容更多。
	 */
	@RequestMapping(value = { "/getUserDetail**" }, method = RequestMethod.GET)
	public UserDetailResponse getUserDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false) Long queriedid) {
		// TODO Auto-generated method stub
		User user = null;

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getToken().equals(token))
			return null;

		/* queriedid==null表示查看自己信息，否则为查看别人信息 */
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
	 * java.lang.String, java.lang.String, java.lang.String) 修改用户信息
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

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
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

		/* 查找用户 */
		try {
			user = userBo.findByUserId(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		/* 用户不存在或者令牌不正确 */
		if (user == null || !user.getPassword().equals(oldpassword))
			return null;

		/* 设置新的 用户密码 */
		user.setPassword(newpassword);

		/* 更新用户密码 */
		try {
			userBo.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

		return "Successed";
	}

}

package tokenTest.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.bo.UserBo;
import tokenTest.model.User;
import tokenTest.response.LoginResponse;

@RestController
public class TokenController {
	@Autowired
	private UserBo userBo;

	@RequestMapping(value = { "/login**" }, method = RequestMethod.GET)
	public LoginResponse login(@RequestParam(required = true) String nickName,
			@RequestParam(required = true) String passWord) {
		User user = null;
		try {
			user = userBo.findByUserNickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		if (user==null)return null;
		if(!user.getPassword().equals(passWord))return null;
		user.setToken(RandomStringUtils.randomAlphanumeric(30));
		userBo.update(user);
		return new LoginResponse(user.getToken());
	}

	@RequestMapping(value = { "/userDetail**" }, method = RequestMethod.GET)
	public String userDetail(@RequestParam(required = true) String nickName,
			@RequestParam(required = true) String token, @RequestParam(required = false) String queriedNickName) {
		User user=null;
		try {
			user = userBo.findByUserNickName(nickName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		/*token不对，非法用户*/
		if(user==null || !user.getToken().equals(token))return null;
		if(queriedNickName==null || nickName.equals(queriedNickName))return user.getEmail_address();
		
		try {
			user = userBo.findByUserNickName(queriedNickName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		/*查找的用户不存在*/
		if(user==null)return null;
		return user.getEmail_address();
	}
}

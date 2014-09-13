package tokenTest.service;

import java.util.Date;

import tokenTest.response.UserDetailResponse;

public interface UserServiceInterface {
	String userRegister(String nickname, String password,
			String building, String phonenumber, Date birthday, String sex,
			String emailaddress, String company);

	String userLogin(Long id, String password);

	String userLogout(Long id, String token);

	UserDetailResponse getUserDetail(Long id, String token,
			Long queriedid);

	String updateUserProfile(Long id, String token, String nickname,
			String building, Date birthday, String sex, String emailaddress,
			String company);

	String changePassWord(Long id, String oldpassword, String newpassword);
}

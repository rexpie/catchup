package tokenTest.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.Util.Status;
import tokenTest.response.LoginResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.response.ValidatePhoneResponse;

public interface UserServiceInterface {
	LoginResponse userRegister(String nickName, String password,
			String building, String phoneNum, String gender, Date birthday,
			String emailaddress, String company);

	/* LoginResponse userLogin(Long id, String password); */

	LoginResponse userLogin(String nickorphone, String password);

	ValidatePhoneResponse validatePhone(String phoneNum);

	Enum<Status> userLogout(Long id, String token);

	UserDetailResponse getUserDetail(Long id, String token, Long targetId);

	Enum<Status> addPhoto(Long id, String token, MultipartFile file,
			String description, Boolean isProfile);

	Enum<Status> deletePhoto(Long id, String token, Long picId);

	void getPhoto(Long id, String token, Long picId, Integer isThumb, HttpServletResponse response);

	// 后面的还没改
	Enum<Status> updateUserProfile(Long id, String token, String nickname,
			String building, Date birthday, String sex, String emailaddress,
			String company);

	LoginResponse changePassWord(Long id, String oldpassword, String newpassword);
	
	
	LoginResponse resetPassword(Long id, String newPassword, String validationCode);

}

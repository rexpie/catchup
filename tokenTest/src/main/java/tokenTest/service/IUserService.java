package tokenTest.service;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import tokenTest.response.BlacklistResponse;
import tokenTest.response.LikeUsersResponse;
import tokenTest.response.LoginResponse;
import tokenTest.response.PhotoListResponse;
import tokenTest.response.PicResponse;
import tokenTest.response.StatusResponse;
import tokenTest.response.UserDetailResponse;
import tokenTest.response.ValidatePhoneResponse;
import tokenTest.response.ViewersResponse;

public interface IUserService {
	LoginResponse userRegister(String nickName, String password,
			String phoneNum, String code, String building, String gender, Date birthday,
			String company);

	/* LoginResponse userLogin(Long id, String password); */

	LoginResponse userLogin(String nickorphone, String password);

	ValidatePhoneResponse validatePhone(String phoneNum);

	ValidatePhoneResponse validateReset(String phoneNum);

	LoginResponse userLogout(Long id, String token);

	UserDetailResponse getUserDetail(Long id, String token, Long targetId);

	PicResponse addPhoto(Long id, String token, MultipartFile file,
			String description, Boolean isProfile);

	PicResponse deletePhoto(Long id, String token, String picId);

	void getPhoto(Long id, String token, String picId, Integer isThumb,
			HttpServletResponse response);

	/* 获取头像 */
	void getPicture(Long id, Integer isThumb, HttpServletResponse response);

	LoginResponse changePassword(Long id, String oldpassword, String newpassword);

	LoginResponse resetPassword(String nickorphone, String newPassword,
			String validationCode);

	BlacklistResponse blacklist(Long id, String token);

	StatusResponse block(Long id, String token, Long target);

	StatusResponse unblock(Long id, String token, Long target);

	StatusResponse complain(Long id, String token, Long target, String reason);

	StatusResponse setTags(Long id, String token, String tags);

	LikeUsersResponse getLikeUsers(Long id, String token);

	ViewersResponse getViewers(Long id, String token);

	StatusResponse like(Long id, String token, Long targetId);

	StatusResponse report(Long id, String token, String reason);

	PhotoListResponse listPhoto(Long id, String token);

	StatusResponse updateUserProfile(Long id, String token, String nickname,
			String building, String birthday, String sex, String job,
			String industry, String company);

	StatusResponse cancelLike(Long id, String token, Long targetId);

}

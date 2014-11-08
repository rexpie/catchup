package tokenTest.bo;

import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.User;

public interface UserBo {
	void save(User user);

	void update(User user);

	void delete(User user);

	void merge(User user);

	User validateUser(Long id, String token) throws UserNotFoundException, WrongTokenException;

	User findByUserNickName(String nickName);

	User findByUserPhoneNum(String phoneNum);

	User findByUserId(Long id);
	
	User findByNickOrPhone(String nickorphone);

	User validateUserWithDetail(Long id, String token);

	User validateUserWithDetail(Long id, String token, int loadFlags);

	User findByUserNickNameWithDetail(String nickName, int flag);

	User findByUserIdWithDetail(Long id, int loadFlags);

	User findByUserPhoneNumWithDetail(String phoneNum, int loadFlags);

	User findByNickOrPhoneWithDetail(String nickorphone, int loadFlags);

}

package tokenTest.bo;

import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.User;

public interface UserBo {
	void save(User user);

	void update(User user);

	void delete(User user);

	User validateUser(Long id, String token) throws UserNotFoundException, WrongTokenException;

	User findByUserNickName(String nickName);

	User findByUserPhoneNum(String phoneNum);

	User findByUserId(Long id);
}

package tokenTest.bo;

import tokenTest.model.User;

public interface UserBo {
void save(User user);
	
	void update(User user);
	
	void delete(User user);
	
	User findByUserNickName(String nickName);
	
	User findByUserId(Long id);
}

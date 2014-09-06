package org.test1.bo;

import org.test1.model.User;

public interface UserBo {
void save(User user);
	
	void update(User user);
	
	void delete(User user);
	
	User findByUserNickName(String nickName);
}

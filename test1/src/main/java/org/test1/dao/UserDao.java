package org.test1.dao;

import org.test1.model.User;

public interface UserDao {
	void save(User user);

	void update(User user);

	void delete(User user);

	User findByUserNickName(String nickName);
}

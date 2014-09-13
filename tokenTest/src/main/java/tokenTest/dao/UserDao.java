package tokenTest.dao;

import tokenTest.model.User;

public interface UserDao {
	void save(User user);

	void update(User user);

	void delete(User user);

	User findByUserNickName(String nickName);
	
	User findById(Long id);
}

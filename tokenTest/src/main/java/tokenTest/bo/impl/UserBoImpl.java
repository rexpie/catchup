package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.UserBo;
import tokenTest.dao.UserDao;
import tokenTest.model.User;

@Service("userBo")
public class UserBoImpl implements UserBo {
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Transactional
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Transactional
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Transactional
	public User findByUserNickName(String nickName) {
		// TODO Auto-generated method stub
		return userDao.findByUserNickName(nickName);
	}

	@Transactional
	public User findByUserId(Long id) {
		// TODO Auto-generated method stub
		return userDao.findById(id);
	}

}

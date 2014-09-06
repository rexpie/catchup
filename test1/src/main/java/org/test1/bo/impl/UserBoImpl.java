package org.test1.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.UserBo;
import org.test1.dao.MeetingDao;
import org.test1.dao.UserDao;
import org.test1.model.User;

@Service("userBo")
public class UserBoImpl implements UserBo {
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public User findByUserNickName(String nickName) {
		// TODO Auto-generated method stub
		return userDao.findByUserNickName(nickName);
	}

}

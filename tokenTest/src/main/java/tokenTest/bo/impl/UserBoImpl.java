package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.UserBo;
import tokenTest.dao.UserDao;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.User;

import org.apache.commons.lang3.StringUtils;

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

	@Transactional
	public User findByUserPhoneNum(String phoneNum) {
		// TODO Auto-generated method stub
		return userDao.findByUserPhoneNum(phoneNum);
	}

	@Transactional
	public User validateUser(Long id, String token)
			throws UserNotFoundException, WrongTokenException {
		// TODO Auto-generated method stub
		User user = findByUserId(id);
		if (user == null)
			throw new UserNotFoundException();

		if (!StringUtils.equals(user.getToken(), token))
			throw new WrongTokenException();

		return user;
	}

	@Transactional
	public User findByNickOrPhone(String nickorphone) {
		User user = null;
		if (StringUtils.containsOnly(nickorphone, "0123456789")) {
			user = findByUserPhoneNum(nickorphone);
			if (user == null) {
				user = findByUserNickName(nickorphone);
			}
		} else {
			user = findByUserNickName(nickorphone);
			if (user == null) {
				user = findByUserPhoneNum(nickorphone);

			}

		}
		return user;
	}

}

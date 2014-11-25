package tokenTest.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.Util.Constants;
import tokenTest.bo.UserBo;
import tokenTest.dao.UserDao;
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.User;

@Service("userBo")
public class UserBoImpl implements UserBo {
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Transactional
	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Transactional
	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Transactional
	@Override
	public void merge(User user) {
		userDao.merge(user);
	}

	@Transactional
	@Override
	public User findByUserNickName(String nickName) {
		return userDao.findByUserNickName(nickName);
	}
	
	@Transactional
	@Override
	public User findByUserNickNameWithDetail(String nickName, int loadFlags) {
		User user = userDao.findByUserNickName(nickName);
		loadDetail(loadFlags, user);
		return user;
	}

	@Transactional
	@Override
	public User findByUserId(Long id) {
		return userDao.findById(id);
	}

	@Transactional
	@Override
	public User findByUserIdWithDetail(Long id, int loadFlags) {
		User user = userDao.findById(id);
		loadDetail(loadFlags, user);
		return user;
	}
	
	@Transactional
	@Override
	public User findByUserPhoneNum(String phoneNum) {
		return userDao.findByUserPhoneNum(phoneNum);
	}
	
	@Transactional
	@Override
	public User findByUserPhoneNumWithDetail(String phoneNum, int loadFlags) {
		User user = userDao.findByUserPhoneNum(phoneNum);
		loadDetail(loadFlags, user);
		return user;
	}

	@Transactional
	@Override
	public User validateUser(Long id, String token)
			throws UserNotFoundException, WrongTokenException {
		User user = findByUserId(id);
		if (user == null)
			throw new UserNotFoundException();

		if (!StringUtils.equals(user.getToken(), token))
			throw new WrongTokenException();

		return user;
	}

	@Override
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

	@Override
	@Transactional
	public User findByNickOrPhoneWithDetail(String nickorphone, int loadFlags) {
		User user = findByNickOrPhone(nickorphone);
		loadDetail(loadFlags, user);
		return user;
	}
	
	@Override
	@Transactional
	public User validateUserWithDetail(Long id, String token, int loadFlags) {
		User user = validateUser(id, token);
		loadDetail(loadFlags, user);
		return user;
	}

	@Transactional
	private void loadDetail(int loadFlags, User user) {
		if (user != null) {
			if ((Constants.USER_LOAD_TAGS & loadFlags) > 0)
				Hibernate.initialize(user.getTags());
			if ((Constants.USER_LOAD_BLACKLIST & loadFlags) > 0)
				Hibernate.initialize(user.getBlacklist());
			if ((Constants.USER_LOAD_PICTURES & loadFlags) > 0)
				Hibernate.initialize(user.getPicture());
			if ((Constants.USER_LOAD_VIEWERS & loadFlags) > 0)
				Hibernate.initialize(user.getViewers());
			if ((Constants.USER_LOAD_LIKES & loadFlags) > 0)
				Hibernate.initialize(user.getLikes());
			if ((Constants.USER_LOAD_PHOTO & loadFlags) > 0)
				Hibernate.initialize(user.getPic());
			if ((Constants.USER_LOAD_BIZCARD & loadFlags) > 0)
				Hibernate.initialize(user.getBizCard());
		}
	}

	@Override
	@Transactional
	public User validateUserWithDetail(Long id, String token) {
		return validateUserWithDetail(id, token, Constants.USER_LOAD_ALL);
	}
	
	@Override
	@Transactional
	public List<User> getBizcardValidations(){
		return userDao.getBizcardValidations();
	}

}

package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.UserDao;
import tokenTest.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(User user) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(user);
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(user);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(user);
	}

	public User findByUserNickName(String nickName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User where nickname= :nickname");
		query.setString("nickname", nickName);
		List list = query.list();
		if (list.size() > 0)
			return (User) list.get(0);
		else
			return null;
	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User where id= :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (User) list.get(0);
		else
			return null;
	}

	public User findByUserPhoneNum(String phoneNum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User where phone_number= :phone_number");
		query.setString("phone_number", phoneNum);
		List list = query.list();
		if (list.size() > 0)
			return (User) list.get(0);
		else
			return null;
	}

}

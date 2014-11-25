package tokenTest.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.Util.Constants;
import tokenTest.dao.UserDao;
import tokenTest.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	public void delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
	}

	@Override
	public void merge(User user) {
		sessionFactory.getCurrentSession().merge(user);
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
		Query query = session.createQuery("from User where id= :id");
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

	public Set<User> getBlackList(Long id) {
		// TODO Auto-generated method stub
		User user = (User)sessionFactory.getCurrentSession().load(User.class, id);
		Set<User> blackList = user.getBlacklist();
		return blackList;
	}

	@Override
	public List<User> getBizcardValidations(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User as u inner join fetch u.bizCard "
						+ "where u.bizCardValidated is NULL "
						+ "order by u.bizCard.create_time");
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return query.list();
	}

	@Override
	public List<User> getBizcardRejects(int pagenum){
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User as u inner join fetch u.bizCard "
						+ "where u.bizCardValidated is 0 "
						+ "order by u.bizCard.create_time");
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return query.list();
	}

}

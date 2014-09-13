package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.FriendApplyDao;
import tokenTest.model.FriendApply;
import tokenTest.model.User;

@Repository("friendApplyDao")
public class FriendApplyDaoImpl implements FriendApplyDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(FriendApply friendApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(friendApply);
	}
	
	public void update(FriendApply friendApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(friendApply);
	}

	public void delete(FriendApply friendApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(friendApply);
	}

	public List<FriendApply> findApplyByUser(User toUser) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("from FriendApply as f where f.toUser= :toUser");
		query.setEntity("toUser", toUser);
		List<FriendApply> list = query.list();
		return list;
	}

	

}

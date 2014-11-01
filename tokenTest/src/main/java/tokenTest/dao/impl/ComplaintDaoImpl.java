package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.ComplaintDao;
import tokenTest.model.Complaint;
import tokenTest.model.User;

@Repository("complaintDao")
public class ComplaintDaoImpl implements ComplaintDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Complaint c) {
		sessionFactory.getCurrentSession().save(c);
	}

	@Override
	public void update(Complaint c) {
		sessionFactory.getCurrentSession().update(c);
	}

	@Override
	public void delete(Complaint c) {
		sessionFactory.getCurrentSession().delete(c);
	}

	@Override
	public List<Complaint> findComplaintByOwner(User owner) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Complaint as c where c.owner = :owner");
		query.setEntity("owner", owner);
		List<Complaint> list = query.list();
		return list;
	}

	@Override
	public List<Complaint> findComplaintByTarget(User target) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Complaint as c where c.target = :target");
		query.setEntity("target", target);
		List<Complaint> list = query.list();
		return list;
	}

}

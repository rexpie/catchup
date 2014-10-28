package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.ReportDao;
import tokenTest.model.Report;
import tokenTest.model.User;

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Report c) {
		sessionFactory.getCurrentSession().save(c);
	}

	@Override
	public void update(Report c) {
		sessionFactory.getCurrentSession().update(c);
	}

	@Override
	public void delete(Report c) {
		sessionFactory.getCurrentSession().delete(c);
	}

	@Override
	public List<Report> findReportByOwner(User owner) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Report as r where r.owner = :owner");
		query.setEntity("owner", owner);
		List<Report> list = query.list();
		return list;
	}

}

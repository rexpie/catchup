package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.Util.Constants;
import tokenTest.dao.EventDao;
import tokenTest.model.Event;
import tokenTest.model.Meeting;
import tokenTest.model.User;

@Repository("eventDao")
public class EventDaoImpl implements EventDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Event e) {
		sessionFactory.getCurrentSession().save(e);

	}

	@Override
	public void update(Event e) {
		sessionFactory.getCurrentSession().update(e);
	}

	@Override
	public void delete(Event e) {
		sessionFactory.getCurrentSession().delete(e);
	}
	
	@Override
	public void merge(Event e){
		sessionFactory.getCurrentSession().merge(e);
	}

	@Override
	public Event findByEventId(Long id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Event where id= :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (Event) list.get(0);
		else
			return null;
	}

	@Override
	public List getEventList(Double longitude, Double latitude,
			Integer pagenum, Integer range) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT e, (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(e.latitude)) * pi()/180 / 2),2) + "
								+ "COS(:ulatitude * pi()/180 ) * COS(abs(e.latitude) * pi()/180) * "
								+ "POWER(SIN((:ulongitude - e.longitude) * pi()/180 / 2), 2))))*1000 as distance "
								+ ", count(e) as count "
								+ "FROM Event as e "
								+ "WHERE (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(e.latitude)) * pi()/180 / 2),2) + COS(:ulatitude * pi()/180 ) * COS(abs(e.latitude) * pi()/180)*POWER(SIN((:ulongitude - e.longitude) * pi()/180 / 2), 2))))*1000 < :urange "
								+ "ORDER BY distance ASC");
		/* 设置参数 */
		query.setDouble("ulongitude", longitude);
		query.setDouble("ulatitude", latitude);
		query.setDouble("urange", range);
		// 分页处理
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return (List) query.list();
	}

	@Override
	public List getEventListByUser(User user, Integer pagenum) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT e , count(e) as count FROM Event as e "
								+ "WHERE :user in elements(e.participants)"
								+ "ORDER BY e.startTime DESC");
		/* 设置参数 */
		query.setEntity("user", user);
		// 分页处理
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return (List) query.list();
	}

}

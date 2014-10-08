package tokenTest.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.Util.Constants;
import tokenTest.dao.MeetingDao;
import tokenTest.model.Meeting;
import tokenTest.model.User;

@Repository("meetingDao")
public class MeetingDaoImpl implements MeetingDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(meeting);
	}

	public void update(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(meeting);
	}

	public void delete(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(meeting);
	}

	public Meeting getMeetingById(Long id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Meeting where id= :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() > 0)
			return (Meeting) list.get(0);
		else
			return null;
	}

	public List getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName) {
		/* 根据经纬度计算距离，6371km为地球半径，结果为m */
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT m, (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(m.shop.latitude)) * pi()/180 / 2),2) +"
								+ "COS(:ulatitude * pi()/180 ) * COS(abs(m.shop.latitude) * pi()/180) *"
								+ "POWER(SIN((:ulongitude - m.shop.longitude) * pi()/180 / 2), 2))))*1000 as distance "
								+ "FROM Meeting as m inner join fetch m.shop inner join fetch m.owner "
								+ "WHERE m.genderConstraint like :ugender AND m.shop.name like :uname AND m.owner.role like :ujob AND (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(m.shop.latitude)) * pi()/180 / 2),2) + COS(:ulatitude * pi()/180 ) * COS(abs(m.shop.latitude) * pi()/180)*POWER(SIN((:ulongitude - m.shop.longitude) * pi()/180 / 2), 2))))*1000 < :urange "
								+ "ORDER BY distance ASC");
		/* 设置参数 */
		query.setDouble("ulongitude", longitude);
		query.setDouble("ulatitude", latitude);
		query.setString("ugender", "%" + gender + "%");
		query.setString("uname", "%" + shopName + "%");
		query.setString("ujob", "%" + job + "%");
		query.setDouble("urange", range);
		// 分页处理
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return (List) query.list();
	}

	public List getMeetingListByUser(User user, Integer pagenum) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT m "
								+ "FROM Meeting as m inner join fetch m.shop inner join fetch m.owner "
								+ "WHERE m.owner =:user "
								+ "ORDER BY m.create_time DESC");
		/* 设置参数 */
		query.setEntity("user", user);
		// 分页处理
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return (List) query.list();
	}

	public List getMeetingListByParticipate(User user, Integer pagenum) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT m "
								+ "FROM Meeting as m inner join fetch m.shop inner join fetch m.owner "
								+ "WHERE :user in elements(m.participator) "
								+ "ORDER BY m.create_time DESC");
		/* 设置参数 */
		query.setEntity("user", user);
		// 分页处理
		query.setFirstResult(pagenum * Constants.NUM_PER_PAGE);
		query.setMaxResults(Constants.NUM_PER_PAGE);
		return (List) query.list();
	}

}

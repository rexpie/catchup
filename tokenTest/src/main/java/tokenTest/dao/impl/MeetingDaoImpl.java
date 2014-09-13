package tokenTest.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.MeetingDao;
import tokenTest.model.Meeting;

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

}

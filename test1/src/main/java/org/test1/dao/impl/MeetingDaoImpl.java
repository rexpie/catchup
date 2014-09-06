package org.test1.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test1.dao.MeetingDao;
import org.test1.model.Meeting;

@Repository("meetingDao")
public class MeetingDaoImpl implements MeetingDao{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(meeting);
	}

	@Override
	public void update(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(meeting);
	}

	@Override
	public void delete(Meeting meeting) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(meeting);
	}

}

package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.MeetingApplyDao;
import tokenTest.model.FriendApply;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;

@Repository("meetingApplyDao")
public class MeetingApplyDaoImpl implements MeetingApplyDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(MeetingApply meetingApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(meetingApply);
	}

	public void update(MeetingApply meetingApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(meetingApply);
	}

	public void delete(MeetingApply meetingApply) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(meetingApply);
	}

	public List<MeetingApply> findApplyByMeeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("from MeetingApply as m where f.toMeeting= :toMeeting");
		query.setEntity("toUser", meeting);
		List<MeetingApply> list = query.list();
		return list;
	}

}

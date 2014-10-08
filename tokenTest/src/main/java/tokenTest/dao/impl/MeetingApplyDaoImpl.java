package tokenTest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tokenTest.dao.MeetingApplyDao;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;
import tokenTest.model.User;

@Repository("meetingApplyDao")
public class MeetingApplyDaoImpl implements MeetingApplyDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(MeetingApply meetingApply) {
		sessionFactory.getCurrentSession().save(meetingApply);
	}

	public void update(MeetingApply meetingApply) {
		sessionFactory.getCurrentSession().update(meetingApply);
	}

	public void delete(MeetingApply meetingApply) {
		sessionFactory.getCurrentSession().delete(meetingApply);
	}

	public MeetingApply getApplyById(Long applyId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from MeetingApply as m where m.id= :id");
		query.setLong("id", applyId);
		List list = query.list();
		if (list.size() > 0)
			return (MeetingApply) list.get(0);
		else
			return null;
	}

	public List<MeetingApply> getApplyByMeeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from MeetingApply as m where m.toMeeting= :toMeeting");
		query.setEntity("toUser", meeting);
		List<MeetingApply> list = query.list();
		return list;
	}

}

package org.test1.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test1.bo.MeetingBo;
import org.test1.dao.MeetingDao;
import org.test1.model.Meeting;

@Service("meetingBo")
public class MeetingBoImpl implements MeetingBo {
	@Autowired
	private MeetingDao meetingDao;

	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	@Override
	@Transactional
	public void save(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.save(meeting);
	}

	@Override
	@Transactional
	public void update(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.update(meeting);
	}

	@Override
	@Transactional
	public void delete(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.delete(meeting);
	}

}

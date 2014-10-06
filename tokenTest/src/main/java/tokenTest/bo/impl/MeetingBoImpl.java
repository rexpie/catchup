package tokenTest.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.MeetingBo;
import tokenTest.dao.MeetingApplyDao;
import tokenTest.dao.MeetingDao;
import tokenTest.exception.MeetingNotFoundException;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;
import tokenTest.model.User;

@Service("meetingBo")
public class MeetingBoImpl implements MeetingBo {
	@Autowired
	private MeetingDao meetingDao;

	@Autowired
	private MeetingApplyDao meetingApplyDao;

	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	@Transactional
	public void save(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.save(meeting);
	}

	@Transactional
	public void update(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.update(meeting);
	}

	@Transactional
	public void delete(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingDao.delete(meeting);
	}

	@Transactional
	public Meeting getMeetingById(Long id) throws MeetingNotFoundException {
		Meeting meeting = meetingDao.getMeetingById(id);
		if (meeting == null)
			throw new MeetingNotFoundException();
		return meeting;
	}

	@Transactional
	public void applyForMeeting(User user, Meeting meeting, String applyContent) {
		meetingApplyDao.save(new MeetingApply(user, meeting, applyContent));
	}

	@Transactional
	public List<MeetingApply> getApplyByMeeting(Meeting meeting)
			throws Exception {
		List<MeetingApply> applies = meetingApplyDao
				.getApplyByMeeeting(meeting);
		if (applies == null)
			throw new Exception();
		return applies;
	}

	@Transactional
	public List getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName) throws Exception {
		// TODO Auto-generated method stub
		List list = meetingDao.getMeetingList(longitude, latitude, pagenum,
				sorttype, range, gender, job, shopName);
		if (list == null)
			throw new Exception();
		return list;
	}

	@Transactional
	public List getMeetingListByUser(User user, Integer pagenum)
			throws Exception {
		List list = meetingDao.getMeetingListByUser(user, pagenum);
		if (list == null)
			throw new Exception();
		return list;
	}

	@Transactional
	public List getMeetingListByParticipate(User user, Integer pagenum)
			throws Exception {
		List list = meetingDao.getMeetingListByParticipate(user, pagenum);
		if (list == null)
			throw new Exception();
		return list;
	}

}

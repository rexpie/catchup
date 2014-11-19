package tokenTest.bo.impl;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.Util.Constants;
import tokenTest.Util.IMUtil;
import tokenTest.Util.Messages;
import tokenTest.Util.Status;
import tokenTest.bo.MeetingBo;
import tokenTest.dao.MeetingApplyDao;
import tokenTest.dao.MeetingDao;
import tokenTest.dao.UserDao;
import tokenTest.exception.ApplyNotFoundException;
import tokenTest.exception.MeetingNotFoundException;
import tokenTest.exception.TooManyAppliesException;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;
import tokenTest.model.User;

@Service("meetingBo")
public class MeetingBoImpl implements MeetingBo {
	@Autowired
	private MeetingDao meetingDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MeetingApplyDao meetingApplyDao;

	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	@Transactional
	public void save(Meeting meeting) {
		meetingDao.save(meeting);
	}

	@Transactional
	public void update(Meeting meeting) {
		meetingDao.update(meeting);
	}

	@Transactional
	public void delete(Meeting meeting) {
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
	public void applyForMeeting(User user, Meeting meeting, String applyContent)
			throws TooManyAppliesException {
		List list = meetingApplyDao.getApplyByUser(user);
		if (list != null && list.size() <= 2) {
			if (meetingApplyDao.getApplyByUserAndMeeting(user, meeting) == null) {
				meetingApplyDao.save(new MeetingApply(user, meeting,
						applyContent));
			}
		} else {
			throw new TooManyAppliesException();
		}
	}

	@Transactional
	public List<MeetingApply> getApplyByMeeting(Meeting meeting)
			throws Exception {
		List<MeetingApply> applies = meetingApplyDao.getApplyByMeeting(meeting);
		if (applies == null)
			throw new Exception();
		return applies;
	}

	@Transactional
	public MeetingApply getApplyById(Long applyId)
			throws ApplyNotFoundException {
		MeetingApply apply = meetingApplyDao.getApplyById(applyId);
		if (apply == null)
			throw new ApplyNotFoundException();
		return apply;
	}

	@Transactional
	public List getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName) throws Exception {
		List list = meetingDao.getMeetingList(longitude, latitude, pagenum,
				sorttype, range, gender, job, shopName);
		if (list == null)
			throw new Exception();
		return list;
	}

	@Transactional
	public List getMeetingListForUser(User user, Double longitude,
			Double latitude, Integer pagenum, Integer sorttype, Integer range,
			String gender, String job, String shopName) throws Exception {
		List list = meetingDao.getMeetingListWithId(user, longitude, latitude,
				pagenum, sorttype, range, gender, job, shopName);
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

	@Transactional
	public void processMeetingApply(MeetingApply meetingApply, boolean approved) {

		if (approved) {
			meetingApply.getToMeeting().getParticipator()
					.add(meetingApply.getFromUser());
			meetingApply.setStatus(Constants.APPLY_STATUS_ACC);
		} else {
			meetingApply.setStatus(Constants.APPLY_STATUS_REJ);
		}
		meetingDao.update(meetingApply.getToMeeting());
		meetingApplyDao.update(meetingApply);

		/* 清除其他申请 */
		Iterator iterator = meetingApplyDao.getApplyByUser(
				meetingApply.getFromUser()).iterator();
		MeetingApply otherApply = null;
		while (iterator.hasNext()) {
			otherApply = (MeetingApply) iterator.next();
			otherApply.setStatus(Constants.APPLY_STATUS_WITHDRAWN_BY_SYS);
			meetingApplyDao.update(otherApply);
		}
		// TODO rex send messages

	}

	@Transactional
	@Override
	public void withdrawMeetingApply(MeetingApply meetingApply) {
		if (meetingApply == null)
			return;
		meetingApply.setStatus(Constants.APPLY_STATUS_WITHDRAWN);
		meetingApplyDao.update(meetingApply);
	}

	@Transactional
	@Override
	public Status stopMeeting(Meeting meeting, String cancelReason) {
		User user = meeting.getOwner();
		IMUtil.notifyUser(user,
				Messages.WARN_STOP_WITH_APPLICANTS);

		try {
			for (MeetingApply apply : meetingApplyDao
					.getApplyByMeeting(meeting)) {
				apply.setStatus(Constants.APPLY_STATUS_STOPPED_BY_SYS);
				meetingApplyDao.update(apply);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		meeting.setStatus(Constants.MEETING_STATUS_STOPPED);
		meetingDao.merge(meeting);
		return null;
	}

	@Override
	@Transactional
	public boolean testIfApplied(Meeting m, User u) {
		if (u == null || m == null) {
			return false;
		}

		List<MeetingApply> list = meetingApplyDao.getApplyByMeeting(m);
		if (list != null && list.size() != 0) {
			for (MeetingApply apply : list) {
				if (apply.getFromUser().equals(u)) {
					return true;
				}
			}
		}
		return false;
	}

}

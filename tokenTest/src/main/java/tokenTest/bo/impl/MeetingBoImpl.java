package tokenTest.bo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.MeetingBo;
import tokenTest.dao.MeetingDao;
import tokenTest.model.Meeting;

@Service("meetingBo")
public class MeetingBoImpl implements MeetingBo {
	@Autowired
	private MeetingDao meetingDao;

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
	public List getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName) throws Exception {
		// TODO Auto-generated method stub
		List list = meetingDao.getMeetingList(longitude, latitude, pagenum,
				sorttype, range, gender, job, shopName);
		if(list==null)throw new Exception();
		return list;
	}

}

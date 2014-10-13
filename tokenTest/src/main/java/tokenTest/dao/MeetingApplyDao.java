package tokenTest.dao;

import java.util.List;

import tokenTest.model.FriendApply;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;
import tokenTest.model.User;

public interface MeetingApplyDao {
	void save(MeetingApply meetingApply);

	void update(MeetingApply meetingApply);

	void delete(MeetingApply meetingApply);

	List<MeetingApply> getApplyByMeeting(Meeting meeting);

	MeetingApply getApplyById(Long applyId);

	List getApplyByUser(User user);

	MeetingApply getApplyByUserAndMeeting(User user, Meeting meeting);
}

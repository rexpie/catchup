package tokenTest.dao;

import java.util.List;

import tokenTest.model.FriendApply;
import tokenTest.model.Meeting;
import tokenTest.model.MeetingApply;

public interface MeetingApplyDao {
	void save(MeetingApply meetingApply);

	void update(MeetingApply meetingApply);

	void delete(MeetingApply meetingApply);
	
	List<MeetingApply> getApplyByMeeeting(Meeting meeting);
}

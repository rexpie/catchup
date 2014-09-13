package tokenTest.bo;

import tokenTest.model.Meeting;

public interface MeetingBo {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);
}

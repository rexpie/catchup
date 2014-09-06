package org.test1.bo;

import org.test1.model.Meeting;

public interface MeetingBo {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);
}

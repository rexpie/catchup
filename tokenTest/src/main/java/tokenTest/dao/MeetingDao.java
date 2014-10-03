package tokenTest.dao;

import java.util.ArrayList;

import tokenTest.model.Meeting;

public interface MeetingDao {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);

	ArrayList<Meeting> getMeetingList(Double longtitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName);
}

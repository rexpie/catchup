package tokenTest.dao;

import java.util.List;

import tokenTest.model.Meeting;

public interface MeetingDao {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);

	List getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName);
}

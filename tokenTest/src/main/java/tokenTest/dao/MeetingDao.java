package tokenTest.dao;

import java.util.Collection;
import java.util.List;

import tokenTest.model.Meeting;
import tokenTest.model.User;

public interface MeetingDao {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);

	void merge(Meeting meeting);
	
	Meeting getMeetingById(Long id);

	List getMeetingList(Double longitude, Double latitude, Integer pagenum,
			Integer sorttype, Integer range, String gender, String job,
			String shopName);

	List getMeetingListByUser(User user, Integer pagenum);

	List getMeetingListByParticipate(User user, Integer pagenum);

	List getMeetingListWithId(Long userid, Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName);
}

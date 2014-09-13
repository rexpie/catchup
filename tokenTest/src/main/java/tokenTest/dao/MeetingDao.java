package tokenTest.dao;

import tokenTest.model.Meeting;

public interface MeetingDao {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);

	// Meeting findByStockCode(String stockCode);
}

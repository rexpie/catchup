package org.test1.dao;

import org.test1.model.Meeting;

public interface MeetingDao {
	void save(Meeting meeting);

	void update(Meeting meeting);

	void delete(Meeting meeting);

	// Meeting findByStockCode(String stockCode);
}

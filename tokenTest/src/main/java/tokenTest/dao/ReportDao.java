package tokenTest.dao;

import java.util.List;

import tokenTest.model.Report;
import tokenTest.model.User;

public interface ReportDao {

	void save(Report c);
	
	void update(Report c);
	
	void delete(Report c);
	
	List<Report> findReportByOwner(User user);

}

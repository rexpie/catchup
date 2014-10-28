package tokenTest.bo;

import java.util.List;

import tokenTest.model.Report;
import tokenTest.model.User;

public interface ReportBo {

	void save(Report c);
	
	void update(Report c);
	
	void delete(Report c);
	
	List<Report> findReportByOwner(User owner);
	
}

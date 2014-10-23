package tokenTest.dao;

import java.util.List;

import tokenTest.model.Complaint;
import tokenTest.model.User;

public interface ComplaintDao {

	void save(Complaint c);
	
	void update(Complaint c);
	
	void delete(Complaint c);
	
	List<Complaint> findComplaintByOwner(User user);

	List<Complaint> findComplaintByTarget(User user);
	
	
}

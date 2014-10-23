package tokenTest.bo;

import java.util.List;

import tokenTest.model.Complaint;
import tokenTest.model.User;

public interface ComplaintBo {

	void save(Complaint c);
	
	void update(Complaint c);
	
	void delete(Complaint c);
	
	List<Complaint> findComplaintByOwner(User owner);
	
	List<Complaint> findComplaintByTarget(User target);
}

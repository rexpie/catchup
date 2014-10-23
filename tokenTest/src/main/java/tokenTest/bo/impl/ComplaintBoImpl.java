package tokenTest.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.ComplaintBo;
import tokenTest.dao.ComplaintDao;
import tokenTest.model.Complaint;
import tokenTest.model.User;

@Service("complaintBo")
public class ComplaintBoImpl implements ComplaintBo {

	@Autowired
	ComplaintDao complaintDao;
	
	@Override
	@Transactional
	public void save(Complaint c) {
		complaintDao.save(c);
	}

	@Override
	@Transactional
	public void update(Complaint c) {
		complaintDao.update(c);
	}

	@Override
	@Transactional
	public void delete(Complaint c) {
		complaintDao.delete(c);
	}

	@Override
	@Transactional
	public List<Complaint> findComplaintByOwner(User owner) {
		return complaintDao.findComplaintByOwner(owner);
	}

	@Override
	@Transactional
	public List<Complaint> findComplaintByTarget(User target) {
		return complaintDao.findComplaintByTarget(target);
	}

}

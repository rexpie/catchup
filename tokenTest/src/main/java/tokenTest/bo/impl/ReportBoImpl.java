package tokenTest.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.ReportBo;
import tokenTest.dao.ReportDao;
import tokenTest.model.Report;
import tokenTest.model.User;

@Service("reportBo")
public class ReportBoImpl implements ReportBo {

	@Autowired
	ReportDao reportDao;
	
	@Override
	@Transactional
	public void save(Report c) {
		reportDao.save(c);
	}

	@Override
	@Transactional
	public void update(Report c) {
		reportDao.update(c);
	}

	@Override
	@Transactional
	public void delete(Report c) {
		reportDao.delete(c);
	}

	@Override
	@Transactional
	public List<Report> findReportByOwner(User owner) {
		return reportDao.findReportByOwner(owner);
	}

}

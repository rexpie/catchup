package tokenTest.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.ValidationCodeBo;
import tokenTest.dao.ValidationCodeDao;
import tokenTest.model.ValidationCode;

@Service("validationCodeBo")
public class ValidationCodeBoImpl implements ValidationCodeBo {

	@Autowired
	private ValidationCodeDao validationCodeDao;
	
	@Transactional
	public void save(ValidationCode code) {
		validationCodeDao.save(code);
	}

	@Transactional
	public void update(ValidationCode code) {
		validationCodeDao.update(code);
	}

	@Transactional
	public void delete(ValidationCode code) {
		validationCodeDao.delete(code);
	}

	@Transactional
	public ValidationCode findByPhoneNum(String phoneNum) {
		return validationCodeDao.findByPhoneNum(phoneNum);
	}

}

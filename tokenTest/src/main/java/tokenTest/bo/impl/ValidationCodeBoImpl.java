package tokenTest.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.ValidationCodeBo;
import tokenTest.dao.UserDao;
import tokenTest.model.ValidationCode;

@Service("validationCodeBo")
public class ValidationCodeBoImpl implements ValidationCodeBo {

	@Autowired
	private ValidationCodeBo validationCodeBo;
	
	@Override
	public void save(ValidationCode code) {
		validationCodeBo.save(code);
	}

	@Override
	public void update(ValidationCode code) {
		validationCodeBo.update(code);
	}

	@Override
	public void delete(ValidationCode code) {
		validationCodeBo.delete(code);
	}

	@Override
	public ValidationCode findByPhoneNum(String phoneNum) {
		return validationCodeBo.findByPhoneNum(phoneNum);
	}

}

package tokenTest.bo;

import tokenTest.model.ValidationCode;

public interface ValidationCodeBo {
	void save(ValidationCode code);

	void update(ValidationCode code);

	void delete(ValidationCode code);

	ValidationCode findByPhoneNum(String phoneNum);
}

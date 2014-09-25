package tokenTest.service.impl;

public enum ValidationCodeStatus {

	NEW(0),
	PASSWD_RESET(1),
	INVALID(-1);
	
	int value;
	public int getValue() {
		return value;
	}
	
	ValidationCodeStatus(int val){
		value = val;
	}
}

package tokenTest.Util;

public enum Status {
	OK(0),
	ERROR_GENERIC(-1),
	ERROR_USER_NOT_FOUND(-2),
	ERROR_WRONG_TOKEN(-3),
	SERVICE_NOT_AVAILABLE(-999);
	
	int value;
	public int getValue() {
		return value;
	}
	
	Status(int val){
		value = val;
	}
}

package tokenTest.Util;

public enum Status {
	OK(0),

	SERVICE_NOT_AVAILABLE(-999),

	ERR_GENERIC(-10086), ERR_USER_NOT_FOUND(-2), ERR_WRONG_TOKEN(-1000), ERR_MAX_LOGIN_ATTEMPTS(
			-3),

	ERR_INVALID_PHONE_NUMBER(-4), ERR_PHONE_VALIDATION_FAIL(-5),

	ERR_PHONE_NUM_NOT_FOUND(-22),

	ERR_WRONG_VALIDATION_CODE(-6),

	ERR_INVALID_PAGE_NUM(-7), ERR_CANNOT_GET_LOCATION(-8),

	ERR_CANNOT_DELETE_PROFILE_PHOTO(-9),

	ERR_PIC_NOT_FOUND(-10),

	ERR_INVALID_GENDER(-11),

	ERR_INVALID_MEETING_STATUS(-12),

	ERR_MEETING_NOT_FOUND(-13),

	ERR_SHOP_NOT_FOUND(-14),

	ERR_NO_CURRENT_MEETING(-15),

	ERR_NO_SUCH_USER(-16),

	ERR_NO_REQUEST_MADE(-17),

	ERR_NO_REAPPLY(-19),

	ERR_BANNED(-20),

	ERR_NOT_BANNED(-21),

	ERROR_NOT_BLACKLISTED(-22),

	ERR_PIC_FORMAT(-23),

	ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD(-24),
	
	ERR_CAN_NOT_APPLY_FOR_THE_MEETING(-25),
	
	ERR_NO_SUCH_APPLY(-26);

	int value;

	public int getValue() {
		return value;
	}

	Status(int val) {
		value = val;
	}
}

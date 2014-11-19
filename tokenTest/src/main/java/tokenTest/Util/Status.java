package tokenTest.Util;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Status {
	OK(0),

	SERVICE_NOT_AVAILABLE(-999),

	ERR_GENERIC(-10086), 
	
	ERR_DUPLICATE_ENTRY(-1),
	
	ERR_USER_NOT_FOUND(-2), 
	
	ERR_WRONG_TOKEN(-1000), 
	
	ERR_MAX_LOGIN_ATTEMPTS(-3),

	ERR_INVALID_PHONE_NUMBER(-4), 
	
	ERR_PHONE_VALIDATION_FAIL(-5),

	ERR_PHONE_NUM_NOT_FOUND(-22),

	ERR_WRONG_VALIDATION_CODE(-6),

	ERR_INVALID_PAGE_NUM(-7), 
	
	ERR_CANNOT_GET_LOCATION(-8),

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

	ERR_NOT_BLACKLISTED(-22),

	ERR_PIC_FORMAT(-23),

	ERR_USER_NOT_FOUND_OR_WRONG_PASSWORD(-24),
	
	ERR_CAN_NOT_APPLY_FOR_THE_MEETING(-25),
	
	ERR_NEW_MEETING_MUST_HAVE_PIC(-26),
	
	ERR_NO_SUCH_APPLY(-27),
	
	ERR_TOO_MANY_APPLY(-28),
	
	ERR_NOT_APPLIER(-29),
	
	ERR_NOT_MEETING_OWNER(-30),
	
	ERR_ALREADY_BLACKLISTED(-31), 
	
	ERR_NEW_MEETING_MUST_HAVE_JOB(-32),
	
	ERR_BLACKLISTED(-33), 
	
	ERR_THAT_IS_SO_PATHETIC(-34), 
	
	ERR_NEW_MEETING_MUST_HAVE_GENDER(-35),
	
	ERR_NEW_MEETING_MUST_HAVE_BUILDING(-36),
	
	ERR_INVALID_DATE_FORMAT(-37),
	
	ERR_NO_SUCH_EVENT(-38),

	SUCCESS(200),
	
	ERR_MSG_BLACKLISTED(403),
	
	ERR_MSG_SERVICE_UNAVAILABLE(502),
	
	ERR_MSG_TIMEOUT(504),
	
	ERR_TAG_FORMAT(-39);
	
	int value;
	
	private static Map<Integer, Status> map = Maps.newHashMap();

	static {
		for (Status status: Status.values()){
			map.put(status.getValue(), status);
		}
	}
	public int getValue() {
		return value;
	}

	Status(int val) {
		value = val;
	}
	
	public static Status get(int i){
		return map.get(i);
	}
}

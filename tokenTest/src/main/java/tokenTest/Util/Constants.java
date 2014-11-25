package tokenTest.Util;

public class Constants {
	public static final int MAX_LOGIN_ATTEMPTS = 3;
	public static final int MAX_VALIDATION_CODE = 10000;
	public static final int TOKEN_LENGTH = 30;
	public static final int THUMBNAIL_WIDTH = 100;
	public static final int THUMBNAIL_HEIGHT = 100;
	public static final String PICTURE_FORMAT = "png";
	public static final String PICTURE_ROOT_PATH = "picture";
	public static final String RIGIN_PICTURE_PATH = "origin";
	public static final String THUMB_PICTURE_PATH = "thumb";
	public static final int NUM_PER_PAGE = 20;

	public static final int APPLY_STATUS_NEW = 0;
	public static final int APPLY_STATUS_ACC = 1;
	public static final int APPLY_STATUS_REJ = 2;
	public static final int APPLY_STATUS_WITHDRAWN = 3;
	public static final int APPLY_STATUS_WITHDRAWN_BY_SYS = 4;
	public static final int APPLY_STATUS_STOPPED_BY_SYS = 5;

	public static final int MEETING_STATUS_NEW = 0;
	public static final int MEETING_STATUS_STOPPED = 1;

	public static final int COMPLAINT_STATUS_NEW = 0;
	public static final int COMPLAINT_STATUS_DEALT = 1;

	public static final String DP_API_KEY = "5912365198";
	public static final String DP_API_SEC = "d04763" + "003ddc4" + "8168e2d6e"
			+ "bf8e60" + "7fc1";
	
	public static final int REPORT_STATUS_NEW = 0;
	
	public static final Integer EVENT_RANGE = 30000;
	
	public static final int USER_LOAD_TAGS		= 0x1 << 0;
	public static final int USER_LOAD_BLACKLIST	= 0x1 << 1;
	public static final int USER_LOAD_PICTURES	= 0x1 << 2;
	public static final int USER_LOAD_VIEWERS	= 0x1 << 3;
	public static final int USER_LOAD_LIKES		= 0x1 << 4;
	public static final int USER_LOAD_PHOTO		= 0x1 << 5;
	public static final int USER_LOAD_BIZCARD	= 0x1 << 6;
	public static final int USER_LOAD_ALL = 
			USER_LOAD_TAGS | USER_LOAD_BLACKLIST | USER_LOAD_PICTURES | USER_LOAD_VIEWERS | USER_LOAD_LIKES | USER_LOAD_PHOTO | USER_LOAD_BIZCARD;
	
	public static final String MSG_APPLY_MEETING = "[系统通知]Hi~我申请了你发起的约会";
	public static final String MSG_APPLY_APPROVED = "[系统通知]Hi~很高兴能和你见面，咱去聊聊吧";
	public static final String MSG_MEETING_STOP = "[系统通知]您好，用户[%s]挥泪与您告知不能赴约，请见谅。";
	public static final String MSG_APPLY_WITHDRAWN_BY_SYS = "[系统通知]您好，用户[%s]挥泪与您告知不能赴约，请见谅。";
	
	public static final long MSG_SYSTEM_UID = -1L;

}

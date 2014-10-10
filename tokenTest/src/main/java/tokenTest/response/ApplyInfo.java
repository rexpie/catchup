package tokenTest.response;

import tokenTest.model.MeetingApply;

public class ApplyInfo {
	private Long applyId;
	private Long userId;
	private String userName;
	private String applyContent;

	public ApplyInfo(MeetingApply meetingApply) {
		this.applyId = meetingApply.getId();
		this.userId = meetingApply.getFromUser().getId();
		this.userName = meetingApply.getFromUser().getNickname();
		this.applyContent = meetingApply.getApplyContent();
	}

	public ApplyInfo(Long applyId, Long userId, String userName,
			String applyContent) {
		super();
		this.applyId = applyId;
		this.userId = userId;
		this.userName = userName;
		this.applyContent = applyContent;
	}

	public ApplyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

}

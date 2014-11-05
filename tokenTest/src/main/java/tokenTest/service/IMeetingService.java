package tokenTest.service;

import tokenTest.response.MeetingDetailResponse;
import tokenTest.response.MeetingListResponse;
import tokenTest.response.NewApplyResponse;
import tokenTest.response.StatusResponse;
import tokenTest.response.WithdrawApplyResponse;

public interface IMeetingService {
	/* 获取附近的饭约列�?*/
	MeetingListResponse getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName, Long id, String token);

	/* 获取自己发起的饭约列�?,按时间排�?*/
	MeetingListResponse getMyMeetingList(Long id, String token, Integer pagenum);

	/* 获取自己参见的饭约列�?,按时间排�?*/
	MeetingListResponse getMyPartMeetingList(Long id, String token,
			Integer pagenum);

	/* 获取自己饭约详细信息,包括参与�?*/
	MeetingDetailResponse getMeetingDetail(Long id, String token, Long meetingid);

	/* 发起饭约 */
	StatusResponse newMeeting(Long id, String token, Long shopid,
			String genderConstraint, String description);

	/* 申请参加饭约 */
	NewApplyResponse applyForMeeting(Long id, String token, Long meetingid,
			String applyContent);

	WithdrawApplyResponse withdrawMeetingApply(Long id, String token, Long meetingid, String withdrawReason);

	StatusResponse stopMeeting(Long id, String token, Long meetingid, String stopReason);
	
	/* 处理饭约申请 */
	StatusResponse processMeetingApply(Long id, String token,
			Long applyid,Boolean approved);
	
	/* ���۷�Լ����ʱ���� */
	String commentOnMeeting(Long id, String token, Long meetingid,
			String comment);
}

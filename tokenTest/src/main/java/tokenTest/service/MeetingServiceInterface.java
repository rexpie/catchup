package tokenTest.service;

import tokenTest.response.MeetingResponse;
import antlr.collections.List;

public interface MeetingServiceInterface {
	/* 未登录状态，使用客户端位置查询 */
	List getMeetingList(String longtitude, String latitude, String pagenum, String sorttype);

	/* 登录状态，使用用户位置查询，建议先更新用户位置 */
	List getMeetingList(Long id, String token, String pagenum);

	/* 发起饭约 */
	MeetingResponse newMeeting(Long id, String token, Long shopid,
			String genderConstraint, String description);

	/* 返回饭约详情，含参与者 */
	MeetingResponse getMeetingDetail(Long meetingid);

	/* 申请加入饭约 */
	String applyForMeeting(Long id, String token, Long meetingid);

	/* 同意饭约申请 */
	String approveMeetingApply(Long id, String token, Long meetingid,
			Long applyid);

	/* 不同意饭约申请 */
	String disapproveMeetingApply(Long id, String token, Long meetingid,
			Long applyid);

	/* 评论饭约，暂时不做 */
	String commentOnMeeting(Long id, String token, Long meetingid,
			String comment);
}

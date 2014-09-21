package tokenTest.service;

import tokenTest.response.MeetingResponse;
import antlr.collections.List;

public interface MeetingServiceInterface {
	/* δ��¼״̬��ʹ�ÿͻ���λ�ò�ѯ */
	List getMeetingList(String longtitude, String latitude, String pagenum, String sorttype);

	/* ��¼״̬��ʹ���û�λ�ò�ѯ�������ȸ����û�λ�� */
	List getMeetingList(Long id, String token, String pagenum);

	/* ����Լ */
	MeetingResponse newMeeting(Long id, String token, Long shopid,
			String genderConstraint, String description);

	/* ���ط�Լ���飬�������� */
	MeetingResponse getMeetingDetail(Long meetingid);

	/* ������뷹Լ */
	String applyForMeeting(Long id, String token, Long meetingid);

	/* ͬ�ⷹԼ���� */
	String approveMeetingApply(Long id, String token, Long meetingid,
			Long applyid);

	/* ��ͬ�ⷹԼ���� */
	String disapproveMeetingApply(Long id, String token, Long meetingid,
			Long applyid);

	/* ���۷�Լ����ʱ���� */
	String commentOnMeeting(Long id, String token, Long meetingid,
			String comment);
}

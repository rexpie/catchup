package tokenTest.service;

import tokenTest.response.MeetingResponse;
import antlr.collections.List;

public interface MeetingServiceInterface {
	/* δ��¼״̬��ʹ�ÿͻ���λ�ò�ѯ */
	List getMeetingList(String latitude, String longtitude, String pagenum);

	/* ��¼״̬��ʹ���û�λ�ò�ѯ�������ȸ����û�λ�� */
	List getMeetingList(Long id, String token, String pagenum);

	/* ����Լ */
	MeetingResponse startMeeting(Long id, String token, Long shopid, String sex_preference,
			String description, int time_limit);

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

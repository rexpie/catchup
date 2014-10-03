package tokenTest.service;

import java.util.ArrayList;

import tokenTest.response.MeetingListResponse;
import tokenTest.response.StatusResponse;
import antlr.collections.List;

public interface MeetingServiceInterface {
	/* δ��¼״̬��ʹ�ÿͻ���λ�ò�ѯ */
	MeetingListResponse getMeetingList(Double longitude, Double latitude,
			Integer pagenum, Integer sorttype, Integer range, String gender,
			String job, String shopName);

	/* ��¼״̬��ʹ���û�λ�ò�ѯ�������ȸ����û�λ�� */
	ArrayList<MeetingListResponse> getMeetingList(Long id, String token,
			String pagenum, String sorttype);

	/* ����Լ */
	StatusResponse newMeeting(Long id, String token, Long shopid,
			String genderConstraint, String description);

	/* ���ط�Լ���飬�������� */
	MeetingListResponse getMeetingDetail(Long meetingid);

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

package tokenTest.response;

import java.util.ArrayList;

import tokenTest.Util.Status;

public class MeetingDetailResponse extends StatusResponse{

	public MeetingDetailResponse(Enum<Status> status) {
		super(status);
	}

	public MeetingDetailResponse() {
		super(Status.OK);
	}

	private MeetingDetail meetingDetail;

	public MeetingDetail getMeetingDetail() {
		return meetingDetail;
	}

	public void setMeetingDetail(MeetingDetail meetingDetail) {
		this.meetingDetail = meetingDetail;
	}

}

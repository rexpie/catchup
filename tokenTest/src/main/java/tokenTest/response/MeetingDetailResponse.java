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
	private ArrayList<ApplyInfo> applicants = new ArrayList<ApplyInfo>();
	private ArrayList<UserInfo> participates = new ArrayList<UserInfo>();

	public MeetingDetail getMeetingDetail() {
		return meetingDetail;
	}

	public void setMeetingDetail(MeetingDetail meetingDetail) {
		this.meetingDetail = meetingDetail;
	}

	public ArrayList<ApplyInfo> getApplicants() {
		return applicants;
	}

	public void setApplicants(ArrayList<ApplyInfo> applicants) {
		this.applicants = applicants;
	}

	public ArrayList<UserInfo> getParticipates() {
		return participates;
	}

	public void setParticipates(ArrayList<UserInfo> participates) {
		this.participates = participates;
	}

}

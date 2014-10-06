package tokenTest.response;

import java.util.ArrayList;

import tokenTest.Util.Status;

public class MeetingDetailResponse {
	private Enum<Status> status;
	private MeetingDetail meetingDetail;
	private ArrayList<Long> applicants = new ArrayList<Long>();
	private ArrayList<Long> participates = new ArrayList<Long>();

	public MeetingDetailResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public MeetingDetail getMeetingDetail() {
		return meetingDetail;
	}

	public void setMeetingDetail(MeetingDetail meetingDetail) {
		this.meetingDetail = meetingDetail;
	}

	public ArrayList<Long> getApplicants() {
		return applicants;
	}

	public void setApplicants(ArrayList<Long> applicants) {
		this.applicants = applicants;
	}

	public ArrayList<Long> getParticipates() {
		return participates;
	}

	public void setParticipates(ArrayList<Long> participates) {
		this.participates = participates;
	}

}

package tokenTest.response;

import java.util.ArrayList;

import tokenTest.Util.Status;

public class MeetingListResponse {
	private Enum<Status> status;
	private ArrayList<MeetingDetail> meetingList = new ArrayList<MeetingDetail>();

	public MeetingListResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MeetingListResponse(Enum<Status> status,
			ArrayList<MeetingDetail> meetingList) {
		super();
		this.status = status;
		this.meetingList = meetingList;
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public ArrayList<MeetingDetail> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(ArrayList<MeetingDetail> meetingList) {
		this.meetingList = meetingList;
	}

}

package tokenTest.response;

import java.util.ArrayList;

import tokenTest.Util.Status;

public class MeetingListResponse extends StatusResponse{
	private ArrayList<MeetingDetail> meetingList = new ArrayList<MeetingDetail>();

	public MeetingListResponse(Status status) {
		super(status);
	}

	public MeetingListResponse(Enum<Status> status,
			ArrayList<MeetingDetail> meetingList) {
		super(status);
		this.meetingList = meetingList;
	}

	public ArrayList<MeetingDetail> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(ArrayList<MeetingDetail> meetingList) {
		this.meetingList = meetingList;
	}


}

package tokenTest.response;

import tokenTest.Util.Status;

public class EventDetailResponse extends StatusResponse{

	EventDetail event = new EventDetail();
	
	public EventDetailResponse(Enum<Status> status) {
		super(status);
	}

	public EventDetail getEvent() {
		return event;
	}

	public void setEvent(EventDetail event) {
		this.event = event;
	}

	
}

package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class EventListResponse extends StatusResponse{

	public List<EventInfo> eventList = Lists.newArrayList();
	
	public EventListResponse(Enum<Status> status) {
		super(status);
	}
	
}


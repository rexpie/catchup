package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;
import tokenTest.model.Event;

import com.google.common.collect.Lists;

public class EventListResponse extends StatusResponse{

	public List<EventInfo> eventList = Lists.newArrayList();
	
	private int total;
	
	public EventListResponse(Enum<Status> status) {
		super(status);
	}

	public void setEvents(List<Event> eventListByUser) {
		for (Event e: eventListByUser){
			eventList.add(new EventInfo(e, 0));
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}


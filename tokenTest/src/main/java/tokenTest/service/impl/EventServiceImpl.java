package tokenTest.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tokenTest.Util.Constants;
import tokenTest.Util.Status;
import tokenTest.bo.EventBo;
import tokenTest.bo.UserBo;
import tokenTest.model.Event;
import tokenTest.response.EventDetailResponse;
import tokenTest.response.EventInfo;
import tokenTest.response.EventListResponse;
import tokenTest.response.StatusResponse;
import tokenTest.service.IEventService;

@RestController
@RequestMapping("/event")
@Service("eventService")
public class EventServiceImpl implements IEventService {

	@Autowired
	private UserBo userBo;

	@Autowired
	private EventBo eventBo;

	@Override
	@RequestMapping(value = { "/getRecommendEvents**" }, method = RequestMethod.GET)
	public EventListResponse getRecommendEvents(
			@RequestParam(required = true) Double longitude,
			@RequestParam(required = true) Double latitude,
			@RequestParam(required = false, defaultValue = "0") int pagenum) {

		EventListResponse response = new EventListResponse(Status.OK);
		@SuppressWarnings("rawtypes")
		List list;
		try {
			list = eventBo.getEventList(longitude, latitude, pagenum,
					Constants.EVENT_RANGE);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			return response;
		}

		if (list != null) {
			Iterator iterator = list.iterator();
			Object[] objects = null;
			int index = 0;
			while (iterator.hasNext()) {
				objects = (Object[]) iterator.next();
				EventInfo eventInfo = new EventInfo((Event) objects[0],
						(Double) objects[1]);
				eventInfo.setIndex(index++);
				eventInfo.setPagenum(pagenum);
				response.eventList.add(eventInfo);
			}
		}
		return response;
	}

	@Override
	public EventDetailResponse getEventDetail(Long id, String token,
			Long eventid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusResponse withdrawFromEvent(Long id, String token, Long eventid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusResponse applyForEvent(Long id, String token, Long eventid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventListResponse getMyEvents(Long id, String token, Integer pagenum) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

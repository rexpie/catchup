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
import tokenTest.exception.UserNotFoundException;
import tokenTest.exception.WrongTokenException;
import tokenTest.model.Event;
import tokenTest.model.User;
import tokenTest.response.EventDetail;
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
				Long totalNum = (Long) objects[2];
				int totalPage = (int) Math.ceil(totalNum / (double) Constants.NUM_PER_PAGE);
				if (totalNum == 0){
					response.setTotal(totalPage);
					break;
				}
				eventInfo.setIndex(index++);
				eventInfo.setPagenum(pagenum);
				response.eventList.add(eventInfo);
				response.setTotal(totalPage);
			}
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/getEventDetail**" }, method = RequestMethod.GET)
	public EventDetailResponse getEventDetail(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long eventid) {
		User user = null;
		EventDetailResponse response = new EventDetailResponse(Status.OK);
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new EventDetailResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new EventDetailResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new EventDetailResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		Event event = eventBo.findByEventIdWithParticipants(eventid);
		if (event != null) {
			response.setEvent(new EventDetail(event, eventBo.isUserParticipantOfEvent(event, user)));
		} else {
			response.setStatus(Status.ERR_NO_SUCH_EVENT);
			response.setEvent(null);
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/withdrawFromEvent**" }, method = RequestMethod.GET)
	public StatusResponse withdrawFromEvent(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long eventid) {
		User user = null;
		StatusResponse response = new StatusResponse(Status.OK);
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new EventDetailResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new EventDetailResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new EventDetailResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		Event event = eventBo.findByEventIdWithParticipants(eventid);

		boolean removed = event.getParticipants().remove(user);
		if (removed) {
			try {
				eventBo.update(event);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			}
		} else {
			response.setStatus(Status.ERR_NOT_APPLIER);
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/applyForEvent**" }, method = RequestMethod.GET)
	public StatusResponse applyForEvent(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = true) Long eventid) {
		User user = null;
		StatusResponse response = new StatusResponse(Status.OK);
		/* 验证用户 */
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new EventDetailResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new EventDetailResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new EventDetailResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		Event event = eventBo.findByEventIdWithParticipants(eventid);

		boolean added = event.getParticipants().add(user);
		if (added) {
			try {
				eventBo.update(event);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(Status.SERVICE_NOT_AVAILABLE);
			}
		}
		return response;
	}

	@Override
	@RequestMapping(value = { "/getMyEvents**" }, method = RequestMethod.GET)
	public EventListResponse getMyEvents(
			@RequestParam(required = true) Long id,
			@RequestParam(required = true) String token,
			@RequestParam(required = false, defaultValue = "0") Integer pagenum) {
		User user = null;
		EventListResponse response = new EventListResponse(Status.OK);
		try {
			user = userBo.validateUser(id, token);
		} catch (UserNotFoundException e) {
			return new EventListResponse(Status.ERR_USER_NOT_FOUND);
		} catch (WrongTokenException e) {
			return new EventListResponse(Status.ERR_WRONG_TOKEN);
		} catch (Exception e) {
			return new EventListResponse(Status.SERVICE_NOT_AVAILABLE);
		}

		List list;
		try {
			list = eventBo.getEventListByUser(user, pagenum);
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
						0);
				Long totalNum = (Long) objects[1];
				int totalPage = (int) Math.ceil(totalNum / (double) Constants.NUM_PER_PAGE);
				if (totalNum == 0){
					response.setTotal(totalPage);
					break;
				}
				eventInfo.setIndex(index++);
				eventInfo.setPagenum(pagenum);
				response.eventList.add(eventInfo);
				response.setTotal(totalPage);
			}
		}
		return response;
	}

}

package tokenTest.service;

import tokenTest.response.EventDetailResponse;
import tokenTest.response.EventListResponse;
import tokenTest.response.StatusResponse;

public interface IEventService {

	EventListResponse getRecommendEvents(Double longitude, Double latitude,
			int pagenum);

	EventDetailResponse getEventDetail(Long id, String token, Long eventid);

	StatusResponse withdrawFromEvent(Long id, String token, Long eventid);

	StatusResponse applyForEvent(Long id, String token, Long eventid);
	
	EventListResponse getMyEvents(Long id, String token, Integer pagenum);
}

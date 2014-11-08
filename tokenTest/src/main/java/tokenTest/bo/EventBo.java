package tokenTest.bo;

import java.util.List;

import tokenTest.model.Event;
import tokenTest.model.User;

public interface EventBo {
	void save(Event e);

	void update(Event e);

	void delete(Event e);

	Event findByEventId(Long id);

	List<Event> getEventList(Double longitude, Double latitude,
			Integer pagenum, Integer range);

	List<Event> getEventListByUser(User user, Integer pagenum);

	Event findByEventIdWithParticipants(Long eventid);

	boolean isUserParticipantOfEvent(Event e, User u);

}

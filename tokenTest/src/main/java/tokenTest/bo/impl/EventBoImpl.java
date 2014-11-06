package tokenTest.bo.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tokenTest.bo.EventBo;
import tokenTest.dao.EventDao;
import tokenTest.model.Event;
import tokenTest.model.User;

@Service("eventBo")
public class EventBoImpl implements EventBo {

	@Autowired
	EventDao eventDao;
	
	@Override
	@Transactional
	public void save(Event e) {
		eventDao.save(e);
	}

	@Override
	@Transactional
	public void update(Event e) {
		eventDao.update(e);
	}

	@Override
	@Transactional
	public void delete(Event e) {
		eventDao.delete(e);
	}

	@Override
	@Transactional
	public Event findByEventId(Long id) {
		return eventDao.findByEventId(id);
	}

	@Override
	@Transactional
	public List<Event> getEventList(Double longitude, Double latitude,
			Integer pagenum, Integer range) {
		return eventDao.getEventList(longitude, latitude, pagenum, range);
	}


	@Override
	@Transactional
	public List<Event> getEventListByUser(User user, Double longitude, Double latitude,
			Integer pagenum, Integer range) {
		return eventDao.getEventListByUser(user, longitude, latitude, pagenum, range);
	}
}

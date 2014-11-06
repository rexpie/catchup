package tokenTest.response;

import java.util.List;

import com.google.common.collect.Lists;

import tokenTest.model.Event;
import tokenTest.model.Picture;
import tokenTest.model.User;

public class EventDetail {
	long eventid;
	String description;
	int headCount;
	int memberCount;
	String picid;
	List<String> pics;
	long startTime;
	long endTime;
	double longitude;
	double latitude;
	String placeDescription;
	String detail;
	List<Long> participants;
	boolean joined;

	public EventDetail(Event event, boolean joined){
		setEventid(event.getId());
		setDescription(event.getDescription());
		setHeadCount(event.getParticipants().size());
		setMemberCount(event.getMemberCount());
		setPicid(event.getPic().getId());
		
		List<String> picids = Lists.newArrayList();
		for (Picture p: event.getPics()) picids.add(p.getId());
		setPics(picids);
		
		setStartTime(event.getStartTime().getTime());
		setEndTime(event.getEndTime().getTime());
		
		setLongitude(event.getLongitude());
		setLatitude(event.getLatitude());
		
		setPlaceDescription(event.getPlaceDescription());
		setDetail(event.getDetail());
		
		List<Long> partids = Lists.newArrayList();
		for (User u : event.getParticipants()) partids.add(u.getId());
		setParticipants(partids);
		
		setJoined(joined);
	}
	
	public EventDetail() {
	}

	public long getEventid() {
		return eventid;
	}

	public void setEventid(long eventid) {
		this.eventid = eventid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPlaceDescription() {
		return placeDescription;
	}

	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<Long> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Long> participants) {
		this.participants = participants;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
}

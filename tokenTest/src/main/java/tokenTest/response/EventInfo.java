package tokenTest.response;

import tokenTest.model.Event;

public class EventInfo {
	long eventid;
	String description;
	int distance;
	int headCount;
	int memberCount;
	String picid;
	long startTime;
	long endTime;
	double longitude;
	double latitude;
	String placeDescription;
	String detail;
	int pagenum;
	int index;
	
	
	public EventInfo(Event event, double distance) {
		setEventid(event.getId());
		setDescription(event.getDescription());
		setDistance((int)distance);
		setHeadCount(event.getHeadCount());
		setMemberCount(event.getMemberCount());
		setPicid(event.getPic().getId());
		setStartTime(event.getStartTime().getTime());
		setEndTime(event.getEndTime().getTime());
		setLongitude(event.getLongitude());
		setLatitude(event.getLatitude());
		setPlaceDescription(event.getPlaceDescription());
		setDetail(event.getDetail());
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

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}

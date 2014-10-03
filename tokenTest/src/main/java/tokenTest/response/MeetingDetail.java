package tokenTest.response;

import java.util.Date;

import tokenTest.model.Meeting;

public class MeetingDetail {
	private Long meetingID;
	private Long ownerID;
	private Long ownerPhotoID;
	private Long shopID;
	private Date startTime;
	private String genderConstraint;
	private String ownerGender;
	private String description;
	private String building;
	private Double longitude;
	private Double latitude;
	private Double distance;
	
	public MeetingDetail() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MeetingDetail(Meeting meeting, Double distance){
		this.meetingID=meeting.getId();
		this.ownerID=meeting.getOwner().getId();
		this.ownerPhotoID=meeting.getOwner().getPic().getId();
		this.shopID=meeting.getShop().getId();
		this.startTime=meeting.getCreate_time();
		this.genderConstraint=meeting.getGenderConstraint();
		this.ownerGender=meeting.getOwner().getSex();
		this.description=meeting.getDescription();
		this.building=meeting.getOwner().getBuilding();
		this.longitude=meeting.getShop().getLongitude();
		this.latitude=meeting.getShop().getLatitude();
		this.distance=distance;
	}



	public Long getMeetingID() {
		return meetingID;
	}



	public void setMeetingID(Long meetingID) {
		this.meetingID = meetingID;
	}



	public Long getOwnerID() {
		return ownerID;
	}



	public void setOwnerID(Long ownerID) {
		this.ownerID = ownerID;
	}



	public Long getOwnerPhotoID() {
		return ownerPhotoID;
	}



	public void setOwnerPhotoID(Long ownerPhotoID) {
		this.ownerPhotoID = ownerPhotoID;
	}



	public Long getShopID() {
		return shopID;
	}



	public void setShopID(Long shopID) {
		this.shopID = shopID;
	}



	public Date getStartTime() {
		return startTime;
	}



	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public String getGenderConstraint() {
		return genderConstraint;
	}



	public void setGenderConstraint(String genderConstraint) {
		this.genderConstraint = genderConstraint;
	}



	public String getOwnerGender() {
		return ownerGender;
	}



	public void setOwnerGender(String ownerGender) {
		this.ownerGender = ownerGender;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getBuilding() {
		return building;
	}



	public void setBuilding(String building) {
		this.building = building;
	}



	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}



	public Double getDistance() {
		return distance;
	}



	public void setDistance(Double distance) {
		this.distance = distance;
	}
}

package tokenTest.response;

import java.util.Date;

import tokenTest.Util.Utils;
import tokenTest.model.Meeting;

public class MeetingDetail {
	private Long meetingid;
	private String description;
	private Double distance;
	private String name;
	private String shopName;
	private String job;
	private String company;
	private String ownerGender;
	private String building;
	private String genderConstraint;
	private Integer age;
	private Long ownerid;
	private String ownerPhotoid;
	private Long shopid;
	private Date startTime;
	private Double longitude;
	private Double latitude;
	private Integer pageNum;
	private Integer index;

	public MeetingDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MeetingDetail(Meeting meeting) {
		this.meetingid = meeting.getId();
		this.ownerid = meeting.getOwner().getId();
		this.ownerPhotoid = meeting.getOwner().getPic().getId();
		this.shopid = meeting.getShop().getId();
		this.startTime = meeting.getCreate_time();
		this.genderConstraint = meeting.getGenderConstraint();
		this.ownerGender = meeting.getOwner().getSex();
		this.description = meeting.getDescription();
		this.building = meeting.getOwner().getBuilding();
		this.longitude = meeting.getShop().getLongitude();
		this.latitude = meeting.getShop().getLatitude();

		this.name = meeting.getOwner().getNickname();
		this.shopName = meeting.getShop().getName();
		this.job = meeting.getOwner().getJob();
		this.company = meeting.getOwner().getCompany();
		this.age = Utils.getAge(meeting.getOwner().getBirthday());
	}

	public MeetingDetail(Meeting meeting, Double distance) {
		this(meeting);
		this.distance = distance;
	}

	public Long getMeetingid() {
		return meetingid;
	}

	public void setMeetingid(Long meetingid) {
		this.meetingid = meetingid;
	}

	public Long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnerPhotoid() {
		return ownerPhotoid;
	}

	public void setOwnerPhotoid(String ownerPhotoid) {
		this.ownerPhotoid = ownerPhotoid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}

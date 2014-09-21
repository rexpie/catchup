package tokenTest.response;

import tokenTest.Util.Status;

public class MeetingResponse {
	private Long shopid;
	private String genderConstraint;
	private String description;
	private String longtitude;
	private String latitude;
	private Enum<Status> status;

	public MeetingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MeetingResponse(Long shopid, String genderConstraint,
			String description, String longtitude, String latitude,
			Enum<Status> status) {
		super();
		this.shopid = shopid;
		this.genderConstraint = genderConstraint;
		this.description = description;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.status = status;
	}

	public MeetingResponse(Enum<Status> status) {
		super();
		this.status = status;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getGenderConstraint() {
		return genderConstraint;
	}

	public void setGenderConstraint(String genderConstraint) {
		this.genderConstraint = genderConstraint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}
}

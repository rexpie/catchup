package tokenTest.response;

import java.util.Date;

import tokenTest.model.User;

public class UserDetailResponse {
	private String nickname;
	private String sex;
	private String building;
	private String company;
	private Date birthday;
	private long zan_count;
	private String phone_number;
	private String email_address;

	public UserDetailResponse(User user) {
		this.nickname = user.getNickname();
		this.sex = user.getSex();
		this.building = user.getBuilding();
		this.company = user.getCompany();
		this.birthday = user.getBirthday();
		this.zan_count = user.getZan_count();
		this.phone_number = user.getPhone_number();
		this.email_address = user.getEmail_address();
	}

	public UserDetailResponse(String nickname, String sex, String building,
			long zan_count, Date birthday) {
		super();
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.zan_count = zan_count;
		this.birthday = birthday;
	}

	public UserDetailResponse(String nickname, String sex, String building,
			String company, Date birthday, long zan_count, String phone_number,
			String email_address) {
		super();
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.company = company;
		this.birthday = birthday;
		this.zan_count = zan_count;
		this.phone_number = phone_number;
		this.email_address = email_address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public long getZan_count() {
		return zan_count;
	}

	public void setZan_count(long zan_count) {
		this.zan_count = zan_count;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
}

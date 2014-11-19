package tokenTest.response;

import java.util.Date;
import java.util.Set;

import tokenTest.Util.Utils;
import tokenTest.model.Tag;
import tokenTest.model.User;

import com.google.common.collect.Sets;

public class UserDetailInnerResponse {
	private String nickname;
	private String gender;
	private String building;
	private String company;
	private String industry;
	private String job;
	private String city;

	private Date birthday;
	private long numOfLikes;
	private String phonenum;
	private boolean alreadyLiked;
	private int age;
	private Set<String> tags;
	private int numOfViews;
	private boolean blacklisted;

	private boolean hasPic;
	
	public synchronized int getNumOfViews() {
		return numOfViews;
	}

	public synchronized void setNumOfViews(int numOfViews) {
		this.numOfViews = numOfViews;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getNumOfLikes() {
		return numOfLikes;
	}

	public void setNumOfLikes(long numOfLikes) {
		this.numOfLikes = numOfLikes;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public boolean isAlreadyLiked() {
		return alreadyLiked;
	}

	public void setAlreadyLiked(boolean alreadyLiked) {
		this.alreadyLiked = alreadyLiked;
	}

	public UserDetailInnerResponse(User user, boolean isSelf) {
		this.nickname = user.getNickname();
		this.gender = user.getSex();
		this.building = user.getBuilding();
		this.company = user.getCompany();
		this.birthday = user.getBirthday();
		this.numOfLikes = user.getLikes().size();
		this.numOfViews = user.getViewers().size();
		this.tags = convertTags(user.getTags());
		this.age = Utils.getAge(birthday);
		this.industry = user.getIndustry();
		this.job = user.getJob();
		this.city = user.getCity();
		this.hasPic = user.getPic() == null ? false : user.getPic().getId() != null;
		// this.pic=user.getPic();
		if (isSelf) {
			this.phonenum = user.getPhone_number();
		}
	}

	private Set<String> convertTags(Set<Tag> tags) {
		Set<String> tagNames = Sets.newTreeSet();
		
		for (Tag tag : tags){
			tagNames.add(tag.getName());
		}
		
		return tagNames;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public synchronized String getIndustry() {
		return industry;
	}
	
	public synchronized void setIndustry(String industry) {
		this.industry = industry;
	}

	public synchronized String getJob() {
		return job;
	}

	public synchronized void setJob(String job) {
		this.job = job;
	}

	public boolean isBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isHasPic() {
		return hasPic;
	}

	public void setHasPic(boolean hasPic) {
		this.hasPic = hasPic;
	}
}

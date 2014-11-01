package tokenTest.response;

import java.util.Date;

import tokenTest.Util.Status;
import tokenTest.Util.Utils;
import tokenTest.model.Picture;
import tokenTest.model.User;

public class UserDetailResponse extends StatusResponse{
	
	UserDetailInnerResponse user = null;
	
	public UserDetailInnerResponse getUser() {
		return user;
	}


	public void setUser(UserDetailInnerResponse user) {
		this.user = user;
	}


	public UserDetailResponse(User _user, boolean isSelf) {
		super(Status.OK);
		user = new UserDetailInnerResponse(_user, isSelf);
	}
	

	public UserDetailResponse(Enum<Status> status) {
		super(status);
		// TODO Auto-generated constructor stub
	}
	
		

	/*public Picture getPic() {
		return pic;
	}

	public void setPic(Picture pic) {
		this.pic = pic;
	}*/
	
	public static UserDetailResponse getError(Status status) {
		if (status!=Status.OK)
			return new UserDetailResponse(status);
		else {
			return new UserDetailResponse(Status.ERR_GENERIC);
		}
	}


	public void setAlreadyLiked(boolean flag) {
		if (user!=null){
			user.setAlreadyLiked(flag);
		}
	}

}

class UserDetailInnerResponse{
	private String nickname;
	private String sex;
	private String building;
	private String company;
	private Date birthday;
	private long numOfLikes;
	private String phonenum;
	private boolean alreadyLiked;
	private int age;
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
		this.sex = user.getSex();
		this.building = user.getBuilding();
		this.company = user.getCompany();
		this.birthday = user.getBirthday();
		this.numOfLikes = user.getLikes().size();
		this.age = Utils.getAge(birthday);
		//this.pic=user.getPic();
		if(isSelf){
			this.phonenum = user.getPhone_number();
		}
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
		return numOfLikes;
	}

	public void setZan_count(long zan_count) {
		this.numOfLikes = zan_count;
	}
	
}

package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class LikeUsersResponse extends StatusResponse {

	public List<InnerLikeUser> users = Lists.newArrayList();
	
	public LikeUsersResponse(Enum<Status> status) {
		super(status);
		// TODO Auto-generated constructor stub
	}
	
	public void add(String name, Integer age, String gender, Long id){
		users.add(new InnerLikeUser(name, age, gender, id));
	}

}


class InnerLikeUser{
	public InnerLikeUser(String name, Integer age, String gender, Long id) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
	}

	public InnerLikeUser(){}
	
	public String name;
	public String gender;
	public Integer age;
	public Long id;
}
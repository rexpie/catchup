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

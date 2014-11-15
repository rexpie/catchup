package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class BlacklistResponse extends StatusResponse {

	public List<InnerBlackList> users = Lists.newArrayList();
	
	public BlacklistResponse(Enum<Status> status) {
		super(status);
	}
	
	public void add(String name, Integer age, String gender, Long id) {
		users.add(new InnerBlackList(name, gender, age, id));
	}
}


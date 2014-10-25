package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class LikeUsersResponse extends StatusResponse {

	public List<Long> ids = Lists.newArrayList();
	
	public LikeUsersResponse(Enum<Status> status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

}

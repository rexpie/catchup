package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class BlacklistResponse extends StatusResponse {

	public List<Long> blacklist = Lists.newArrayList();
	
	public BlacklistResponse(Enum<Status> status) {
		super(status);
	}
	
}

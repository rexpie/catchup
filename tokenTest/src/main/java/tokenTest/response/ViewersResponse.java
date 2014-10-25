package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class ViewersResponse extends StatusResponse {

	public List<Long> ids = Lists.newArrayList();
	
	public ViewersResponse(Enum<Status> status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

}

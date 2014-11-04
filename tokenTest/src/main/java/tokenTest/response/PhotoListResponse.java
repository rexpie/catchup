package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class PhotoListResponse extends StatusResponse {

	public List<String> ids = Lists.newArrayList();
	
	public PhotoListResponse(Enum<Status> status) {
		super(status);
	}

}

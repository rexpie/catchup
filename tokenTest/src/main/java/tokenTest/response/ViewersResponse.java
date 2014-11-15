package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class ViewersResponse extends StatusResponse {

	public List<InnerViewer> users = Lists.newArrayList();
	
	public ViewersResponse(Enum<Status> status) {
		super(status);
	}

	public void add(String name, Integer age, String gender, Long id) {
		users.add(new InnerViewer(name, age, gender, id));
	}
}


package tokenTest.response;

import tokenTest.Util.Status;

public class NewApplyResponse {
	private Enum<Status> status;

	public NewApplyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}
}

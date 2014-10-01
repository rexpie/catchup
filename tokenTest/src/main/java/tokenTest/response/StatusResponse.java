package tokenTest.response;

import tokenTest.Util.Status;

public class StatusResponse {
	private Enum<Status> status;

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public StatusResponse(Enum<Status> status) {
		super();
		this.status = status;
	}

}

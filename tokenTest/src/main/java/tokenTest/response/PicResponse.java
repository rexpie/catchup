package tokenTest.response;

import tokenTest.Util.Status;

public class PicResponse {
	private Enum<Status> status;
	
	public PicResponse(Enum<Status> status) {
		super();
		this.status = status;
	}
	public Enum<Status> getStatus() {
		return status;
	}
	public void setStatus(Enum<Status> status) {
		this.status = status;
	}
}

package tokenTest.response;

import tokenTest.Util.Status;

public class ValidatePhoneResponse {
	private Enum<Status> status;

	public ValidatePhoneResponse(Enum<Status> status) {
		super();
		this.status = status;
	}

	public ValidatePhoneResponse() {
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

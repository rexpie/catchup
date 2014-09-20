package tokenTest.response;

import tokenTest.Util.Status;

public class ValidatePhoneResponse {
	private Enum<Status> status;
	private String code;

	public ValidatePhoneResponse(Enum<Status> status, String code) {
		super();
		this.status = status;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

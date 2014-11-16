package tokenTest.response;

import tokenTest.Util.Status;

public class MsgTokenResponse extends StatusResponse {

	public MsgTokenResponse(Enum<Status> status) {
		super(status);
	}

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public MsgTokenResponse(Enum<Status> status, String token) {
		super(status);
		this.token = token;
	}
	
	
}

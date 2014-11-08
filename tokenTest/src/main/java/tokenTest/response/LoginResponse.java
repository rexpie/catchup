package tokenTest.response;

import tokenTest.Util.Status;

public class LoginResponse extends StatusResponse {
	private String token;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResponse(Enum<Status> status) {
		super(status);
	}

	public LoginResponse(Enum<Status> status, String token) {
		super(status);
		setStatus(status);
		this.token = token;
	}

	public LoginResponse(Enum<Status> status, Long id, String token) {
		super(status);
		setStatus(status);
		this.id = id;
		this.token = token;
	}
	
	public static final LoginResponse OK = new LoginResponse(Status.OK);

}

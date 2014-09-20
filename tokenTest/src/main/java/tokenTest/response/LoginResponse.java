package tokenTest.response;

import tokenTest.Util.Status;

public class LoginResponse {
	private Enum<Status> status;
	private String token;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public LoginResponse(Enum<Status> status) {
		super();
		this.status = status;
	}

	public LoginResponse(Enum<Status> status, String token) {
		super();
		this.status = status;
		this.token = token;
	}

	public LoginResponse(Enum<Status> status, Long id, String token) {
		super();
		this.status = status;
		this.id = id;
		this.token = token;
	}

}

package tokenTest.response;

import tokenTest.Util.Status;
import tokenTest.model.User;

public class LoginResponse extends StatusResponse {
	private String token;
	private Long id;
	private UserDetailInnerResponse user;

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
	
	public LoginResponse(Enum<Status> status, Long id, String token, User user) {
		this(status, id, token);
		this.user = new UserDetailInnerResponse(user, true, false);
	}
	
	public static final LoginResponse OK = new LoginResponse(Status.OK);

	public UserDetailInnerResponse getUser() {
		return user;
	}

	public void setUser(UserDetailInnerResponse user) {
		this.user = user;
	}

}

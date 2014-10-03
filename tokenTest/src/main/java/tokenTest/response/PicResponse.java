package tokenTest.response;

import tokenTest.Util.Status;

public class PicResponse {
	private Enum<Status> status;
	private String token;
	private Long id;
	
	public PicResponse(Enum<Status> status) {
		super();
		this.status = status;
	}
	public PicResponse(Enum<Status> status, String token, Long id) {
		super();
		this.status = status;
		this.token = token;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}

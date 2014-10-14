package tokenTest.response;

import tokenTest.model.User;

public class UserInfo {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserInfo(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public UserInfo(User user) {
		this.id = user.getId();
		this.name = user.getNickname();
	}

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
}

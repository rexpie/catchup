package tokenTest.response;

public class InnerLikeUser {
	public InnerLikeUser(String name, Integer age, String gender, Long id) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
	}

	public InnerLikeUser() {
	}

	public String name;
	public String gender;
	public Integer age;
	public Long id;
}
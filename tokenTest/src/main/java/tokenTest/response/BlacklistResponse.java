package tokenTest.response;

import java.util.List;

import tokenTest.Util.Status;

import com.google.common.collect.Lists;

public class BlacklistResponse extends StatusResponse {

	public List<InnerBlackList> users = Lists.newArrayList();
	
	public BlacklistResponse(Enum<Status> status) {
		super(status);
	}
	
	public void add(String name, Integer age, String gender, Long id) {
		users.add(new InnerBlackList(name, gender, age, id));
	}
}


class InnerBlackList{
	public String name;
	public String gender;
	public Integer age;
	public Long id;
	
	
	public InnerBlackList(String name, String gender, Integer age, Long id) {
		super();
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
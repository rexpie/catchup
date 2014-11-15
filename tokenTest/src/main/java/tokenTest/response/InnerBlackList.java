package tokenTest.response;

public 
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
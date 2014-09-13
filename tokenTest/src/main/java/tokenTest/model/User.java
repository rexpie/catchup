package tokenTest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6970201418868427060L;
	
	/*登录相关信息*/
	private Long id;
	private String token;
	private String password;
	
	/*必填信息*/
	private String nickname;
	private String phone_number;
	private Date birthday;
	private String sex;
	private String building;
	private Picture pic;	//头像
	private Set<Tag> tags;
	
	/*非必填信息*/
	private String company;
	private String role;
	private String email_address;
	
	/*系统信息*/
	private long zan_count;
	private Date update_time;
	private Date create_time;
	private int status;
	private Set<User> followings;
	
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String password, String nickname, String sex, String building,
			Date birthday, String phone_number) {
		super();
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.birthday = birthday;
		this.phone_number = phone_number;
	}

	public User(String password, String nickname, String sex, String building,
			String company, Date birthday, long zan_count, String phone_number,
			String email_address) {
		super();
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.company = company;
		this.birthday = birthday;
		this.zan_count = zan_count;
		this.phone_number = phone_number;
		this.email_address = email_address;
		this.status = 1;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "password", nullable = false, length = 128)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nickname", unique = true, nullable = false, length = 128)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pic_id", unique = true)
	public Picture getPic() {
		return pic;
	}

	public void setPic(Picture pic) {
		this.pic = pic;
	}

	@Column(name = "building", nullable = false, length = 128)
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	@Column(name = "company", length = 128)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "birthday", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "zan_count")
	public long getZan_count() {
		return zan_count;
	}

	public void setZan_count(long zan_count) {
		this.zan_count = zan_count;
	}

	@Column(name = "phone_number", unique = true, nullable = false, length = 128)
	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	@Column(name = "email_address", length = 128)
	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "friends", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id") })
	public Set<User> getFollowings() {
		return followings;
	}

	public void setFollowings(Set<User> followings) {
		this.followings = followings;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_tag", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Column(name = "token", length = 30)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "role", length = 30)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!this.id.equals(other.id))
            return false;
        return true;
    }

}

package tokenTest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.collect.Lists;

@Entity
@Table(name = "user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6970201418868427060L;

	/* 鐧诲綍鐩稿叧淇℃伅 */
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "token", length = 30)
	private String token;

	@Column(name = "password", nullable = false, length = 128)
	private String password;

	@Column(name = "login_attempts")
	private Integer login_attempts;

	/* 蹇呭～淇℃伅 */
	@Column(name = "nickname", unique = true, nullable = false, length = 128)
	private String nickname;

	@Column(name = "phone_number", unique = true, nullable = false, length = 128)
	private String phone_number;

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "sex", length = 1)
	private String sex;

	@Column(name = "building", nullable = true, length = 128)
	private String building;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "pic_id", unique = true)
	private Picture pic; // 澶村儚

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_tag", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<Tag> tags;

	/* 闈炲繀濉俊鎭�*/
	@Column(name = "company", length = 128)
	private String company;

	@Column(name = "job", length = 30)
	private String job;
	
	@Column(name = "industry", length = 30)
	private String industry;
//
//	@Column(name = "email_address", length = 128)
//	private String email_address;

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/* 绯荤粺淇℃伅 */
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_picture", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "picture_id") })
	@OrderBy("create_time DESC")
	private List<Picture> picture = Lists.newArrayList(); // 闈炲ご鍍忕収鐗�

	@Column(name = "zan_count")
	private long zan_count;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date update_time;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;

	@Column(name = "status")
	private int status;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "blacklist", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "blacklist_id") })
	private Set<User> blacklist;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "like_id") })
	private Set<User> likes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "viewers", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "viewer_id") })
	private Set<User> viewers;


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

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String password, String nickname, String sex, String building,
			String phone_number) {
		super();
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.phone_number = phone_number;
	}

	public User(String password, String nickname, String sex, String building,
			String company, Date birthday, long zan_count, String phone_number) {
		super();
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.building = building;
		this.company = company;
		this.birthday = birthday;
		this.zan_count = zan_count;
		this.phone_number = phone_number;
//		this.email_address = email_address;
		this.status = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Picture getPic() {
		return pic;
	}

	public void setPic(Picture pic) {
		this.pic = pic;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getZan_count() {
		return zan_count;
	}

	public void setZan_count(long zan_count) {
		this.zan_count = zan_count;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
//
//	public String getEmail_address() {
//		return email_address;
//	}
//
//	public void setEmail_address(String email_address) {
//		this.email_address = email_address;
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String role) {
		this.job = role;
	}

	public List<Picture> getPicture() {
		return picture;
	}

	public void setPicture(List<Picture> picture) {
		this.picture = picture;
	}

	public Integer getLogin_attempts() {
		return login_attempts;
	}

	public void setLogin_attempts(Integer login_attempts) {
		this.login_attempts = login_attempts;
	}

	public Set<User> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Set<User> blacklist) {
		this.blacklist = blacklist;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}

	public Set<User> getViewers() {
		return viewers;
	}

	public void setViewers(Set<User> viewers) {
		this.viewers = viewers;
	}
}

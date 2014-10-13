package tokenTest.model;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import tokenTest.Util.Constants;

@Entity
@Table(name = "meeting")
public class Meeting {
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private User owner;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;

	@Column(name = "genderConstraint", length = 1)
	private String genderConstraint;

	@Column(name = "seen_count")
	private int seen_count;

	@Column(name = "apply_count")
	private int apply_count;

	@ManyToOne(targetEntity = Shop.class)
	@JoinColumn(name = "shop_id")
	private Shop shop;

	@Column(name = "time_limit")
	private int time_limit = 0;

	@Column(name = "status")
	private int status = Constants.MEETING_STATUS_NEW;

	@Column(name = "description", length = 140)
	private String description = "";

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "participate", joinColumns = { @JoinColumn(name = "meeting_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> participator = new HashSet<User>();

	public Meeting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Meeting(User owner, Date create_time, Shop shop,
			String genderConstraint, String description) {
		super();
		this.owner = owner;
		this.create_time = create_time;
		this.shop = shop;
		this.genderConstraint = genderConstraint;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGenderConstraint() {
		return genderConstraint;
	}

	public void setGenderConstraint(String genderConstraint) {
		this.genderConstraint = genderConstraint;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getSeen_count() {
		return seen_count;
	}

	public void setSeen_count(int seen_count) {
		this.seen_count = seen_count;
	}

	public int getApply_count() {
		return apply_count;
	}

	public void setApply_count(int apply_count) {
		this.apply_count = apply_count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public int getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(int time_limit) {
		this.time_limit = time_limit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getParticipator() {
		return participator;
	}

	public void setParticipator(Set<User> participator) {
		this.participator = participator;
	}

	@Override
	public int hashCode() {
		final int prime = 43;
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
		final Meeting other = (Meeting) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}

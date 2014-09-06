package org.test1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "meeting")
public class Meeting {
	private long id;
	private User owner;
	private Date create_time;
	private Date start_time;
	private int man_limit;
	private int woman_limit;
	private int person_limit;
	private int seen_count;
	private int apply_count;
	private Shop shop;
	private int time_limit;
	private int status;
	private String description;

	public Meeting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Meeting(User owner, Date create_time, int man_limit,
			int woman_limit, int person_limit, int time_limit,
			String description) {
		super();
		this.owner = owner;
		this.create_time = create_time;
		this.man_limit = man_limit;
		this.woman_limit = woman_limit;
		this.person_limit = person_limit;
		this.time_limit = time_limit;
		this.description = description;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "owner_id")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "man_limit")
	public int getMan_limit() {
		return man_limit;
	}

	public void setMan_limit(int man_limit) {
		this.man_limit = man_limit;
	}

	@Column(name = "woman_limit")
	public int getWoman_limit() {
		return woman_limit;
	}

	public void setWoman_limit(int woman_limit) {
		this.woman_limit = woman_limit;
	}

	@Column(name = "person_limit")
	public int getPerson_limit() {
		return person_limit;
	}

	public void setPerson_limit(int person_limit) {
		this.person_limit = person_limit;
	}

	@Column(name = "seen_count")
	public int getSeen_count() {
		return seen_count;
	}

	public void setSeen_count(int seen_count) {
		this.seen_count = seen_count;
	}

	@Column(name = "apply_count")
	public int getApply_count() {
		return apply_count;
	}

	public void setApply_count(int apply_count) {
		this.apply_count = apply_count;
	}

	@ManyToOne(targetEntity = Shop.class)
	@JoinColumn(name = "shop_id")
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Column(name = "time_limit")
	public int getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(int time_limit) {
		this.time_limit = time_limit;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "description", length = 140)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

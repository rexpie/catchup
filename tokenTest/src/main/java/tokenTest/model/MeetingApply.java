package tokenTest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@EntityListeners(MeetingApply.class)
@Table(name = "meetingapply")
public class MeetingApply implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 3992077677951012769L;

	private Long id;

	private User fromUser;
	private Meeting toMeeting;
	private String applyContent;
	/* 0，新建，1，已批准，2已驳回 */
	private Integer status;
	private Date createTime;
	private Date updateTime;

	public MeetingApply(User fromUser, Meeting toMeeting, String applyContent) {
		super();
		this.fromUser = fromUser;
		this.toMeeting = toMeeting;
		this.applyContent = applyContent;
		this.status = 0;
	}

	public MeetingApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "from_id", nullable = false)
	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	@ManyToOne(targetEntity = Meeting.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "to_id", nullable = false)
	public Meeting getToMeeting() {
		return toMeeting;
	}

	public void setToMeeting(Meeting toMeeting) {
		this.toMeeting = toMeeting;
	}

	@Column(name = "applyContent")
	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@PrePersist
	protected void onCreate() {
		createTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updateTime = new Date();
	}
}

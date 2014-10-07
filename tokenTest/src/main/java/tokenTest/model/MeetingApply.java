package tokenTest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "meetingapply")
public class MeetingApply implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3992077677951012769L;

	private long id;

	private User fromUser;
	private Meeting toMeeting;
	private String applyContent;

	public MeetingApply(User fromUser, Meeting toMeeting, String applyContent) {
		super();
		this.fromUser = fromUser;
		this.toMeeting = toMeeting;
		this.applyContent = applyContent;
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
}

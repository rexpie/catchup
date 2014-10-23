package tokenTest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import tokenTest.Util.Constants;



@Entity
@Table(name = "complaint")
public class Complaint {

	@Column(name = "comment", length = 500)
	private String comment;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private User owner;

	@Column(name = "reason", length = 500)
	private String reason;
	
	@Column(name = "status")
	private int status = Constants.COMPLAINT_STATUS_NEW;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "target_id")
	private User target;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complaint other = (Complaint) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (create_time == null) {
			if (other.create_time != null)
				return false;
		} else if (!create_time.equals(other.create_time))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status != other.status)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	public String getComment() {
		return comment;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public Long getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public String getReason() {
		return reason;
	}

	public int getStatus() {
		return status;
	}

	public User getTarget() {
		return target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((create_time == null) ? 0 : create_time.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + status;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTarget(User target) {
		this.target = target;
	}
}

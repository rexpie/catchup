package tokenTest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "friendapply")
public class FriendApply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769798290600705970L;
	private long id;

	/* …Í«Î’ﬂ */
	private User fromUser;
	private User toUser;
	/* …Í«Îƒ⁄»› */
	private String applyContent;

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
	@JoinColumn(name = "from_id", nullable = false)
	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "to_id", nullable = false)
	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	@Column(name = "applyContent")
	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
}

package tokenTest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "picture")
public class Picture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9000760920068463356L;

	private Long id;
	/* private User owner; */
	private String description;
	private String name;
	private Date create_time;
	private String filename;

	public Picture() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Picture(Date create_time) {
		super();
		this.create_time = create_time;
	}

	public Picture(Date create_time, String description) {
		super();
		this.create_time = create_time;
		this.description = description;
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

	/*
	 * @ManyToOne(targetEntity = User.class)
	 * 
	 * @JoinColumn(name = "owner_id", nullable = false) public User getOwner() {
	 * return owner; }
	 * 
	 * public void setOwner(User owner) { this.owner = owner; }
	 */

	@Column(name = "create_time")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "description", length = 140)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "name", length = 140)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "filename", length = 140)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 37;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Picture other = (Picture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}

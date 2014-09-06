package org.test1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "picture")
public class Picture implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9000760920068463356L;
	private long id;
	private Date create_time;
	private String description;
	private String name;

	public Picture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Picture(Date create_time, String description, String name) {
		super();
		this.create_time = create_time;
		this.description = description;
		this.name = name;
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
}

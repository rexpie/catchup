package org.test1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7191685113122856284L;
	private String roleName;

	private long id;

	public Role(RoleMapping roleName){
		this.roleName = roleName.name();
	}
	
	public Role() {
		this(RoleMapping.ROLE_USER);
	}
	
	@Id
	@Column
	public long getRoleId() {
		return id;
	}

	@Column
	public String getRoleName() {
		return roleName;
	}

	public void setRoleId(long roleId) {
		this.id = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

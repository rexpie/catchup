package org.test1.model;

public enum RoleMapping {
	ROLE_USER(1), ROLE_ADMIN(2), ROLE_SUPERUSER(3);

	int roleId;

	RoleMapping(int id) {
		roleId = id;
	}
	
	int getId(){
		return roleId;
	}
}

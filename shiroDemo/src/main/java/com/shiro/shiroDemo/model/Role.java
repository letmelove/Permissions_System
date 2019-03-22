package com.shiro.shiroDemo.model;

import java.util.HashSet;
import java.util.Set;

public class Role {
	
	private Integer rid;
	
	private String name;
	
	private Set<Permissions> permissions = new HashSet<>();
	
	private Set<User> user = new HashSet<>();
	
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Permissions> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permissions> permissions) {
		this.permissions = permissions;
	}
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}
	
}

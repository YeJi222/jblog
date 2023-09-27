package com.poscodx.jblog.vo;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UserVo {
	@NotEmpty
	@Length(min=2, max=15)
	private String id;
	
	@NotEmpty
	@Length(min=4, max=16)
	private String password;
	
	@NotEmpty
	@Length(min=2, max=8)
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", password=" + password + ", name=" + name + "]";
	}
}

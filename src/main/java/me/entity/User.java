/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import me.entity.common.DataEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sys_user")
public class User extends DataEntity {
	
	
	private String loginName;    
	
	private String name;
	
	private String plainPassword;
	
	private String password;
	
	private String salt;
	
	/**
	 * 所属部门
	 */
	private Long officeId;
	private String officeName;
	
	private String email;
	
	private String phone;
	
	
	
	

	@Transient
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="login_Name")
	@NotBlank
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@Column(name="name")
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="password")
	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
//	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="salt")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

//	@Transient
//	@JsonIgnore
//	public List<String> getRoleList() {
//		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
//		return ImmutableList.copyOf(StringUtils.split(roles, ","));
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import me.entity.common.DataEntity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "sys_office")
public class Office extends DataEntity {
	
	private Long parentId;
	
//	private Office parent;	// 父级编号
	private String parentIds ; // 所有父级编号
	
	private String name;
	private String type;
	private String grade;
	private String address;
	private String zipCode;
	private String master;
	private String phone;
	private String fax;
	private String email;
	
	private String officeCode;
	/**
	 * 拼音码
	 */
	private String spellCode;
	
	private String leaf;
	
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	//不适合ext
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="parent_id")
//	@NotFound(action = NotFoundAction.IGNORE)
//	@NotNull
//	public Office getParent() {
//		return parent;
//	}
//	public void setParent(Office parent) {
//		this.parent = parent;
//	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getSpellCode() {
		return spellCode;
	}
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
//	@Column(name="parent_id")
//	public Long getParentId() {
//		return parentId;
//	}
//	public void setParentId(Long parentId) {
//		this.parentId = parentId;
//	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="grade")
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="zip_code")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Column(name="master")
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
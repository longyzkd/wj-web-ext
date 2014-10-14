/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import me.entity.common.DataEntity;
import me.utils.Comboable;


/**
 * 指标项
 * @author wj
 *
 */
@Entity
@Table(name = "base_zbx")
public class Zbx extends DataEntity implements Comboable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 指标项名称
	 */
	private String zbxMc;
	/**
	 * 上传周期
	 */
	private String uploadCycle;
	private String propertyText;
	private String propertyName;
	private String entityName;
	
	private String propertyJson;
	
	private Long officeId;
	@Transient
	public String getPropertyJson() {
		return propertyJson;
	}
	public void setPropertyJson(String propertyJson) {
		this.propertyJson = propertyJson;
	}
	public String getZbxMc() {
		return zbxMc;
	}
	public void setZbxMc(String zbxMc) {
		this.zbxMc = zbxMc;
	}
	public String getUploadCycle() {
		return uploadCycle;
	}
	public void setUploadCycle(String uploadCycle) {
		this.uploadCycle = uploadCycle;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getPropertyText() {
		return propertyText;
	}
	public void setPropertyText(String propertyText) {
		this.propertyText = propertyText;
	}
	
	public Long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}
	/**
	 * 指标项名称唯一
	 */
	@Transient
	public String getCode() {
		return ""+this.zbxMc;
	}
	@Transient
	public String getName() {
		return this.zbxMc;
	}
	
	private String officeName;
	@Transient
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	
	
}
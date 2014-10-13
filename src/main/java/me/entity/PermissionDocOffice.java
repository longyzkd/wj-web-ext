/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import me.entity.common.IdEntity;

/**
 * 文档部门数据权限
 * @author wj
 *
 */
@Entity
@Table(name = "permission_doc_office")
public class PermissionDocOffice extends IdEntity {
	
	private String wdMc;
	private Long uploadId;
	private Long officeId;
	
	public Long getUploadId() {
		return uploadId;
	}
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
	public String getWdMc() {
		return wdMc;
	}
	public void setWdMc(String wdMc) {
		this.wdMc = wdMc;
	}
	public Long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}
	
}
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
@Table(name = "sys_user_role")
public class PermissionDocOffice extends IdEntity {
	
	private String wdMc;
	private Long officeId;
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
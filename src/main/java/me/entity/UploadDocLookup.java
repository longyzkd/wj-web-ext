/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import me.entity.common.IdEntity;


/**
 * 上传文档
 * @author wj
 *
 */
@Entity
@Table(name = "base_upload_lookup")
public class UploadDocLookup extends IdEntity {
	
	
	/**
	 * 指标项名称
	 */
	private String zbxMc;
	/**
	 * 文档名称
	 */
	private String wdMc;
	/**
	 * 上传时间
	 */
	private Date createDate;
	/**
	 * 上传人
	 */
	private String createBy;
	public String getZbxMc() {
		return zbxMc;
	}
	public void setZbxMc(String zbxMc) {
		this.zbxMc = zbxMc;
	}
	public String getWdMc() {
		return wdMc;
	}
	public void setWdMc(String wdMc) {
		this.wdMc = wdMc;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
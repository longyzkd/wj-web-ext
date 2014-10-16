/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import me.entity.common.IdEntity;
import me.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


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
	
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	
	private Long officeId;
	public Long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}
	
	
	private String from;
	
	private String to;
	@Transient
//	@DateTimeFormat(pattern="yyyy-MM-dd")  
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@Transient
//	@DateTimeFormat(pattern="yyyy-MM-dd")  
	public String getTo() {
//		if(StringUtils.isEmpty(to)){
//			return (DateUtils.getDate("yyyy-MM-dd")) ;
//		}
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
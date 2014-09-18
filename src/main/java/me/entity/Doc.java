/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "t_doc")
public class Doc extends IdEntity {
	
	/**
	 * 指标项名称
	 */
	
	private String zbxmc;
	
	
	private Date uploadTime;
	
	/**
	 * 文档名称
	 */
	private String docName;
	
	/**
	 * 文档上传者
	 */
	
	private String docOfferer;
	
	
	private String status;
	
	
	private long downloadCounts;


	@Column(name="zbxmc")
	public String getZbxmc() {
		return zbxmc;
	}

	public void setZbxmc(String zbxmc) {
		this.zbxmc = zbxmc;
	}
	@Column(name="doc_name")
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	@Column(name="doc_Offerer")
	public String getDocOfferer() {
		return docOfferer;
	}

	public void setDocOfferer(String docOfferer) {
		this.docOfferer = docOfferer;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="download_Counts")
	public long getDownloadCounts() {
		return downloadCounts;
	}

	public void setDownloadCounts(long downloadCounts) {
		this.downloadCounts = downloadCounts;
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name="upload_Time")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
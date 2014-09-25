/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package me.entity.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import me.service.accout.ShiroDbRealm.ShiroUser;

import org.apache.shiro.SecurityUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据Entity类,带有主键 如果不需要数据, 可以直接继承IdEntity
 * @author wj
 */
@MappedSuperclass
public abstract class DataEntity extends IdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;// 更新日期

	public DataEntity() {
		super();
	}
	
	@PrePersist
	public void prePersist(){
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		if (user.id!=null){
			this.updateBy = user.loginName;
			this.createBy = user.loginName;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	@PreUpdate
	public void preUpdate(){
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		if (user.id !=null){
			this.updateBy = user.loginName;
		}
		this.updateDate = new Date();
	}

	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="create_by")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Column(name="create_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="update_by")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Column(name="update_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
	

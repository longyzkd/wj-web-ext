/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.entity.common.IdEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;


@Entity
@Table(name = "sys_menu")
public class Menu extends IdEntity {
	

	private String component;
	private String description;
	private String iconCls;
	private String text;
	private Integer sort;
	private String type;
	private Long parentId;
	private String leaf;
	
	@Column(name="component")
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="iconCls")
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	@Column(name="text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column(name="sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(name="leaf")
	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
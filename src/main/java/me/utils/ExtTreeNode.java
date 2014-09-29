package me.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 
 * @author wj
 *
 */
public class ExtTreeNode {

	private Long id;
	private Long parentId;
	private String text;
	private boolean leaf;
	private String iconCls;
	private List<ExtTreeNode> children = Lists.newArrayList();
	private ExtTreeNode  parent;
	
	@JsonIgnore
	public ExtTreeNode getParent() {
		return parent;
	}
	public void setParent(ExtTreeNode parent) {
		this.parent = parent;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	@JsonIgnore
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public void addChild(ExtTreeNode node) {
	    if (children == null) {
	        children = Lists.newArrayList();
	    }
	    children.add(node);
	}
	public List<ExtTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<ExtTreeNode> children) {
		this.children = children;
	}
	
	
}

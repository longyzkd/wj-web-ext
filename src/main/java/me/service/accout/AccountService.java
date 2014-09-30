/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.service.accout;

import java.util.List;
import java.util.Map;

import me.entity.Office;
import me.entity.User;
import me.repository.account.UserDao;
import me.repository.common.Page;
import me.repository.office.OfficeDao;
import me.service.accout.ShiroDbRealm.ShiroUser;
import me.utils.ExtTreeNode;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OfficeDao officeDao;

	@Transactional(readOnly=true)
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}


	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	



	public Page<User> getUsers(Page<User> page, User user) {
		return userDao.findUsers(page,user);
	}


	public List<Office> getOfficeNodesBy(String id) {
		return officeDao.findOfficesBy(id);
	}


	/**
	 * 保存
	 * @param user
	 */
	public void create(User user) {
		entryptPassword(user);
		userDao.save(user);
		
	}
	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
		
	}

	public void del(User u) {
		if(u != null && u.getId()!=null){
			userDao.delete(u.getId());
		}else{
			throw new RuntimeException();
		}
		
	}
	public void del(List<User> beanList) {
			if(!CollectionUtils.isEmpty(beanList)){
				for(User user:beanList){
					del(user);
				}
			}
			
		
	}


	public List<ExtTreeNode> getAllOfficeNodes() {
//		public List<Map<String, Object>> getOfficeNodes() {
		List<Office> list =  officeDao.findAll();
		if(CollectionUtils.isEmpty(list)){
			return Lists.newArrayList();
		}
		
		List<ExtTreeNode> mapList = Lists.newArrayList();
//		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			//树
//			Map<String, Object> map = Maps.newHashMap();
//			map.put("id", String.valueOf(e.getId()));
//			map.put("parentId", String.valueOf(e.getParentId()==null?"":e.getParentId()));
//			map.put("text", e.getName());
			ExtTreeNode node = new ExtTreeNode();
			node.setId(e.getId());
//			node.setParentId(e.getParent().getId());
			node.setParentId(e.getParentId());
			node.setText(e.getName());
			node.setLeaf(Boolean.parseBoolean(e.getLeaf()));
			mapList.add(node);
//			mapList.add(map);
		}
		return mapList;
	}


	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.updatePwd(user);
		
	}


	public List<Office> getAllOffices() {
		return  officeDao.findAll();
	}


	public Office getOffice(Long parentId) {
		// TODO Auto-generated method stub
		if(parentId ==null){
			return null;
		}
		return officeDao.get(parentId);
	}


//	public void update(User user) {
//		userDao.updateUser(user);
//		
//	}
}

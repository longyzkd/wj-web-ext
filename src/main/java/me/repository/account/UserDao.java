/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.account;

import me.entity.User;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.repository.common.Parameter;
import me.service.accout.ShiroDbRealm.ShiroUser;
import me.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends CommonDao<User>  {
	
	public User findByLoginName(String loginName){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("loginName", loginName));
		return find(detachedCriteria).get(0);
		
		
		
	}

	public Page<User> findUsers(Page<User> page, User user) {

		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(User.class);
		if(!StringUtils.isBlank(user.getName())){
			detachedCriteria.add(Restrictions.eq("name", user.getName()));
		}
		if(!StringUtils.isBlank(user.getLoginName())){
			detachedCriteria.add(Restrictions.eq("loginName", user.getLoginName()));
		}
		return find(page, detachedCriteria);
	
	}

	public void updatePwd(User user) {
		ShiroUser u = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		Parameter p = new Parameter(user.getPassword(),DateUtils.parseDate(DateUtils.getDate()),u.loginName,user.getId());
		update("update User set password = :p1 ,updateDate=:p2 ,updateBy=:p3  where id = :p4", p);
		
	}

	
}

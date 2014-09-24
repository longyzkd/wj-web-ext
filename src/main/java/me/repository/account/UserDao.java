/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.account;

import me.entity.User;
import me.repository.common.CommonDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends CommonDao<User>  {
	
	public User findByLoginName(String loginName){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("loginName", loginName));
		return find(detachedCriteria).get(0);
		
		
		
	};
}

/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.account;

import me.entity.User;
import me.repository.CommonDao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImp extends CommonDao implements UserDao {
	
	public User findByLoginName(String loginName){
		User   i = (User)currentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("loginName", loginName))
				.uniqueResult();
		currentSession().flush();
		return i;
		
		
		
	};
}

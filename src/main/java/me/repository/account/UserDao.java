/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.account;

import me.entity.User;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao  {
	User findByLoginName(String loginName);
}

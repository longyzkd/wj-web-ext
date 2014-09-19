/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.resource;

import java.util.List;

import me.entity.Resource;
import me.repository.CommonDao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class ResourceDaoImp extends CommonDao implements ResourceDao {
	
	
	@SuppressWarnings("unchecked")
	public List<Resource> findPanels() {
		return (List<Resource>)currentSession()
										.createCriteria(Resource.class)
										.add(Restrictions.isNull("parentId"))
										.addOrder(Order.asc("sort"))
										.list();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> findNodes(String id) {
		return (List<Resource>)currentSession()
								.createCriteria(Resource.class)
								.add(Restrictions.eq("parentId", Long.valueOf(id)))
								.addOrder(Order.asc("sort"))
								.list();
	}


	
}

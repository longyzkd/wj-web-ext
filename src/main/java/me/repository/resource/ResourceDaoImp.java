/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.resource;

import java.util.List;

import me.entity.Menu;
import me.repository.CommonDao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class ResourceDaoImp extends CommonDao implements ResourceDao {
	
	
	@SuppressWarnings("unchecked")
	public List<Menu> findPanels(Long userId) {
//		return (List<Menu>)currentSession()
//										.createCriteria(Menu.class)
//										.add(Restrictions.isNull("parentId"))
//										.addOrder(Order.asc("sort"))
//										.list();
		
		StringBuilder hql  =  new StringBuilder();
		
		hql.append(" select m from Menu m ,User u ,Role r , UserRole ur ,RoleMenu rm where u.id=? and u.id = ur.userId and ur.roleId = r.id and r.id = rm.roleId and rm.menuId = m.id ");
		return currentSession().createQuery(hql.toString()).setParameter(0, userId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Menu> findNodes(String id) {
		return (List<Menu>)currentSession()
								.createCriteria(Menu.class)
								.add(Restrictions.eq("parentId", Long.valueOf(id)))
								.addOrder(Order.asc("sort"))
								.list();
	}


	
}

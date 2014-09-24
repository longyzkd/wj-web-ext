/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.office;

import me.entity.Menu;
import me.entity.Office;
import me.repository.common.CommonDao;
import me.repository.common.Page;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class OfficeDaoImp extends CommonDao<Office> implements OfficeDao {
	
	
	public Page<Office> findOffices(Page<Office> pageObj, Office office) {
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Office.class);
		if(!StringUtils.isBlank(office.getName())){
			detachedCriteria.add(Restrictions.eq("name", office.getName()));
		}
		if(!StringUtils.isBlank(office.getOfficeCode())){
			detachedCriteria.add(Restrictions.eq("officeCode", office.getOfficeCode()));
		}
		return find(pageObj, detachedCriteria);
	}
	


	
}

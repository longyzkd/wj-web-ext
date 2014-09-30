/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.office;

import java.util.List;

import me.entity.Office;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.repository.common.Parameter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class OfficeDao extends CommonDao<Office>  {
	
	
	public Page<Office> findOffices(Page<Office> pageObj, Office office) {
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Office.class);
		if(!StringUtils.isBlank(office.getName())){
			detachedCriteria.add(Restrictions.like("name", "%"+office.getName()+"%"));
		}
		if(!StringUtils.isBlank(office.getOfficeCode())){
			detachedCriteria.add(Restrictions.like("officeCode", "%"+office.getOfficeCode()+"%"));
		}
		detachedCriteria.add(Restrictions.isNotNull("parentId"));//政府  根节点 排除在外
//		detachedCriteria.add(Restrictions.isNotNull("parent.id"));//政府  根节点 排除在外
		return find(pageObj, detachedCriteria);
	}

	
	public List<Office> findOfficesBy(String id) {
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Office.class);
		if(!StringUtils.isEmpty(id)){
			detachedCriteria.add(Restrictions.eq("parentId", Long.valueOf(id)));
		}else{
			detachedCriteria.add(Restrictions.isNull("parentId"));
		}
		return find(detachedCriteria);
	}


	public List<Office> findByParentIdsLike(String parentIds) {
		return find("from Office where parentIds like :p1", new Parameter(parentIds));
	}


	
	


	
}

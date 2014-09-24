/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.docShared;

import java.util.List;

import me.entity.Doc;
import me.entity.Menu;
import me.repository.common.CommonDao;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

@Component
public class DocSharedDao extends CommonDao<Doc>  {

	public List<Doc> findDocs() {
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Doc.class);
		return find(detachedCriteria);
	}
	
}

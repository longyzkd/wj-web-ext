/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.docShared;

import java.util.List;

import me.entity.Doc;
import me.repository.CommonDao;

import org.springframework.stereotype.Component;

@Component
public class DocSharedDaoImp extends CommonDao implements DocSharedDao {

	@SuppressWarnings("unchecked")
	public List<Doc> findDocs() {
		return  currentSession()
				.createCriteria(Doc.class).list();
	}
	
}

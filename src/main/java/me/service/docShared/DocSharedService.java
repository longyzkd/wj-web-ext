/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.service.docShared;

import java.util.List;

import me.entity.Doc;
import me.repository.docShared.DocSharedDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class DocSharedService {


	private static Logger logger = LoggerFactory.getLogger(DocSharedService.class);
	
	@Autowired
	private DocSharedDao dao;

	@Transactional(readOnly=true)
	public List<Doc> getDocs() {
		return dao.findDocs();
	}


}

/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.dataPermission;

import java.util.List;
import java.util.Map;

import me.entity.PermissionDocOffice;
import me.entity.UploadDocLookup;
import me.repository.common.Page;
import me.service.dataPermission.DataPermService;
import me.utils.ExtUtils;
import me.web.CommonController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;


/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/
 * Create page : GET /task/create
 * Create action : POST /task/create
 * Update page : GET /task/update/{id}
 * Update action : POST /task/update
 * Delete action : GET /task/delete/{id}
 * 
 * @author wj
 */
@Controller
@RequestMapping(value = "/dataPerm")
public class DataPermController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(DataPermController.class);
	
	private static JsonMapper mapper = new JsonMapper(Include.ALWAYS);

	@Autowired
	private DataPermService service;

	
	
	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<UploadDocLookup> page,UploadDocLookup doc) {

		page = service.getAllDocs(page, doc);
		
		
		return ExtUtils.listToMap(page);
	}
	@RequestMapping(value="getPermited" ,method = RequestMethod.POST)
	public @ResponseBody  Object getPermited(Long nodeId) {
		try{
			
			List<UploadDocLookup> docs = service.getDocs(nodeId);
			
			return ExtUtils.toMap(docs);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
		
		
	}
	
	@RequestMapping(value="edit" ,method = RequestMethod.POST)
	public @ResponseBody  Object edit(@RequestBody Map<String,Object> map) {
		try {
			Long officeId = Long.valueOf(""+map.get("officeId"));
			
			JavaType beanListType = mapper.contructCollectionType(List.class, UploadDocLookup.class);
			List<UploadDocLookup>  docs  = mapper.fromJson((String)map.get("docsJson"), beanListType);
			service.editDataPermission(officeId,docs);
			
			return ExtUtils.mapOK("编辑成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
		
	}
	
	
}


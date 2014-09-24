/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.resource;

import java.util.Map;

import me.entity.Office;
import me.repository.common.Page;
import me.service.docShared.DocSharedService;
import me.service.resource.ResourceService;
import me.utils.DataWrapper;
import me.utils.ExtJSReturn;
import me.web.CommonController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;


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
 * @author calvin
 */
@Controller
@RequestMapping(value = "/sys/office")
public class OfficeController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(OfficeController.class);
	

	@Autowired
	private ResourceService service;

	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<Office> page,Office office) {

		page = service.getOffices(page,office);
		
		
		return ExtJSReturn.listToMap(page);
	}
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Object delete(@RequestBody DataWrapper<Office> office)  {
		try{
//			service.del(office);
			
			Map<String,Object> modelMap = Maps.newHashMap();
			modelMap.put("success", true);
			return modelMap;

		} catch (Exception e) {//TODO 做成过滤器
			logger.error("删除出错");
			return ExtJSReturn.mapError("删除出错");
		}
	}
	
	
}


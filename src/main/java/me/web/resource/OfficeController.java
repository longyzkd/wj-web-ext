/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.resource;

import me.entity.Office;
import me.repository.common.Page;
import me.service.resource.ResourceService;
import me.utils.ExtJSReturn;
import me.web.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


	

	@Autowired
	private ResourceService service;

	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(int page,int limit,Office office) {

		System.out.println("-----------------------------------");  
		System.out.println("page=="+page);  
		System.out.println("limit=="+limit);//pagesize  
		System.out.println("-----------------------------------");  
		
		Page<Office> pageObj = new Page<Office>();
		pageObj.setPageNo(page);
		pageObj.setPageSize(limit);
		pageObj = service.getOffices(pageObj,office);
		return ExtJSReturn.listToMap(pageObj);
	}
	
	
	
}


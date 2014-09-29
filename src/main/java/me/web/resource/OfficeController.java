/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.resource;

import java.util.List;
import java.util.Map;

import me.entity.Office;
import me.repository.common.Page;
import me.service.resource.ResourceService;
import me.utils.DataWrapper;
import me.utils.ExtUtils;
import me.web.CommonController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

import com.fasterxml.jackson.databind.JavaType;
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
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

	@Autowired
	private ResourceService service;

	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<Office> page,Office office) {

		page = service.getOffices(page,office);
		
		
		return ExtUtils.listToMap(page);
	}
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Object delete(@RequestBody String officesJson)  {
		try{
			
			JavaType beanListType = mapper.contructCollectionType(List.class, Office.class);
			List<Office> beanList = mapper.fromJson(wrap(officesJson), beanListType);
			service.del(beanList);
			return ExtUtils.mapOK("刪除成功");
//			throw new Exception();

		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
	}
	@RequestMapping(value="create",method = RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody Office office)  {
		try{
			
			service.create(office);
			return ExtUtils.mapOK("新增成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
	}
	@RequestMapping(value="edit",method = RequestMethod.POST)
	public @ResponseBody Object edit(@RequestBody Office office)  {
		try{
			
			service.create(office);
			return ExtUtils.mapOK("修改成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	
	private String wrap(String s){
		if( s.charAt(0)== '['  ){
			return s;
		}else{
			return "["+s+"]";
		}
	}
	
}


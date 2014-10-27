/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.entity.Office;
import me.entity.User;
import me.entity.Zbx;
import me.repository.common.Page;
import me.service.zbx.ZbxService;
import me.utils.ExtTreeNode;
import me.utils.ExtUtils;
import me.utils.StringUtils;
import me.web.CommonController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Reflections;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;


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
@RequestMapping(value = "/lookup/zbx")
public class ZbxController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(ZbxController.class);
	
	private static JsonMapper mapper = new JsonMapper(Include.ALWAYS);

	@Autowired
	private ZbxService service;

	
	
	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<Zbx> page,Zbx zbx) {

		page = service.getList(page,zbx);
		
		
		return ExtUtils.listToMap(page);
	}
	@RequestMapping(value="getEntitys" ,method = RequestMethod.GET)
	public @ResponseBody  Object getEntitys() {
		

		List<Zbx> data = service.getEntitys();
		
		return ExtUtils.toMap(data);
	}
	@RequestMapping(value="getProperty" ,method = RequestMethod.POST)
	public @ResponseBody  Object getProperty(String entityName) {
		
		
//		Zbx zbx = new Zbx();
//		zbx.setPropertyName("name1");
		List<Zbx> data = service.getPropertys(entityName);
		
		return ExtUtils.toMap(data);
	}
//	
//	@RequestMapping(value="delete",method = RequestMethod.POST)
//	public @ResponseBody Object delete(@RequestBody String usersJson)  {
//		try{
//			
//			JavaType beanListType = mapper.contructCollectionType(List.class, User.class);
//			List<User> beanList = mapper.fromJson(StringUtils.wrap(usersJson), beanListType);
//			service.del(beanList);
//			return ExtUtils.mapOK("刪除成功");
////			throw new Exception();
//
//		} catch (Exception e) {//TODO 做成过滤器
//			logger.error(e.getMessage());
//			return ExtUtils.mapError("系统错误");
//		}
//	}
	@RequestMapping(value="edit",method = RequestMethod.POST)
	public @ResponseBody Object edit(@RequestBody Zbx zbx)  {
		try{
//			String s = zbx;
//			service.create(user);
			String propertyJson = zbx.getPropertyJson();
			String entityName = zbx.getEntityName();
			String zbxMc = zbx.getZbxMc();
			String uploadCycle = zbx.getUploadCycle();
			Long officeId  = zbx.getOfficeId();
//			Class entityClass = Class.forName(entityName);
//		    Object obj =  mapper.fromJson(propertyJson, entityClass);
		    Map<String, Object> map = mapper.fromJson(propertyJson, HashMap.class) ;
		    
		    List<Zbx>  zbxList = service.getZbxList(entityName);
		    Assert.notEmpty(map);
		    
		    for(Zbx zbx1 :zbxList ){
		    	zbx1.setPropertyText((String)map.get(zbx1.getPropertyName()));
		    	zbx1.setZbxMc(zbxMc);
		    	zbx1.setUploadCycle(uploadCycle);
		    	zbx1.setOfficeId(officeId);
		    	service.create(zbx1);
		    }
			return ExtUtils.mapOK("编辑成功");
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	
	
}


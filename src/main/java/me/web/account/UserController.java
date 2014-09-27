/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.account;

import java.util.List;

import me.entity.Office;
import me.entity.User;
import me.repository.common.Page;
import me.service.accout.AccountService;
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
import org.springside.modules.mapper.JsonMapper;

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
 * @author calvin
 */
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

	@Autowired
	private AccountService service;

	
	
	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<User> page,User user) {

		page = service.getUsers(page,user);
		
		
		return ExtJSReturn.listToMap(page);
	}
	@RequestMapping(value="getOfficeNodesOf" ,method = RequestMethod.GET)
	public @ResponseBody  Object getOfficeNodesOf (String id) {
		
		List<Office> offices = service.getOfficeNodesBy(id);
		
		
		return ExtJSReturn.listToMap(offices);
	}
//	@RequestMapping(value="delete",method = RequestMethod.POST)
//	public @ResponseBody Object delete(@RequestBody String usersJson)  {
//		try{
//			
//			JavaType beanListType = mapper.contructCollectionType(List.class, Office.class);
//			List<User> beanList = mapper.fromJson(wrap(usersJson), beanListType);
//			service.del(beanList);
//			return ExtJSReturn.mapOK("刪除成功");
////			throw new Exception();
//
//		} catch (Exception e) {//TODO 做成过滤器
//			logger.error(e.getMessage());
//			return ExtJSReturn.mapError("系统错误");
//		}
//	}
	@RequestMapping(value="create",method = RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody User user)  {
		try{
			
			service.create(user);
			return ExtJSReturn.mapOK("新增成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtJSReturn.mapError("系统错误");
		}
	}
	@RequestMapping(value="edit",method = RequestMethod.POST)
	public @ResponseBody Object edit(@RequestBody User user)  {
		try{
			
			service.create(user);
			return ExtJSReturn.mapOK("修改成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtJSReturn.mapError("系统错误");
		}
	}
//	
//	private String wrap(String s){
//		if( s.charAt(0)== '['  ){
//			return s;
//		}else{
//			return "["+s+"]";
//		}
//	}
	
}


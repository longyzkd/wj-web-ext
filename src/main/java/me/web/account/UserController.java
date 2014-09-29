/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.entity.Office;
import me.entity.User;
import me.repository.common.Page;
import me.service.accout.AccountService;
import me.utils.ExtUtils;
import me.utils.ExtTreeNode;
import me.utils.StringUtils;
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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
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
@RequestMapping(value = "/sys/user")
public class UserController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static JsonMapper mapper = new JsonMapper(Include.ALWAYS);

	@Autowired
	private AccountService service;

	
	
	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<User> page,User user) {

		page = service.getUsers(page,user);
		
		
		return ExtUtils.listToMap(page);
	}
	@RequestMapping(value="getOfficeNodesOf" ,method = RequestMethod.GET)
	public @ResponseBody  Object getOfficeNodesOf (String id) {
		
		List<Office> offices = service.getOfficeNodesBy(id);
		
		
		return ExtUtils.listToMap(offices);
	}
	@RequestMapping(value="getAllOfficeNodes" ,method = RequestMethod.GET)
	public @ResponseBody  Object getAllOfficeNodes () {
		
		List<ExtTreeNode> offices = service.getOfficeNodes();
		
		
		return ExtUtils.toComplexJson(offices);
	        
	        
	        
		
	}
//	private Map<String, Object> toComplexJson(List<ExtTreeNode> offices) {
//		
//		Map<Long, ExtTreeNode> lookup = Maps.newHashMap();
//		
//		for (ExtTreeNode o : offices) {
//			lookup.put(o.getId(), o);
//		}
//		Set<Long> keySet = lookup.keySet();
//		for (Long id : keySet) {
//			ExtTreeNode value = lookup.get(id);
//			Long parentId = value.getParentId();
//			ExtTreeNode parentNode = lookup.get(parentId);
//			if (parentNode != null) {
//				parentNode.addChild(value);
//				value.setParent(parentNode);
//			}
//		}
//		for (Long id : keySet) {
//			ExtTreeNode value = lookup.get(id);
//			if (value.getParent() == null) {
////				String s = mapper.toJson(value);
//				return ExtJSReturn.toMap(value);
//			}
//		}
//		return null;
//	}
	
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Object delete(@RequestBody String usersJson)  {
		try{
			
			JavaType beanListType = mapper.contructCollectionType(List.class, User.class);
			List<User> beanList = mapper.fromJson(StringUtils.wrap(usersJson), beanListType);
			service.del(beanList);
			return ExtUtils.mapOK("刪除成功");
//			throw new Exception();

		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
	}
	@RequestMapping(value="create",method = RequestMethod.POST)
	public @ResponseBody Object create(@RequestBody User user)  {
		try{
			
			service.create(user);
			return ExtUtils.mapOK("新增成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	/**
	 * model api 提交 json
	 * @param user
	 * @return
	 */
	@RequestMapping(value="edit",method = RequestMethod.POST)
	public @ResponseBody Object edit(@RequestBody User user)  {
		try{
			
			service.update(user);
			return ExtUtils.mapOK("修改成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	/**
	 * form提交
	 * @param user
	 * @return
	 */
	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
	public @ResponseBody Object updatePwd( User user)  {
		try{
			
			service.updatePwd(user);
			return ExtUtils.mapOK("修改成功");
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	
	
	
}


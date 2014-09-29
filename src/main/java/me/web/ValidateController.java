/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web;

import me.entity.Office;
import me.service.resource.ResourceService;
import me.utils.ExtUtils;
import me.web.resource.OfficeController;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/validate")
public class ValidateController {
	private static Logger logger = LoggerFactory.getLogger(ValidateController.class);
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

	@Autowired
	private ResourceService service;
	
	/**
	 * 
	 * @param beanClazz
	 * @param property
	 * @param val 参数暂仅支持String
	 * @param action 新增还是修改
	 * @return
	 */
	@RequestMapping(value="checkunique",method = RequestMethod.POST)
	public @ResponseBody Object checkunique(String beanClazz,String property,String val , String rawValue ,String action)  {
		try{
			boolean exist = CollectionUtils.isEmpty(service.getEntityBy(beanClazz,property,val,rawValue,action))?false:true;
			return ExtUtils.mapValidate(exist);
//			throw new Exception();
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
}

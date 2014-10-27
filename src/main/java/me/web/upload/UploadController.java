/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.upload;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import me.entity.UploadDocLookup;
import me.entity.User;
import me.entity.Zbx;
import me.entity.data.fgj.Wshtba;
import me.repository.common.Page;
import me.service.upload.UploadService;
import me.utils.BusinessException;
import me.utils.Constants;
import me.utils.DateUtils;
import me.utils.ExtUtils;
import me.utils.FileUploadBean;
import me.utils.StringUtils;
import me.utils.excel.ImportExcelme;
import me.utils.excel.Uploadable;
import me.web.CommonController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Reflections;

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
@RequestMapping(value = "/upload")
public class UploadController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	private static JsonMapper mapper = new JsonMapper(Include.ALWAYS);

	@Autowired
	private UploadService service;

	
	@RequestMapping(value="list" ,method = RequestMethod.GET)
	public @ResponseBody  Object list(Page<UploadDocLookup> page,UploadDocLookup doc) {

		try {
			page = service.getAllDocs(page, doc);
			
			
			return ExtUtils.listToMap(page);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统错误");
		}
	}
	@RequestMapping(value="getZbx" ,method = RequestMethod.GET)
	public @ResponseBody  Object getZbx(String zbxName) {

//		page = service.getUsers(page,user);
		List<Zbx> data = service.getZbxs(zbxName);
		
		return ExtUtils.toMap(ExtUtils.toCombo(data));
	}
	
	@RequestMapping(value="upload" ,method = RequestMethod.POST)
	public @ResponseBody Object upload(FileUploadBean uploadItem, BindingResult result) {
		
		StringBuilder failureMsg = new StringBuilder();
		try {
			if (result.hasErrors()) {
				for (ObjectError error : result.getAllErrors()) {
					System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
				}
			}
			String zbxMc = uploadItem.getZbxMc();
			String fileName = uploadItem.getFile().getOriginalFilename();
			List<Zbx> zbxs = service.getZbxList(zbxMc);
			Assert.notEmpty(zbxs, "指标项为空");
			Long officeId = zbxs.get(0).getOfficeId();
			String entityName = zbxs.get(0).getEntityName();
			
			// 创建导入Excel对象
			ImportExcelme ei = new ImportExcelme(uploadItem.getFile(), 2, 0);
			// 获取传入Excel文件的数据，根据传入参数类型，自动转换为对象
			Class<?> clazz = Class.forName(entityName);
			List<Uploadable> list = (List<Uploadable>)ei.getDataList(clazz, zbxs);
			
			UploadDocLookup  upload = setterUploadDocLookup(zbxMc, fileName, officeId);
			
			int successNum = service.upload(list, upload, validator);
			
			return ExtUtils.mapOK("已成功导入 " + successNum + " 条数据");

		}catch (ConstraintViolationException ex) {//只要有一列不对。由下面先catch住了
			List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			for (String message : messageList) {
				failureMsg.append(message + "; ");
			}
			failureMsg.insert(0, "失败 ，导入信息如下：");
			return ExtUtils.mapError(failureMsg.toString());
		} catch (BusinessException e) {//ei.getDataList,会检查数据是否为空
			e.printStackTrace();
			return ExtUtils.mapError(" 导入失败："+e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ExtUtils.mapError("系统异常");
		}

	}
	
	private UploadDocLookup setterUploadDocLookup(String zbxMc,String fileName,Long officeId){
		UploadDocLookup upload = new UploadDocLookup();
		upload.setZbxMc(zbxMc);
		upload.setWdMc(fileName);
		upload.setOfficeId(officeId);
		upload.setCreateBy(getCurrentUser().loginName);
		upload.setCreateDate(DateUtils.getDate_());
		upload.setStatus(Constants.STATUS.s.toString());
		return upload;
	}
	
	
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public @ResponseBody Object delete(@RequestBody String json)  {
		try{
			
			UploadDocLookup upload = mapper.fromJson(json,UploadDocLookup.class);
			String zbxMc = upload.getZbxMc();
			Zbx zbx = service.getZbx(zbxMc);
			service.del(upload,zbx.getEntityName());
			return ExtUtils.mapOK("刪除成功");
//			throw new Exception();

		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
	}
	
	/**
	 * 动态grid
	 * @param page
	 * @param upload
	 * @return
	 */
	@RequestMapping(value="view",method = RequestMethod.POST)
//	@RequestMapping(value="view",method = RequestMethod.GET)
	public @ResponseBody Object view( Page<Object> page,   UploadDocLookup upload)  {
//		public @ResponseBody Object view(Page<Object> page,  @RequestBody String upload)  {
		try{
			Map<String,Object> map = service.view(page, upload);
			return map;
//			JavaType beanListType = mapper.contructCollectionType(List.class, UploadDocLookup.class);
//			
//			String s = "%7B%22id%22%3A2%2C%22zbxMc%22%3A%22%5Cu623f%5Cu4ea7%5Cu4ea4%5Cu6613%5Cu660e%5Cu7ec6%22%2C%22wdMc%22%3A%22fcjymx_141011.xlsx%22%2C%22createBy%22%3A%22wj%22%2C%22createDate%22%3A%222014-10-11+17%3A36%3A15%22%2C%22status%22%3A%22s%22%2C%22officeId%22%3A2%7D";
//			List<UploadDocLookup> beanList = mapper.fromJson(StringUtils.wrap(s), beanListType);

		} catch (Exception e) {//TODO 做成过滤器
			e.printStackTrace();
			logger.error(e.getMessage());
			return ExtUtils.mapError("系统错误");
		}
	}
	
}


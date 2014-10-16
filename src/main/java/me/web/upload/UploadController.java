/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.web.upload;

import java.util.List;

import javax.validation.ConstraintViolationException;

import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.entity.data.fgj.Wshtba;
import me.repository.common.Page;
import me.service.upload.UploadService;
import me.utils.BusinessException;
import me.utils.Constants;
import me.utils.DateUtils;
import me.utils.ExtUtils;
import me.utils.FileUploadBean;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Reflections;

import com.fasterxml.jackson.annotation.JsonInclude.Include;


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
		List<Zbx> data = service.getZbx(zbxName);
		
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
	
}


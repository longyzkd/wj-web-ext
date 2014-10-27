package me.service.upload;

import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.repository.common.Page;
import me.repository.lookup.LookupDao;
import me.repository.upload.UploadDao;
import me.repository.zbx.ZbxDao;
import me.utils.ExtGridColumn;
import me.utils.ExtGridField;
import me.utils.excel.Uploadable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.beanvalidator.BeanValidators;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
@Transactional
public class UploadService {
	private static Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	@Autowired
	private UploadDao dao;
	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private ZbxDao zbxDao;
	public List<Zbx> getZbxs(String zbxName) {
		return dao.findZbxs(zbxName);
		
	}
	
	public Zbx getZbx(String zbxName) {
		return dao.findZbx(zbxName);
		
	}

	public Page<UploadDocLookup> getAllDocs(Page<UploadDocLookup> page, UploadDocLookup doc) {
		return lookupDao.findDocsUpload(page, doc);
	}

	public <E> void  create(E cur) {
		dao.saveme(cur);
		
	}
	
	public  int upload(List<Uploadable> list, UploadDocLookup upload,Validator validator){
		dao.save(upload);
		int successNum = 0;
		// 遍历数据，保存数据
		for (Uploadable cur : list) {
			BeanValidators.validateWithException(validator, cur);
			cur.setUploadId(upload.getId());
			cur.setWdMc(upload.getWdMc());
			dao.saveme(cur);
			successNum++;
		}
		return successNum;
	}

	public List<Zbx> getZbxList(String zbxMc) {
		return zbxDao.findZbxListBy(zbxMc);
	}

	/**
	 * 删除 upload 以及相应的data
	 * @param upload
	 * @param entityName
	 */
	public void del(UploadDocLookup upload,String entityName) {
		dao.delete(upload);
		dao.delData(upload,entityName);
		
	}

	public Map<String,Object> view(Page<Object> page,UploadDocLookup upload) {
		List<Zbx> zbxList = zbxDao.findZbxListBy(upload.getZbxMc());
		Map<String,String> propertyNameTextMap = Maps.newHashMap();
		List<ExtGridField> fields = Lists.newArrayList();
		List<ExtGridColumn> columns = Lists.newArrayList();
		Assert.notEmpty(zbxList);
		for(Zbx zbx : zbxList){
			propertyNameTextMap.put(zbx.getPropertyText(), zbx.getPropertyName());
			
			ExtGridField field = new ExtGridField();
			field.setName( zbx.getPropertyName());
			fields.add(field);
			
			ExtGridColumn column = new ExtGridColumn();
			column.setHeader(zbx.getPropertyText());
			column.setDataIndex( zbx.getPropertyName());
			columns.add(column);
		}
		
		 page = dao.findData(page, upload, zbxList.get(0).getEntityName());
		 
		Map<String,Object> map = Maps.newHashMap();
		
		Map<String,Object> metaData = Maps.newHashMap();
		metaData.put("fields", fields);
		metaData.put("columns", columns);
		
		map.put("metaData", metaData);
		
		Map<String,Object> data = Maps.newHashMap();
		map.put("data", page.getList());
		
		map.put("success", true);
		map.put("totalCount", page.getCount());
		return map;
	}
	
}

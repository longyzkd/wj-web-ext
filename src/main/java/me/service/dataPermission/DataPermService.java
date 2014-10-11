package me.service.dataPermission;

import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.repository.common.Page;
import me.repository.dataPermission.DataPermDao;
import me.repository.lookup.LookupDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataPermService {
private static Logger logger = LoggerFactory.getLogger(DataPermService.class);
	
	@Autowired
	private DataPermDao dao;
	
	@Autowired
	private LookupDao lookupDao;
	

	public Page<UploadDocLookup> getAllDocs(Page<UploadDocLookup> page, UploadDocLookup doc) {
		
		return lookupDao.findDocs(page,doc);
	}
}

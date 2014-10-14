package me.service.upload;

import java.util.List;

import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.repository.common.Page;
import me.repository.lookup.LookupDao;
import me.repository.upload.UploadDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UploadService {
	private static Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	@Autowired
	private UploadDao dao;
	@Autowired
	private LookupDao lookupDap;
	public List<Zbx> getZbx(String zbxName) {
		return dao.findZbx(zbxName);
		
	}

	public Page<UploadDocLookup> getAllDocs(Page<UploadDocLookup> page, UploadDocLookup doc) {
		return lookupDap.findDocsUpload(page, doc);
	}
	
}
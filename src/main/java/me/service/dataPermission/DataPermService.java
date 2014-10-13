package me.service.dataPermission;

import java.util.List;

import me.entity.PermissionDocOffice;
import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.repository.common.Page;
import me.repository.common.Parameter;
import me.repository.dataPermission.DataPermDao;
import me.repository.lookup.LookupDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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


	
	public void editDataPermission(Long officeId, List<UploadDocLookup> docs) {
		DetachedCriteria detachedCriteria = dao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("officeId", officeId));
		List<PermissionDocOffice> ps = dao.find(detachedCriteria);
		if(!CollectionUtils.isEmpty(ps)){
			for(PermissionDocOffice p :ps){
				dao.delete(p);
			}
		}
		
		Assert.notEmpty(docs, "文档为空");
		for(UploadDocLookup doc: docs){
			PermissionDocOffice p = new PermissionDocOffice();
			p.setOfficeId(officeId);
			p.setWdMc(doc.getWdMc());
			p.setUploadId(doc.getId());
			dao.save(p);
		}
		
	}



	public List<UploadDocLookup> getDocs(Long officeId) {
		String hql = "select u from PermissionDocOffice p ,UploadDocLookup u where p.officeId = :p1 and p.uploadId=u.id ";
		return lookupDao.find(hql,new Parameter(officeId));
	}
}

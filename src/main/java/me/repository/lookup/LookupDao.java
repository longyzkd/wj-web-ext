package me.repository.lookup;

import me.entity.UploadDocLookup;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.utils.DateUtils;
import me.utils.StringUtils;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class LookupDao extends CommonDao<UploadDocLookup> {

	public Page<UploadDocLookup> findDocs(Page<UploadDocLookup> page, UploadDocLookup doc) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UploadDocLookup.class);
		if (!StringUtils.isBlank(doc.getWdMc())) {
			detachedCriteria.add(Restrictions.like("wdMc", "%" + doc.getWdMc() + "%"));
		}
		if (!StringUtils.isBlank(doc.getZbxMc())) {
			detachedCriteria.add(Restrictions.like("zbxMc", "%" + doc.getZbxMc() + "%"));
		}
		
		
		return find(page, detachedCriteria);
	}
	public Page<UploadDocLookup> findDocsUpload(Page<UploadDocLookup> page, UploadDocLookup doc) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UploadDocLookup.class);
		if (null!=(doc.getOfficeId())) {
			detachedCriteria.add(Restrictions.eq("officeId",doc.getOfficeId()));
		}
		if (!StringUtils.isBlank(doc.getZbxMc())) {
			detachedCriteria.add(Restrictions.like("zbxMc", "%" + doc.getZbxMc() + "%"));
		}
		if (!StringUtils.isBlank(doc.getStatus())) {
			detachedCriteria.add(Restrictions.eq("status",doc.getStatus()));
		}
		
		//起始 截止时间比较
		if (!StringUtils.isBlank(doc.getFrom())) {
			 
			if (!StringUtils.isBlank(doc.getTo())) {
				detachedCriteria.add(Restrictions.between("createDate", DateUtils.parseDate(doc.getFrom()), DateUtils.parseDate(doc.getTo())));
			}else{
				detachedCriteria.add(Restrictions.ge("createDate", DateUtils.parseDate(doc.getFrom())));
			}
		}else{
			if (!StringUtils.isBlank(doc.getTo())) {
				detachedCriteria.add(Restrictions.le("createDate", DateUtils.parseDate(doc.getTo())));
			}
		}
		return find(page, detachedCriteria);
	}
}

package me.repository.lookup;

import me.entity.UploadDocLookup;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.utils.StringUtils;

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
}

package me.repository.upload;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import me.entity.Office;
import me.entity.UploadDocLookup;
import me.entity.Zbx;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.repository.common.Parameter;
import me.utils.StringUtils;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class UploadDao extends CommonDao<UploadDocLookup>{

	public Page<UploadDocLookup> findZbxs(Page<UploadDocLookup> page, UploadDocLookup zbx) {
		
		Parameter pars = new Parameter();
		
		StringBuilder hql = new StringBuilder("from Zbx where 1=1 ");
		
		if(!StringUtils.isEmpty(zbx.getZbxMc())){
			hql.append(" and zbxMc like :p1 ");
			pars.put("p1",  "%"+zbx.getZbxMc()+"%");
		}
		hql.append(" group by entityName ");
		return findme(page, hql.toString(),pars);
	}

	public List<Zbx> findZbxs(String zbxName) {
		Parameter pars = new Parameter();
		
		StringBuilder hql = new StringBuilder("from Zbx where 1=1 ");
		
		if(!StringUtils.isEmpty(zbxName)){
			hql.append(" and zbxMc like :p1 ");
			pars.put("p1",  "%"+zbxName+"%");
		}
		hql.append(" group by entityName ");
		
		return find(hql.toString(), pars)	;
	}
	public Zbx findZbx(String zbxMc) {
		Assert.hasText(zbxMc);
		
		StringBuilder hql = new StringBuilder("from Zbx where 1=1  and zbxMc = :p1  group by entityName ");
		
		return (Zbx)find(hql.toString(), new Parameter(zbxMc)).get(0)	;
	}

	public void delData(UploadDocLookup upload, String entityName) {
//		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(dataClazz);
//		detachedCriteria.add(Restrictions.eq("uploadId",upload.getId()));
		
		executeUpdate("delete from "+ entityName +" where uploadId = :p1 " ,new Parameter(upload.getId()));
	}
	public Page<Object> findData(Page<Object> page,UploadDocLookup upload, String entityName) {
//		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(dataClazz);
//		detachedCriteria.add(Restrictions.eq("uploadId",upload.getId()));
		
		String hql = " from "+ entityName +" where uploadId = :p1 " ;
		return find(page, hql, new Parameter(upload.getId()));
	}
	

	
	
	
}

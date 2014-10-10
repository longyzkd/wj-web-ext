package me.repository.zbx;


import java.util.List;

import me.entity.Zbx;
import me.repository.common.CommonDao;
import me.repository.common.Page;
import me.repository.common.Parameter;
import me.utils.StringUtils;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class ZbxDao extends CommonDao<Zbx>{

	//     报错 ---Column 'create_by' not found.
//	public Page<Zbx> findZbxs(Page<Zbx> page, Zbx zbx) {
//		
//		Parameter pars = new Parameter();
//		
//		StringBuilder sql = new StringBuilder("select entity_name as entityName ,max(zbx_mc) as zbxMc ,min(id) as id from base_zbx  where 1=1 ");
//		
//		if(!StringUtils.isEmpty(zbx.getZbxMc())){
//			sql.append(" and zbx_Mc like :p1 ");
//			pars.put("p1",  "%"+zbx.getZbxMc()+"%");
//		}
//		sql.append(" group by entity_name  ");
//		return findBySql(page, sql.toString(),pars,Zbx.class);
//	}
//	//TODO 总数有问题
	public Page<Zbx> findZbxs(Page<Zbx> page, Zbx zbx) {
		
		Parameter pars = new Parameter();
		
		StringBuilder hql = new StringBuilder("from Zbx where 1=1 ");
		
		if(!StringUtils.isEmpty(zbx.getZbxMc())){
			hql.append(" and zbxMc like :p1 ");
			pars.put("p1",  "%"+zbx.getZbxMc()+"%");
		}
		hql.append(" group by entityName ");
		return findme(page, hql.toString(),pars);
	}
//	//TODO 总数有问题
//	public Page<Zbx> findZbxs(Page<Zbx> page, Zbx zbx) {
//		
//		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Zbx.class);
//		if(!StringUtils.isBlank(zbx.getZbxMc())){
//			detachedCriteria.add(Restrictions.like("zbxMc", "%"+zbx.getZbxMc()+"%"));
//		}
//		
//		detachedCriteria.setProjection(Projections.projectionList().add(Projections.groupProperty("entityName")));
//		return find(page, detachedCriteria);
//	}

	public List<Zbx> findPropertys(String entityName) {
		String hql = "from Zbx where entityName = :p1";
		return find(hql,new Parameter(entityName));
		
	}

	public List<Zbx> findAllEntitys() {
		String hql = "select entityName as entityName  from Zbx group by entityName";
		return findByHql(hql,Zbx.class);
	}

	public List<Zbx> findZbxList(String entityName) {
		DetachedCriteria  detachedCriteria =createDetachedCriteria()  ;
		Assert.hasText(entityName);
//		if(StringUtils.isEmpty(entityName)){
//			return Lists.newArrayList();
//		}
		detachedCriteria.add(Restrictions.eq("entityName",entityName));
		return find(detachedCriteria);
	}

}

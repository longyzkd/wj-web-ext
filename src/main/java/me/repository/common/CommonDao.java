package me.repository.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import me.entity.Zbx;
import me.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Reflections;
//@Component
//public class CommonDao extends HibernateDaoSupport{
//
//	
//	@Resource(name = "sessionFactory")
//	public void setSuperSessionFactory(SessionFactory sessionFactory){
//		super.setSessionFactory(sessionFactory);
//	}
//}
/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

/**
 * DAO支持类实现
 * @author ThinkGem
 * @version 2013-05-15
 * @param <T>
 */
public class CommonDao<T> {

	/**
	 * SessionFactory
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<?> entityClass;
	
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public CommonDao() {
		entityClass = Reflections.getClassGenricType(getClass());
	}

	/**
	 * 获取 Session
	 */
	public Session getSession(){  
	  return sessionFactory.getCurrentSession();
	}

	/**
	 * 强制与数据库同步
	 */
	public void flush(){
		getSession().flush();
	}

	/**
	 * 清除缓存数据
	 */
	public void clear(){ 
		getSession().clear();
	}
	
	// -------------- QL Query --------------

	/**
	 * QL 分页查询
	 * @param page
	 * @param qlString
	 * @return
	 */
	public <E> Page<E> find(Page<E> page, String qlString){
    	return find(page, qlString, null);
    }
    
	/**
	 * QL 分页查询
	 * @param page
	 * @param qlString
	 * @param parameter
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public <E> Page<E> find(Page<E> page, String qlString, Parameter parameter){
		// get count
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countQlString = "select count(*) " + removeSelect(removeOrders(qlString));  
//	        page.setCount(Long.valueOf(createQuery(countQlString, parameter).uniqueResult().toString()));
	        Query query = createQuery(countQlString, parameter);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setCount(Long.valueOf(list.get(0).toString()));
	        }else{
	        	page.setCount(list.size());
	        }
			if (page.getCount() < 1) {
				return page;
			}
    	}
    	// order by
    	String ql = qlString;
		if (StringUtils.isNotBlank(page.getOrderBy())){
			ql += " order by " + page.getOrderBy();
		}
        Query query = createQuery(ql, parameter); 
    	// set page
        if (!page.isDisabled()){
	        query.setFirstResult(page.getFirstResult());
	        query.setMaxResults(page.getMaxResults()); 
        }
        page.setList(query.list());
		return page;
    }
    /**
     * 解决hql分组查询count不对，而且hql不支持from子查询
     * @author wj
     * @param page
     * @param qlString
     * @param parameter
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> Page<E> findme(Page<E> page, String qlString, Parameter parameter){
    	
    	// order by
    	String ql = qlString;
    	if (StringUtils.isNotBlank(page.getOrderBy())){
    		ql += " order by " + page.getOrderBy();
    	}
    	Query query = createQuery(ql, parameter); 
    	// set page
    	if (!page.isDisabled()){
    		query.setFirstResult(page.getFirstResult());
    		query.setMaxResults(page.getMaxResults()); 
    	}
    	page.setList(query.list());
    	
    	// get count
    	if (!page.isDisabled() && !page.isNotCount()){
    			page.setCount(page.getList().size());
    	}
    	return page;
    }
    

    /**
	 * QL 查询
	 * @param qlString
	 * @return
	 */
	public <E> List<E> find(String qlString){
		return find(qlString, null);
	}
	public <E>  List<E> findByHql(String qlString, Parameter parameter ,Class<?> resultClass){
		return find(qlString, parameter,resultClass);
	}
	public <E>  List<E> findByHql(String qlString ,Class<?> resultClass){
		return find(qlString, null,resultClass);
	}
	
    /**
	 * QL 查询
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> find(String qlString, Parameter parameter){
		Query query = createQuery(qlString, parameter);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public <E> List<E> find(String qlString, Parameter parameter,Class<?> resultClass){
		Query query = createQuery(qlString, parameter,resultClass);
		return query.list();
	}
	
	
	/**
	 * QL 查询所有
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return getSession().createCriteria(entityClass).list();
	}
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id){
		return (T)getSession().get(entityClass, id);
	}
	
	/**
	 * 获取实体
	 * @param qlString
	 * @return
	 */
	public T getByHql(String qlString){
		return getByHql(qlString, null);
	}
	
	/**
	 * 获取实体
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getByHql(String qlString, Parameter parameter){
		Query query = createQuery(qlString, parameter);
		return (T)query.uniqueResult();
	}
	
	/**
	 * 保存实体，不依赖于各模块dao泛型
	 * @author wj
	 * @param cur
	 */
	public <E> void  saveme(E cur){
		try {
			// 获取实体编号
			Object id = null;
			for (Method method : cur.getClass().getMethods()){
				Id idAnn = method.getAnnotation(Id.class);
				if (idAnn != null){
					id = method.invoke(cur);
					break;
				}
			}
			// 插入前执行方法
//			if (StringUtils.isBlank((String)id)){
			if (id==null ){	//id为Long
				for (Method method : cur.getClass().getMethods()){
					PrePersist pp = method.getAnnotation(PrePersist.class);
					if (pp != null){
						method.invoke(cur);
						break;
					}
				}
			}
			// 更新前执行方法
			else{
				for (Method method : cur.getClass().getMethods()){
					PreUpdate pu = method.getAnnotation(PreUpdate.class);
					if (pu != null){
						method.invoke(cur);
						break;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
//		catch (Exception e) { //catch住  不会回滚
//			e.printStackTrace();
//		}
		getSession().saveOrUpdate(cur);
	
	}
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity){
		try {
			// 获取实体编号
			Object id = null;
			for (Method method : entity.getClass().getMethods()){
				Id idAnn = method.getAnnotation(Id.class);
				if (idAnn != null){
					id = method.invoke(entity);
					break;
				}
			}
			// 插入前执行方法
//			if (StringUtils.isBlank((String)id)){
			if (id==null ){	//id为Long
				for (Method method : entity.getClass().getMethods()){
					PrePersist pp = method.getAnnotation(PrePersist.class);
					if (pp != null){
						method.invoke(entity);
						break;
					}
				}
			}
			// 更新前执行方法
			else{
				for (Method method : entity.getClass().getMethods()){
					PreUpdate pu = method.getAnnotation(PreUpdate.class);
					if (pu != null){
						method.invoke(entity);
						break;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
//		catch (Exception e) { //catch住  不会回滚
//			e.printStackTrace();
//		}
		getSession().saveOrUpdate(entity);
	}
	
	/**
	 * 保存实体列表
	 * @param entityList
	 */
	public void save(List<T> entityList){
		for (T entity : entityList){
			save(entity);
		}
	}

	/**
	 * 更新
	 * @param qlString
	 * @return
	 */
	public int update(String qlString){
		return update(qlString, null);
	}
	
	/**
	 * 更新
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public int update(String qlString, Parameter parameter){
		return createQuery(qlString, parameter).executeUpdate();
	}
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int deleteById(Serializable id){
		return update("update "+entityClass.getSimpleName()+" set delFlag='" + Constants.DEL_FLAG_DELETE + "' where id = :p1", 
				new Parameter(id));
	}
	
	/**
	 * deleteCasade
	 * @param id
	 * @param likeParentIds
	 * @return
	 */
	public int deleteById(Serializable id, String likeParentIds){
		return update("update "+entityClass.getSimpleName()+" set delFlag = '" + Constants.DEL_FLAG_DELETE + "' where id = :p1 or parentIds like :p2",
				new Parameter(id, likeParentIds));
	}
	/**
	 * 物理删除本身及其所有子节点
	 * @param id
	 * @param likeParentIds
	 * @return
	 */
	public int deleteCasade(Serializable id, String likeParentIds){
		return update("delete "+entityClass.getSimpleName()+ " where id = :p1 or parentIds like :p2",
				new Parameter(id, likeParentIds));
	}
	
	/**
	 * 物理删除
	 * @param id
	 * @return
	 */
	public int delete(Serializable id){
		String hql = "delete from " +entityClass.getSimpleName()+" where id=:p1";
		return createQuery(hql, new Parameter(id)).executeUpdate();
	}
	/**
	 * 物理删除
	 */
	public void delete(T t){
		getSession().delete(t);
	}
	
	/**
	 * 更新删除标记
	 * @param id
	 * @param delFlag
	 * @return
	 */
	public int updateDelFlag(Serializable id, String delFlag){
		return update("update "+entityClass.getSimpleName()+" set delFlag = :p2 where id = :p1", 
				new Parameter(id, delFlag));
	}
	
	/**
	 * 创建 QL 查询对象
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public Query createQuery(String qlString, Parameter parameter){
		Query query = getSession().createQuery(qlString);
		setParameter(query, parameter);
		return query;
	}
	public Query createQuery(String qlString, Parameter parameter,Class<?> resultClass){
		Query query = getSession().createQuery(qlString);
		setParameter(query, parameter);
		setResultTransformer(query, resultClass);
		return query;
	}
	
	// -------------- SQL Query --------------

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString){
    	return findBySql(page, sqlString, null, null);
    }

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter){
    	return findBySql(page, sqlString, parameter, null);
    }

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Class<?> resultClass){
    	return findBySql(page, sqlString, null, resultClass);
    }
    
    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter, Class<?> resultClass){
		// get count
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countSqlString = "select count(*) " + removeSelect(removeOrders(sqlString));  
//	        page.setCount(Long.valueOf(createSqlQuery(countSqlString, parameter).uniqueResult().toString()));
	        Query query = createSqlQuery(countSqlString, parameter);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setCount(Long.valueOf(list.get(0).toString()));
	        }else{
	        	page.setCount(list.size());
	        }
			if (page.getCount() < 1) {
				return page;
			}
    	}
    	// order by
    	String sql = sqlString;
		if (StringUtils.isNotBlank(page.getOrderBy())){
			sql += " order by " + page.getOrderBy();
		}
        SQLQuery query = createSqlQuery(sql, parameter); 
		// set page
        if (!page.isDisabled()){
	        query.setFirstResult(page.getFirstResult());
	        query.setMaxResults(page.getMaxResults()); 
        }
        setResultTransformer(query, resultClass);
        page.setList(query.list());
		return page;
    }
    /**
     * 嵌套查询count
     * @author wj
     * @param page
     * @param sqlString
     * @param parameter
     * @param resultClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> Page<E> findBySqlme(Page<E> page, String sqlString, Parameter parameter, Class<?> resultClass){
    	// get count
    	if (!page.isDisabled() && !page.isNotCount()){
    		String countSqlString = "select count(*) from (" + (removeOrders(sqlString)+" ) as t");  
//	        page.setCount(Long.valueOf(createSqlQuery(countSqlString, parameter).uniqueResult().toString()));
    		Query query = createSqlQuery(countSqlString, parameter);
    		List<Object> list = query.list();
    		if (list.size() > 0){
    			page.setCount(Long.valueOf(list.get(0).toString()));
    		}else{
    			page.setCount(list.size());
    		}
    		if (page.getCount() < 1) {
    			return page;
    		}
    	}
    	// order by
    	String sql = sqlString;
    	if (StringUtils.isNotBlank(page.getOrderBy())){
    		sql += " order by " + page.getOrderBy();
    	}
    	SQLQuery query = createSqlQuery(sql, parameter); 
    	// set page
    	if (!page.isDisabled()){
    		query.setFirstResult(page.getFirstResult());
    		query.setMaxResults(page.getMaxResults()); 
    	}
    	setResultTransformer(query, resultClass);
    	page.setList(query.list());
    	return page;
    }

	/**
	 * SQL 查询
	 * @param sqlString
	 * @return
	 */
	public <E> List<E> findBySql(String sqlString){
		return findBySql(sqlString, null, null);
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public <E> List<E> findBySql(String sqlString, Parameter parameter){
		return findBySql(sqlString, parameter, null);
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> findBySql(String sqlString, Parameter parameter, Class<?> resultClass){
		SQLQuery query = createSqlQuery(sqlString, parameter);
		setResultTransformer(query, resultClass);
		return query.list();
	}
	
	/**
	 * SQL 更新
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public int updateBySql(String sqlString, Parameter parameter){
		return createSqlQuery(sqlString, parameter).executeUpdate();
	}
	
	/**
	 * 创建 SQL 查询对象
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public SQLQuery createSqlQuery(String sqlString, Parameter parameter){
		SQLQuery query = getSession().createSQLQuery(sqlString);
		setParameter(query, parameter);
		return query;
	}
	
	// -------------- Query Tools --------------

	/**
	 * 设置查询结果类型
	 * @param query
	 * @param resultClass
	 */
	private void setResultTransformer(SQLQuery query, Class<?> resultClass){
		if (resultClass != null){
			if (resultClass == Map.class){
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}else if (resultClass == List.class){
				query.setResultTransformer(Transformers.TO_LIST);
			}else{
				query.addEntity(resultClass);
			}
		}
	}
	private void setResultTransformer(Query query, Class<?> resultClass){
		if (resultClass != null){
			if (resultClass == Map.class){
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}else if (resultClass == List.class){
				query.setResultTransformer(Transformers.TO_LIST);
			}else{
				query.setResultTransformer(Transformers.aliasToBean(resultClass));
			}
		}
	}
	
	/**
	 * 设置查询参数
	 * @param query
	 * @param parameter
	 */
	private void setParameter(Query query, Parameter parameter){
		if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(value instanceof Collection<?>){
                    query.setParameterList(string, (Collection<?>)value);
                }else if(value instanceof Object[]){
                    query.setParameterList(string, (Object[])value);
                }else{
                    query.setParameter(string, value);
                }
            }
        }
	}
	
    /** 
     * 去除qlString的select子句。 
     * @param qlString
     * @return 
     */  
    private String removeSelect(String qlString){  
        int beginPos = qlString.toLowerCase().indexOf("from");  
        return qlString.substring(beginPos);  
    }  
      
    /** 
     * 去除hql的orderBy子句。 
     * @param qlString
     * @return 
     */  
    private String removeOrders(String qlString) {  
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(qlString);  
        StringBuffer sb = new StringBuffer();  
        while (m.find()) {  
            m.appendReplacement(sb, "");  
        }
        m.appendTail(sb);
        return sb.toString();  
    } 
	
	// -------------- Criteria --------------
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page<T> find(Page<T> page) {
		return find(page, createDetachedCriteria());
	}
	

	/**
	 * 使用检索标准对象分页查询
	 * @param page
	 * @param detachedCriteria
	 * @return
	 */
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria) {
		return find(page, detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	/**
	 * 使用检索标准对象分页查询
	 * @param page
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
		// get count
		if (!page.isDisabled() && !page.isNotCount()){
			page.setCount(count(detachedCriteria));
			if (page.getCount() < 1) {
				return page;
			}
		}
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.setResultTransformer(resultTransformer);
		// set page
		if (!page.isDisabled()){
	        criteria.setFirstResult(page.getFirstResult());
	        criteria.setMaxResults(page.getMaxResults()); 
		}
		// order by
		if (StringUtils.isNotBlank(page.getOrderBy())){
			for (String order : StringUtils.split(page.getOrderBy(), ",")){
				String[] o = StringUtils.split(order, " ");
				if (o.length==1){
					criteria.addOrder(Order.asc(o[0]));
				}else if (o.length==2){
					if ("DESC".equals(o[1].toUpperCase())){
						criteria.addOrder(Order.desc(o[0]));
					}else{
						criteria.addOrder(Order.asc(o[0]));
					}
				}
			}
		}
		page.setList(criteria.list());
		return page;
	}

	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 * @return
	 */
	public List<T> find(DetachedCriteria detachedCriteria) {
		return find(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.setResultTransformer(resultTransformer);
		return criteria.list(); 
	}
	
	/**
	 * 使用检索标准对象查询记录数
	 * @param detachedCriteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public long count(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		long totalCount = 0;
		try {
			// Get orders
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			List orderEntrys = (List)field.get(criteria);
			// Remove orders
			field.set(criteria, new ArrayList());
			// Get count
			criteria.setProjection(Projections.rowCount());
			totalCount = Long.valueOf(criteria.uniqueResult().toString());
			// Clean count
			criteria.setProjection(null);
			// Restore orders
			field.set(criteria, orderEntrys);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	/**
	 * 创建与会话无关的检索标准对象
	 * @param criterions Restrictions.eq("name", value);
	 * @return 
	 */
	public DetachedCriteria createDetachedCriteria(Criterion... criterions) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		for (Criterion c : criterions) {
			dc.add(c);
		}
		return dc;
	}

	/**
	 * 
	 * @param beanClazz 类名
	 * @param property 属性名
	 * @param val 值
	 * @return
	 */
	public <E> List<E> findBy( String beanClazz,String property,Object val) {
		return find("from "+beanClazz + " where "+ property +" = :p1 ",
				new Parameter(val));
	}
	/**
	 * 值不等于val的集合
	 * @param beanClazz
	 * @param property
	 * @param val
	 * @return
	 */
	public <E> List<E> findExcept( String beanClazz,String property,Object val,Object rawValue) {
		
		String hql = " from "+beanClazz+"  where id not in ( select id  from "+beanClazz+" where  "+property+" = :p1 ) and "+property+" =:p2";
		return find(hql,new Parameter(rawValue,val));
				
	}
	
	// -------------- Hibernate search --------------
	
	
	// --------------  --------------
	/**
	 * 执行hql
	 * @param string
	 * @param parameter
	 */
	public void executeUpdate(String string, Parameter parameter) {
		createQuery(string,parameter).executeUpdate();
		
	}
	
}
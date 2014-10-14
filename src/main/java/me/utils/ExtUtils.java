package me.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.repository.common.Page;
import me.utils.Comboable.Combo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springside.modules.mapper.JsonMapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * Util class, returns a Map in the format Ext JS expects
 * 
 * Sample project presented at BrazilJS
 * Brazilian JavaScript Conference
 * Fortaleza - Cear� - 13-14 May 2011
 * http://braziljs.com.br/2011
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Component
public class ExtUtils {
	private static Logger logger = LoggerFactory.getLogger(ExtUtils.class);
	
	private static JsonMapper mapper = new JsonMapper(Include.ALWAYS);
	
	
	public static List<Combo>  toCombo(List<? extends Comboable> src){
		if(!CollectionUtils.isEmpty(src)){
			List<Combo> result = Lists.newArrayList();
			for(Comboable comboable : src){
				Combo combo = new Combo();
				combo.code = comboable.getCode();
				combo.name = comboable.getName();
				result.add(combo);
			}
			return result;
		}
		return null;
	}
	/**
	 * 分页
	 * @param T
	 * @return
	 */
	public static <T> Map<String, Object> listToMap(Page<T> T) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("root", T.getList());
		map.put("success", true);
		map.put("totalCount", T.getCount());
		return map;
	}
	/**
	 * 不分页
	 * @param T
	 * @return
	 */
	public static <T> Map<String, Object> listToMap(List<T> T) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("root", T);
		map.put("success", true);
		return map;
	}
	public static  Map<String, Object> toMap(Object T) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("data", T);
		map.put("success", true);
		return map;
	}
	/**
	 * 验证
	 * @param T
	 * @return
	 */
	public static <T> Map<String, Object> mapValidate(T T) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("root", T);
		map.put("success", true);
		return map;
	}
	
	
	/**
	 * Generates modelMap to return in the modelAndView
	 * @param T
	 * @return
	 */
	public static <T> Map<String,Object> mapOK(List<T> T){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", T.size());
		modelMap.put("data", T);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView
	 * @param T
	 * @return
	 */
	public static <T> Map<String,Object> mapOK(List<T> T, int total){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", total);
		modelMap.put("data", T);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	public static Map<String,Object> mapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 
	public static Map<String,Object> mapOK(String msg){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", true);
		
		return modelMap;
	} 
	
	
	
	/**
	 * 转成ext格式的树
	 * @author wj
	 * @param src
	 * @return
	 */
	public static String toComplexJson(List<ExtTreeNode> src) {

		Map<Long, ExtTreeNode> lookup = Maps.newHashMap();

		for (ExtTreeNode o : src) {
			lookup.put(o.getId(), o);
		}
		Set<Long> keySet = lookup.keySet();
		for (Long id : keySet) {
			ExtTreeNode value = lookup.get(id);
			Long parentId = value.getParentId();
			ExtTreeNode parentNode = lookup.get(parentId);
			if (parentNode != null) {
				parentNode.addChild(value);
				value.setParent(parentNode);
			}
		}
		for (Long id : keySet) {
			ExtTreeNode value = lookup.get(id);
			if (value.getParent() == null) {
				//....
				return  mapper.toJson(Lists.newArrayList(value) );
			}
		}
		return "";
	}
}

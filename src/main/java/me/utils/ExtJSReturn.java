package me.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.repository.common.Page;

import org.springframework.stereotype.Component;

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
public class ExtJSReturn {

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
}

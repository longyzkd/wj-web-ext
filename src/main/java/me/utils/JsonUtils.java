package me.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class JsonUtils {
	public static <T> Map<String, Object> listToMap(List<T> T) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("root", T);
		map.put("success", true);
		return map;
	}
}

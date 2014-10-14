package me.utils;

/**
 * ext combo的值格式
 * @author wj
 *
 */
public interface Comboable {

	String getCode();
	String getName();
	
	
	class Combo{
		public String code;
		public String name;
	}
}

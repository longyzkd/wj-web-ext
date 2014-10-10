package me.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils{
	/**
	 * []包裹String
	 * @author wj
	 * @param s
	 * @return
	 */
	public  static String wrap(String s){
		if( s.charAt(0)== '['  ){
			return s;
		}else{
			return "["+s+"]";
		}
	}
}

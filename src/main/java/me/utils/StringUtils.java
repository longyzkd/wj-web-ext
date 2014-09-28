package me.utils;

public class StringUtils {
	public  static String wrap(String s){
		if( s.charAt(0)== '['  ){
			return s;
		}else{
			return "["+s+"]";
		}
	}
}

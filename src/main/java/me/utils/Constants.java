package me.utils;

/**
 * 常量
 * @author wj
 * @date 2014-9-19
 *
 */
public class Constants {

	public enum TREE_VIEW{
		list,node
	}
	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String FIELD_DEL_FLAG = "delFlag";
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
}

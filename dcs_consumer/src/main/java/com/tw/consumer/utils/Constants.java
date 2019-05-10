package com.tw.consumer.utils;
/**
 * 
 * @author xiesc
 * @TODO 常量
 * @time 2018年5月24日
 * @version 1.0
 */
public final class Constants {
	/**
	 * 遥测列簇
	 */
	public static final byte[] FAMILYYC = "yc".getBytes();
	/**
	 * 遥信列簇
	 */
	public static final byte[] FAMILYYX = "yx".getBytes();
	/**
	 * 组串式逆变器cube
	 */
	public static final String CASCADECUBE   = "jw_cascade_cube";
	/**
	 * 集中式逆变器
	 */
	public static final String CENTRALIZEDCUBE   = "jw_centralized_cube";
	/**
	 * 环境仪
	 */
	public static final String ENVIRCUBE   = "jw_envir_cube";
	/**
	 * 直流汇流箱
	 */
	public static final String COMBINERDCCUBE   = "jw_combinerdc_cube";
	/**
	 * kylin zk路径
	 */
	public static final String KYLINSCHEDULERPATH = "/tw/jw/kylin/schedule";
	/**
	 * kylin zk parent路径
	 */
	public static final String KYLINSCHEDULERPARENTPATH = "/tw/jw/kylin";
}

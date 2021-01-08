package com.tw.connect.aggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.api.Types;

import com.tw.connect.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2019年1月22日
 * @version 1.0
 */
public class CascadeAggregation extends PropertyResources{

	//flink聚合sql
	private StringBuffer aggregationSqlString = new StringBuffer();
	//mysql统计表insert sql
	private StringBuffer mysqlInsertString = new StringBuffer("insert into "+getMysqlTableName()+" (");
	private StringBuffer mysqlValueString = new StringBuffer(" values(");
	//字段类型
	private final ArrayList<TypeInformation<?>> fieldTypes = new ArrayList<TypeInformation<?>>();

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "cascadeAggregation.properties";
	}
	
	public CascadeAggregation(){
		init();
	}
	
	private void init(){
		Set<Object> keys = pro.keySet();
		for(Object key : keys){
			fieldTypes.add(Types.DECIMAL());
			String value = pro.getProperty(key.toString());
			aggregationSqlString.append(key+" as "+value+",");
			mysqlInsertString.append(value+",");
			mysqlValueString.append("?,");
			
		}
	}
	/**
	 * 获取flink 聚合sql
	 * @return
	 */
	public String getAggregationSqlString(){
		return aggregationSqlString.substring(0, aggregationSqlString.length()-1);
	}
	/**
	 * 
	 * @param extrafields  需要额外查询的字段
	 * @return
	 */
	public String getMysqlInsertString(String...extrafields){
		if(null != extrafields && extrafields.length > 0){
			for(String field : extrafields){
				mysqlInsertString.append(field+",");
				mysqlValueString.append("?,");
			}
		}
		String insertString = mysqlInsertString.substring(0, mysqlInsertString.length()-1)+")";
		String valuestring = mysqlValueString.substring(0, mysqlValueString.length()-1)+")";
		return insertString+valuestring;
	}
	
	public List<TypeInformation<?>> getTypeList(){
		return fieldTypes;
	}
	/**
	 * mysql 统计表
	 * @return
	 */
	public String getMysqlTableName(){
		//组串式逆变器
		return "sc_sta_cascade_inverter_hour";
	}
}

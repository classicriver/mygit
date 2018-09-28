package com.tw.connect.flink;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.table.api.Types;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.tw.connect.utils.Utils;

/**
 * 
 * @author xiesc
 * @TODO  jsonschema 处理类
 * @time 2018年9月27日
 * @version 1.0
 */
public class FlinkTableSchema {
	
	private JSONObject rawSchema;
	/**
	 * jsonschema和filink schema数据类型映射
	 */
	private Map<String,TypeInformation<?>> mapping = new HashMap<String,TypeInformation<?>>();

	/**
	 * jsonschema中的字段类型集合
	 */
	private final ArrayList<TypeInformation<?>> fieldTypes = new ArrayList<TypeInformation<?>>();
	
	/**
	 * jsonschema中的字段类型集合
	 */
	private final ArrayList<String> fieldNames = new ArrayList<String>();
	
	private final StringBuffer  selectBuffer = new StringBuffer();
	
	private final Schema schema = new Schema();
	
	public FlinkTableSchema(){
		try (InputStream inputStream = FlinkTableSchema.class.getClassLoader().getResourceAsStream("jsonschema.json")) {
			rawSchema = new JSONObject(new JSONTokener(inputStream));
			initMapping();
			initSchema();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * kafka json schema
	 * @return
	 */
	public String getJsonSchema(){
		return rawSchema.toString();
	}
	
	public String getSelectString(){
		return selectBuffer.toString();
	}
	
	public String getInsertString(String tableName){
		StringBuffer s = new StringBuffer("INSERT INTO "+tableName+Utils.getCurrentMonth()+" (");
		StringBuffer v = new StringBuffer("VALUES (");
		for(String name : fieldNames){
			s.append(name + " , ");
			v.append("? , ");
		}
		//统计结束时间字段
		s.append(" endtime )");
		v.append(" ? )");
		return s.toString()+v.toString();
	}
	
	public TypeInformation<?>[] getFieldTypes(){
		return (TypeInformation<?>[]) fieldTypes.toArray(new TypeInformation[fieldTypes.size()]);
	}
	/**
	 * flink sql schema
	 * @return
	 */
	public Schema getSchema(){
		return schema;
	}
	
	/** 
	 * @return jsonschema转换为flinkTable需要的schema
	 */
	public Schema initSchema(){
		JSONObject jsonObject = rawSchema.getJSONObject("properties");
		Iterator<String> it = jsonObject.keySet().iterator();
		while(it.hasNext()){
			String fieldName = it.next();
			TypeInformation<?> fieldType = mapping.get(jsonObject.getJSONObject(fieldName).getString("type").toUpperCase());
			String description = jsonObject.getJSONObject(fieldName).getString("description");
			//timestamps 既是 eventtime
			if("timestamps".equals(fieldName)){
				//时间戳加水位线
				schema.field(fieldName, Types.SQL_TIMESTAMP()).rowtime(new Rowtime()
			    .timestampsFromSource().watermarksPeriodicBounded(10000));;
			}else{
				schema.field(fieldName, fieldType);
				//拼select语句,{{ ipv1.sum,tgsn,timestamps }}
				if(!"none".equals(description)){
					selectBuffer.append(fieldName.trim() +"."+description.trim()+" , ");
				}else{
					selectBuffer.append(fieldName +" , ");
				}
				fieldNames.add(fieldName);
				fieldTypes.add(fieldType);
			}
		}
		//加入统计结束时间字段
		selectBuffer.append("w.end");
		fieldTypes.add(Types.SQL_TIMESTAMP());
		return schema;
	}
	
	private void initMapping(){
		mapping.put("STRING", Types.STRING());
		mapping.put("NUMBER", Types.DECIMAL());
		mapping.put("INTEGER", Types.DECIMAL());
	}

}

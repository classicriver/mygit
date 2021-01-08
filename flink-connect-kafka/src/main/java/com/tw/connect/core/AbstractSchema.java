package com.tw.connect.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.tw.connect.aggregation.CascadeInverterSchema;
import com.tw.connect.utils.Utils;
/**
 * more infomation see https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/table/connect.html#json-format
 * @author xiesc
 * @TODO	把jsonschema 转换成flink schema
 * @time 2018年10月15日
 * @version 1.0
 */
public abstract class AbstractSchema {
	/**
	 * original jsonschema
	 */
	private JSONObject jsonSchema;
	/**
	 * jsonschema中的字段类型集合
	 */
	private final ArrayList<TypeInformation<?>> fieldTypes = new ArrayList<TypeInformation<?>>();
	
	/**
	 * jsonschema中的字段类型集合
	 */
	private final ArrayList<String> insertFields = new ArrayList<String>();
	/**
	 * select string
	 */
	private final StringBuffer selectString = new StringBuffer();
	
	/**
	 * jsonschema和filink schema数据类型映射
	 */
	private final Map<String,TypeInformation<?>> typeMapping = new HashMap<String,TypeInformation<?>>();
	/**
	 * flink sql schema
	 */
	private final Schema flinkSchema = new Schema();
	
	private final String windowAlias = "alias"+System.nanoTime();
	
	protected AbstractSchema(){
		try (InputStream inputStream = CascadeInverterSchema.class.getClassLoader().getResourceAsStream(getSchemaName())) {
			jsonSchema = new JSONObject(new JSONTokener(inputStream));
			initMapping();
			transform();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getWindowAlias(){
		return windowAlias;
	}
	
	private void initMapping(){
		typeMapping.put("STRING", Types.STRING());
		typeMapping.put("NUMBER", Types.DECIMAL());
		typeMapping.put("INTEGER", Types.DECIMAL());
		//typeMapping.put("STRING:DATE-TIME", Types.SQL_TIMESTAMP());
	}
	
	/**
	 * kafka json schema
	 * @return
	 */
	public String getJsonSchema(){
		return jsonSchema.toString();
	}
	
	/**
	 * flink sql schema
	 * @return
	 */
	public Schema getSchema(){
		return flinkSchema;
	}
	/**
	 * E.g. sn,timestamps,ipv1.sum,ipv2.avg
	 * @return
	 */
	public String getSelectString(){
		return selectString.toString().substring(0, selectString.length()-1);
	}
	/**
	 * E.g. INSERT INTO books (sn,endtime,ipv1) VALUES (?,?,?)
	 * @param tableName
	 * @return
	 */
	public String getInsertString(String tableName){
		StringBuffer s = new StringBuffer("INSERT INTO "+tableName+Utils.getCurrentYearAndMonth()+" ( ");
		StringBuffer v = new StringBuffer("VALUES ( ");
		for(String name : insertFields){
			s.append(name + " ,");
			v.append(" ? ,");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(s.substring(0, s.length()-1));
		sql.append(" )");
		sql.append(v.substring(0, v.length()-1));
		sql.append(" )");
		return sql.toString();
	}
	
	public TypeInformation<?>[] getFieldTypes(){
		return (TypeInformation<?>[]) fieldTypes.toArray(new TypeInformation[fieldTypes.size()]);
	}
	

	/** 
	 * @return jsonschema转换为flinkTable需要的schema
	 */
	private Schema transform(){
		JSONObject jsonObject = jsonSchema.getJSONObject("properties");
		Iterator<String> it = jsonObject.keySet().iterator();
		while(it.hasNext()){
			String fieldName = it.next().toUpperCase();
			JSONObject field = jsonObject.getJSONObject(fieldName);
			TypeInformation<?> fieldType = getTypeMapping(field,field.getString("type").toUpperCase());
			//description none 表示不参与聚合运算，逗号后的as 是该字段insert的别名
			String description = jsonObject.getJSONObject(fieldName).getString("description");
			//timestamps 既 eventtime
			if("timestamps".toUpperCase().equals(fieldName)){
				//水位线
				flinkSchema.field(fieldName, Types.SQL_TIMESTAMP()).rowtime(new Rowtime()
			    .timestampsFromSource().watermarksPeriodicBounded(300000));
				//end是flink table每个window自带的字段,表示window结束时间，该字段作为统计结束时间
				handleDescription(description,windowAlias+".end",insertFields);
				fieldTypes.add(Types.SQL_TIMESTAMP());
			}else{
				flinkSchema.field(fieldName, fieldType);
				handleDescription(description,fieldName,insertFields);
				fieldTypes.add(fieldType);
			}
		}

		return flinkSchema;
	}
	/**
	 * as前表示聚合运算类型，none表示不参与聚合运算，as后是该字段的别名
	 * <p>
	FIELD.count
	Returns the number of input rows for which the field is not null.
	</p>
	<p>
	FIELD.avg
	Returns the average (arithmetic mean) of the numeric field across all input values.
	</p>
	<p>
	FIELD.sum
	Returns the sum of the numeric field across all input values. If all values are null, null is returned.
	</p>
	<p>
	FIELD.sum0
	Returns the sum of the numeric field across all input values. If all values are null, 0 is returned.
	</p>
	<p>
	FIELD.max
	Returns the maximum value of field across all input values.
	</p>
	<p>
	FIELD.min
	Returns the minimum value of field across all input values.
	</p>esn ,output_power_day.sum as day_energy_power ,timestamps.none as statistics_date ,active_power.max as max_input_power , w.end
	 * @param description
	 */
	private void handleDescription(String description,String fieldName,ArrayList<String> insertFields){
		String[] split = description.split("as");
		String agg = split[0].trim();
		//判断字段是否要参与聚合运算
		if(!"none".equals(agg)){
			//参与聚合运算的  拼成flink需要的   FIELD.sum  的格式 ，.sum表示合计聚合
			selectString.append(fieldName.trim() +"."+agg);
		}else{
			selectString.append(fieldName);
		}
		//判断字段是否有别名
		if(split.length == 2){
			//有别名,insert的时候按别名写入
			String as = split[1].trim();
			selectString.append(" as "+ as);
			insertFields.add(as);
		}else{
			insertFields.add(fieldName);
		}
		selectString.append(",");
	}
	//json数据类型映射
	private TypeInformation<?> getTypeMapping(JSONObject field,String type){
		/*if("STRING".equals(type)){
			String format = field.getString("format").toUpperCase();
			if("DATE-TIME".equals(format)){
				return typeMapping.get(type+":"+format);
			}
		}*/
		return typeMapping.get(type);
	} 
	
	protected abstract String getSchemaName();

}

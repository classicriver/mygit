package com.tw.connect.schema;

import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.Schema;

import com.tw.connect.resources.InputStreamResources;


/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2018年11月23日
 * @version 1.0
 */
public class CascadeAvroSchema extends InputStreamResources {

	protected Schema schema;

	public Schema getSchema() {
		return schema;
	}

	@Override
	public void init(InputStream stream) {
		// TODO Auto-generated method stub
		try {
			this.schema = new Schema.Parser().parse(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "avro/Cascade.avsc";
	}
	

}

package com.tw.avro;

import java.io.IOException;

import org.apache.avro.Schema;

import com.tw.resources.FileResources;

public class AvroConfig extends FileResources{
	
	protected Schema schema;

	protected AvroConfig(){
		try {
			schema = new Schema.Parser().parse(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.close();
		}
	}

	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		return "protocol.avsc";
	}
	
	public Schema getSchema() {
		// TODO Auto-generated method stub
		return schema;
	}

}

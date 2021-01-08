package com.justbon.lps.schema;

import java.io.IOException;

import org.apache.avro.Schema;

public abstract class AbstractSchema {

	protected Schema schema;

	public AbstractSchema() {
		try {
			schema = new Schema.Parser().parse(AbstractSchema.class.getClassLoader().getResourceAsStream(getAvroFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Schema getSchema(){
		return schema;
	}
	
	protected abstract String getAvroFileName();
}

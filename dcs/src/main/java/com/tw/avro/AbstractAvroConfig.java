package com.tw.avro;

import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.Schema;

public class AbstractAvroConfig {
	
	protected static Schema schema;

	static {
		InputStream inputStream = ClassLoader
				.getSystemResourceAsStream("protocol.avsc");
		try {
			schema = new Schema.Parser().parse(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Schema getSchema() {
		return schema;
	}
}

package com.tw.avro;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

import com.tw.common.utils.Constants;
import com.tw.resources.ConfigProperties;

/**
 * 
 * @author xiesc
 * @TODO avro序列化
 * @time 2018年5月24日
 * @version 1.0
 */
public class AvroWriter extends AvroConfig {

	private static final String FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
	
	private String generateFileName(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);  
		return sdf.format(new Date());
	}
	
	public DataFileWriter<GenericRecord> getWriter() {
		/*// 静态序列化
		File diskFile = new File(Config.getStoragePath()+getFileName()+Constants.AVROSUFFIX);
		DatumWriter<Protocol> userDatumWriter = new SpecificDatumWriter<Protocol>(
				Protocol.class);
		DataFileWriter<Protocol> dataFileWriter = new DataFileWriter<Protocol>(
				userDatumWriter);
		try {
			dataFileWriter.create(new Protocol1().getSchema(), diskFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//动态序列化
		File diskFile = new File(ConfigProperties.getInstance().getStoragePath()+generateFileName()+Constants.AVROSUFFIX);
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);  
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);  
		try {
			dataFileWriter.create(schema, diskFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
		return dataFileWriter;
	}

	public void syncAndClose(DataFileWriter<GenericRecord> dataFileWriter) {
		// 多次写入之后，可以调用fsync将数据同步写入磁盘(IO)通道
		try {
			dataFileWriter.fSync();
			dataFileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

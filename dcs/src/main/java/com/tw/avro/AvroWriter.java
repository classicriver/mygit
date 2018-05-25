package com.tw.avro;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import com.tw.common.utils.Constants;
import com.tw.config.Config;
import com.tw.model.Protocol;

/**
 * 
 * @author xiesc
 * @TODO avro序列化
 * @time 2018年5月24日
 * @version 1.0
 */
public class AvroWriter {

	private static final String FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
	
	private String getFileName(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);  
		return sdf.format(new Date());
	}
	
	public DataFileWriter<Protocol> getWriter() {
		// 序列化
		File diskFile = new File(Config.getStoragePath()+getFileName()+Constants.AVROSUFFIX);
		DatumWriter<Protocol> userDatumWriter = new SpecificDatumWriter<Protocol>(
				Protocol.class);
		DataFileWriter<Protocol> dataFileWriter = new DataFileWriter<Protocol>(
				userDatumWriter);
		try {
			dataFileWriter.create(new Protocol().getSchema(), diskFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dataFileWriter;
	}

	public void syncAndClose(DataFileWriter<Protocol> dataFileWriter) {
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

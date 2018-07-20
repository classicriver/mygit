package com.tw.avro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.tw.common.utils.Constants;
import com.tw.common.utils.Utils;
import com.tw.resources.ConfigProperties;

/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年5月24日
 * @version 1.0
 */
public class AvroReader extends AvroConfig {
	/*
	 * public List<Message> readSerializeFileAsList() { List<Message> messages =
	 * new ArrayList<Message>(); File file = new File(Config.getStoragePath());
	 * if (file.isDirectory()) { String[] fileNames = file.list(); for (String
	 * filename : fileNames) { File avroFile = null; DataFileReader<Protocol1>
	 * fileReader = null; try { avroFile = new File(Config.getStoragePath() +
	 * "\\" + filename); DatumReader<Protocol1> reader = new
	 * SpecificDatumReader<Protocol1>( Protocol1.class); fileReader = new
	 * DataFileReader<Protocol1>(avroFile, reader); while (fileReader.hasNext())
	 * { Message message = new Message(Constants.DEFUALTTOPIC,
	 * Constants.DEFUALTTAG, Utils.getStringUniqueId(), fileReader .next()
	 * .getMessage() .toString() .getBytes( RemotingHelper.DEFAULT_CHARSET));
	 * messages.add(message); } } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } try { fileReader.close(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } //删除文件 avroFile.delete(); } } return messages; }
	 */

	public List<Message> readSerializeFileAsList() {
		List<Message> messages = new ArrayList<Message>();
		File file = new File(ConfigProperties.getInstance().getStoragePath());
		if (file.isDirectory()) {
			String[] fileNames = file.list();
			for (String filename : fileNames) {
				File avroFile = null;
				DataFileReader<GenericRecord> dataFileReader = null;
				try {
					avroFile = new File(ConfigProperties.getInstance().getStoragePath() + "\\"
							+ filename);
					DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(
							schema);
					dataFileReader = new DataFileReader<GenericRecord>(
							avroFile, datumReader);
					GenericRecord _current = null;
					while (dataFileReader.hasNext()) {
						Message message = new Message(Constants.DEFUALTTOPIC,
								Constants.DEFUALTTAG,
								Utils.getStringUniqueId(), dataFileReader
										.next(_current)
										.get("message")
										.toString()
										.getBytes(
												RemotingHelper.DEFAULT_CHARSET));
						messages.add(message);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					dataFileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 删除文件
				avroFile.delete();
			}
		}
		return messages;
	}
}

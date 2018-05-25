package com.tw.avro;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.tw.common.utils.Constants;
import com.tw.common.utils.Utils;
import com.tw.config.Config;
import com.tw.model.Protocol;

/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年5月24日
 * @version 1.0
 */
public class AvroReader {

	public List<Message> readSerializeFileAsList() {
		List<Message> messages = new ArrayList<Message>();
		File file = new File(Config.getStoragePath());
		if (file.isDirectory()) {
			String[] fileNames = file.list();
			for (String filename : fileNames) {
				File avroFile = null;
				DataFileReader<Protocol> fileReader = null;
				try {
					avroFile = new File(Config.getStoragePath() + "\\"
							+ filename);
					DatumReader<Protocol> reader = new SpecificDatumReader<Protocol>(
							Protocol.class);
					fileReader = new DataFileReader<Protocol>(avroFile, reader);
					while (fileReader.hasNext()) {
						Message message = new Message(Constants.DEFUALTTOPIC,
								Constants.DEFUALTTAG,
								Utils.getStringUniqueId(), fileReader
										.next()
										.getMessage()
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
					fileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//删除文件
				avroFile.delete();
			}
		}
		return messages;
	}
}

package com.justbon.lps.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;

import com.google.gson.annotations.SerializedName;
import com.justbon.lps.annotation.Nested;

/**
 * @author xiesc
 * @date 2020年7月23日
 * @version 1.0.0
 * @Description: TODO
 */
public class AvroUtils {
	private final static MethodHandles.Lookup lookup = MethodHandles.lookup();

	/**
	 * @Title: jsonToAvro
	 * @Description: Convert JSON to avro binary array.
	 * @param: @param
	 *             json
	 * @param: @param
	 *             schema
	 * @param: @return
	 * @param: @throws
	 *             IOException
	 * @return:byte[]
	 */
	public static byte[] jsonToAvro(String json, Schema schema) throws IOException {
		Record record = json2Avro(json, schema);
		GenericDatumWriter<Object> writer = new GenericDatumWriter<>(schema);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Encoder encoder = EncoderFactory.get().binaryEncoder(output, null);
		writer.write(record, encoder);
		encoder.flush();
		return output.toByteArray();
	}

	/**
	 * @Title: json2Avro
	 * @Description: Convert JSON to GenericData.Record.
	 * @param: @param
	 *             json
	 * @param: @param
	 *             schema
	 * @param: @return
	 * @param: @throws
	 *             IOException
	 * @return:GenericData.Record
	 */
	public static GenericData.Record json2Avro(String json, Schema schema) throws IOException {
		DatumReader<GenericData.Record> reader = new GenericDatumReader<>(schema);
		Decoder decoder = DecoderFactory.get().jsonDecoder(schema, json);
		GenericData.Record datum = reader.read(null, decoder);
		return datum;
	}

	/**
	 * Convert Avro binary byte array back to JSON String.
	 * 
	 * @param avro
	 * @param schema
	 * @return
	 * @throws IOException
	 */
	public static String avroToJson(byte[] avro, Schema schema) throws IOException {
		boolean pretty = false;
		GenericDatumReader<Object> reader = new GenericDatumReader<>(schema);
		DatumWriter<Object> writer = new GenericDatumWriter<>(schema);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JsonEncoder encoder = EncoderFactory.get().jsonEncoder(schema, output, pretty);
		Decoder decoder = DecoderFactory.get().binaryDecoder(avro, null);
		Object datum = reader.read(null, decoder);
		writer.write(datum, encoder);
		encoder.flush();
		output.flush();
		return new String(output.toByteArray(), "UTF-8");
	}

	/**
	 * @Title: object2Avro
	 * @Description: Object 转avro Record
	 * @param: @param
	 *             obj
	 * @param: @param
	 *             schema
	 * @param: @param
	 *             serializedNameSupport 是否兼容gson serializedName注解
	 * @param: @return
	 * @return:GenericData.Record
	 * @throws Throwable
	 * @throws IllegalAccessException
	 */
	public static GenericRecord object2Avro(Object obj, Schema schema, boolean serializedNameSupport)
			throws IllegalAccessException, Throwable {
		GenericRecord record = new GenericData.Record(schema);
		final Class<? extends Object> klass = obj.getClass();

		for (Field field : klass.getDeclaredFields()) {
			String fieldName = field.getName();
			field.setAccessible(true);
			Object data = lookup.unreflectGetter(field).invoke(obj);
			if (null != data) {
				// 兼容gson的别名注解
				if (serializedNameSupport) {
					SerializedName sn = field.getDeclaredAnnotation(SerializedName.class);
					if (null != sn) {
						fieldName = sn.value();
					}
				}
				// 嵌套类型
				Nested nested = field.getDeclaredAnnotation(Nested.class);
				if (null != nested) {
					// 集合嵌套
					if (data instanceof Collection) {
						List<GenericRecord> recordList = new ArrayList<>();
						for (Object d : (Collection<?>) data) {
							recordList.add(object2Avro(d, schema.getField(fieldName).schema().getElementType(),
									serializedNameSupport));
						}
						record.put(fieldName, recordList);
					} else {// 非集合嵌套
						record.put(fieldName,
								object2Avro(data, schema.getField(fieldName).schema(), serializedNameSupport));
					}
				} else {// 非嵌套
					record.put(fieldName, data);
				}
			}
		}
		return record;
	}

	/**
	 * @Title: object2Avro
	 * @Description: bean转avro Record
	 * @param: @param
	 *             obj
	 * @param: @param
	 *             schema
	 * @param: @return
	 * @return:GenericData.Record
	 * @throws Throwable
	 * @throws IllegalAccessException
	 */
	public static GenericRecord object2Avro(Object obj, Schema schema) throws IllegalAccessException, Throwable {
		return object2Avro(obj, schema, false);
	}
}

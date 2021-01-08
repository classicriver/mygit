package io.svectors.hbase.parser;

import com.google.common.base.Preconditions;

import io.confluent.connect.avro.AvroData;

import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.sink.SinkRecord;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiesc
 * @date 2020年8月17日
 * @version 1.0.0
 * @Description: avro data transform.
 * @deprecated use io.confluent.connect.avro.AvroConverter instead.
 */
@Deprecated
public class AvroEventParser implements EventParser {

	private static final AvroData AVRO_CONVERTER = new AvroData(100);
	private final Map<String, byte[]> EMPTY_MAP = Collections.emptyMap();

	/**
	 * default c.tor
	 */
	public AvroEventParser() {
	}

	@Override
	public Map<String, byte[]> parseKey(SinkRecord sr) throws EventParsingException {
		return parse(sr.topic(), sr.keySchema(), sr.key());
	}

	@Override
	public Map<String, byte[]> parseValue(SinkRecord sr) throws EventParsingException {
		return parse(sr.topic(), sr.valueSchema(), sr.value());
	}

	/**
	 * parses the value.
	 * 
	 * @param schema
	 * @param value
	 * @return
	 */
	private Map<String, byte[]> parse(final String topic, final Schema schema, final Object data) {
		final Map<String, byte[]> values = new LinkedHashMap<>();
		try {
			if (data == null || !(data instanceof GenericRecord)) {
				return EMPTY_MAP;
			}
			final GenericRecord record = (GenericRecord) AVRO_CONVERTER.fromConnectData(schema, data);
			final List<Field> fields = schema.fields();
			for (Field field : fields) {
				final byte[] fieldValue = toValue(record, field);
				if (fieldValue == null) {
					continue;
				}
				values.put(field.name(), fieldValue);
			}
			return values;
		} catch (Exception ex) {
			final String errorMsg = String.format("Failed to parse the schema [%s] , value [%s] with ex [%s]", schema,
					data, ex.getMessage());
			throw new EventParsingException(errorMsg, ex);
		}
	}

	private byte[] toValue(final GenericRecord record, final Field field) {
		Preconditions.checkNotNull(field);
		final Schema.Type type = field.schema().type();
		final String fieldName = field.name();
		final Object fieldValue = record.get(fieldName);
		switch (type) {
		case STRING:
			return fieldValue == null ? null : Bytes.toBytes(fieldValue.toString());
		case BOOLEAN:
			return fieldValue == null ? null : Bytes.toBytes(Boolean.valueOf(fieldValue.toString()));
		case BYTES:
			return fieldValue == null ? null : Bytes.toBytes((ByteBuffer) fieldValue);
		case FLOAT32:
			return fieldValue == null ? null : Bytes.toBytes(Float.valueOf(fieldValue.toString()));
		case FLOAT64:
			return fieldValue == null ? null : Bytes.toBytes(Double.valueOf(fieldValue.toString()));
		case INT8:
			return fieldValue == null ? null : Bytes.toBytes((Byte) fieldValue);
		case INT16:
			return fieldValue == null ? null : Bytes.toBytes(Short.valueOf(fieldValue.toString()));
		case INT32:
			return fieldValue == null ? null : Bytes.toBytes(Integer.valueOf(fieldValue.toString()));
		case INT64:
			return fieldValue == null ? null : Bytes.toBytes(Long.valueOf(fieldValue.toString()));
		default:
			return null;
		}
	}
}

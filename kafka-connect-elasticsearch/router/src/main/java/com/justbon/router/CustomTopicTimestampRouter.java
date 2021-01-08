package com.justbon.router;

import static org.apache.kafka.connect.transforms.util.Requirements.requireStruct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;
/**
 * 
 * @author xiesc
 * @date 2020年8月20日
 * @version 1.0.0
 * @Description: kafka connector 自定义路由函数  可根据指定的key获取记录中的值
 */
public class CustomTopicTimestampRouter<R extends ConnectRecord<R>> implements Transformation<R> {
	
	private static final Pattern TOPIC = Pattern.compile("${topic}", Pattern.LITERAL);
	private static final Pattern KEY = Pattern.compile("${key}", Pattern.LITERAL);
    private static final Pattern TIMESTAMP = Pattern.compile("${timestamp}", Pattern.LITERAL);
    
    private static final String PURPOSE = "get record value to topic";
    public static final String OVERVIEW_DOC =
            "Update the record's topic field as a function of the original topic value and the record timestamp."
                    + "<p/>"
                    + "This is mainly useful for sink connectors, since the topic field is often used to determine the equivalent entity name in the destination system"
                    + "(e.g. database table or search index name).";

    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(ConfigName.TOPIC_FORMAT, ConfigDef.Type.STRING, "${topic}-${key}-${timestamp}", ConfigDef.Importance.HIGH,
                    "Format string which can contain <code>${topic}</code> and <code>${timestamp}</code> as placeholders for the topic and timestamp, respectively.")
            .define(ConfigName.TIMESTAMP_FORMAT, ConfigDef.Type.STRING, "yyyyMMdd", ConfigDef.Importance.HIGH,
                    "Format string for the timestamp that is compatible with <code>java.text.SimpleDateFormat</code>.")
            .define(ConfigName.RECORD_KEY, ConfigDef.Type.STRING, "sysName",ConfigDef.Importance.LOW,
                    "Get record value by key <code>record.key</code>.");

    private interface ConfigName {
        String TOPIC_FORMAT = "topic.format";
        String TIMESTAMP_FORMAT = "timestamp.format";
        String RECORD_KEY = "record.key";
    }

    private String topicFormat;
    private ThreadLocal<SimpleDateFormat> timestampFormat;
    private String recordKey;

    @Override
    public void configure(Map<String, ?> props) {
        final SimpleConfig config = new SimpleConfig(CONFIG_DEF, props);

        topicFormat = config.getString(ConfigName.TOPIC_FORMAT);

        recordKey = config.getString(ConfigName.RECORD_KEY);
        final String timestampFormatStr = config.getString(ConfigName.TIMESTAMP_FORMAT);
        timestampFormat = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                final SimpleDateFormat fmt = new SimpleDateFormat(timestampFormatStr);
                fmt.setTimeZone(TimeZone.getDefault());
                return fmt;
            }
        };
    }

    @Override
    public R apply(R record) {
        final Long timestamp = record.timestamp();
        if (timestamp == null) {
            throw new DataException("Timestamp missing on record: " + record);
        }
        final String formattedTimestamp = timestampFormat.get().format(new Date(timestamp));
        String replace1 = TOPIC.matcher(topicFormat).replaceAll(Matcher.quoteReplacement(record.topic()));
    	final Struct value = requireStruct(record.value(), PURPOSE);
        Object obj = value.get(recordKey);
        String replaceAll = KEY.matcher(replace1).replaceAll(Matcher.quoteReplacement(obj.toString()));

        final String updatedTopic = TIMESTAMP.matcher(replaceAll).replaceAll(Matcher.quoteReplacement(formattedTimestamp));
        
        return record.newRecord(
                updatedTopic, record.kafkaPartition(),
                record.keySchema(), record.key(),
                record.valueSchema(), record.value(),
                record.timestamp()
        );
    }

    @Override
    public void close() {
        timestampFormat = null;
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

}

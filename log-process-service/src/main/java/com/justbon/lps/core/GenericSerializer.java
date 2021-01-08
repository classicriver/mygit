package com.justbon.lps.core;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

public class GenericSerializer implements KafkaSerializationSchema<Tuple2<GenericRecord,GenericRecord>>{
	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = 2787506686973666400L;
	private final SerializationSchema<GenericRecord> valueSerializer;
    private final SerializationSchema<GenericRecord> keySerializer;
    private final String topic;

    public GenericSerializer(String topic, Schema schemaKey, Schema schemaValue, String registryUrl) {
        this.keySerializer = ConfluentRegistryAvroSerializationSchema.forGeneric(topic + "-key", schemaKey, registryUrl);
        this.valueSerializer = ConfluentRegistryAvroSerializationSchema.forGeneric(topic + "-value", schemaValue, registryUrl); 
        this.topic = topic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple2<GenericRecord,GenericRecord> element, Long timestamp) {
        byte[] key = keySerializer.serialize(element.f0);
        byte[] value = valueSerializer.serialize(element.f1);

        return new ProducerRecord<byte[], byte[]>(topic, key, value);
    }
}

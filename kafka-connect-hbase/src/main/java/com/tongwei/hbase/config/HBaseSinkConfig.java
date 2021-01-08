/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tongwei.hbase.config;

import com.google.common.base.Preconditions;
import com.tongwei.hbase.parser.EventParser;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

/**
 * @author ravi.magham
 */
public class HBaseSinkConfig extends AbstractConfig {

    public static final String ZOOKEEPER_QUORUM_CONFIG = "zookeeper.quorum";
    public static final String EVENT_PARSER_CONFIG = "event.parser.class";
    public static String DEFAULT_HBASE_COLUMN_FAMILY = "yc";

    /*
     * The configuration for a table "test" will be in the format
     * hbase.test.rowkey.columns = id , ts
     * hbase.test.rowkey.delimiter = |
     */
    public static final String TABLE_NAME_TEMPLATE = "hbase.%s.table.name";
    public static final String TABLE_COLUMN_FAMILY_TEMPLATE = "hbase.%s.family";
    public static final String KFKJOSN_TOPIC = "sink.kfkjson.topic";
    public static final String KFKAVRO_CASCADE_TOPIC = "sink.kfkavro.cascade.topic";
    public static final String KFKAVRO_CENTRALIZED_TOPIC = "sink.kfkavro.centralized.topic";
    public static final String KFKAVRO_COMBINER_TOPIC = "sink.kfkavro.combiner.topic";

    private static ConfigDef CONFIG = new ConfigDef();
    private Map<String, String> properties;

    static {

        CONFIG.define(ZOOKEEPER_QUORUM_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Zookeeper quorum " +
          "of the hbase cluster");

        CONFIG.define(EVENT_PARSER_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Event parser class " +
          "to parse the SinkRecord");

    }

    public HBaseSinkConfig(Map<String, String> originals) {
        this(CONFIG, originals);
    }

    public HBaseSinkConfig(ConfigDef definition, Map<String, String> originals) {
        super(definition, originals);
        properties = originals;
    }

    /**
     * Validates the properties to ensure the rowkey property is configured for each table.
     */
   /* public void validate() {
        final String topicsAsStr = properties.get(ConnectorConfig.TOPICS_CONFIG);
        final String[] topics = topicsAsStr.split(",");
        for(String topic : topics) {
            String key = String.format(TABLE_ROWKEY_COLUMNS_TEMPLATE, topic);
            if(!properties.containsKey(key)) {
                throw new ConfigException(String.format(" No rowkey has been configured for table [%s]", key));
            }
        }
    }*/

    /**
     * Instantiates and return the event parser .
     * @return
     */
    public EventParser eventParser()  {
        try {
            final String eventParserClass = getString(EVENT_PARSER_CONFIG);
            final Class<? extends EventParser> eventParserImpl = (Class<? extends EventParser>) Class.forName(eventParserClass);
            return eventParserImpl.newInstance();
        } catch (ClassNotFoundException | InstantiationException  | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param propertyName
     * @param defaultValue
     * @return
     */
    public String getPropertyValue(final String propertyName, final String defaultValue) {
        String propertyValue = getPropertyValue(propertyName);
        return propertyValue != null ? propertyValue : defaultValue;
    }

    /**
     * @param propertyName
     * @return
     */
    public String getPropertyValue(final String propertyName) {
        Preconditions.checkNotNull(propertyName);
        return properties.get(propertyName);
    }
}

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
package com.tongwei.hbase.sink;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.hbase.util.HandleFunction;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author ravi.magham
 */
public class HBaseSinkTask extends SinkTask {

	private HandleFunction handle;

	@Override
	public String version() {
		return HBaseSinkConnector.VERSION;
	}

	@Override
	public void start(Map<String, String> props) {
		final HBaseSinkConfig sinkConfig = new HBaseSinkConfig(props);
		this.handle = new HandleFunction(sinkConfig);
	}

	@Override
	public void put(Collection<SinkRecord> records) {
		Map<String, List<SinkRecord>> byTopic = records.stream().collect(
				groupingBy(SinkRecord::topic));

		byTopic.entrySet()
				.stream()
				.collect(
						toMap(Map.Entry::getKey, (e) -> e.getValue().stream()
								.map(sr -> handle.apply(sr))));
	}

	@Override
	public void flush(Map<TopicPartition, OffsetAndMetadata> offsets) {
		// NO-OP
	}

	@Override
	public void stop() {
		// NO-OP
	}

}

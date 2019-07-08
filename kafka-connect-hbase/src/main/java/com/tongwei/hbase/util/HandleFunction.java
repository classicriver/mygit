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
package com.tongwei.hbase.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.tongwei.facade.BaseFacade;
import com.tongwei.facade.CascadeFacade;
import com.tongwei.facade.CentralizedFacade;
import com.tongwei.facade.CombinerdcFacade;
import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.hbase.parser.EventParser;

import org.apache.hadoop.hbase.client.Put;
import org.apache.kafka.connect.sink.SinkRecord;

import java.util.Map;

/**
 * @author ravi.magham
 */
public class HandleFunction implements Function<SinkRecord, Void> {

	private final EventParser eventParser;
	private final BaseFacade cascade;
	private final BaseFacade centralized;
	private final BaseFacade combinerdc;

	public HandleFunction(HBaseSinkConfig sinkConfig) {
		this.cascade = new CascadeFacade(sinkConfig);
		this.centralized = new CentralizedFacade(sinkConfig);
		this.combinerdc = new CombinerdcFacade(sinkConfig);
		this.eventParser = sinkConfig.eventParser();
	}

	/**
	 * Converts the sinkRecord to a {@link Put} instance The event parser parses
	 * the key schema of sinkRecord only when there is no property configured
	 * for {@link HBaseSinkConfig#TABLE_ROWKEY_COLUMNS_TEMPLATE}
	 *
	 * @param sinkRecord
	 * @return
	 */
	@Override
	public Void apply(final SinkRecord record) {
		Preconditions.checkNotNull(record);
		Map<String, Object> ycData = eventParser.parseValue(record);
		String esn = ycData.get("esn").toString();
		try {
			if (esn.matches("(.*)-N1-(.*)")) {
				cascade.execute(ycData);
			} else if (esn.matches("(.*)-H1-(.*)")) {
				centralized.execute(ycData);
			} else if (esn.matches("(.*)-N3-(.*)")) {
				combinerdc.execute(ycData);
			} else if (esn.matches("(.*)-HJ-(.*)")) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
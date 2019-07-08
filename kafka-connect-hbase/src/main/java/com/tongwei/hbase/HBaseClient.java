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
package com.tongwei.hbase;

import com.google.common.base.Preconditions;
import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.hbase.sink.SinkConnectorException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ravi.magham
 */
public final class HBaseClient {

    private final HBaseConnectionFactory connectionFactory;
    /**
	 * list的size()方法并不是线程安全的，加了一个计数器
	 */
    protected final AtomicInteger counter;
    private final List<Put> list;
	protected final int batchNum = 100;
	private final HBaseSinkConfig config;
	
	
	public void add(Put put){
		int size = counter.incrementAndGet();
		list.add(put);
		if(size % batchNum == 0){
			synchronized(list){
				write(list);
				list.clear();
			}
		}
	}

    public HBaseClient(final HBaseConnectionFactory connectionFactory,HBaseSinkConfig config) {
    	this.config = config;
        this.connectionFactory = connectionFactory;
        this.counter = new AtomicInteger();
        list = Collections.synchronizedList(new ArrayList<Put>());
    }

    public void write(final List<Put> puts) {
        Preconditions.checkNotNull(puts);
        final TableName table = TableName.valueOf(config.getPropertyValue(HBaseSinkConfig.TABLE_NAME_TEMPLATE, "kktest"));
        write(table, puts);
    }

    public void write(final TableName table, final List<Put> puts) {
        Preconditions.checkNotNull(table);
        Preconditions.checkNotNull(puts);
        try(final Connection connection = this.connectionFactory.getConnection();
            final BufferedMutator mutator = connection.getBufferedMutator(table);) {
            mutator.mutate(puts);
            mutator.flush();
        } catch(Exception ex) {
            final String errorMsg = String.format("Failed with a [%s] when writing to table [%s] ", ex.getMessage(),
              table.getNameAsString());
            throw new SinkConnectorException(errorMsg, ex);
        }
    }
}

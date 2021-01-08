package com.tw.connect.sink;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.table.sinks.RetractStreamTableSink;
import org.apache.flink.table.sinks.TableSink;

import com.tw.connect.model.Inverter;

public class JDBCRetractTableSink implements RetractStreamTableSink<Inverter>{

	@Override
	public TableSink<Tuple2<Boolean, Inverter>> configure(String[] arg0,
			TypeInformation<?>[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getFieldNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInformation<?>[] getFieldTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void emitDataStream(DataStream<Tuple2<Boolean, Inverter>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TupleTypeInfo<Tuple2<Boolean, Inverter>> getOutputType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInformation<Inverter> getRecordType() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.tw.coprocessor;

import java.io.IOException;

import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import com.tw.es.entity.RowKeyEntity;
import com.tw.es.client.EsJestClient;
import com.tw.es.dao.JestDao;
import com.tw.es.dao.JestDaoImpl;
/**
 * Elasticsearch 二级索引，hbase每写入一次数据，同步rowkey到es
 * @author xiesc
 * @TODO
 * @time 2018年12月24日
 * @version 1.0
 */
public class ESObServer extends BaseRegionObserver {
	private EsJestClient esClient;
	private JestDao esDao;
	@Override
	public void postPut(ObserverContext<RegionCoprocessorEnvironment> e,
			Put put, WALEdit edit, Durability durability) throws IOException {
		// TODO Auto-generated method stub
		String rowkey = Bytes.toString(put.getRow());
		esDao.insert(getEntity(rowkey));
	}

	@Override
	public void start(CoprocessorEnvironment e) throws IOException {
		// TODO Auto-generated method stub
		init(e);
	}

	@Override
	public void stop(CoprocessorEnvironment e) throws IOException {
		// TODO Auto-generated method stub
		esClient.close();
	}

	private void init(CoprocessorEnvironment e){
	     esClient = new EsJestClient(false);
	     esDao = new JestDaoImpl(esClient);
	}
	//rowkey   e.g. 3577-TH-N2-0901001-1541552700000
	//3577是hash，TH-N2-0901001是esn，1541552700000是时间戳
	private RowKeyEntity getEntity(String rowkey){
		long timestamps = 0L;
		StringBuffer esn = new StringBuffer();
		String[] splits = rowkey.split("-");
		//put内部数据是byte，获取需要遍历，这里直接把rowkey重新还原成esn和时间戳
		for(int i = 1 ; i < splits.length-1; i++){
			esn.append(splits[i]+"-");
		}
		timestamps = Long.parseLong(splits[4]);
		RowKeyEntity entity = new RowKeyEntity();
		entity.setEsn(esn.toString().substring(0, esn.length()-1));
		entity.setRowkey(rowkey);
		entity.setTimestamps(timestamps);
		return entity;
	}
}

package com.tw.ddcs.disruptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lmax.disruptor.WorkHandler;
import com.tw.ddcs.dao.AlertDao;
import com.tw.ddcs.dao.SolarManDao;
import com.tw.ddcs.dao.core.DaoFactory;
import com.tw.ddcs.model.Alert;
import com.tw.ddcs.model.Message;
import com.tw.ddcs.model.SolarMan;
import com.tw.ddcs.utils.Utils;
/**
 * 
 * @author xiesc
 * @TODO disruptor业务处理类
 * @time 2018年7月24日
 * @version 1.0
 */
public class MessageWorkHandler implements WorkHandler<Message>{
	
	private DaoFactory daoFactory;
	
	public MessageWorkHandler(DaoFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void onEvent(Message event) throws Exception {
		// TODO Auto-generated method stub
		String sn = event.getSn();
		Date date = Utils.getDate(event.getTime());
		HashMap<String, Object> data_yc = event.getData_yc();
		saveYcData(data_yc,sn,date);
		HashMap<String, Object> data_yx = event.getData_yx();
		saveYxData(data_yx,sn,date);
		
	}
	/**
	 * 保存遥测数据
	 * @param data
	 * @param sn
	 * @param date
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void saveYcData(HashMap<String, Object> data,String sn,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			HashMap<String, Object> map = new HashMap<>();
			String key = it.next();
			map.put("datetime", date);
			map.put("inverter_sn", sn);
			ArrayList<Map<?,?>> objects = (ArrayList<Map<?,?>>) data.get(key);
			for(Map<?,?> obj : objects){
				map.put((String) obj.get("desc"), obj.get("value"));
			}
			daoFactory.getDao(SolarManDao.class).batchInsert(Utils.mapToObject(map, SolarMan.class));
		}
	}
	/**
	 * 保存遥信数据
	 * @param data
	 * @param sn
	 * @param date
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void saveYxData(HashMap<String, Object> data,String sn,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			ArrayList<Map<String,Object>> objects = (ArrayList<Map<String,Object>>) data.get(key);
			for(Map<String,Object> obj : objects){
				obj.put("datetime", date);
				obj.put("inverter_sn", sn);
				daoFactory.getDao(AlertDao.class).batchInsert(Utils.mapToObject(obj, Alert.class));
			}
			
		}
	}

}

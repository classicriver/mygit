package com.tw.ddcs.disruptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lmax.disruptor.WorkHandler;
import com.tw.ddcs.config.GeneralMachineConfig;
import com.tw.ddcs.dao.AlertDao;
import com.tw.ddcs.dao.SolarManDao;
import com.tw.ddcs.dao.core.DaoFactory;
import com.tw.ddcs.model.Alert;
import com.tw.ddcs.model.GeneralMachineModel;
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
		if(event.isManaged()){
			saveManagedData(data_yc,sn,date);
		}else{
			saveYcData(data_yc,sn,date);
		}
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
	private void saveYcData(Map<String, Object> data,String sn,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			ArrayList<Map<?,?>> objects = (ArrayList<Map<?,?>>) data.get(key);
			daoFactory.getDao(SolarManDao.class).batchInsert(Utils.mapToObject(getYcData(objects,sn,date), SolarMan.class));
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
	private void saveYxData(Map<String, Object> data,String sn,Date date) throws Exception{
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
	/**
	 * 有配置的遥测数据
	 * @param data
	 * @param sn
	 * @param date
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void saveManagedData(Map<String, Object> data,String sn,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			ArrayList<Map<?,?>> subData = (ArrayList<Map<?,?>>) data.get(key);
			Map<String, Object> ycData = getYcData(subData,sn,date);
			Map<String, Object> result = new HashMap<>();
			result.put("datetime", date);
			
			//获取配置文件中的字段
			GeneralMachineModel configModel = GeneralMachineConfig.getConfig(sn);
			
			for(int i = 1;i <= configModel.getCirculation();i++){
				Map<String, String> properties = configModel.getProperties();
				Iterator<String> iterator = properties.keySet().iterator();
				while(iterator.hasNext()){
					String key1 = (String)iterator.next();
					String proValue = properties.get(key1);
					result.put(proValue, ycData.get(key1.replace("${id}",String.valueOf(i))));
				}
				result.put("inverter_sn", sn+"_"+i);
				daoFactory.getDao(SolarManDao.class).batchInsert(Utils.mapToObject(result, SolarMan.class));
			}
		}
	}
	
	private Map<String, Object> getYcData(ArrayList<Map<?,?>> datas,String sn,Date date){
			HashMap<String, Object> map = new HashMap<>();
			map.put("datetime", date);
			map.put("inverter_sn", sn);
			for(Map<?,?> obj : datas){
				map.put((String) obj.get("desc"), obj.get("value"));
			}
		return map;
	}
}

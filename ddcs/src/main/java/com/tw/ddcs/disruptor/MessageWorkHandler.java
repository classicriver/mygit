package com.tw.ddcs.disruptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.lmax.disruptor.WorkHandler;
import com.tw.ddcs.config.GeneralMachineConfig;
import com.tw.ddcs.dao.AlertDao;
import com.tw.ddcs.dao.SolarManDao;
import com.tw.ddcs.dao.core.DaoFactory;
import com.tw.ddcs.model.Alert;
import com.tw.ddcs.model.GeneralMachineModel;
import com.tw.ddcs.model.Message;
import com.tw.ddcs.model.OriginMessage;
import com.tw.ddcs.model.SolarMan;
import com.tw.ddcs.utils.Utils;
/**
 * 
 * @author xiesc
 * @TODO disruptor业务处理类
 * @time 2018年7月24日
 * @version 1.0
 */
public class MessageWorkHandler implements WorkHandler<OriginMessage>{
	
	private DaoFactory daoFactory;
	private final Gson gson = new Gson();
	
	public MessageWorkHandler(DaoFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void onEvent(OriginMessage event) throws Exception {
		// TODO Auto-generated method stub
		Message msg = gson.fromJson(event.getMessage(), Message.class);
		for(String sn : GeneralMachineConfig.getManagedMachineSns()){
			if(msg.getSn().equals(sn)){
				msg.setManaged(true);
			}
		}
		String sn = msg.getSn();
		Date date = Utils.getDate(msg.getTime());
		HashMap<String, Object> data_yc = msg.getData_yc();
		if(msg.isManaged()){
			saveManagedData(data_yc,sn,date);
		}else{
			saveYcData(data_yc,date);
		}
		HashMap<String, Object> data_yx = msg.getData_yx();
		saveYxData(data_yx,date);
		//help gc
		event.setMessage(null);
	}
	/**
	 * 保存遥测数据
	 * @param data  
	 * <p>data 格式{逆变器sn1:[{desc:ipv1,value:1.0},{desc:ipv2,value:2.0}],逆变器sn2:[{......}]} <p>
	 * @param sn
	 * @param date
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void saveYcData(Map<String, Object> data,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String sn = it.next();
			ArrayList<Map<?,?>> objects = (ArrayList<Map<?,?>>) data.get(sn);
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
	private void saveYxData(Map<String, Object> data,Date date) throws Exception{
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String sn = it.next();
			ArrayList<Map<String,Object>> objects = (ArrayList<Map<String,Object>>) data.get(sn);
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
				result.put("provider_type", configModel.getProvider_type());
				daoFactory.getDao(SolarManDao.class).batchInsert(Utils.mapToObject(result, SolarMan.class));
			}
		}
	}
	
	private Map<String, Object> getYcData(ArrayList<Map<?,?>> datas,String sn,Date date){
			HashMap<String, Object> map = new HashMap<>();
			map.put("datetime", date);
			map.put("inverter_sn", sn);
			map.put("provider_type", 5);
			for(Map<?,?> obj : datas){
				map.put((String) obj.get("desc"), obj.get("value"));
			}
		return map;
	}
}

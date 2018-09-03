package com.tw.ddcs.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tw.ddcs.model.GeneralMachineModel;

public class GeneralMachineConfig {
	
	private final static Map<String,GeneralMachineModel> config = new HashMap<>();
	private final static List<String> list = new ArrayList<>();
	private final static String path ="manager/";
	private final static String suffix =".properties";
	
	static{
		String[] fileNames = DdcsConfig.getInstance().getManagedFileNames();
		if(fileNames.length > 0){
			for (String fileName : fileNames) {
				try {
					final GeneralMachineModel model = new GeneralMachineModel();
				    final Properties pro = new Properties();
				    pro.load(GeneralMachineConfig.class.getClassLoader().getResourceAsStream(path+fileName+suffix));
				    model.setCirculation(Integer.valueOf((String) pro.get("circulation")));
					model.setProperties(properties2Map(pro));
				    config.put(fileName, model);
				    list.add(fileName);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private GeneralMachineConfig(){
		
	}
	
	public static GeneralMachineModel getConfig(String sn){
		return config.get(sn);
	}
	
	public static String getValue(String sn,String key){
		return (String) config.get(sn).getProperties().get(key);
	}
	
	public static List<String> getManagedMachineSns(){
		return list;
	}
	
	private static Map<String,String> properties2Map(Properties pro){
		  Iterator<Object> iterator = pro.keySet().iterator();
		    Map<String,String> map = new HashMap<>();
			while(iterator.hasNext()){
				String proKey = (String)iterator.next();
				if("circulation".equals(proKey)){
					continue;
				}
				map.put(proKey, pro.getProperty(proKey));
			}
			return map;
	}
}

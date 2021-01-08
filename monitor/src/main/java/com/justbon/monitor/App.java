package com.justbon.monitor;

import java.util.List;
import java.util.Map;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.scheduler.SchedulerFactory;
import com.justbon.monitor.sqlite.SqliteDataSource;

/**
 * Hello world!
 *
 */
public class App {
	

	private static String configFilePath = "C:\\Users\\Administrator.DESKTOP-HAAH264"
			+ "\\git\\pi\\pi\\monitor\\src\\main\\resources\\application.properties";
	static {PropertiesConfig.getInstance().initProperties(configFilePath);}
	private static final SchedulerFactory instance = SchedulerFactory.getInstance();
	
	public static void exe(){
		System.out.println("do");
	}
	public static void main(String[] args) {
		instance.enableSingleScheduleAtFixedRate("dataSendThread", () -> {
			List<Map<String, Object>> rs = SqliteDataSource.executeQuerySQL(
					"select id,startTime,endTime,description from " + PropertyKeys.MONITOR_SQLITE_RECORDSTABLE);
			System.out.println(rs.get(0).get("id"));
		});
	}
}

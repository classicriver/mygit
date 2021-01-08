package com.justbon.monitor.scheduler;


import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.core.APPRecorderContainer;
import com.justbon.monitor.entity.Recorder;
import com.justbon.monitor.log.LogFactory;
import com.justbon.monitor.sqlite.SqliteDataSource;
import com.justbon.monitor.utils.SnowFlake;

/**
 * 
 * @author xiesc
 * @date 2020年4月29日
 * @version 1.0.0
 * @Description: TODO agent端执行的调度，把采集到的app信息写入sqlite
 */
public class AgentRecorderScheduler {
	
	private static final SnowFlake sf = new SnowFlake(2);
	public boolean init() {
		try {
			SchedulerFactory.getInstance().enableSingleScheduleAtFixedRate("agentSchedulerThread", () -> {
				APPRecorderContainer.getRecorders().forEach((k, v) -> {
					LogFactory.info("agentScheduler running!  "+v.toString());
					SqliteDataSource.executeUpdateSQL(recorder2InsertSQL(v));
				});
			});
			LogFactory.info("agentScheduler init success!");
		} catch (Exception e) {
			LogFactory.error("agentSchedulerError："+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String recorder2InsertSQL(Recorder r) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + PropertyKeys.MONITOR_SQLITE_RECORDSTABLE);
		sb.append(" (id,startTime,endTime,description)");
		sb.append(" VALUES( ");
		sb.append(sf.nextId()+",");
		sb.append(r.getStartTime()+",");
		sb.append(r.getEndTime()+",'");
		sb.append(r.getDescription()+"'");
		sb.append(" )");
		return sb.toString();
	}
	
}

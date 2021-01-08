package com.justbon.lps.datasource;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.justbon.lps.config.ServiceConfig;
import com.justbon.lps.constants.Constants;
import com.justbon.lps.entity.MetaEntity;
import com.justbon.lps.jdbc.SourceJDBC;

/**
 * 
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 日志元数据源
 */
public class LogMetaDataSource extends RichParallelSourceFunction<MetaEntity> {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -1033877947194355877L;
	private volatile boolean isRuning;
	private transient SourceJDBC source;
	private static transient Logger log;
	private final static String sql = "SELECT i.bp_code, i.bp_name, i.bp_level, b.biz_code, "
			+ "b.biz_name, s.full_code, s.full_name, s.site_code, s.site_name, e.event_code, e.event_name "
			+ "FROM bp_info i LEFT JOIN bp_business b ON i.biz_id = b.id LEFT JOIN bp_site s ON i.site_id = s.id "
			+ "left join bp_event e on i.event_id = e.id";
	private static ServiceConfig config;
	private static transient ScheduledExecutorService jdbcScheduler;
	private transient LinkedBlockingQueue<MetaEntity> queue;

	/**
	 * 打开连接
	 */
	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);
		source = new SourceJDBC();
		isRuning = true;
		log = LogManager.getLogger(LogMetaDataSource.class);
		config = ServiceConfig.getInstance();
		queue = new LinkedBlockingQueue<>();
		jdbcScheduler = Executors.newSingleThreadScheduledExecutor((r) -> {
			Thread t = new Thread(r);
			t.setName("metaDataScheduler");
			t.setUncaughtExceptionHandler((th, e) -> {
				log.error(" jdbc scheduled error :" + e.getMessage());
				e.printStackTrace();
			});
			return t;
		});
		jdbcScheduler.scheduleAtFixedRate(() -> {
			List<MetaEntity> metas = source.getData(sql, MetaEntity.class);
			for (MetaEntity m : metas) {
				try {
					queue.put(m);
				} catch (InterruptedException e) {
					Thread.interrupted();
					e.printStackTrace();
				}
			}
		}, 0, config.getAsInt(Constants.METADATA_SCHEDULER_RATE), TimeUnit.MINUTES);
	}

	@Override
	public void run(SourceFunction.SourceContext<MetaEntity> ctx) throws Exception {
		// 定时
		while (isRuning) {
			ctx.collect(queue.take());
		}
	}

	@Override
	public void cancel() {
		isRuning = false;
		// 关闭mysql连接
		source.close();
		jdbcScheduler.shutdown();
	}

}

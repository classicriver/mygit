package com.tw.ddcs.quartz.job;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tw.ddcs.dao.BaseDao;
import com.tw.ddcs.dao.core.DaoFactory;

//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
public class MysqlSubmitJob extends DaoFactory implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		List<BaseDao<?>> allDaos = getAllDaos();
		for(BaseDao<?> dao : allDaos){
			dao.submit();
		}
	}
}

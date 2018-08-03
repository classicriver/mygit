package com.tw.ddcs.quartz.job;

import java.util.Iterator;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tw.ddcs.dao.DdcsDao;
import com.tw.ddcs.db.core.SingleBeanFactory;

//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
public class MysqlSubmitJob extends SingleBeanFactory implements Job{

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		Iterator<String> it = beanCache.keySet().iterator();
		while(it.hasNext()){
			Object object = beanCache.get(it.next());
			if(object instanceof DdcsDao){
				((DdcsDao) object).submit();
			}
		}
	}

}

package com.justbon.monitor.asm;

import com.justbon.monitor.core.APPRecorderContainer;
import com.justbon.monitor.core.MethodTag;
import com.justbon.monitor.core.MethodTagMaintainer;
import com.justbon.monitor.entity.Recorder;
import com.justbon.monitor.log.LogFactory;

/**
 * 
 * @author xiesc
 * @date 2020年4月28日
 * @version 1.0.0
 * @Description: TODO
 */
public final class MonitorAspect {

	private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

	public static void profiling(final long startNanos, final int methodTagId) {
		try {
			Recorder recorder = APPRecorderContainer.getRecorder(methodTagId);
			if (recorder == null) {
				recorder = new Recorder();
				recorder.setId(methodTagId);
				MethodTag methodTag = methodTagMaintainer.getMethodTag(methodTagId);
				recorder.setDescription(methodTag.getSimpleClassName()+"."+methodTag.getMethodName());
				APPRecorderContainer.saveRecorde(methodTagId, recorder);
			}
			recorder.setStartTime(startNanos);
			recorder.setEndTime(System.currentTimeMillis());
		} catch (Exception e) {
			LogFactory.error("ProfilingAspect.profiling(" + startNanos + ", " + methodTagId + ", "
					+ MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
		}
	}
}

package com.justbon.monitor.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.justbon.monitor.ASMBootStrap;
import com.justbon.monitor.asm.AgentFilter;

import com.justbon.monitor.asm.MonitorTransformer;
import com.justbon.monitor.log.LogFactory;

/**
 * @author xiesc
 * @date 2020年4月21日
 * @version 1.0.0
 * @Description: TODO 加载前启动函数
 */
public class AgentPerMain {
    public static void agentmain(String agentArgs, Instrumentation inst) {
    	LogFactory.info(" starting execute agentmain function...");
    	ASMBootStrap asmBoot = new ASMBootStrap(agentArgs);
    	if(asmBoot.init()){
			inst.addTransformer(new MonitorTransformer(), true);
			Class<?> classes[] = inst.getAllLoadedClasses();
		    for (int i = 0; i < classes.length; i++) {
		    	//classes[i].getName() 格式：java.lang.String
		    	String fullClassName = classes[i].getName();
		        if (AgentFilter.needInject(fullClassName)) {
		            System.out.println("Reloading Class: " + fullClassName);
		            try {
						inst.retransformClasses(classes[i]);
					} catch (UnmodifiableClassException e) {
						LogFactory.error(" retransformClasses exception : "+e.getMessage());
						e.printStackTrace();
					}
		        }
		    }
    	}
    }
}

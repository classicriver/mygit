package com.justbon.monitor.asm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * 
 * @author xiesc
 * @date 2020年4月23日
 * @version 1.0.0
 * @Description: TODO class加载与转换
 */
public class MonitorTransformer implements ClassFileTransformer {

	/**
	 * className 格式：java/lang/String
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		String fullClassName = className.replace('/', '.');
		if (!AgentFilter.needInject(fullClassName))
			return classfileBuffer;
		ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
		ClassVisitor cv = new MonitorClassAdapter(cw, fullClassName);
		cr.accept(cv, ClassReader.EXPAND_FRAMES);
		return cw.toByteArray();
	}
}

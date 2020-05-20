package com.justbon.monitor.asm;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import com.justbon.monitor.log.LogFactory;
import com.justbon.monitor.utils.TypeDescUtils;

/**
 * 
 * @author xiesc
 * @date 2020年4月23日
 * @version 1.0.0
 * @Description: TODO class增强
 */
public class MonitorClassAdapter extends ClassVisitor {

	private final String fullClassName;
	private final String simpleClassName;
	private boolean isInterface;
	//private boolean isInvocationHandler;
	private List<String> injectMethodList = new ArrayList<>();

	public MonitorClassAdapter(ClassVisitor classVisitor, String fullClassName) {
		super(org.objectweb.asm.Opcodes.ASM7, classVisitor);
		this.fullClassName = fullClassName;
		this.simpleClassName = TypeDescUtils.getSimpleClassName(fullClassName);
		injectMethodList = AgentFilter.getInjectMethods(fullClassName);
	}

	@Override
	public void visit(int javaVersion, int access, String name, String signature, String superName,
			String[] interfaces) {
		super.visit(javaVersion, access, name, signature, superName, interfaces);
		this.isInterface = (access & ACC_INTERFACE) != 0;
		//this.isInvocationHandler = isInvocationHandler(interfaces);
	}

//	private boolean isInvocationHandler(String[] interfaces) {
//		if (interfaces == null || interfaces.length <= 0) {
//			return false;
//		}
//		for (int i = 0; i < interfaces.length; ++i) {
//			if ("java/lang/reflect/InvocationHandler".equals(interfaces[i])) {
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		return super.visitField(access, name, descriptor, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {
		if (isInterface || !isNeedVisit(access, name)) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
		MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
		String desc4Human = TypeDescUtils.getMethodParamsDesc(descriptor);
        if (mv == null) {
            return null;
        }
        LogFactory.debug("ProfilingClassAdapter.visitMethod(" + access + ", " + name + ", " + descriptor + ", " + signature + ", " + Arrays.toString(exceptions) + ")" );
        return new MonitorMethodVisitor(access, name, descriptor, mv,fullClassName,simpleClassName,desc4Human);
	}

	private boolean isNeedVisit(int access, String methodName) {
		if ("<init>".equals(methodName) || "<clinit>".equals(methodName)) {
			return false;
		}
		if (injectMethodList.contains(methodName)) {
			return true;
		}
		return false;
	}
	//判断是不是反射调用的方法
//	private boolean isInvokeMethod(String methodName, String methodDesc) {
//		return methodName.equals("invoke") && methodDesc
//				.equals("(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;");
//	}
}

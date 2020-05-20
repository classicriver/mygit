package com.justbon.monitor.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import com.justbon.monitor.core.MethodTag;
import com.justbon.monitor.core.MethodTagMaintainer;

/**
 * 
 * @author xiesc
 * @date 2020年4月23日
 * @version 1.0.0
 * @Description: TODO method增强
 */
public class MonitorMethodVisitor extends AdviceAdapter{
	
    private static final String PROFILING_ASPECT_INNER_NAME = Type.getInternalName(MonitorAspect.class);
    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();
    private int methodTagId;
    private int startTimeIdentifier;
	
	public MonitorMethodVisitor(int access, String name, String descriptor, MethodVisitor methodVisitor,
			String fullClassName, String simpleClassName, String desc4Human) {
		super(org.objectweb.asm.Opcodes.ASM7, methodVisitor, access, name, descriptor);
        this.methodTagId = methodTagMaintainer.addMethodTag(getMethodTag(fullClassName, simpleClassName, name, desc4Human));
	}

	private MethodTag getMethodTag(String fullClassName, String simpleClassName, String methodName, String humanMethodDesc) {
        return MethodTag.getGeneralInstance(fullClassName, simpleClassName, methodName, "");
    }

    @Override
    protected void onMethodEnter() {
        //maintainer.addRecorder(methodTagId, profilingConfig.getProfilingParam(innerClassName + "/" + methodName));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startTimeIdentifier = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startTimeIdentifier);
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW)) {
            mv.visitVarInsn(LLOAD, startTimeIdentifier);
            mv.visitLdcInsn(methodTagId);
            mv.visitMethodInsn(INVOKESTATIC, PROFILING_ASPECT_INNER_NAME, "profiling", "(JI)V", false);
        }
    }
}

package com.justbon.monitor.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 
 * @author xiesc
 * @date 2020年4月27日
 * @version 1.0.0
 * @Description: TODO 反射方法增强
 */
public class MonitorDynamicMethodVisitor extends AdviceAdapter{
	
	private int startTimeIdentifier;
	
	private static final String PROFILING_ASPECT_INNER_NAME = Type.getInternalName(MonitorAspect.class);

	protected MonitorDynamicMethodVisitor(int access, String name,
			String descriptor,MethodVisitor methodVisitor) {
		super(org.objectweb.asm.Opcodes.ASM7, methodVisitor, access, name, descriptor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onMethodEnter() {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        startTimeIdentifier = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startTimeIdentifier);
    }

    @Override
    protected void onMethodExit(int opcode) {
        if ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitVarInsn(LLOAD, startTimeIdentifier);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitMethodInsn(INVOKESTATIC, PROFILING_ASPECT_INNER_NAME, "profiling", "(JLjava/lang/reflect/Method;)V", false);
        }
    }
}

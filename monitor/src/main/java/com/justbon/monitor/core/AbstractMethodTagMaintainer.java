package com.justbon.monitor.core;


import java.lang.reflect.Method;

/**
 * 
 * @author xiesc
 * @date 2020年4月28日
 * @version 1.0.0
 * @Description: TODO
 */
public abstract class AbstractMethodTagMaintainer {

    public abstract int addMethodTag(MethodTag methodTag);

    public abstract int addMethodTag(Method method);

    public abstract MethodTag getMethodTag(int methodId);

    public abstract int getMethodTagCount();

}

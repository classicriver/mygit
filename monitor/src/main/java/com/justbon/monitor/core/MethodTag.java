package com.justbon.monitor.core;

/**
 * 
 * @author xiesc
 * @date 2020年4月28日
 * @version 1.0.0
 * @Description: TODO
 */
public class MethodTag {

    private static final String TYPE_GENERAL = "General";

    private static final String TYPE_DYNAMIC_PROXY = "DynamicProxy";

    private final String simpleClassName;

    private final String methodName;

    private final String methodParamDesc;

    private final String fullDescription;

    private final String description;

    private final String type;


    private MethodTag(String fullClassName, String simpleClassName, String methodName, String methodParamDesc, String type) {
        this.simpleClassName = simpleClassName;
        this.methodName = methodName;
        this.methodParamDesc = methodParamDesc;
        this.fullDescription = fullClassName + "." + methodName;
        this.description = simpleClassName + "." + methodName + methodParamDesc;
        this.type = type;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodParamDesc() {
        return methodParamDesc;
    }

    public String getSimpleDesc() {
        return description;
    }

    public String getFullDesc() {
        return fullDescription;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MethodTag{" +
                "simpleClassName='" + simpleClassName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodParamDesc='" + methodParamDesc + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static MethodTag getGeneralInstance(String fullClassName, String simpleClassName, String methodName, String methodParamDesc) {
        return new MethodTag(fullClassName, simpleClassName, methodName, methodParamDesc, TYPE_GENERAL);
    }

    public static MethodTag getDynamicProxyInstance(String fullClassName, String className, String methodName, String methodParamDesc) {

        return new MethodTag(fullClassName, className, methodName, methodParamDesc, TYPE_DYNAMIC_PROXY);
    }
}

package com.justbon.monitor.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiesc
 * @TODO log4j2
 * @time 2018年5月14日
 * @version 1.0
 */
public class LogFactory {

	private static final ThreadLocal<DateFormat> TO_MILLS_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
    };

    private static boolean debugEnable = false;

    private static final String PREFIX = " [monitor] ";

    private static final String INFO_LEVEL = "INFO ";

    private static final String DEBUG_LEVEL = "DEBUG ";

    private static final String WARN_LEVEL = "WARN ";

    private static final String ERROR_LEVEL = "ERROR ";

    public static void setDebugEnable(boolean debugEnable) {
    	LogFactory.debugEnable = debugEnable;
    }

    public static void info(String msg) {
        System.out.println(getPrefix(INFO_LEVEL) + msg);
    }

    private static String getPrefix(String logLevel) {
        return getToMillisStr(new Date()) + PREFIX + logLevel;
    }

    private static String getToMillisStr(Date date) {
        return TO_MILLS_DATE_FORMAT.get().format(date);
    }

    public static void debug(String msg) {
        if (debugEnable) {
            System.out.println(getPrefix(DEBUG_LEVEL) + msg);
        }
    }

    public static void warn(String msg) {
        System.out.println(getPrefix(WARN_LEVEL) + msg);
    }

    public static void error(String msg) {
        System.err.println(getPrefix(ERROR_LEVEL) + msg);
    }

    public static void error(String msg, Throwable throwable) {
        synchronized (System.err) {
            System.err.println(getPrefix(ERROR_LEVEL) + msg + " " + throwable.getMessage());
            throwable.printStackTrace();
        }
    }
}

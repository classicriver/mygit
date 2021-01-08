package com.justbon.monitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 
 * @author xiesc
 * @date 2020年5月7日
 * @version 1.0.0
 * @Description: TODO linux工具类
 */
public class CommandLineUtils {
	public String executeLinuxCmd(String cmd) {
		Runtime run = Runtime.getRuntime();
		try {
			Process process = run.exec(cmd);
			String line;
			BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuffer out = new StringBuffer();
			while ((line = stdoutReader.readLine()) != null) {
				out.append(line);
			}
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

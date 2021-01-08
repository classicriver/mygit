package com.justbon.monitor.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.StringTokenizer;

import com.justbon.monitor.entity.Message;
import com.sun.management.OperatingSystemMXBean;

/**
 * 
 * @author xiesc
 * @date 2020年5月7日
 * @version 1.0.0
 * @Description: TODO获取树莓派系统信息
 */
public class PI {
	private final Base64.Encoder encoder = Base64.getEncoder();
	public String executeLinuxCmd(String cmd) {
		Runtime run = Runtime.getRuntime();
		try {
			Process process = run.exec(cmd);
			String line;
			StringBuffer out = new StringBuffer();
			try (BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				while ((line = stdoutReader.readLine()) != null) {
					out.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			process.waitFor();
			process.destroyForcibly();
			return out.toString();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	// cpu温度
	public String getCpuTemperatureInfo(String cmd) {
		String value = executeLinuxCmd(cmd);
		BigDecimal result = new BigDecimal("-1");
		if (null != value && !"".equals(value)) {
			result = new BigDecimal(value).divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_DOWN);
		}
		return encoder.encodeToString(result.unscaledValue().toByteArray());
	}

	/**
	 * 
	 * @Title: getDiskInfo
	 * @Description: TODO 磁盘使用率
	 * @param: @param
	 *             cmd
	 * @param: @return
	 * @return:double
	 */
	public String getDiskInfo(String cmd) {
		String value = executeLinuxCmd(cmd);
		BigDecimal result = new BigDecimal("-1");
		if (null != value && !"".equals(value)) {
			String[] split = value.split(" +");
			result = new BigDecimal(split[10].replace("%", "")).setScale(2,BigDecimal.ROUND_HALF_UP);
		}
		return encoder.encodeToString(result.unscaledValue().toByteArray());
	}

	/**
	 * 
	 * @Title: getMemInfo1
	 * @Description: TODO 内存占用率
	 * @param: @return
	 * @param: @throws
	 *             IOException
	 * @param: @throws
	 *             InterruptedException
	 * @return:int[]
	 */
	public String getMemInfo() {
		File file = new File("/proc/meminfo");
		BigDecimal result = new BigDecimal("-1");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
			String str = null;
			StringTokenizer token = null;
			BigDecimal memTotal = null;
			BigDecimal memAvailable = null;
			while ((str = br.readLine()) != null) {
				token = new StringTokenizer(str);
				if (!token.hasMoreTokens())
					continue;
				str = token.nextToken();
				if (!token.hasMoreTokens())
					continue;
				if (str.equalsIgnoreCase("MemTotal:")) {
					memTotal = new BigDecimal(token.nextToken());
				} else if (str.equalsIgnoreCase("MemAvailable:")) {
					memAvailable = new BigDecimal(token.nextToken());
				}
			}
			if (memTotal != null && memAvailable != null) {
				BigDecimal memUsed = memTotal.subtract(memAvailable);
				result = memUsed.divide(memTotal, 4, BigDecimal.ROUND_HALF_DOWN)
						.multiply(new BigDecimal("100"))
						.setScale(2,BigDecimal.ROUND_HALF_UP);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encoder.encodeToString(result.unscaledValue().toByteArray());
	}

	/**
	 * 
	 * @Title: getCpuInfo
	 * @Description: TODO 获取cpu占用率
	 * @param: @return
	 * @param: @throws
	 *             IOException
	 * @param: @throws
	 *             InterruptedException
	 * @return:float
	 */
	/*public double getCpuInfo() {
		File file = new File("/proc/stat");
		BufferedReader br1 = null;
		int user1 = 0;
		int nice1 = 0;
		int sys1 = 0;
		int idle1 = 0;
		int user2 = 0;
		int nice2 = 0;
		int sys2 = 0;
		int idle2 = 0;
		try {
			br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringTokenizer token = new StringTokenizer(br1.readLine());
			token.nextToken();
			user1 = Integer.parseInt(token.nextToken());
			nice1 = Integer.parseInt(token.nextToken());
			sys1 = Integer.parseInt(token.nextToken());
			idle1 = Integer.parseInt(token.nextToken());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != br1) {
				try {
					br1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br2 = null;
		try {
			br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringTokenizer token = new StringTokenizer(br2.readLine());
			token.nextToken();
			user2 = Integer.parseInt(token.nextToken());
			nice2 = Integer.parseInt(token.nextToken());
			sys2 = Integer.parseInt(token.nextToken());
			idle2 = Integer.parseInt(token.nextToken());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != br2) {
				try {
					br2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int s = (user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1);
		if (s != 0) {
			return new BigDecimal((user2 + sys2 + nice2) - (user1 + sys1 + nice1))
					.divide(new BigDecimal(s), 4, BigDecimal.ROUND_HALF_DOWN).doubleValue() * 100;
		}
		return -1;
	}*/
	/**
	 * 
	 * @Title: getCpuInfo
	 * @Description: TODO 获取cpu使用率
	 * @param: @param
	 *             cmd
	 * @param: @return
	 * @return:double
	 */
	public String getCpuInfo() {
		OperatingSystemMXBean osMBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return encoder.encodeToString(
				new BigDecimal(String.valueOf(osMBean.getSystemLoadAverage()))
				.setScale(2,BigDecimal.ROUND_HALF_UP)
				.unscaledValue().toByteArray()
				);
	}

	public enum SystemInfo {
		CPU, MEM, DISK, TEMPERATURE;

		private final PI pi = new PI();
		// cpu 温度
		private final String cpuTemperatureCMD = "cat /sys/class/thermal/thermal_zone0/temp";
		// 硬盘使用率
		private final String diskCMD = "df -h /";

		public void getInfo(Message msg) {
			switch (this) {
			case CPU:
				msg.setCpu(pi.getCpuInfo());
				break;
			case MEM:
				msg.setMem(pi.getMemInfo());
				break;
			case DISK:
				msg.setDisk(pi.getDiskInfo(diskCMD));
				break;
			case TEMPERATURE:
				msg.setTemperature(pi.getCpuTemperatureInfo(cpuTemperatureCMD));
				break;
			default:
				break;
			}
		}
	}
	public static void main(String[] args) {
		OperatingSystemMXBean osMBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		System.out.println(new BigDecimal(osMBean.getSystemLoadAverage()).setScale(2).toString());
	}
}

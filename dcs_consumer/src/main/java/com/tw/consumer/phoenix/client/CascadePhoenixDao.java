package com.tw.consumer.phoenix.client;

import java.sql.PreparedStatement;
import java.util.Map;

import com.tw.consumer.model.CascadeModel;
import com.tw.consumer.utils.CommonUtils;
/**
 * 
 * @author xiesc
 * @TODO 组串式逆变器
 * @time 2019年1月15日
 * @version 1.0
 */
public class CascadePhoenixDao extends PhoenixJDBC {

	@Override
	protected String getSqlString() {
		// TODO Auto-generated method stub
		return "upsert into \"cascade\" (\"power_factor\",\"dc_i\",\"total_output_power\",\"output_power_month\",\"uca\""
				+ ",\"reactive_power\",\"uab\",\"ia\",\"temperature\",\"ib\",\"ic\",\"dc_u\",\"line_frequency\""
				+ ",\"total_run_time\",\"i1\",\"i2\",\"i3\",\"i4\",\"i5\",\"i6\",\"i7\",\"i8\",\"u1\",\"u2\",\"u3\",\"u4\""
				+ ",\"u5\",\"u6\",\"u7\",\"u8\",\"inverter_efficiency\",\"output_power_day\",\"ubc\",\"active_power\""
				+ ",\"dc_input_total_power\",\"run_minute_day\",\"esn\",\"timestamps\") "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	
	@Override
	protected void prepareData(PreparedStatement ps, Map<String, Object> data) {
		// TODO Auto-generated method stub
		try {
			//此处顺序和上面问号顺序是对应的
			CascadeModel cascade = CommonUtils.mapToObject(data,
					CascadeModel.class);
			ps.setBigDecimal(1, cascade.getPower_factor());
			ps.setBigDecimal(2, cascade.getDc_i());
			ps.setBigDecimal(3, cascade.getTotal_output_power());
			ps.setBigDecimal(4, cascade.getOutput_power_month());
			ps.setBigDecimal(5, cascade.getUca());
			ps.setBigDecimal(6, cascade.getReactive_power());
			ps.setBigDecimal(7, cascade.getUab());
			ps.setBigDecimal(8, cascade.getIa());
			ps.setBigDecimal(9, cascade.getTemperature());
			ps.setBigDecimal(10, cascade.getIb());
			ps.setBigDecimal(11, cascade.getIc());
			ps.setBigDecimal(12, cascade.getDc_u());
			ps.setBigDecimal(13, cascade.getLine_frequency());
			ps.setBigDecimal(14, cascade.getTotal_run_time());
			ps.setBigDecimal(15, cascade.getI1());
			ps.setBigDecimal(16, cascade.getI2());
			ps.setBigDecimal(17, cascade.getI3());
			ps.setBigDecimal(18, cascade.getI4());
			ps.setBigDecimal(19, cascade.getI5());
			ps.setBigDecimal(20, cascade.getI6());
			ps.setBigDecimal(21, cascade.getI7());
			ps.setBigDecimal(22, cascade.getI8());
			ps.setBigDecimal(23, cascade.getU1());
			ps.setBigDecimal(24, cascade.getU2());
			ps.setBigDecimal(25, cascade.getU3());
			ps.setBigDecimal(26, cascade.getU4());
			ps.setBigDecimal(27, cascade.getU5());
			ps.setBigDecimal(28, cascade.getU6());
			ps.setBigDecimal(29, cascade.getU7());
			ps.setBigDecimal(30, cascade.getU8());
			ps.setBigDecimal(31, cascade.getInverter_efficiency());
			ps.setBigDecimal(32, cascade.getOutput_power_day());
			ps.setBigDecimal(33, cascade.getUbc());
			ps.setBigDecimal(34, cascade.getActive_power());
			ps.setBigDecimal(35, cascade.getDc_input_total_power());
			ps.setBigDecimal(36, cascade.getRun_minute_day());
			ps.setString(37, cascade.getEsn());
			ps.setLong(38, cascade.getTimestamps());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

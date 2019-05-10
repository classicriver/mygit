package com.tw.consumer.phoenix.client;

import java.sql.PreparedStatement;
import java.util.Map;

import com.tw.consumer.model.CombinerModel;
import com.tw.consumer.utils.CommonUtils;

/**
 * 
 * @author xiesc
 * @TODO 直流汇流箱
 * @time 2019年1月15日
 * @version 1.0
 */
public class CombinerPhoenixDao extends PhoenixJDBC {

	@Override
	protected String getSqlString() {
		// TODO Auto-generated method stub
		return "upsert into \"combiner\" (\"ratio_flat\",\"temperature\",\"dispersion_ratio\",\"pvu\",\"is_compute_ratio\",\"i1\""
				+ ",\"i2\",\"i3\",\"i4\",\"i5\",\"i6\",\"i7\",\"i8\",\"i9\",\"i10\",\"i11\",\"i12\",\"i13\",\"i14\",\"i15\",\"i16\",\"i17\""
				+ ",\"i8\",\"i19\",\"i20\",\"u1\",\"u2\",\"u3\",\"u4\",\"u5\",\"u6\",\"u7\",\"u8\",\"u9\",\"u10\",\"u11\",\"u12\",\"u13\""
				+ ",\"u14\",\"u15\",\"u16\",\"u17\",\"u8\",\"u19\",\"u20\",\"esn\",\"timestamps\") "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	protected void prepareData(PreparedStatement ps, Map<String, Object> data) {
		// TODO Auto-generated method stub
		try {
			// 此处顺序和上面问号顺序是对应的
			CombinerModel combiner = CommonUtils.mapToObject(data,
					CombinerModel.class);
			ps.setBigDecimal(1, combiner.getRatio_flat());
			ps.setBigDecimal(2, combiner.getTemperature());
			ps.setBigDecimal(3, combiner.getDispersion_ratio());
			ps.setBigDecimal(4, combiner.getPvu());
			ps.setBigDecimal(5, combiner.getIs_compute_ratio());
			ps.setBigDecimal(6, combiner.getI1());
			ps.setBigDecimal(7, combiner.getI2());
			ps.setBigDecimal(8, combiner.getI3());
			ps.setBigDecimal(9, combiner.getI4());
			ps.setBigDecimal(10, combiner.getI5());
			ps.setBigDecimal(11, combiner.getI6());
			ps.setBigDecimal(12, combiner.getI7());
			ps.setBigDecimal(13, combiner.getI8());
			ps.setBigDecimal(14, combiner.getI9());
			ps.setBigDecimal(15, combiner.getI10());
			ps.setBigDecimal(16, combiner.getI11());
			ps.setBigDecimal(17, combiner.getI12());
			ps.setBigDecimal(18, combiner.getI13());
			ps.setBigDecimal(19, combiner.getI14());
			ps.setBigDecimal(20, combiner.getI15());
			ps.setBigDecimal(21, combiner.getI16());
			ps.setBigDecimal(22, combiner.getI17());
			ps.setBigDecimal(23, combiner.getI18());
			ps.setBigDecimal(24, combiner.getI19());
			ps.setBigDecimal(25, combiner.getI20());
			ps.setBigDecimal(26, combiner.getU1());
			ps.setBigDecimal(27, combiner.getU2());
			ps.setBigDecimal(28, combiner.getU3());
			ps.setBigDecimal(29, combiner.getU4());
			ps.setBigDecimal(30, combiner.getU5());
			ps.setBigDecimal(31, combiner.getU6());
			ps.setBigDecimal(32, combiner.getU7());
			ps.setBigDecimal(33, combiner.getU8());
			ps.setBigDecimal(34, combiner.getU9());
			ps.setBigDecimal(35, combiner.getU10());
			ps.setBigDecimal(36, combiner.getU11());
			ps.setBigDecimal(37, combiner.getU12());
			ps.setBigDecimal(38, combiner.getU13());
			ps.setBigDecimal(39, combiner.getU14());
			ps.setBigDecimal(40, combiner.getU15());
			ps.setBigDecimal(41, combiner.getU16());
			ps.setBigDecimal(42, combiner.getU17());
			ps.setBigDecimal(43, combiner.getU18());
			ps.setBigDecimal(44, combiner.getU19());
			ps.setBigDecimal(45, combiner.getU20());
			ps.setString(46, combiner.getEsn());
			ps.setLong(47, combiner.getTimestamps());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

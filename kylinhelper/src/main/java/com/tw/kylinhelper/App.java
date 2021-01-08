package com.tw.kylinhelper;

import java.util.List;

import com.tw.kylinhelper.condition.StationAndDayCondition;
import com.tw.kylinhelper.dao.CombinerdcDisperseDao;
import com.tw.kylinhelper.model.CombinerdcDisperse;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		StationAndDayCondition stationAndDayCondition = new StationAndDayCondition();
		stationAndDayCondition.setDay("2019-04-06");
		stationAndDayCondition.setStation("18");
		/*stationAndDayCondition.setLimit(10);
		stationAndDayCondition.setOffset(1);*/
		CombinerdcDisperseDao combinerdcDisperseDao = new CombinerdcDisperseDao();
		List<CombinerdcDisperse> select = combinerdcDisperseDao
				.select(stationAndDayCondition);
		select.forEach( cd ->{
			System.out.println(cd.toString());
		});
	}
}

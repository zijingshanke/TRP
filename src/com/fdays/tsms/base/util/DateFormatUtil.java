package com.fdays.tsms.base.util;

import java.sql.Timestamp;
import java.util.Date;
import com.neza.tool.DateUtil;

public class DateFormatUtil {
	public static String getFormatDateString(Timestamp dateTime,
			String dateFormat) {
		String mydate = "";
		if (dateTime != null && "".equals(dateTime) == false) {
			Date tempDate = new Date(dateTime.getTime());
			mydate = DateUtil.getDateString(tempDate, dateFormat);
		}
		return mydate;
	}

}

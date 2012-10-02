package com.fdays.tsms.airticket.util;

import java.io.File;

import com.fdays.tsms.base.util.LogUtil;

/**
 * 机票业务操作日志
 */
public class AirticketLogUtil extends LogUtil {

	@SuppressWarnings("unchecked")
	public AirticketLogUtil(boolean isSystemOut, boolean isPrintClass,
			Class classIn, String des) {
		super(isSystemOut, isPrintClass, classIn, des);
	}

	@SuppressWarnings("unchecked")
	public void init(boolean isSystemOut, boolean isPrintClass, Class useClass) {
		setInputValue(isSystemOut, isPrintClass, useClass);

		String cbstLogFilePath = File.separator + "opt" + File.separator
				+ "IBM" + File.separator + "WebSphere" + File.separator
				+ "AppServer" + File.separator + "profiles" + File.separator
				+ "AppSrv01" + File.separator + "logs" + File.separator
				+ "tsms";// 设置日志目录;
		String cbstLogName = "airticket.log";// 名称项目

		arrangePrintLog(cbstLogFilePath, cbstLogName);
	}
}

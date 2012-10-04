package com.fdays.tsms.base;

import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

public class TimestampInit extends HttpServlet {
	/**
	 * 向Struts1的action注册null值的SqlTimestamp
	 * 作用：注册后，actionForm某属性类型为java.sql.Timestamp时，表单传参可以为NULL
	 * 否则若传NULL会报异常
	 * 整个项目只需注册一次就有效
	 */
	static {
		ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
    }
}

package com.fdays.tsms.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 单位转换工具类
 * 
 * @author yanrui
 */

public class UnitConverter {
	/**
	 * 金额进制转换，元转分 工商银行
	 * 
	 * @param:BigDecimal dollarAmount
	 * @return:String
	 */
	public static String getCentAmount(BigDecimal dollarAmount) {
		String tempAmount = (dollarAmount.multiply(new BigDecimal(100)))
				.toString();
		int isDecimal = tempAmount.indexOf(".");
		if (isDecimal > 0) {
			tempAmount = tempAmount.substring(0, isDecimal);
		}
		return tempAmount;
	}

	/**
	 * 金额进制转换，分转元
	 * 
	 * @example：工商银行
	 * @param:String
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getDollarAmount(String centAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		Double doubleAmount = Double.valueOf(centAmount);
		doubleAmount = doubleAmount / 100;
		tempAmount = BigDecimal.valueOf(doubleAmount);
		// System.out.println(tempAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:String strAmount
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getBigDecimalByString(String strAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		Double doubleAmount = Double.valueOf(strAmount);
		tempAmount = BigDecimal.valueOf(doubleAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:Double doubleAmount
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getBigDecimalByDouble(Double doubleAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		tempAmount = BigDecimal.valueOf(doubleAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:BigDecimal dollarAmount
	 * @return:String strAmount
	 */
	public static String getStringByBigDecimal(BigDecimal amount) {
		Double doubleAmount = amount.doubleValue();
		// 得到本地的缺省格式
		DecimalFormat df = new DecimalFormat("####.00");
		return df.format(doubleAmount);
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:BigDecimal dollarAmount
	 * @return:String strAmount
	 */
	public static String getStringByLong(long amount) {
		// 得到本地的缺省格式
		DecimalFormat df = new DecimalFormat("####.00");
		return df.format(amount);
	}
}

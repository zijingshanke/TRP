package com.fdays.tsms.base.util;

import java.math.BigDecimal;

public class ArithUtil {
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 11. 1. * 提供精确的加法运算。 2. 1. *
	 * 
	 * @param v1
	 *            被加数 2. 1. *
	 * @param v2
	 *            加数 2. 1. *
	 * @return 两个参数的和 2. 1.
	 */

	// 3. public static double add(double v1,double v2)
	// 4. {
	// 5.
	// 6. BigDecimal b1 = new BigDecimal(Double.toString(v1));
	// 7.
	// 8. BigDecimal b2 = new BigDecimal(Double.toString(v2));
	// 9.
	// 10. return b1.add(b2).doubleValue();
	// 11.
	// 12. }
	// 13.
	// 14. /**
	// * 15. 1. * 提供精确的减法运算。 2. 1. *
	// *
	// * @param v1
	// * 被减数 2. 1. *
	// * @param v2
	// * 减数 2. 1. *
	// * @return 两个参数的差 2. 1.
	// */
	// 2.
	// 3. public static double sub(double v1,double v2)
	// 4. {
	// 5.
	// 6. BigDecimal b1 = new BigDecimal(Double.toString(v1));
	// 7.
	// 8. BigDecimal b2 = new BigDecimal(Double.toString(v2));
	// 9.
	// 10. return b1.subtract(b2).doubleValue();
	// 11.
	// 12. }
	// 13.
	// 14.
	// 15. /**
	// * 16. 1. * 提供精确的乘法运算。 2. 1. *
	// *
	// * @param v1
	// * 被乘数 2. 1. *
	// * @param v2
	// * 乘数 2. 1. *
	// * @return 两个参数的积 2. 1.
	// */
	// 2.
	// 3. public static double mul(double v1,double v2)
	// 4. {
	// 5.
	// 6. BigDecimal b1 = new BigDecimal(Double.toString(v1));
	// 7.
	// 8. BigDecimal b2 = new BigDecimal(Double.toString(v2));
	// 9.
	// 10. return b1.multiply(b2).doubleValue();
	// 11.
	// 12. }
	// 13.
	// 14.
	// 15. /**
	// * 16. 1. * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 2. 1. * 小数点以后10位，以后的数字四舍五入。 2.
	// * 1. *
	// *
	// * @param v1
	// * 被除数 2. 1. *
	// * @param v2
	// * 除数 2. 1. *
	// * @return 两个参数的商 2. 1.
	// */
	// 2.
	// 3. public static double div(double v1,double v2)
	// 4. {
	// 5.
	// 6. return div(v1,v2,DEF_DIV_SCALE);
	// 7.
	// 8. }
	// 9.
	// 10.
	// 11.
	// 12. /**
	// * 13. 1. * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 2. 1. * 定精度，以后的数字四舍五入。 2.
	// * 1. *
	// *
	// * @param v1
	// * 被除数 2. 1. *
	// * @param v2
	// * 除数 2. 1. *
	// * @param scale
	// * 表示表示需要精确到小数点以后几位。 2. 1. *
	// * @return 两个参数的商 2. 1.
	// */
	// 2.
	// 3. public static double div(double v1,double v2,int scale)
	// 4. {
	// 5.
	// 6. if(scale<0)
	// 7. {
	// 8.
	// 9. throw new IllegalArgumentException("The scale must be a positive
	// integer or zero");
	// 10.
	// 11. }
	// 12.
	// 13. BigDecimal b1 = new BigDecimal(Double.toString(v1));
	// 14.
	// 15. BigDecimal b2 = new BigDecimal(Double.toString(v2));
	// 16.
	// 17. return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	// 18.
	// 19. }
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 * 
	 */

	public static double round(double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}
		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 * 
	 */

	public static BigDecimal round(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		if(b!=null){
			BigDecimal one = new BigDecimal("1");

			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
		}else{
			return BigDecimal.ZERO;
		}	

	}

}

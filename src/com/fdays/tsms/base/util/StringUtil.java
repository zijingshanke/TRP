package com.fdays.tsms.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 字符串处理工具类
 * 
 * @author yanrui
 */
public class StringUtil {
	
	public static void main(String[] args) {
		int[] example = getUnitNoByBegin(10001, 15000, 1);
		for (int i = 0; i < example.length; i++) {
			System.out.println(example[i]);
		}
	}

	/**
	 * 读取文本文件的内容
	 * 
	 * @param:String url
	 * @return String
	 */
	public static String readStrFromTxt(String url) {
		String str = "";
		try {
			char data[] = new char[6000];
			FileReader reader = new FileReader(url);

			int num = reader.read(data);
			System.out.println("num is--" + num);

			str = new String(data, 0, num);

			System.out.println("读取的字符是----");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("读取文件失败，原因是----------");
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * method 将字符串类型的日期转换为一个Date（java.sql.Date）
	 * 
	 * @param dateString
	 *            需要转换为Date的字符串
	 * @return dataTime Date
	 */
	public final static java.sql.Date string2Date(String dateString)
			throws java.lang.Exception {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);// util类型
		java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());// sql类型
		return dateTime;
	}

	/**
	 * 将字符串按分隔符转成Vector @ 支持 |
	 * @param String
	 *            strSrc
	 * @param String
	 *            strKen
	 * @return Vector<String>
	 */
	public static Vector<String> getVectorString(String strSrc, String strKen) {
		StringTokenizer toKen = new StringTokenizer(strSrc, strKen);
		Vector<String> vec = new Vector<String>();
		int i = 0;
		while (toKen.hasMoreElements()) {
			String value = (String) toKen.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			vec.add(i++, value);
		}
		for (int j = 0; j < vec.size(); j++) {
			System.out.println(j + "---" + vec.get(j));
		}
		return vec;
	}

	/**
	 * 删除字符数组中指定的元素
	 * 
	 * @param String[]
	 *            array
	 * @param String
	 *            para 需要删除的元素
	 */
	public static String[] delArrayCellByStr(String[] array, String para) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(para)) {
				array[i] = "";
			}
		}
		return array;
	}

	/**
	 * @param int
	 *            type:1,sampleTxt字符串 2,sampleTxt路径
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferReaderByInput(int type,
			String sampleTxt) {
		BufferedReader br = null;

		try {
			if (type == 1) {
				ByteArrayInputStream byteIn = new ByteArrayInputStream(
						sampleTxt.getBytes());
				br = new BufferedReader(new InputStreamReader(byteIn));
			} else if (type == 2) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(sampleTxt)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 去除多余空格
	 */
	public static String[] removeSpilthSpace(String[] infoArray, int newArrayLen) {
		if (newArrayLen == 0) {
			return new String[0];
		}
		String[] newInfoArray = new String[newArrayLen];
		int j = 0;
		for (int i = 0; i < infoArray.length; i++) {
			String info = infoArray[i];
			if (info.length() > 0) {
				newInfoArray[j] = info;
				j++;
			}
		}
		return newInfoArray;
	}

	public static int getIntByString(String str) {
		int a = 0;

		try {
			a = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return a;
	}

	public static int[] getUnitNoByBeginString(String beginNoStr,
			String endNoStr, int increment) {
		int beginNo = StringUtil.getIntByString(beginNoStr);
		int endNo = StringUtil.getIntByString(endNoStr);

		int[] a = getUnitNoByBegin(beginNo, endNo, increment);

		return a;
	}

	public static int[] getUnitNoByBegin(int beginNo, int endNo, int increment) {
		int[] a = new int[0];

		int count = (endNo - beginNo) + 1;

		if (count > 0) {
			a = new int[count];

			for (int i = 0; i < count; i++) {
				a[i] = beginNo;
				beginNo = beginNo + increment;
			}

		} else {
			a[0] = beginNo;
		}

		return a;
	}


}

package com.fdays.tsms.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.neza.exception.AppException;

/**
 * @author yanrui
 */
public class ServletUtil {

	/**
	 * 获取本地IP
	 */
	public static String getLocalHost() {
		String address = "";
		try {
			address = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("getLocalHost:" + address);
		return address;
	}

	/**
	 * 比较是否与本地IP相同
	 */
	public static boolean isLocalHost(String host) {

		try {
			String address = InetAddress.getLocalHost().getHostAddress();
//			System.out.println("---------ServletUtil.isLocalHost:" + address);
			if (host.equals(address)) {
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 获取请求Head,例如：https://www.qmpay.com
	 */
	public static String getRequestHead(HttpServletRequest request) {
		String requestHead = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return requestHead;
	}

	public static PrintWriter getPrintWriter(HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pw;
	}

	public static void printResult(String result, HttpServletResponse response)
			throws AppException {
		PrintWriter pw = getPrintWriter(response);

		pw.println(result);
	}
}

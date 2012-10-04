package com.fdays.tsms.base.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP工具类
 */
public class HttpInvoker {
	public static String readContentFromGet(String url) throws IOException {
		URL getUrl = new URL(url);
		StringBuilder sb = new StringBuilder();
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.setConnectTimeout(10000);// 设置主机连接超时，否则返回connect timed out
		connection.setReadTimeout(25000);// 设置读超时，否则返回Read timed out
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		System.out.println("===========Contents of get request=============");
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
			sb.append(lines);
		}
		reader.close();
		connection.disconnect();
		System.out.println("========Contents of get request ends=========");
		return sb.toString();
	}

	public static String readContentFromPost(String url, String parameters)
			throws IOException {
		try {
			// Post请求的url，与get不同的是不需要带参数
			url = url.replaceAll("[ ]", "%20");
			System.out.println("HttpInvoker readContentFromPost(),url:" + url);
			URL postUrl = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();

			connection.setConnectTimeout(50000);

			// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true
			connection.setDoOutput(true);

			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post cannot use caches
			connection.setUseCaches(false);

			// This method takes effects to every instances of this class.
			// connection.setFollowRedirects(true);

			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的意思是正文是urlencoded编码过的form参数，
			// 下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();

			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			String content = parameters;
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(content);

			out.flush();
			out.close(); // flush and close

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			connection.disconnect();
			return result.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	/**
	 * 获取客户端地址方法一
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 获取客户端地址方法二
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
		try {

			String temp = "http://pid.fdays.net:352/ib_tranx_req.asp?uid=Websd&sessionid=0&verify=0&termid=sztest&cmd=rt_parse&pnr=TBP3G&get_price=1&hk=1&get_ticket=0";
			// temp="http://www.baidu.com";
			String result = HttpInvoker.readContentFromGet(temp);
			System.out.println("------start result------------");
			System.out.println(result);
			System.out.println("------end result------------");
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
	}
}
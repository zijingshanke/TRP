package com.fdays.tsms.base.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpInvoker {
	
	
	public static String readContentFromGet(String url) throws IOException {
		URL getUrl = new URL(url);
		 StringBuilder sb = new StringBuilder(); 
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
				
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取7
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		while ((lines = reader.readLine()) != null) {
			//System.out.println(lines);
			sb.append(lines);
		}
		
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println("=============================");
		System.out.println("Contents of get request ends");
		System.out.println("=============================");
		return sb.toString();
	}

	public static String readContentFromPost(String url, String parameters)
			throws IOException {
		try {
			// Post请求的url，与get不同的是不需要带参数
			url = url.replaceAll("[ ]", "%20");
			System.out.println("HttpInvoker readContentFromPost(),url:" + url);
			URL postUrl = new URL(url);

			// 打开连接
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
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
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
			// return "链接超时";
			return ex.getMessage();
		}
	}




	public static void main(String[] args) {
		try {
			
			String temp = "http://pid.fdays.net:352/ib_tranx_req.asp?uid=Websd&sessionid=0&verify=0&termid=sztest&cmd=rt_parse&pnr=TBP3G&get_price=1&hk=1&get_ticket=0";
			 HttpInvoker.readContentFromGet(temp);
			System.out.println(temp.substring(0,5));
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
	}
}
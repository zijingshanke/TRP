package com.fdays.tsms.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.neza.exception.AppException;

/**
 * @author yanrui
 */
public class ServletUtil {
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

package com.fdays.tsms.base.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fdays.tsms.base.Constant;

public class ImageShowServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	// 处理post请求
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/gif");
		try {
			String sysPath = Constant.PROJECT_IMAGES_PATH;
			// String sysPath="D:\\test";

			String imgName = request.getParameter("imgName");
			if (imgName != null && !"".equals(imgName)) {
				String filePath = sysPath + File.separator + File.separator
						+ imgName;

				// 获取图片文件内容并输出
				File file = new File(filePath);
				if (file.exists()) {
					int fileLength = (int) file.length();
					response.setContentLength(fileLength);

					if (fileLength != 0) {

						/* 创建输入流 */
						InputStream inStream = new FileInputStream(file);
						byte[] buff = new byte[6090];

						/* 创建输出流 */
						ServletOutputStream out = response.getOutputStream();

						int readDataLen;
						while (((readDataLen = inStream.read(buff)) != -1)) {
							out.write(buff, 0, readDataLen);
						}
						inStream.close();
						out.flush();
						out.close();
					}

				}
			} else {
				System.out.println("fileName imgName 不能为空！！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void destroy() {
	}

}

package com.fdays.tsms.base;

import java.util.ArrayList;

public class Constant {
	public static String PROJECT_IMAGES_PATH = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/tsms/images";
	public static String PROJECT_PLATFORMREPORTS_PATH = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/tsms/PlatformReport";

	public static ArrayList<String> url = null;

	public void setUrl(ArrayList<String> url) {
		Constant.url = url;
	}

	public static void main(String[] args) {
		String str = "ca n 00000";
		str=clearSpace(str);
		System.out.println(toUpperCase(str, new Long(3)));
	}

	public static String clearSpace(String paramString) {
		if(paramString!=null){
			paramString = paramString.replaceAll("\\s*", "");
		}
		return paramString;
	}

	public static String toUpperCase(String paramString, Long length) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = clearSpace(paramString);
				localString = localString.trim().toUpperCase();

				if (localString.length() > length) {
					localString = localString.substring(0, length.intValue());
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localString;
	}

	public static String toUpperCase(String paramString) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = paramString.trim().toUpperCase();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localString;
	}

	public static String toLowerCase(String paramString) {
		String localString = "";
		try {
			if (paramString != null) {
				localString = paramString.trim().toLowerCase();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localString;
	}

	public static int toInt(String paramString) {
		int i = 0;
		try {
			i = Integer.parseInt(paramString);
		} catch (Exception localException) {
			i = 0;
		}
		return i;
	}

	public static Integer toInteger(String paramString) {
		Integer localInteger;
		try {
			localInteger = new Integer(paramString);
		} catch (Exception localException) {
			localInteger = new Integer(0);
		}
		return localInteger;
	}

	public static Long toLong(String paramString) {
		Long localLong;
		try {
			localLong = new Long(paramString);
		} catch (Exception localException) {
			localLong = new Long(0L);
		}
		return localLong;
	}

	public static Long toLong(Long paramString) {
		Long localLong;
		try {
			if (paramString != null) {
				localLong = new Long(paramString);
			} else {
				localLong = new Long(0L);
			}
		} catch (Exception localException) {
			localLong = new Long(0L);
		}
		return localLong;
	}

	public static float toFloat(String paramString) {
		float f = 0.0F;
		try {
			f = Float.parseFloat(paramString);
		} catch (Exception localException) {
			f = 0.0F;
		}
		return f;
	}
}
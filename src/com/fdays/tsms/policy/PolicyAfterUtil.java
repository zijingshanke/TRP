package com.fdays.tsms.policy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * 检查航班某些属性格式是否正确
 * @author chenqx
 * date:2010-12-15
 */
public class PolicyAfterUtil {
	
  	private static final long serialVersionUID = 1L;
   
  	/**
  	 * 检查是否符合航班号格式(开头2个字母加3至5个数字结束(Ex:CA1234))
  	 * @param flightCode
  	 * @return
  	 */
  	public static boolean isFlightCode(String flightCode){
  		String [] each = flightCode.split("[,，]");				//将用中英逗号分隔的字符串处理成字符串数组
  		String flightCodeStr = "[A-Za-z]{2}[0-9]{3,5}";
  		Pattern pattern = Pattern.compile(flightCodeStr); 
  		Matcher matcher = null;
  		for(int i=0;i<each.length;i++){
  			matcher =  pattern.matcher(each[i]);
  			if(!matcher.matches()){
//  				System.out.println("航班格式出错");
  				return false;
  			}
  		}
  		return true;
  	}
 
  	/**
  	 * 检查是否符合航段格式(3个字母-3个字母(Ex:cae-abe)或任意(一边或两边)3个字母换成*(Ex:*-abe))
  	 * @param str
  	 * @return
  	 */
  	public static boolean isFlightPoint(String flightPoint){
  		String [] each = flightPoint.split("[,，]");				//将用中英逗号分隔的字符串处理成字符串数组
  		String flightCodeStr = "([A-Za-z]{3}||[*])[-]([A-Za-z]{3}||[*])";
  		Pattern pattern = Pattern.compile(flightCodeStr); 
  		Matcher matcher = null;
  		for(int i=0;i<each.length;i++){
  			matcher =  pattern.matcher(each[i]);
  			if(!matcher.matches()){
//  				System.out.println("航段格式出错");
  				return false;
  			}
  		}
  		return true;
  	}
  	
  	/**
  	 * 检查是否符合舱位格式(单个字母(EX:y))
  	 * @param fligthClass
  	 * @return
  	 */
  	public static boolean isFlightClass(String fligthClass){
  		String [] each = fligthClass.split("[,，]");				//将用中英逗号分隔的字符串处理成字符串数组
  		String flightCodeStr = "[A-Za-z]{1}";
  		Pattern pattern = Pattern.compile(flightCodeStr); 
  		Matcher matcher = null;
  		for(int i=0;i<each.length;i++){
  			matcher =  pattern.matcher(each[i]);
  			if(!matcher.matches()){
//  				System.out.println("舱位格式出错");
  				return false;
  			}
  		}
  		return true;
  	}
 
}

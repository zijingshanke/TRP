package com.fdays.tsms.airticket.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Alidz业务工具类
 * @author chenqx
 * date	2010-12-3
 */
public class AlidzUtil {
//	private static final String BASE_HTTP = "http://192.168.1.166:6350/alidz.do?";
	private static final String BASE_HTTP = "http://localhost:6350/alidz.do?";
	
	
	/**
	 * 检测本票通是否在运行
	 * @return
	 */
	public static AlidzResult isRunning(){
		AlidzResult ar = new AlidzResult();
		String httpRequest = BASE_HTTP+"cmd=checkclt&fmt=xml";
		String backInf = backInf(httpRequest);
		if(backInf.contains("<exception>")){
			ar.setStatus(0);
			ar.setMessage("本票通未运行:"+subStringXML(backInf,"exception"));
			return ar;
		}
		if(backInf.contains("<Code>1</Code>")){
			ar.setStatus(1);
			ar.setMessage("本票通正在运行");
		}else{
			ar.setStatus(0);
			ar.setMessage("本票通未运行");
		}
		return ar;
	}
	
	/**
	 * 查询本电政策和价格（动态本电账号）
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码  如果是小编码，此项可以为空
	 * @param b2bUser		b2b用户
	 * @param b2bPass		b2b密码
	 * @return
	 *  注：入库后的PNR不能再查询此信息(查询失败：查价格失败，该PNR已经入库！)
	 */
	public static AlidzResult queryPriceByPnrex(String pnr, int bigpnr, String air,String b2bUser, String b2bPwd){
		String backInf = "";
		AlidzResult ar = new AlidzResult();
		String httpRequest = BASE_HTTP+ "cmd=querypricebypnrex&pnr="+pnr.toUpperCase()+"&bigpnr="+bigpnr+"&air="+air.toUpperCase()
				+"&b2buser="+b2bUser+"&b2bPwd="+b2bPwd+"&fmt=xml";
		backInf = backInf(httpRequest);
		if(backInf.contains("<exception>")){
			ar.setStatus(0);
			ar.setMessage("查询操作失败:"+subStringXML(backInf,"exception"));
			return ar;
		}
		if(backInf.contains("<errorInfo>")){
			ar.setStatus(0);
			ar.setMessage("查询失败："+subStringXML(backInf,"errorInfo"));
		}else{
			ar.setStatus(1);
			ar.setMessage("查询结果：\n"+"pnr:"+subStringXML(backInf,"pnr")+"\n"
					+"票面价："+subStringXML(backInf,"ticketprice")+"\n"
					+"所有税费:"+subStringXML(backInf,"totaltax")+"\n"
					+"返点："+subStringXML(backInf,"policynum")+"\n"
					+"支付总价(全部含燃油):"+subStringXML(backInf,"payprice"));
		}
		return ar;
	}
	
	/**
	 * 查询本电订单状态
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码
	 * @return
	 */
	public static AlidzResult queryorder(String pnr, int bigpnr,String air){
		String backInf = "";
		AlidzResult ar = new AlidzResult();
		StringBuffer message = new StringBuffer();
		String httpRequest = BASE_HTTP + "cmd=queryorder&pnr="+pnr.toUpperCase()+"&bigpnr="+bigpnr+"&air="+air.toUpperCase()
				+"&fmt=xml";
		backInf = backInf(httpRequest);
		if(backInf.contains("<exception>")){
			ar.setStatus(0);
			ar.setPayStatus(2);
			ar.setMessage("查询操作失败:"+subStringXML(backInf,"exception"));
//			System.out.println("exception----------");
			return ar;
		}
		if(backInf.contains("<code>1</code>")){
			message.append("查询成功：\n");
			message.append("订单号："+subStringXML(backInf,"orderno")+"\n");
			String payStatu = subStringXML(backInf,"paystatus");
			ar.setPayStatus(Integer.parseInt(payStatu));
			if("1".equals(payStatu)){
				message.append("支付状态：已支付\n");
			}else if("0".equals(payStatu)){
				message.append("支付状态：未支付\n");
			}else{
				message.append("支付状态：查询支付状态出错\n");
			}
			String orderStatu = subStringXML(backInf,"orderstatus");
			ar.setOrderstatus(Integer.parseInt(orderStatu));
			if("1".equals(orderStatu)){
				message.append("出票状态：出票成功\n");
			}else if ("0".equals(orderStatu)){
				message.append("出票状态：未出票\n");
			}else if ("7".equals(orderStatu)){
				message.append("出票状态：取消入库\n");
			}else if ("9".equals(orderStatu)){
				message.append("出票状态：其他状态\n");
			}else{
				message.append("出票状态：其他状态\n");
			}
			backInf = subStringXML(backInf,"tickets");
			String [] ticket = backInf.split("</ticket>");
			String passenger = "";
			String tktno = "";
			for(int i=0;i<ticket.length;i++){
				passenger = subStringXML(ticket[i],"passenger");
				tktno = subStringXML(ticket[i],"tktno");
				message.append("乘客名："+passenger+"\n");
				message.append("tktno:"+tktno+"\n");
			}
			ar.setStatus(1);
			ar.setMessage(message.toString());
		}else{
			message.append("查询失败:");
			if(backInf.contains("<errorInfo>")){
				message.append(subStringXML(backInf,"errorInfo"));
			}
			ar.setStatus(0);
			ar.setPayStatus(2);
			ar.setMessage(message.toString());
//			System.out.println("失败：支付状态"+ar.getPayStatus());
		}
		return ar;
	}
	
	/**
	 * 自动支付（导入本电系统,针对已经配置好本票通）
	 * @param pnr				大编码或者小编码
	 * @param bigpnr			1 大编码 0 小编码
	 * @param air				航空公司编码
	 * @param b2bUser			b2b用户
	 * @param b2bPwd			b2b密码
	 * @param srcticketprice	票面价
	 * @return
	 */
	public static AlidzResult order(String pnr, int bigpnr, String air,String b2bUser, String b2bPwd,
			float srcticketprice){
		String backInf = "";
		AlidzResult ar = new AlidzResult();
		String httpRequest =BASE_HTTP+ "cmd=order&pnr="+pnr.toUpperCase()+"&bigpnr="+bigpnr+"&air="+air.toUpperCase()
				+"&b2buser="+b2bUser+"&b2bPwd="+b2bPwd+"&srcticketprice="+srcticketprice+"&autopayflag=1";
		backInf = backInf(httpRequest);
		if(backInf.contains("<exception>")){
			ar.setStatus(0);
			ar.setIsPaySuccess(0);
			ar.setMessage("支付操作失败:"+subStringXML(backInf, "exception"));
			return ar;
		}
		if(backInf.contains("<paystatus>1</paystatus>")){
			ar.setStatus(1);
			ar.setIsPaySuccess(1);
			ar.setMessage("支付成功,支付金额："+subStringXML(backInf,"payprice"));

		}else if(backInf.contains("<errorInfo>")){
			ar.setStatus(0);
			ar.setIsPaySuccess(0);
			ar.setMessage("支付失败："+subStringXML(backInf,"errorInfo"));

		}else{
			ar.setStatus(0);
			ar.setIsPaySuccess(0);
			ar.setMessage("支付失败");
		}
		return ar;
	}

	
	/**
	 * 返回信息
	 * @param httpRequest 命令参数
	 * @return
	 */
	private static String backInf(String httpRequest){
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(httpRequest);
			System.out.println("AlidzUtil.backInf.传来的URL："+url);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "GB2312"));
			String temp = "";
			while((temp = in.readLine()) != null){
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			sb.append("<?xml version='1.0' encoding='GB2312' ?><exception>"+e.getMessage()+"</exception>");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			sb.append("<?xml version='1.0' encoding='GB2312' ?><exception>"+e.getMessage()+"</exception>");
			e.printStackTrace();
		} catch (IOException e) {
			sb.append("<?xml version='1.0' encoding='GB2312' ?><exception>"+e.getMessage()+"</exception>");
			e.printStackTrace();
		}
		System.out.println("AlidzUtil.backInf返回信息："+sb.toString());
		return sb.toString();
		
	}
	
	/**
	 * 截取XML格式中的某一节点值
	 * @param xml
	 * @param nodeName
	 * @return
	 */
	private static String subStringXML(String xml,String nodeName){
		String back = "";
		if(xml.contains(nodeName)){
			back = xml.substring(xml.indexOf(nodeName)+nodeName.length()+1,xml.indexOf("</"+nodeName+">"));
		}else{
			back = "无此信息";
		}
		return back;
	}
	
	/**
	 * 进行gb2312的URLEncoding转换
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String encodingGB2312(String source){
		String newEncod = "";
		if(!"".equals(source) && source != null){
			try {
				newEncod = URLEncoder.encode(source,"GB2312");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(newEncod);
		}
		return newEncod;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String []args){

	}
	
}

package com.fdays.tsms.airticket.biz;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.fdays.tsms.base.util.Dom4jDemo;
import com.neza.base.Inform;

public class AlidzBizImp implements AlidzBiz {
	private Dom4jDemo dom4j;													//xml解释类
	private final String BASE_HTTP = "http://localhost:6350/alidz.do?";			//基本请求地址
	private URL url;					
	private StringBuffer sb;
	private BufferedReader in;
	private String cmd = "";
	private String temp = "";
	private String inf = "";
	public AlidzBizImp(){
		dom4j = new Dom4jDemo();
	}
	/**
	 * 检测本票通是否在运行
	 */
	public String isRunning() {
		cmd = "cmd=checkclt&fmt=xml";
		inf = this.backInf(cmd);
		if(inf.contains("<Code>1</Code>")){
			inf = "本票通正在运行";
		}else{
			inf = "本票通未运行";
		}
		return inf;
	}

	/**
	 * 查询本电政策和价格
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码  如果是小编码，此项可以为空
	 * @param b2bUser		b2b用户
	 * @param b2bPass		b2b密码
	 * @return
	 */
	public String queryPriceByPnr(String pnr, int bigpnr, String air,
			String user, String pass) {
		if(bigpnr==1){														//大编码
			cmd = "cmd=querypricebypnrex&pnr="+pnr+"&bigpnr=1&air="+air.toUpperCase()+"&b2buser="
			+user+"&b2bpwd="+pass+"&fmt=xml";
		}else if(air==null || "".equals(air)){
			cmd = "cmd=querypricebypnrex&pnr="+pnr+"&bigpnr=0&b2buser="
			+user+"&b2bpwd="+pass+"&fmt=xml";
		}else{
			cmd = "cmd=querypricebypnrex&pnr="+pnr+"&bigpnr=0&air="+air.toUpperCase()+"&b2buser="
			+user+"&b2bpwd="+pass+"&fmt=xml";
		}
		inf = this.backInf(cmd);
		Document document = dom4j.createXml(inf);
		Element root = document.getRootElement();
		if(root.getName().equals("errorInfo")){
			inf = root.getText();
		}else{
			inf = "您好，您要查询的信息如下:<br>" +
					"prn:"+dom4j.getElementText(root,"pnr")+"<br>"+
					"air:"+dom4j.getElementText(root,"air")+"<br>"+
					"ticketprice:"+dom4j.getElementText(root,"ticketprice")+"<br>"+
					"policynum:"+dom4j.getElementText(root,"policynum")+"<br>"+
					"totaltax:"+dom4j.getElementText(root,"totaltax")+"<br>"+
					"payprice:"+dom4j.getElementText(root,"payprice")+"<br>";
		}
		return inf;
	}
	
	/**
	 * 查询本电订单状态
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryOrder(String pnr, int bigpnr,String air,String b2buser,String b2bpwd) {
		if(bigpnr==1){														//大编码
			cmd = "cmd=queryorder&pnr="+pnr+"&bigpnr=1&air="+air.toUpperCase()+"&b2buser="
			+b2buser+"&b2bpwd="+b2bpwd+"&fmt=xml";
		}else if(air==null || "".equals(air)){
			cmd = "cmd=queryorder&pnr="+pnr+"&bigpnr=0&b2buser="
			+b2buser+"&b2bpwd="+b2bpwd+"&fmt=xml";
		}else{
			cmd = "cmd=queryorder&pnr="+pnr+"&bigpnr=0&air="+air.toUpperCase()+"&b2buser="
			+b2buser+"&b2bpwd="+b2bpwd+"&fmt=xml";
		}
		inf = this.backInf(cmd);
		Document document = dom4j.createXml(inf);
		Element root = document.getRootElement();
		if(root.getName().equals("errorInfo")){
			inf = root.getText();
		}else{
			Element tickets = root.element("tickets");
			List<Element> ticket = tickets.elements();
			String temp = "";
			inf = "您好，您要查询的信息如下:<br>" +
					"prn:"+dom4j.getElementText(root,"pnr")+"<br>"+
					"orderno:"+dom4j.getElementText(root,"orderno")+"<br>"+
					"orderstatus:"+dom4j.getElementText(root,"orderstatus")+"<br>"+
					"paystatus:"+dom4j.getElementText(root,"paystatus")+"<br>"+
					"pnrsrcid:"+dom4j.getElementText(root,"pnrsrcid")+"<br>";
			for(int i=0;i<ticket.size();i++){
				temp = temp+"passenger:"+dom4j.getElementText(ticket.get(i),"passenger")+"<br>"+
						"tktno:"+dom4j.getElementText(ticket.get(i),"tktno")+"<br>";
			}
			inf = inf+temp;
		}
		return inf;
	}

	/**
	 * 导入本电系统
	 * @param pnr		大编码或者小编码
	 * @param bigpnr	1 大编码 0 小编码
	 * @param air		航空公司编码
	 * @param fmt		json 通过json数据格式返回 xml 通过xml格式返回订单信息
	 * @param b2bUser	b2b用户
	 * @param b2bPwd	b2b密码
	 * @param autopayflag	支付方式（1自动、0手动）
	 * @return
	 */
	public String order(String pnr, int bigpnr, String air,String user, String pwd,int autopayflag) {
		if(bigpnr==1){														//大编码
			cmd = "cmd=order&pnr="+pnr+"&bigpnr=1&air="+air.toUpperCase()+"&b2buser="
			+user+"&b2bpwd="+pwd+"&autopayflag="+autopayflag+"&fmt=xml";
		}else if(air==null || "".equals(air)){
			cmd = "cmd=order&pnr="+pnr+"&bigpnr=0&b2buser="
			+user+"&b2bpwd="+pwd+"&autopayflag="+autopayflag+"&fmt=xml";
		}else{
			cmd = "cmd=order&pnr="+pnr+"&bigpnr=0&air="+air.toUpperCase()+"&b2buser="
			+user+"&b2bpwd="+pwd+"&autopayflag="+autopayflag+"&fmt=xml";
		}
		inf = this.backInf(cmd);
		Inform inform = new Inform();
		Document document = dom4j.createXml(inf);
		Element root = document.getRootElement();
		String paystatus = dom4j.getElementText(root,"paystatus");
		if("1".equals(paystatus)){
			inf = "支付成功<br>"+
				"支付金额"+dom4j.getElementText(root,"payprice ");
			inform.setMessage("支付成功");
		}else{
			inf = dom4j.getElementText(root,"errorInfo");
			inform.setMessage("支付失败:"+dom4j.getElementText(root,"errorInfo"));
		}
		return inf;
		
	}
	
	
	
	/**
	 * 返回信息
	 * @param cmd 命令参数
	 * @return
	 */
	private String backInf(String cmd){
		Inform inf = new Inform();
		try {
			url = new URL(BASE_HTTP+cmd);
//			System.out.println(url);
			in = new BufferedReader(new InputStreamReader(url.openStream(), "GB2312"));
			sb = new StringBuffer();
			while((temp = in.readLine())!=null){
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			inf.setMessage("URLException:"+e.getMessage());
			inf.setBack(true);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			inf.setMessage("EncodingException:"+e.getMessage());
			inf.setBack(true);
			e.printStackTrace();
		} catch (IOException e) {
			inf.setMessage("IOException:"+e.getMessage());
			inf.setBack(true);
			sb = new StringBuffer();
			sb.append("<?xml version='1.0' encoding='GB2312' ?><errorInfo>"+e.getMessage()+"</errorInfo>");
		}
		System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	/**
	 * 进行gb2312的URLEncoding转换
	 * @param source
	 * @return
	 */
	private String EncodingGB2312(String source){
		
		String newEncod = "";
		try {
			newEncod = URLEncoder.encode(source,"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(newEncod);
		return newEncod;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AlidzBizImp abi = new AlidzBizImp();
//		System.out.println(abi.isRunning());
//		System.out.println(abi.queryPriceByPnr("V183M", 0, "ZH", "ff", "ss"));
//		System.out.println(abi.queryOrder("V183M", 0, "sz","cc","cc"));
		System.out.println(abi.order("V183M", 0, "U3","ff","ss",1));
//		abi.EncodingGB2312("张三");
	}


}

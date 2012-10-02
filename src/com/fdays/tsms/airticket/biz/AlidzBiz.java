package com.fdays.tsms.airticket.biz;


public interface AlidzBiz {

	/**
	 * 检测本票通是否在运行
	 * @return
	 */
	public String isRunning();
	
	
	/**
	 * 查询本电政策和价格
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码  如果是小编码，此项可以为空
	 * @param b2bUser		b2b用户
	 * @param b2bPass		b2b密码
	 * @return
	 */
	public String queryPriceByPnr(String pnr,int bigpnr,String air,String b2bUser,String b2bPwd);
	
	/**
	 * 查询本电订单状态
	 * @param pnr			大编码或者小编码
	 * @param bigpnr		1 大编码 0 小编码
	 * @param air			航空公司编码
	 * @return
	 */
	public String queryOrder(String pnr,int bigpnr,String air,String b2buser,String b2bpwd);
	
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
	public String order(String pnr,int bigpnr,String air,String b2bUser,String b2bPwd,int autopayflag);
	
}

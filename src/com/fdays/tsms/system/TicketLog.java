package com.fdays.tsms.system;

import com.fdays.tsms.system._entity._TicketLog;
import com.neza.tool.DateUtil;

public class TicketLog extends _TicketLog {
	private static final long serialVersionUID = 1L;

	public static final String GROUP_1 = "1,2,4,5,13,14,15,16,17,20,21";// 出票组
	public static final String GROUP_2 = "35,40,41,42,43,45,51,52,53,54,55,56,60";// 退废组
	public static final String GROUP_3 = "71,72,73,74,75,76,77,78,79,80";// 改签组
	public static final String GROUP_7 = "100,110,101,102,103,104,117";// 团队正常
	public static final String GROUP_8 = "121,122,125,126,127,117";// 团队退废
	
	public static final long TYPE_1 = 1;// 卖出订单录入*
	public static final long TYPE_2 = 2;// 收款(销售)*
	
	public static final long TYPE_4 = 4;// 取消出票*
	public static final long TYPE_5 = 5;// 确定出票*
	
	public static final long TYPE_13 = 13;// 申请支付（创建买入订单）*
	public static final long TYPE_14 = 14;// 锁定
	public static final long TYPE_15 = 15;// 付款（采购）*
	public static final long TYPE_16 = 16;// 解锁（自己的订单）
	public static final long TYPE_17 = 17;// 解锁（别人的订单）	

	public static final long TYPE_20 = 20;// 付退款（取消出票）*
	public static final long TYPE_21 = 21;// 收退款（取消出票）*	

	public static final long TYPE_35 = 35;// 退票订单录入
	public static final long TYPE_40 = 40;// 申请退票（向供应商,创建买入退票单）
	public static final long TYPE_41 = 41;// 退票审核通过（同意卖出的退票申请）
	public static final long TYPE_42 = 42;// 收退款（退票）
	public static final long TYPE_43 = 43;// 付退款（退票）
	
	public static final long TYPE_45 = 45;// 退票未通过

	public static final long TYPE_51 = 51;// 废票订单录入
	public static final long TYPE_52 = 52;// 申请废票
	public static final long TYPE_53 = 53;// 废票审核通过
	public static final long TYPE_54 = 54;// 收退款（废票）
	public static final long TYPE_55 = 55;// 付退款（废票）
	
	public static final long TYPE_60 = 60;// 退票未通过	

	public static final long TYPE_71 = 71;// 改签订单录入
	public static final long TYPE_72 = 72;// 申请改签订单
	public static final long TYPE_73 = 73;// 改签审核通过
	public static final long TYPE_74 = 74;// 确定改签审核通过
	public static final long TYPE_75 = 75;// 改签已支付
	public static final long TYPE_76 = 76;// 确认支付,改签完成
	public static final long TYPE_80 = 80;// 改签未通过


	
	
	public static final long TYPE_201 = 201;// 编辑备注
	public static final long TYPE_202 = 202;// 编辑订单(散票)
	
	public static final long TYPE_203 = 203;// 编辑订单(团队)
	

	public static final long TYPE_88 = 88;// 删除订单()

	public static final long TYPE_98 = 98;// 创建收支单

	public static final long TYPE_99 = 99;// 修改结算单
	
	// -------------团队订单----------------
	public static final long TYPE_100 = 100;//录入销售订单（卖出）
	public static final long TYPE_110 = 110;//录入销售订单（买入）
	
	public static final long TYPE_101 = 101;//统计利润
	public static final long TYPE_102 = 102;//申请支付
	public static final long TYPE_103 = 103;//确认支付
	public static final long TYPE_104 = 104;//确认出票	
	
	public static final long TYPE_117 = 117;// 解锁订单
	
	public static final long TYPE_121 = 121;// 录入退票订单（卖出）
	public static final long TYPE_122 = 122;// 录入退票订单（买入）
	public static final long TYPE_124 = 124;// 申请退票
	public static final long TYPE_125 = 125;// 审核退票
	public static final long TYPE_126 = 126;// 确认收退款	
	public static final long TYPE_127 = 127;// 确认付退款	
	
		

	// B2C----------------------------------
	public static final long TYPE_301 = 301;// B2C订单录入
	public static final long TYPE_302 = 302;// B2C确认收回票款

	public static final long ORDERTYPE_1 = 1;// 机票
	public static final long ORDERTYPE_2 = 2;// 酒店

	public String getOrderTypeInfo() {
		if (orderType != null) {
			if (orderType == ORDERTYPE_1) {
				return "机票";
			}
			if (orderType == ORDERTYPE_2) {
				return "酒店";
			} else {
				return "未定义";
			}
		} else {
			return "空";
		}
	}

	public String getTypeInfo() {
		if (type != null) {
			if(type==TYPE_1){
				return "销售订单录入";
			}else if(type==TYPE_2){
				return "销售订单收款";
			}else if(type==TYPE_4){
				return "取消出票";
			}else if(type==TYPE_5){
				return "确定出票";
			}else if(type==TYPE_13){
				return "申请支付";//（创建买入订单）
			}else if(type==TYPE_14){
				return "锁定";
			}else if(type==TYPE_15){
				return "确认支付";//付款（采购）*
			}else if(type==TYPE_16){
				return "解锁";//（自己的订单）
			}else if(type==TYPE_17){
				return "解锁";//（别人的订单）
			}else if(type==TYPE_20){
				return "付退款（取消出票）";
			}else if(type==TYPE_21){
				return "收退款（取消出票）";
			}else if(type==TYPE_35){
				return "退票订单录入";
			}else if(type==TYPE_40){
				return "通过申请1（退票）";//（向供应商,创建买入退票单）
			}else if(type==TYPE_41){
				return "通过申请2（退票）";//（同意卖出的退票申请）
			}else if(type==TYPE_42){
				return "收退款（退票）";
			}else if(type==TYPE_43){
				return "付退款（退票）";
			}else if(type==TYPE_45){
				return "退票未通过";
			}else if(type==TYPE_51){
				return "废票订单录入";
			}else if(type==TYPE_52){
				return "通过申请1(废票)";
			}else if(type==TYPE_53){
				return "通过申请2(废票)";
			}else if(type==TYPE_54){
				return "收退款（废票）";
			}else if(type==TYPE_55){
				return "付退款（废票）";
			}else if(type==TYPE_60){
				return "退票未通过";
			}
			else if(type==TYPE_71){
				return "录入改签订单";
			}
			else if(type==TYPE_72){
				return "审核改签订单";
			}
			else if(type==TYPE_73){
				return "确认支付（改签）";
			}
			else if(type==TYPE_98){
				return "创建结算单";
			}			
			else if(type==TYPE_99){
				return "修改结算单";
			}
			else if(type==TYPE_100){
				return "录入销售订单";//团队
			}
			else if(type==TYPE_101){
				return "统计利润";
			}
			else if(type==TYPE_102){
				return "申请支付";
			}
			else if(type==TYPE_103){
				return "确认支付";
			}
			else if(type==TYPE_104){
				return "确认出票";
			}
			else if(type==TYPE_121){
				return "录入退票订单";
			}else if(type==TYPE_124){
				return "申请退票";
			}else if(type==TYPE_125){
				return "审核退票";
			}else if(type==TYPE_126){
				return "确认收退款";
			}else if(type==TYPE_127){
				return "确认付退款";
			}else if(type==TYPE_117){
				return "解锁";
			}
			else if(type==TYPE_201){
				return "编辑备注";
			}else if(type==TYPE_202){
				return "编辑订单";
			}else if(type==TYPE_88){
				return "删除订单";
			}else{
				return "未定义";
			}			
		} else {
			return "";
		}
	}

	public String getFormatOptTime() {
		if (this.optTime != null) {
			return DateUtil.getDateString(this.optTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
}

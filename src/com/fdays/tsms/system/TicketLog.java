package com.fdays.tsms.system;

import com.fdays.tsms.system._entity._TicketLog;

public class TicketLog extends _TicketLog {
	public  static final long TYPE_0 = 0;//新订单录入
	public static final long TYPE_1 = 1;//卖出订单录入
	public static final long TYPE_2 = 2;//买入订单录入
	public static final long TYPE_3 = 3;//申请支付
	public static final long TYPE_4 = 4;//取消出票
	public static final long TYPE_5 = 5;//确定出票
	public static final long TYPE_6 = 6;//出票退款
	public static final long TYPE_7 = 7;//确定支付
	
	
	public static final long TYPE_9 = 9;//退票订单录入
	public static final long TYPE_10 =10;//申请退票
	public static final long TYPE_11 =11;//退票审核通过
	public static final long TYPE_12 =12;//确定退票退款
	public static final long TYPE_13 =13;//退票未通过
	
	public static final long TYPE_14 = 14;//废票订单录入
	public static final long TYPE_15 =15;//申请废票
	public static final long TYPE_16 =16;//废票审核通过
	public static final long TYPE_17 =17;//确定废票退款
	public static final long TYPE_18 =18;//退票未通过

	public static final long TYPE_21 = 21;//改签订单录入
	public static final long TYPE_22 = 22;//申请改签订单
	public static final long TYPE_23 = 23;//改签审核通过
	public static final long TYPE_24 = 24;//确定改签审核通过
	public static final long TYPE_25 = 25;//改签已支付
	public static final long TYPE_26 = 26;//确认支付改签完成
	public static final long TYPE_27 = 27;//改签未通过
	
	public static final long TYPE_29 = 29;//get lock 锁定
	public static final long TYPE_30 = 30;//release lock 解锁
	
	
	public static final long TYPE_31 = 31;//团队订单录入
	public static final long TYPE_32 = 32;//B2C订单录入
	public static final long TYPE_33 = 33;//修改团队订单录入
	public static final long TYPE_34 = 34;//团队利润录入
	public static final long TYPE_35 = 35;//修改团队利润录入
	public static final long TYPE_41 =41;//B2C确认收回票款
	
	public static final long TYPE_50 = 50;//编辑备注
	public static final long TYPE_51 = 51;//修改订单
	
	public static final long TYPE_88 =88;//废弃
	
	//---------------------------------------------团队票定案状态从100开始
	public static final long TYPE_101 = 101;// 1：新订单
	public static final long  TYPE_111 = 111;// 1：新订单,待统计利润
	
	public static final long TYPE_102 = 102;// 2：申请成功，等待支付
	public static final long TYPE_103 = 103;// 3：支付成功，等待出票
	public static final long TYPE_104 = 104;// 4：取消出票，等待退款
	public static final long TYPE_105 = 105;// 5：出票成功，交易结束
	public static final long TYPE_106 = 106;// 6：已退款，交易结束
	
	public static final long TYPE_107 = 107;// 退票订单，等待审核
	public static final long TYPE_108 = 108;// 退票审核通过，等待退款
	public static final long TYPE_109 = 109;// 已经退款，交易结束
	public static final long TYPE_110 = 110;// 退票未通过，交易结束
	
	//------------------------团队专用------------------------------------
	
	public static final long ORDERTYPE_1=1;//机票
	public static final long ORDERTYPE_2=2;//酒店
	public String getOrderTypeInfo() {
		if (orderType != null) {
			if (orderType == ORDERTYPE_1) {
				return "机票";
			} if (orderType == ORDERTYPE_2) {
				return "酒店";
			}else {
				return "未定义";
			}
		} else {
			return "空";
		}
	}

	public String getTypeInfo() {
		if (type != null) {
			if (type == this.TYPE_0) {
				return "新订单录入";
			} else if (type == this.TYPE_1) {
				return "卖出订单录入";
			} else if (type == this.TYPE_2) {
				return "买入订单录入";
			} else if (type == this.TYPE_3) {
				return "申请支付";
			} else if (type == this.TYPE_4) {
				return "取消出票";
			}  else if (type ==this.TYPE_5 ) {
				return "确定出票";
			} else if (type == this.TYPE_6) {
				return "出票退款";
			} else if (type == this.TYPE_7) {
				return "出票确定支付";
			} else if (type == this.TYPE_9) {
				return "退票订单录入";
			} else if (type == this.TYPE_10) {
				return "申请退票";
			} else if (type == this.TYPE_11) {
				return "退票审核通过";
			}else if (type == this.TYPE_12) {
				return "确定退票退款";
			}else if (type == this.TYPE_13) {
				return "退票未通过";
			}else if (type == this.TYPE_14) {
				return "废票订单录入";
			}else if (type == this.TYPE_15) {
				return "申请废票";
			}else if (type == this.TYPE_16) {
				return "废票审核通过";
			}else if (type == this.TYPE_17) {
				return "确定废票退款";
			}else if (type == this.TYPE_18) {
				return "退票未通过";
			}else if (type == this.TYPE_21) {
				return "改签订单录入";
			}else if (type == this.TYPE_22) {
				return "申请改签订单";
			}else if (type == this.TYPE_23) {
				return "改签审核通过";
			}else if (type == this.TYPE_24) {
				return "确定改签审核通过";
			}else if (type == this.TYPE_25) {
				return "改签已支付";
			}else if (type == this.TYPE_26) {
				return "确认支付改签完成";
			}else if (type == this.TYPE_27) {
				return "改签未通过";
			}else if (type == this.TYPE_29) {
				return "锁定";
			}else if (type == this.TYPE_30) {
				return "解锁";
			} else if (type == 31) {
				return "团队订单录入";
			} else if (type == 32) {
				return "B2C订单录入";
			} else if (type == 33) {
				return "修改团队订单录入";
			} else if (type == 34) {
				return "团队利润录入";
			} else if (type == 35) {
				return "修改团队利润录入";
			} else if (type == 41) {
				return "B2C确认收回票款";
			}else if (type == this.TYPE_50) {
				return "编辑备注";
			}else if (type == this.TYPE_51) {
				return "修改订单";
			}else if (type == this.TYPE_88) {
				return "废弃订单";
			}else if (type == this.TYPE_101) {
				return "新团队订单录入";
			}else if (type == this.TYPE_111) {
				return "新团队订单订单,待统计利润";
			}else if (type == this.TYPE_102) {
				return "团队订单申请成功，等待支付";
			}else if (type == this.TYPE_103) {
				return "团队订单支付成功，等待出票";
			}else if (type == this.TYPE_104) {
				return "团队订单取消出票，等待退款";
			}else if (type == this.TYPE_105) {
				return "团队订单出票成功，交易结束";
			}else if (type == this.TYPE_106) {
				return "团队订单已退款，交易结束";
			}else if (type == this.TYPE_107) {
				return "团队订单退票订单录入";
			}else if (type == this.TYPE_108) {
				return "团队订单退票审核通过，等待退款";
			}else if (type == this.TYPE_109) {
				return "团队订单已经退款，交易结束";
			}else if (type == this.TYPE_110) {
				return "团队订单退票未通过，交易结束";
			}else {
				return "未定义";
			}
		} else {
			return "空";
		}
		
	}
}

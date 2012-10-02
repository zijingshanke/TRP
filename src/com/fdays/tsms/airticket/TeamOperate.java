package com.fdays.tsms.airticket;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.base.MyLabel;
import com.fdays.tsms.base.Operate;
import com.fdays.tsms.right.UserRightInfo;

/**
 * 团队订单操作
 */
public class TeamOperate {
	private AirticketOrder saleOrder = new AirticketOrder();// 卖出(对客户)
	private AirticketOrder buyOrder = new AirticketOrder();// 买入(对航司)
	private OrderGroup orderGroup = new OrderGroup();
	private Operate operate = new Operate();

	private long saleId = new Long(0);
	private long buyId = new Long(0);
	private long saleStatus = new Long(0);
	private long buyStatus = new Long(0);
	private long saleTranType = new Long(0);
	private long buyTranType = new Long(0);
	private long saleBusinessType = new Long(0);
	private long buyBusinessType = new Long(0);
	private String drawer="";

	private String path;
	private UserRightInfo uri;
	
	public TeamOperate(){
		
	}

	public TeamOperate(AirticketOrder saleOrder, AirticketOrder buyOrder) {
		this.saleOrder = saleOrder;
		this.buyOrder = buyOrder;
		this.uri = saleOrder.getUri();
		setTeamOperateByOrder(saleOrder, buyOrder);
	}

	private void setTeamOperateByOrder(AirticketOrder saleOrder,
			AirticketOrder buyOrder) {
		this.orderGroup = saleOrder.getOrderGroup();
		this.path = saleOrder.getPath();
		
		this.saleId = saleOrder.getId();
		this.buyId = buyOrder.getId();

		this.saleStatus = saleOrder.getStatus();
		this.buyStatus = buyOrder.getStatus();

		this.saleTranType = saleOrder.getTranType();
		this.buyTranType = buyOrder.getTranType();

		this.saleBusinessType = saleOrder.getBusinessType();
		this.buyBusinessType = buyOrder.getBusinessType();
		if(saleOrder.getDrawer()!=null)
		  this.drawer=saleOrder.getDrawer().trim();
		else
			 this.drawer="";
	}

	public String getTeamOperateHTML() {
		List<MyLabel> myLabels = new ArrayList<MyLabel>();
//		System.out.println("===="+this.saleStatus);
		// 待处理新订单
		if (this.saleStatus == AirticketOrder.STATUS_101) {
			if (uri.hasRight("sb71")) {
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv(");
				sb.append("'" + this.orderGroup.getId() + "',");
				sb.append("'" + this.saleOrder.getSubGroupMarkNo() +"'");		
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[统计利润]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}
		
		// 待统计利润退票订单
		if (this.saleTranType==AirticketOrder.TRANTYPE_3&&this.saleStatus == AirticketOrder.STATUS_107) {
			if (uri.hasRight("sb71")) {
				if (uri.hasRight("sb71")) {
					MyLabel ml2 = new MyLabel();
					StringBuffer sb = new StringBuffer();
					sb.append("onclick=\"");
					sb.append("showDiv(");
					sb.append("'" + this.orderGroup.getId() + "',");
					sb.append("'" + this.saleOrder.getSubGroupMarkNo() +"'");					
					sb.append(")\"");
					ml2.setEvents(sb.toString());
					ml2.setLabText("[统计利润]");
					ml2.setEndText("<br/>");
					myLabels.add(ml2);
				}
				operate.setMyLabels(myLabels);
			}
			operate.setMyLabels(myLabels);
		}

		// 待申请支付订单
		if (this.saleStatus == AirticketOrder.STATUS_102) {
			if (uri.hasRight("sb72")) {
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
								+ "/airticket/listAirTicketOrder.do?thisAction=applyTeamPayment&id="
								+ this.saleId);
				ml.setLabText(" [申请支付]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// 待确认支付订单
		if (this.buyStatus == AirticketOrder.STATUS_103) {
			
			if("B2B网电".equals(drawer)){
				if (uri.hasRight("sb76")) {
					MyLabel ml2 = new MyLabel();
					StringBuffer sb = new StringBuffer();
					sb.append("onclick=\"");
					sb.append("showDiv10(");
					sb.append(this.buyId + ",");
					sb.append(this.orderGroup.getId() + ",");
					sb.append("'" + this.buyOrder.getAirOrderNo() + "',");
					sb.append("'" + this.buyOrder.getTotalAmount() + "',");
					sb.append("'" + this.buyOrder.getCyrs() + "'");
					sb.append(")\"");
					ml2.setEvents(sb.toString());
					ml2.setLabText("[确认支付]");
					ml2.setEndText("<br/>");
					myLabels.add(ml2);
				}
				operate.setMyLabels(myLabels);
			}else{
				if (uri.hasRight("sb73")) {
					MyLabel ml2 = new MyLabel();
					StringBuffer sb = new StringBuffer();
					sb.append("onclick=\"");
					sb.append("showDiv10(");
					sb.append(this.buyId + ",");
					sb.append(this.orderGroup.getId() + ",");
					sb.append("'" + this.buyOrder.getAirOrderNo() + "',");
					sb.append("'" + this.buyOrder.getTotalAmount() + "',");
					sb.append("'" + this.buyOrder.getCyrs() + "'");
					sb.append(")\"");
					ml2.setEvents(sb.toString());
					ml2.setLabText("[确认支付]");
					ml2.setEndText("<br/>");
					myLabels.add(ml2);
				}
				operate.setMyLabels(myLabels);
			}
		}
		
		// 出票成功--创建退票订单
		if (this.saleStatus == AirticketOrder.STATUS_105) {
			MyLabel ml = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv21(");
			sb.append("'" + this.orderGroup.getId() + "'");
			sb.append(")\"");
			ml.setEvents(sb.toString());
			ml.setLabText("[创建退票单]");
			ml.setEndText("<br/>");
			myLabels.add(ml);
			operate.setMyLabels(myLabels);
		}

		// 待审核退票订单
		if (this.saleStatus == AirticketOrder.STATUS_117) {
			if (uri.hasRight("sb77")) {
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
								+ "/airticket/listAirTicketOrder.do?thisAction=applyTeamRefund&id="
								+ this.saleId);
				ml.setLabText("[同意退票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// 退票已审待退款订单,此退款退给旅行社，暂时不用
		/*
		if (this.buyStatus == AirticketOrder.STATUS_108) {
			if (this.buyBusinessType == AirticketOrder.BUSINESSTYPE__1) {
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv11(");
				sb.append("'" + this.buyId + "',");
				sb.append("'" + this.buyOrder.getIncomeretreatCharge() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[确认已付退款]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);

				operate.setMyLabels(myLabels);
			}
		}
   */
		// 退票已审待退款订单,此退款航空公司退给我们
		if (this.saleStatus == AirticketOrder.STATUS_108) {
			if (this.saleBusinessType == AirticketOrder.BUSINESSTYPE__2) {
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv12(");
				sb.append("'" + this.buyOrder.getId() + "',");
				sb.append("'" + this.buyOrder.getIncomeretreatCharge() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[确认收退款]"); 
			
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
		}
		
		if (this.buyTranType == 2 && buyStatus== AirticketOrder.STATUS_105 && uri.hasRight("sb80") )
		{
			MyLabel ml2 = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv101(");
			sb.append(this.buyId + ",");
			sb.append(this.orderGroup.getId() + ",");
			sb.append("'" + this.buyOrder.getAirOrderNo() + "',");
			sb.append("'" + this.buyOrder.getTotalAmount() + "',");
			sb.append("'" + this.buyOrder.getCyrs() + "',");
			if(this.buyOrder.getAccount()!=null)
			{
				sb.append("'" + this.buyOrder.getAccount().getId()+ "',");
			}
			else
			{
				sb.append("'" +0+ "',");
			}
			
			sb.append("'" + this.buyOrder.getOperate126Time() + "'");
			sb.append(")\"");
			ml2.setEvents(sb.toString());
			ml2.setLabText("[确认已支付]");
			ml2.setEndText("<br/>");
			myLabels.add(ml2);			
			operate.setMyLabels(myLabels);
		}
		
		if (this.buyTranType == 3 && buyStatus== AirticketOrder.STATUS_109 && uri.hasRight("sb81") )
		{
			MyLabel ml2 = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv14(");
			sb.append("'" + this.buyOrder.getId() + "',");
			sb.append("'" + this.buyOrder.getIncomeretreatCharge() + "',");
			if(this.buyOrder.getAccount()!=null)
			{
				sb.append("'" + this.buyOrder.getAccount().getId()+ "',");
			}
			else
			{
				sb.append("'" +0+ "',");
			}			
			sb.append("'" + this.buyOrder.getMemo() + "',");
			sb.append("'" + this.buyOrder.getPayTime() + "'");
			sb.append(")\"");
			ml2.setEvents(sb.toString());
			ml2.setLabText("[确认已收退款]");		
			myLabels.add(ml2);
			operate.setMyLabels(myLabels);
		}
		String operateText = this.operate.getOperateText();
//		System.out.println("operateText:"+operateText);
		return operateText;
	}

	public UserRightInfo getUri() {
		return uri;
	}

	public void setUri(UserRightInfo uri) {
		this.uri = uri;
	}

	public AirticketOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(AirticketOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public AirticketOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(AirticketOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public OrderGroup getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(OrderGroup orderGroup) {
		this.orderGroup = orderGroup;
	}

	public Operate getOperate() {
		return operate;
	}

	public void setOperate(Operate operate) {
		this.operate = operate;
	}

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public long getBuyId() {
		return buyId;
	}

	public void setBuyId(long buyId) {
		this.buyId = buyId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(long saleStatus) {
		this.saleStatus = saleStatus;
	}

	public long getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(long buyStatus) {
		this.buyStatus = buyStatus;
	}

	public long getSaleTranType() {
		return saleTranType;
	}

	public void setSaleTranType(long saleTranType) {
		this.saleTranType = saleTranType;
	}

	public long getBuyTranType() {
		return buyTranType;
	}

	public void setBuyTranType(long buyTranType) {
		this.buyTranType = buyTranType;
	}

	public long getSaleBusinessType() {
		return saleBusinessType;
	}

	public void setSaleBusinessType(long saleBusinessType) {
		this.saleBusinessType = saleBusinessType;
	}

	public long getBuyBusinessType() {
		return buyBusinessType;
	}

	public void setBuyBusinessType(long buyBusinessType) {
		this.buyBusinessType = buyBusinessType;
	}

	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
	
	
}

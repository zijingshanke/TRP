package com.fdays.tsms.airticket.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.user.SysUser;
import com.neza.exception.AppException;

public interface AirticketOrderBiz
{
	public String createOrderByTempPNR(AirticketOrder airticketOrderFrom,
	    TempPNR tempPNR, UserRightInfo uri) throws AppException;

	// 申请支付
	public String createApplyTickettOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 重新申请支付
	public String anewApplyTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 锁定
	public String lockupOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 解锁（自己的订单）
	public String unlockSelfOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 解锁（所有人的订单）
	public String unlockAllOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 确认支付
	public String confirmPayment(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 确认出票
	public String confirmTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	//卖出 取消出票
	public String quitSaleTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;
	
	//买入 取消出票
	public String quitBuyTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 取消出票,确认退款
	public String agreeCancelRefund(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 编辑备注
	public String editRemark(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	public String updateAirticketOrderStatus(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 确认退票/废票/改签 收、退款
	public String collectionRetire(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 确认收款，改签完成
	public String finishUmbuchenOrder(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
	    throws AppException;

	public String createOrder(HttpServletRequest request,
	    AirticketOrder airticketOrderForm) throws AppException;
	

	// 创建退废票(内部)
	public String addRetireOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 审核退废票（卖出单，第一次通过申请，创建买入）
	public String auditRetire(AirticketOrder airticketOrderFrom, UserRightInfo uri)
	    throws AppException;

	// 审核退废票（卖出单，第二次通过申请，更新卖出）
	public String auditRetire2(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	// 改签
	public String createUmbuchenOrder(AirticketOrder airticketOrderFrom,
	    AirticketOrder airticketOrder, UserRightInfo uri) throws AppException;

	public String addOrderByHand(HttpServletRequest request) throws AppException;

	public String editOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException;

	public void editOrder(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException;

	public void processing(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException;

	public String view(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException;

	public void deleteAirticketOrder(Long airticketOrderId) throws AppException;

	// 团队--跳转到编辑页面
	public void editTeamOrder(long airTicketOrderId, HttpServletRequest request)
	    throws AppException;

	// 团队--编辑利润统计
	public void editTeamProfit(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	// 团队--解锁订单
	public void unlockTeam(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	public void lockTeam(AirticketOrder form, HttpServletRequest request)
	    throws AppException;

	// 修改团队
	public long updateTeamAirticketOrder(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	// 团队--根据现有销售订单,创建退票订单
	public long createTeamRefundBySale(AirticketOrder orderForm,
	    HttpServletRequest request) throws AppException;

	// 查看团队
	public void viewTeam(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException;

	public boolean checkPnrisToday(AirticketOrder airticketOrder)
	    throws AppException;

	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
	    throws AppException;

	public AirticketOrder getDrawedAirticketOrderByGroupId(long groupId,
	    long tranType) throws AppException;

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
	    long businessType, long tranType) throws AppException;

	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
	    throws AppException;

	public AirticketOrder getAirticketOrderByStatementId(long statementId)
	    throws AppException;

	// 团队申请支付
	public void applyTeamPayment(Long airticketOrderId, HttpServletRequest request)
	    throws AppException;

	// 团队确认支付
	public String confirmTeamPayment(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	public void reconfirmTeamPayment(AirticketOrder form,
	    HttpServletRequest request) throws AppException;

	// 申请退票
	public void applyTeamRefund(Long airticketOrderId, HttpServletRequest request)
	    throws AppException;
	// 审核退票
	public void checkTeamRefund(Long airticketOrderId, HttpServletRequest request)
	    throws AppException;

	// 团队退票确认付退款
	public void confirmTeamRefundPayment(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;

	// 团队退票确认收退款
	public void confirmTeamRefundCollection(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException;
	
	public void reconfirmTeamRefund(AirticketOrder form,
			HttpServletRequest request) throws AppException;

	public void saveAirticketTicketLog(long orderid, String groupNo,
	    SysUser sysUser, long ticketLogType) throws AppException;

	public void saveAirticketTicketLog(long orderid, String groupNo,
	    SysUser sysUser, HttpServletRequest request, long ticketLogType)
	    throws AppException;

	public AirticketOrder getAirticketOrderByGroupIdAndTranType(long groupId,
	    String tranType) throws AppException;

	public AirticketOrder getAirticketOrderByGroupIdStatus(long groupId,
	    String tranType, String status) throws AppException;

	public List list(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException;

	public List listTeam(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException;
	
	public List list() throws AppException;

	public List<AirticketOrder> listByGroupId(long groupId) throws AppException;

	public List<AirticketOrder> listBySubGroupAndGroupId(long orderGroupId,
	    Long subMarkNo) throws AppException;

	public List<AirticketOrder> getAirticketOrderListByPNR(String subPnr,
	    String tranType) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirticketOrder airticketOrder) throws AppException;

	public long update(AirticketOrder airticketOrder) throws AppException;
}

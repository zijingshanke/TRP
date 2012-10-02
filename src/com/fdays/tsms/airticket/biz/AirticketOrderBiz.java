package com.fdays.tsms.airticket.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.user.SysUser;
import com.neza.exception.AppException;

public interface AirticketOrderBiz {
	public String createPNR(AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			UserRightInfo uri) throws AppException;

	// 申请支付
	public String createApplyTickettOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;
	//重新申请支付
	public String anewApplyTicket(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException;

	//锁定
	public String lockupOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException;

	//解锁（自己的订单）
	public String unlockSelfOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException;
	//解锁（所有人的订单）
	public String unlockAllOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException;	
	
	// 确认支付
	public String confirmPayment(AirticketOrder airticketOrderFrom,HttpServletRequest request) throws AppException ;
	
	// 确认出票
	public String confirmTicket(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// 取消出票
	public String quitTicket(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;
	// 取消出票,确认退款
	public String agreeCancelRefund(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// 编辑备注
	public String editRemark(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;
	public String updateAirticketOrderStatus(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException;

	// 确认退票/废票/改签 收、退款
	public String collectionRetireTrading(AirticketOrder airticketOrderFrom, HttpServletRequest request) throws AppException;
	
	//确认收款，改签完成
	public String finishUmbuchenOrder(AirticketOrder airticketOrderForm,HttpServletRequest request)throws AppException;

	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException;

	public String createTradingOrder(HttpServletRequest request,
			AirticketOrder airticketOrderForm) throws AppException;

	public String createB2CTradingOrder(HttpServletRequest request,
			AirticketOrder airticketOrderForm) throws AppException;

	/**
	 * 通过 信息PNR获取外部数据
	 */
	public String airticketOrderByBlackOutPNR(HttpServletRequest request,
			AirticketOrder airticketOrderFrom) throws AppException;

	// 创建退废票(内部)
	public String addRetireTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request)
			throws AppException;
	
	// 审核退废票（卖出单，第一次通过申请，创建买入）
	public String auditRetireTrading(AirticketOrder airticketOrderFrom,
			UserRightInfo uri) throws AppException;
	
	// 审核退废票（卖出单，第二次通过申请，更新卖出）
	public String auditRetireTrading2(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// 审核退废票 外部
	public String auditOutRetireTrading(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// 改签
	public String createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,
			AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException;
	
	public void forwardEditTradingOrder(AirticketOrderListForm ulf,HttpServletRequest request)throws AppException;

	public void forwardProcessingTradingOrder(AirticketOrderListForm ulf,HttpServletRequest request)throws AppException;
		
	
	// 团队订单录入
	public void saveAirticketOrderTemp(AirticketOrder airticketOrderFrom,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 显示要修改的团队订单信息
	public void updateTeamAirticketOrderPage(UserRightInfo uri,
			AirticketOrder airticketOrderForm, String airticketOrderId,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException;

	// 修改团队订单信息
	public void updateTeamAirticketOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri, long agentNo, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 修改的团队利润统计信息（客户，航空）
	public void updateAirticketOrderAgentAvia(
			AirticketOrder airticketOrderForm, long airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 显示订单详细信息
	public String viewAirticketOrderPage(AirticketOrderListForm ulf,HttpServletRequest request) throws AppException;

	// 编辑团队机票订单利润(显示)
	public void updaTempAirticketOrderPrice(AirticketOrder airticketOrderForm,
			long airticketOrderId, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 删除团队订单票(改变状态)
	public void deleteAirticketOrder(String airticketOrderId)
			throws AppException;
	// 删除订单的关联结算记录(改变状态)
	public void deleteStatementByAirticketOrder(AirticketOrder airticketOrder)
			throws AppException;

	// 手动添加 订单
	public String handworkAddTradingOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// 编辑订单
	public String editTradingOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException;

	// //添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 通过外部pnr信息创建退废票
	public String createOutRetireTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request)
			throws AppException;

	// 通过外部pnr信息创建改签票
	public String createOutWaitAgreeUmbuchenOrder(
			AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException;

	public boolean checkPnrisToday(AirticketOrder airticketOrder)
			throws AppException;

	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
			throws AppException;

	public AirticketOrder getDrawedAirticketOrderByGroupId(
			long groupId, long tranType) throws AppException;

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
			long businessType, long tranType) throws AppException;

	// B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException;

	// 根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
			throws AppException;

	// 根据结算ID查询
	public AirticketOrder getAirticketOrderByStatementId(long statementId)
			throws AppException;

	// 团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException;

	// 修改状态（新订单--->>申请成功，等待支付）
	public void editTeamAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 确认出票
	public void editTeamAirticketOrderOver(long groupId,String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 申请支付
	public void editTeamForpayAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// -------退票--------
	// 申请退票
	public void editTeamRefundAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException;

	// 申请支付 显示购票客户、账号信息
	public void editTeamAirticketOrderAccount(AirticketOrderListForm alf,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException;

	// 团队确认支付
	public void editTeamAirticketOrderOK(AirticketOrder airticketOrderForm,
			long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException;

	// 团队确认退款----退款
	public void editTeamReFundAirticketOrder(AirticketOrder airticketOrderForm,long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)throws AppException;
	// 团队确认退款-----卖出
	public void editTeamReFundAirticketOrderTo(AirticketOrder airticketOrderForm,long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)throws AppException;
	
	public void saveStatementByAirticketOrder(AirticketOrder order,
			SysUser sysUser, long statementType, long statementStatus) throws AppException;

	public void saveAirticketTicketLog(long orderid,String groupNo, SysUser sysUser,
			long ticketLogType) throws AppException;

	public void saveAirticketTicketLog(long orderid,String groupNo, SysUser sysUser,
			HttpServletRequest request, long ticketLogType) throws AppException;

	public AirticketOrder getAirticketOrderByGroupIdAndTranType(long groupId,
			String tranType) throws AppException;
	
	public AirticketOrder getAirticketOrderByGroupIdStatus(long groupId,
			String tranType,String status) throws AppException;

	public List list(AirticketOrderListForm rlf) throws AppException;
	public List listTeam(AirticketOrderListForm rlf) throws AppException;
	

	public List list() throws AppException;

	public List<AirticketOrder> listByGroupId(long groupId)
			throws AppException;

	public List<AirticketOrder> listByGroupIdAndTranType(
			long groupId, String tranType) throws AppException;

	public List<AirticketOrder> listByGroupIdAndBusinessTranType(
			long groupId, String tranType, String businessType)
			throws AppException;

	public List<AirticketOrder> getAirticketOrderListByPNR(String subPnr,
			String tranType) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirticketOrder airticketOrder) throws AppException;

	public long update(AirticketOrder airticketOrder) throws AppException;
}

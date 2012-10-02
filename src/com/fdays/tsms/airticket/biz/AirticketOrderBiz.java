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

	public List list(AirticketOrderListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(AirticketOrder airticketOrder) throws AppException;
	public long update(AirticketOrder airticketOrder) throws AppException;
	public void createPNR(AirticketOrder airticketOrderFrom,TempPNR tempPNR,UserRightInfo uri) throws AppException;
	//B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf) throws AppException;
	//根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId) throws AppException;
	//根据结算ID查询
	public AirticketOrder getAirticketOrderBystatementId(long statementId) throws AppException;
	//根据订单组编号返加List集合
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo) throws AppException;
	public void createApplyTickettOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException;
	//退费票
	public void createRetireTradingOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException;
	//改签
	public void createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException;
	//出票
	public void ticket(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException;
	public List<AirticketOrder> listBygroupMarkNo(String groupMarkNo,String tranType) throws AppException;
	public AirticketOrder getAirticketOrderBysubPnr(String  subPnr) throws AppException;
	// 团队订单录入
	public void saveAirticketOrderTemp(AirticketOrder airticketOrderFrom,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//显示要修改的团队订单信息
	public void updateAirticketOrderTempPage(String airticketOrderId,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改团队订单信息
	public void updateAirticketOrderTemp(AirticketOrder airticketOrderForm,UserRightInfo uri,long agentNo,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改的团队利润统计信息（客户，航空）
	public void updateAirticketOrderAgentAvia(AirticketOrder airticketOrderForm,long airticketOrderId,UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;

	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException;
	//显示订单详细信息
	public AirticketOrder viewAirticketOrderPage(String groupMarkNo,String tranType,String aircketOrderId,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	//编辑团队机票订单利润(显示)
	public void updaTempAirticketOrderPrice(AirticketOrder airticketOrderForm,long airticketOrderId,
			HttpServletRequest request, HttpServletResponse response) throws AppException;

	//删除团队订单票(改变状态)
	public void deleteAirticketOrder(String airticketOrderId) throws AppException;
	
	//手动添加 订单
	public void handworkAddTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException;
	//编辑订单
	public void editTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException;
	
////添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改图队利润(改 结算表)
	public void updateTeamStatement(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//通过外部pnr信息创建退废票
	public void createOutRetireTradingOrder(AirticketOrder airticketOrderFrom,TempPNR tempPNR,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException;
	
	//通过外部pnr信息创建改签票
	public void createOutWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,TempPNR tempPNR,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException;
	public boolean checkPnrisToday(AirticketOrder airticketOrder)throws AppException;
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)throws AppException;
	public AirticketOrder getAirticketOrderByGroupMarkNor(String  groupMarkNo,long tranType) throws AppException;
	//根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String  subPnr,long businessType,long tranType) throws AppException;
	
	//团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf) throws AppException;
	//修改状态（新订单--->>申请成功，等待支付）
	public void editTeamAirticketOrder(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改状态（支付成功，等待出票--->>出票成功，交易结束）
	public void editTeamAirticketOrderOver(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改状态（新订单,待统计利润--->>申请成功，等待支付）
	public void editTeamAirticketOrderT(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	
	//-------退票--------
	//修改状态（新退票订单--->>退票订单，等待审核）
	public void editTeamRefundAirticketOrder(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改状态（退票审核通过，等待退款--->>已经退款，交易结束）
	public void editTeamRefundAirticketOrderOver(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException;
	//申请支付 显示购票客户、账号信息
	public void editTeamAirticketOrderAgentName(AirticketOrderListForm alf,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//团队确认支付
	public void editTeamAirticketOrderOK(AirticketOrder airticketOrderForm,long airticketOrderId,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response)throws AppException;
	public long resetStatementUserByAirticketOrder(AirticketOrder order,SysUser sysUser)throws AppException;
	public AirticketOrder getAirticketOrderByMarkNo(String markNo,String tranType) throws AppException;
	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType)throws AppException;
}

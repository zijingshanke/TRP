package com.fdays.tsms.airticket.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.OrderGroup;
import com.fdays.tsms.right.UserRightInfo;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AirticketOrderDAO extends BaseDAO {
	public AirticketOrder airticketOrderByPNR(String pnr) throws AppException;

	public AirticketOrder getAirticketOrderByStatementId(long statementId)
			throws AppException;

	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException;

	// 验证pnr是否重复添加
	public boolean checkPnrisToday(AirticketOrder airticketOrder)
			throws AppException;

	// 验证pnr是一月内否重复 退费 改签
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
			throws AppException;

	public AirticketOrder getDrawedAirticketOrderByGroupId(
			long groupId, long tranType) throws AppException;

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
			long businessType, long tranType) throws AppException;
	
	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
			throws AppException;
	
	public List list(AirticketOrderListForm rlf,UserRightInfo uri) throws AppException;
	
	public List listTeam(AirticketOrderListForm rlf,UserRightInfo uri) throws AppException;
	
	public List list() throws AppException;
	
	public List<AirticketOrder> listByCarrier(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	/**
	 * 初始化后返政策的信息，将利润、后返政策、后返利润设为零。
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean iniProfitAfterInformation(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	public List<AirticketOrder> listByCarrier(String carrier,Timestamp startDate,Timestamp endDate,
			int startRow,int rowCount) throws AppException;
	
	//查询适合条件的记录条数
	public int rowCountByCarrier(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	public int sumTicketNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	public int sumOrderNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	public BigDecimal sumSaleAmount(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	
	public List<BigDecimal> sumProfitAfter(String carrier,Timestamp startDate,Timestamp endDate) throws AppException;
	// 根据订单组编号
	public List<AirticketOrder> listByGroupId(long groupId)throws AppException;
	// 根据订单组、小组号 查询
	public List<AirticketOrder> listBySubGroupAndGroupId(long groupId,Long subMarkNo) throws AppException;
	public List listIDBySubGroupAndGroupId(long orderGroupId,Long subMarkNo)throws AppException;
	// 查询整个大组的订单IDList
	public List listIDByGroupId(long orderGroupId) throws AppException;
	public List<AirticketOrder> listBySubGroupByGroupIdAndType(long orderGroupId,long subMarkNo,long businessType) throws AppException;
	
	public List<AirticketOrder> getDrawedOrderListByPNR(String  subPnr)throws AppException;
	public List<AirticketOrder> getSaleDrawedOrderListByPNR(String subPnr) throws AppException;
	
	public List<AirticketOrder> listByGroupIdAndTranType(long groupId, String tranType) throws AppException;

	public List<AirticketOrder> listByGroupIdAndTranTypeStatus(long groupId, String tranType,String status) throws AppException;
	
	public List<AirticketOrder> listByGroupIdAndBusinessTranType(
			long groupId, String tranType,String businessType) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirticketOrder airticketOrder) throws AppException;

	public long update(AirticketOrder airticketOrder) throws AppException;
	
	public long getNewSubGroupMarkNo(long orgerGroupId) throws AppException;
	
	public long saveOrderGroup(OrderGroup og) throws AppException;
	
	public OrderGroup getOrderGroupById(long id) throws AppException;
	// 由订单ID查询GroupID
	public long getGroupIdByOrderId(long orderId)throws AppException;
}

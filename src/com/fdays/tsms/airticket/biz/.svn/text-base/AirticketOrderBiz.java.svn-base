package com.fdays.tsms.airticket.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.right.UserRightInfo;
import com.neza.exception.AppException;

public interface AirticketOrderBiz {

	public List list(AirticketOrderListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(AirticketOrder airticketOrder) throws AppException;
	public long update(AirticketOrder airticketOrder) throws AppException;
	public void createPNR(AirticketOrder airticketOrderFrom,TempPNR tempPNR,UserRightInfo uri) throws AppException;
	//根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId) throws AppException;
	public void createApplyTickettOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException;
	//退费票
	public void createRetireTradingOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException;
	//改签
	public void createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException;
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

	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException;

	//删除团队订单票(改变状态)
	public void deleteAirticketOrderTempByStateus(String airticketOrderId) throws AppException;
	
	//手动添加 订单
	public void handworkAddTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException;
	//编辑订单
	public void editTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException;
	
////添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderFrom,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;
	//修改图队利润(改 结算表)
	public void updateTeamStatement(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException;

}

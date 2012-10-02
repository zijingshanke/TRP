package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.neza.exception.AppException;

public class StatementBizImp implements StatementBiz {

	public StatementDAO statementDAO;
	public AirticketOrderDAO airticketOrderDAO;
	public FlightDAO flightDAO;
	public AgentDAO agentDAO;
	public TicketLogDAO ticketLogDAO;
	public PassengerDAO passengerDAO;

	public PassengerDAO getPassengerDAO() {
		return passengerDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public TicketLogDAO getTicketLogDAO() {
		return ticketLogDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public FlightDAO getFlightDAO() {
		return flightDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public AgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public AirticketOrderDAO getAirticketOrderDAO() {
		return airticketOrderDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public List list(StatementListForm rlf) throws AppException {

		return statementDAO.list(rlf);
	}

	public List list() throws AppException {
		return statementDAO.list();
	}

	public void delete(long id) throws AppException {
		statementDAO.delete(id);
	}

	public long save(Statement statement) throws AppException {
		return statementDAO.save(statement);
	}

	public long update(Statement statement) throws AppException {
		return statementDAO.update(statement);
	}

	//根据id查询 (lrc)
	public Statement getStatementById(long id) throws AppException
	{
		return statementDAO.getStatementById(id);
	}
	
	//返回一个List集合
	public List<Statement> getStatementList() throws AppException
	{
		return statementDAO.getStatementList();
	}
	
	//根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId) throws AppException
	{
		return statementDAO.getStatementListByToAccountId(toAccountId);
	}
	
	
	//显示该订单下的机票订单
	public void viewAirticketOrder(String statementId,HttpServletRequest request, HttpServletResponse response) throws AppException {
		
		AirticketOrder airticketOrder=airticketOrderDAO.getAirticketOrderBystatementId(Long.parseLong(statementId));
		airticketOrder.setThisAction("viewAirticketOrder");
		String groupMarkNo = airticketOrder.getGroupMarkNo();
		if(airticketOrder.getId()>0)
		{
			List<Passenger> passengerList = passengerDAO.listByairticketOrderId(airticketOrder.getId());
			List<Flight> flightList = flightDAO.getFlightListByOrderId(airticketOrder.getId());
			request.setAttribute("flightList", flightList);
			request.setAttribute("passengerList", passengerList);
		}
		if(groupMarkNo !=null && (!groupMarkNo.equals("")))
		{
			TicketLogListForm ticketLogForm  = new TicketLogListForm();	
			ticketLogForm.setOrderNo(groupMarkNo);//订单号
			//ticketLogForm.setPerPageNum(3);//设置3条数据
			ticketLogForm.setList(ticketLogDAO.list(ticketLogForm));
			List<TicketLog> ticketLogList = ticketLogDAO.list(ticketLogForm);
			request.setAttribute("ticketLogList", ticketLogList);
		}
		List<Agent> agentList = agentDAO.getAgentList();
		List<Flight> flightList = flightDAO.getFlightListByOrderId(airticketOrder.getId());
		request.setAttribute("flightList", flightList);
		request.setAttribute("agentList", agentList);
		request.setAttribute("airticketOrder", airticketOrder);
		
	}
	
	public StatementDAO getStatementDAO() {
		return statementDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
}

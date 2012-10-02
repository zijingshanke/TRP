package com.fdays.tsms.transaction.biz;

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
	private StatementDAO statementDAO;
	private AirticketOrderDAO airticketOrderDAO;
	private FlightDAO flightDAO;
	private AgentDAO agentDAO;
	private TicketLogDAO ticketLogDAO;
	private PassengerDAO passengerDAO;


	// 显示该订单下的机票订单(等待删除)
	public void viewAirticketOrder(String statementId,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {

		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderByStatementId(Long.parseLong(statementId));
		airticketOrder.setThisAction("viewAirticketOrder");
		String groupMarkNo = airticketOrder.getGroupMarkNo();
		if (airticketOrder.getId() > 0) {
			List<Passenger> passengerList = passengerDAO
					.listByairticketOrderId(airticketOrder.getId());
			List<Flight> flightList = flightDAO
					.getFlightListByOrderId(airticketOrder.getId());
			request.setAttribute("flightList", flightList);
			request.setAttribute("passengerList", passengerList);
		}
		if (groupMarkNo != null && (!groupMarkNo.equals(""))) {
			TicketLogListForm ticketLogForm = new TicketLogListForm();
			ticketLogForm.setOrderNo(groupMarkNo);// 订单号
			ticketLogForm.setList(ticketLogDAO.list(ticketLogForm));
			List<TicketLog> ticketLogList = ticketLogDAO.list(ticketLogForm);
			request.setAttribute("ticketLogList", ticketLogList);
		}
		List<Agent> agentList = agentDAO.getAgentList();
		List<Flight> flightList = flightDAO
				.getFlightListByOrderId(airticketOrder.getId());
		request.setAttribute("flightList", flightList);
		request.setAttribute("agentList", agentList);
		request.setAttribute("airticketOrder", airticketOrder);

	}
	
	public List getStatementListByOrder(long orderid, long ordertype)
	throws AppException {
return statementDAO.getStatementListByOrder(orderid, ordertype);
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

	// 根据id查询 (lrc)
	public Statement getStatementById(long id) throws AppException {
		return statementDAO.getStatementById(id);
	}

	// 返回一个List集合
	public List<Statement> getStatementList() throws AppException {
		return statementDAO.getStatementList();
	}

	// 根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId)
			throws AppException {
		return statementDAO.getStatementListByToAccountId(toAccountId);
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
	
	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}
}

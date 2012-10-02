package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.fdays.tsms.base.NoUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderBizImp implements AirticketOrderBiz {

	public AirticketOrderDAO airticketOrderDAO;
	public FlightDAO flightDAO;
	public PassengerDAO passengerDAO;
	public StatementDAO statementDAO;
	public NoUtil noUtil;
	public AgentDAO agentDAO;
	public TicketLogDAO ticketLogDAO;

	public void createPNR(AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			UserRightInfo uri) throws AppException {

		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setSysUser(uri.getUser());// 操作员
		statement.setStatus(Statement.STATUS_0);// 状态
		statement.setType(airticketOrderFrom.getStatement_type());// 类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		// 设置平台号
		PlatComAccountStore platComAccountStore = new PlatComAccountStore();
		PlatComAccount platComAccount = null;
		if (airticketOrderFrom.getAccountId() != null
				&& airticketOrderFrom.getCompanyId() != null
				&& airticketOrderFrom.getPlatformId() != null) {
			platComAccount = platComAccountStore.getPlatComAccountByAllId(
					airticketOrderFrom.getPlatformId(), airticketOrderFrom
							.getCompanyId(), airticketOrderFrom.getAccountId());
		}

		if (airticketOrderFrom.getStatement_type() == 1
				&& platComAccount != null) { // 买入
			
		
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (airticketOrderFrom.getStatement_type() == 2
				&& platComAccount != null) {// 卖出
			
			statement.setToPCAccount(platComAccount);// 收款帐号
		}
		statement.setTotalAmount(airticketOrderFrom.getStatement()
				.getTotalAmount());// 总金额
		statement.setActualAmount(airticketOrderFrom.getStatement()
				.getActualAmount());// 实收款
		statement.setUnsettledAccount(airticketOrderFrom.getStatement()
				.getUnsettledAccount());// 未结款
		statement.setCommission(airticketOrderFrom.getStatement()
				.getCommission());// 现返佣金
		statement.setRakeOff(airticketOrderFrom.getStatement().getRakeOff());// 后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statementDAO.save(statement);

		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		// ao.setDrawPnr(tempPNR.getPnr());//出票pnr
		ao.setSubPnr(tempPNR.getPnr());// 预订pnr
		ao.setBigPnr(tempPNR.getB_pnr());// 大pnr
		ao.setStatement(statement);
		//System.out.println("getFare==" + tempPNR.getFare());
		ao.setTicketPrice(tempPNR.getFare());// 票面价格
		ao.setAirportPrice(tempPNR.getTax());// 机建费
		ao.setFuelPrice(tempPNR.getYq());// 燃油税
		if (airticketOrderFrom.getAgentId() != null
				&& airticketOrderFrom.getAgentId() > 0) {
			Agent agent = agentDAO
					.getAgentByid(airticketOrderFrom.getAgentId());
			ao.setAgent(agent); // 购票客户
		}else{
			ao.setAgent(null);
		}
		ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		ao.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getStatement_type());// 交易类型
		ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		airticketOrderDAO.save(ao);
		airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		// 乘机人
		List<TempPassenger> tempPassengerList = tempPNR.getTempPassengerList();
		List tempTicketsList = tempPNR.getTempTicketsList();
		for (int i = 0; i < tempPassengerList.size(); i++) {
			TempPassenger tempPassenger = tempPassengerList.get(i);
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(ao);
			passenger.setName(tempPassenger.getName()); // 乘机人姓名
			passenger.setCardno(tempPassenger.getNi());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			if (tempTicketsList != null
					&& tempTicketsList.size() == tempPassengerList.size()) {
				System.out.println("tempTicketsList===="
						+ tempPNR.getTempTicketsList().get(i));
				passenger.setTicketNumber(tempTicketsList.get(i).toString()); // 票号
			}
			passengerDAO.save(passenger);
		}

		// 航班
		List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
		for (TempFlight tempFlight : tempFlightList) {
			Flight flight = new Flight();
			flight.setAirticketOrder(ao);
			flight.setFlightCode(tempFlight.getAirline());// 航班号
			flight.setStartPoint(tempFlight.getDepartureCity()); // 出发地
			flight.setEndPoint(tempFlight.getDestineationCity());// 目的地
			flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
			flight.setDiscount(tempFlight.getDiscount());// 折扣
			flight.setFlightClass(tempFlight.getCabin());// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);

		}
		// 操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(statement.getStatementNo());// 订单号
		ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
		ticketLog.setSysUser(uri.getUser());// 操作员
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}

	// 根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderById(airtickeOrderId);
	}

	//创建申请支付订单 
	public void createApplyTickettOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(airticketOrder.getStatement().getStatementNo());//结算单号
		statement.setSysUser(airticketOrder.getStatement().getSysUser());//操作员
		statement.setStatus(Statement.STATUS_0);//状态
		statement.setType(airticketOrderFrom.getStatement_type());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		//设置平台号 
		PlatComAccountStore platComAccountStore=new PlatComAccountStore();
		PlatComAccount platComAccount=null;
		if(airticketOrderFrom.getAccountId()!=null&&airticketOrderFrom.getCompanyId()!=null&&airticketOrderFrom.getPlatformId()!=null){
			System.out.println("airticketOrderFrom.getAccountId()=="+airticketOrderFrom.getAccountId());
			platComAccount=platComAccountStore.getPlatComAccountByAllId(airticketOrderFrom.getPlatformId(), airticketOrderFrom.getCompanyId(), airticketOrderFrom.getAccountId());
		}

		if (airticketOrderFrom.getStatement_type() == 1
				&& platComAccount != null) { // 买入
			System.out.println("airticketOrderFrom.getAccoun==="
					+ airticketOrderFrom.getAccountId());
			
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (airticketOrderFrom.getStatement_type() == 2
				&& platComAccount != null) {// 卖出
			
			statement.setToPCAccount(platComAccount);// 收款帐号
		}
		statement.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
		statement.setActualAmount(airticketOrder.getStatement()
				.getActualAmount());// 实收款
		statement.setUnsettledAccount(airticketOrder.getStatement()
				.getUnsettledAccount());// 未结款
		statement.setCommission(airticketOrder.getStatement().getCommission());// 现返佣金
		statement.setRakeOff(airticketOrder.getStatement().getRakeOff());// 后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statementDAO.save(statement);

		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		// ao.setDrawPnr(tempPNR.getPnr());//出票pnr
		ao.setSubPnr(airticketOrderFrom.getPnr());// 预订pnr
		ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
		// ao.setAgent(null);
		ao.setStatement(statement);
		ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
		ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
		ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
		ao.setAgent(airticketOrder.getAgent()); // 购票客户
		
		ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrder.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getStatement_type());// 交易类型
		ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		airticketOrderDAO.save(ao);

		// 乘机人
		Set tempPassengerList = airticketOrder.getPassengers();

		for (Object obj : tempPassengerList) {

			Passenger passengerTmp = (Passenger) obj;
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(ao);
			passenger.setName(passengerTmp.getName()); // 乘机人姓名
			passenger.setCardno(passengerTmp.getCardno());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passengerTmp.getTicketNumber()); // 票号
			passengerDAO.save(passenger);
		}

		// 航班
		Set tempFlightList = airticketOrder.getFlights();
		for (Object fobj : tempFlightList) {
			Flight flightTmp = (Flight) fobj;
			Flight flight = new Flight();
			flight.setAirticketOrder(ao);
			flight.setFlightCode(flightTmp.getFlightCode());// 航班号
			flight.setStartPoint(flightTmp.getStartPoint()); // 出发地
			flight.setEndPoint(flightTmp.getEndPoint());// 目的地
			flight.setBoardingTime(flightTmp.getBoardingTime());// 起飞时间
			flight.setDiscount(flightTmp.getDiscount());// 折扣
			flight.setFlightClass(flightTmp.getFlightClass());// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);

		}
		// 操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(statement.getStatementNo());// 订单号
		ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
		ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());// 操作员
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);

		airticketOrderDAO.update(airticketOrder);// 修改原订单信息

	}

	
	
	//创建退废票
	public void createRetireTradingOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(airticketOrder.getStatement().getStatementNo());//结算单号
		statement.setSysUser(airticketOrder.getStatement().getSysUser());//操作员
		statement.setStatus(airticketOrderFrom.getStatement().getStatus());//状态
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		//设置平台号 
		if(airticketOrderFrom.getStatement().getType()==Statement.type_1){ //收入
			
		if(airticketOrder.getStatement().getFromPCAccount()!=null){
			
			statement.setFromPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){
			
			statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
		 }
		}
		if(airticketOrderFrom.getStatement().getType()==Statement.type_2){ //支出
			
			if(airticketOrder.getStatement().getFromPCAccount()!=null){
				
				statement.setFromPCAccount(airticketOrder.getStatement().getFromPCAccount());//付款帐号
				
			}else if(airticketOrder.getStatement().getToPCAccount()!=null){
				
				statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//付款帐号
			 }
		}
		
		statement.setTotalAmount(airticketOrder.getStatement().getTotalAmount());//总金额
		statement.setActualAmount(airticketOrder.getStatement().getActualAmount());//实收款
		statement.setUnsettledAccount(airticketOrder.getStatement().getUnsettledAccount());//未结款
		statement.setCommission(airticketOrder.getStatement().getCommission());//现返佣金
		statement.setRakeOff(airticketOrder.getStatement().getRakeOff());//后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statementDAO.save(statement);
	
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
		  ao.setSubPnr(airticketOrder.getSubPnr());//预订pnr
		  ao.setBigPnr(airticketOrderFrom.getBigPnr());//大pnr
		 
		  ao.setStatement(statement);
		  ao.setTicketPrice(airticketOrder.getTicketPrice());//票面价格
		  ao.setAirportPrice(airticketOrder.getAirportPrice());//机建费
		  ao.setFuelPrice(airticketOrder.getFuelPrice());//燃油税
		  ao.setAgent(airticketOrder.getAgent()); //购票客户
		  ao.setHandlingCharge(airticketOrderFrom.getHandlingCharge());//手续费
		  ao.setDocumentPrice(airticketOrder.getDocumentPrice());//行程单费用
		  ao.setInsurancePrice(airticketOrder.getInsurancePrice());//保险费
		  ao.setRebate(airticketOrder.getRebate());//政策
		  ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());//机票订单号
		  ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());//订单组编号
		  ao.setStatus(airticketOrderFrom.getStatus()); //订单状态
		  ao.setTicketType(airticketOrder.getTicketType());//机票类型
		  ao.setTranType(airticketOrderFrom.getTranType());//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  airticketOrderDAO.save(ao);
		
		  // 创建退费
		if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_19||airticketOrderFrom.getStatus()==AirticketOrder.STATUS_29){
	
		  //乘机人
		  Set tempPassengerList=airticketOrder.getPassengers();
		  String[] passengerIds=airticketOrderFrom.getPassengerIds();
		  String[] passengerNames= airticketOrderFrom.getPassengerNames();
		  String[] passengerCardno= airticketOrderFrom.getPassengerCardno();
		  String[] passengerTicketNumber= airticketOrderFrom.getPassengerTicketNumber();
 		  	
		  for(int p=0;p<passengerIds.length;p++){
			 
			  Passenger passenger=new Passenger();
			  passenger.setAirticketOrder(ao);
			  passenger.setName(passengerNames[p]); //乘机人姓名
			  passenger.setCardno(passengerCardno[p]);//证件号
			  passenger.setType(1L); //类型
			  passenger.setStatus(1L);//状态
			  passenger.setTicketNumber(passengerTicketNumber[p]); //票号
			  passengerDAO.save(passenger);
			  
		}
			
		 
		  //航班
		  String[] flightIds=airticketOrderFrom.getFlightIds();
		  String[] flightCodes=airticketOrderFrom.getFlightCodes();//航班号
		  String[] discounts=airticketOrderFrom.getDiscounts();//折扣
		  String[] startPoints=airticketOrderFrom.getStartPoints();//出发地
		  String[] endPoints=airticketOrderFrom.getEndPoints();//目的地
		  String[] flightClasss=airticketOrderFrom.getFlightClasss();//舱位
		  String[] boardingTimes=airticketOrderFrom.getBoardingTimes();//起飞时间
		  
		//  Set tempFlightList=airticketOrder.getFlights();
		  
		  for(int f=0;f<flightIds.length;f++){
			  
			  Flight flight=new Flight();
			  flight.setAirticketOrder(ao);
			  flight.setFlightCode(flightCodes[f]);//航班号
			  flight.setStartPoint(startPoints[f]); //出发地
			  flight.setEndPoint(endPoints[f]);//目的地
			  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[f].toString(), "yyyy-MM-dd"));//起飞时间
			  flight.setDiscount(discounts[f]);//折扣
			  flight.setFlightClass(flightClasss[f]);//舱位
			  flight.setStatus(1L);  //状态
			  flightDAO.save(flight);
			
			  
		  }
		  //审核退废 并且创建（ 收款订单）
		}else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_21||airticketOrderFrom.getStatus()==AirticketOrder.STATUS_31){
			
		// 乘机人
		Set tempPassengerList = airticketOrder.getPassengers();

		for (Object obj : tempPassengerList) {

			Passenger passengerTmp = (Passenger) obj;
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(ao);
			passenger.setName(passengerTmp.getName()); // 乘机人姓名
			passenger.setCardno(passengerTmp.getCardno());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passengerTmp.getTicketNumber()); // 票号
			passengerDAO.save(passenger);
		}
		// 航班
		Set tempFlightList = airticketOrder.getFlights();
		for (Object fobj : tempFlightList) {
			Flight flightTmp = (Flight) fobj;
			Flight flight = new Flight();
			flight.setAirticketOrder(ao);
			flight.setFlightCode(flightTmp.getFlightCode());// 航班号
			flight.setStartPoint(flightTmp.getStartPoint()); // 出发地
			flight.setEndPoint(flightTmp.getEndPoint());// 目的地
			flight.setBoardingTime(flightTmp.getBoardingTime());// 起飞时间
			flight.setDiscount(flightTmp.getDiscount());// 折扣
			flight.setFlightClass(flightTmp.getFlightClass());// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);

		}
		
	 }
			
		//操作日志
		  TicketLog ticketLog=new TicketLog();
		  ticketLog.setOrderNo(statement.getStatementNo());//订单号
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
		  	 
		
	}

	//创建改签票
	public void createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(airticketOrder.getStatement().getStatementNo());//结算单号
		statement.setSysUser(airticketOrder.getStatement().getSysUser());//操作员
		statement.setStatus(airticketOrderFrom.getStatement().getStatus());//状态
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		//设置平台号 
		if(airticketOrderFrom.getStatement().getType()==Statement.type_1){
		if(airticketOrder.getStatement().getFromPCAccount()!=null){
			
			statement.setFromPCAccount(airticketOrder.getStatement().getFromPCAccount());//付款帐号
			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){
			
			statement.setFromPCAccount(airticketOrder.getStatement().getToPCAccount());//付款帐号
		}
		}
		
		//设置平台号 
		if(airticketOrderFrom.getStatement().getType()==Statement.type_2){
		if(airticketOrder.getStatement().getFromPCAccount()!=null){
			
			statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){
			
			statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
		}
		}
		statement.setTotalAmount(airticketOrder.getStatement().getTotalAmount());//总金额
		statement.setActualAmount(airticketOrder.getStatement().getActualAmount());//实收款
		statement.setUnsettledAccount(airticketOrder.getStatement().getUnsettledAccount());//未结款
		statement.setCommission(airticketOrder.getStatement().getCommission());//现返佣金
		statement.setRakeOff(airticketOrder.getStatement().getRakeOff());//后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statementDAO.save(statement);
		
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
		  ao.setSubPnr(airticketOrder.getSubPnr());//预订pnr
		  ao.setBigPnr(airticketOrderFrom.getBigPnr());//大pnr
		 
		  ao.setStatement(statement);
		  ao.setTicketPrice(airticketOrder.getTicketPrice());//票面价格
		  ao.setAirportPrice(airticketOrder.getAirportPrice());//机建费
		  ao.setFuelPrice(airticketOrder.getFuelPrice());//燃油税
		  ao.setAgent(airticketOrder.getAgent()); //购票客户
		 
		  ao.setDocumentPrice(airticketOrder.getDocumentPrice());//行程单费用
		  ao.setInsurancePrice(airticketOrder.getInsurancePrice());//保险费
		  ao.setRebate(airticketOrder.getRebate());//政策
		  ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());//机票订单号
		  ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());//订单组编号
		  ao.setStatus(airticketOrderFrom.getStatus()); //订单状态
		  ao.setTicketType(airticketOrder.getTicketType());//机票类型
		  ao.setTranType(airticketOrderFrom.getTranType());//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  airticketOrderDAO.save(ao);
		
		  if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_39){
		    //乘机人
			//  Set tempPassengerList=airticketOrder.getPassengers();
			  String[] passengerIds=airticketOrderFrom.getPassengerIds();
			  String[] passengerNames= airticketOrderFrom.getPassengerNames();
			  String[] passengerCardno= airticketOrderFrom.getPassengerCardno();
			  String[] passengerTicketNumber= airticketOrderFrom.getPassengerTicketNumber();
	 		
			  
			  for(int p=0;p<passengerIds.length;p++){
				  
			
				  Passenger passenger=new Passenger();
				  passenger.setAirticketOrder(ao);
				  passenger.setName(passengerNames[p]); //乘机人姓名
				  passenger.setCardno(passengerCardno[p]);//证件号
				  passenger.setType(1L); //类型
				  passenger.setStatus(1L);//状态
				  passenger.setTicketNumber(passengerTicketNumber[p]); //票号
				  passengerDAO.save(passenger);
				  
			  }
	 		 
			 
			  //航班
			  String[] flightIds=airticketOrderFrom.getFlightIds();
			  String[] flightCodes=airticketOrderFrom.getFlightCodes();//航班号
			  String[] discounts=airticketOrderFrom.getDiscounts();//折扣
			  String[] startPoints=airticketOrderFrom.getStartPoints();//出发地
			  String[] endPoints=airticketOrderFrom.getEndPoints();//目的地
			  String[] flightClasss=airticketOrderFrom.getFlightClasss();//舱位
			  String[] boardingTimes=airticketOrderFrom.getBoardingTimes();//起飞时间
			  
			//  Set tempFlightList=airticketOrder.getFlights();
			  
			  for(int f=0;f<flightIds.length;f++){
				
				  Flight flight=new Flight();
				  flight.setAirticketOrder(ao);
				  flight.setFlightCode(flightCodes[f]);//航班号
				  flight.setStartPoint(startPoints[f]); //出发地
				  flight.setEndPoint(endPoints[f]);//目的地
				  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[f].toString(), "yyyy-MM-dd"));//起飞时间
				  flight.setDiscount(discounts[f]);//折扣
                  System.out.println("discounts[f]==="+discounts[f]);
				  flight.setFlightClass(flightClasss[f]);//舱位
				  flight.setStatus(1L);  //状态
				  flightDAO.save(flight);
				
				  
			  }
		  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_41){
			  
				// 乘机人
				Set tempPassengerList = airticketOrder.getPassengers();

				for (Object obj : tempPassengerList) {

					Passenger passengerTmp = (Passenger) obj;
					Passenger passenger = new Passenger();
					passenger.setAirticketOrder(ao);
					passenger.setName(passengerTmp.getName()); // 乘机人姓名
					passenger.setCardno(passengerTmp.getCardno());// 证件号
					passenger.setType(1L); // 类型
					passenger.setStatus(1L);// 状态
					passenger.setTicketNumber(passengerTmp.getTicketNumber()); // 票号
					passengerDAO.save(passenger);
				}
				// 航班
				Set tempFlightList = airticketOrder.getFlights();
				for (Object fobj : tempFlightList) {
					Flight flightTmp = (Flight) fobj;
					Flight flight = new Flight();
					flight.setAirticketOrder(ao);
					flight.setFlightCode(flightTmp.getFlightCode());// 航班号
					flight.setStartPoint(flightTmp.getStartPoint()); // 出发地
					flight.setEndPoint(flightTmp.getEndPoint());// 目的地
					flight.setBoardingTime(flightTmp.getBoardingTime());// 起飞时间
					flight.setDiscount(flightTmp.getDiscount());// 折扣
					flight.setFlightClass(flightTmp.getFlightClass());// 舱位
					flight.setStatus(1L); // 状态
					flightDAO.save(flight);

				}
			  
		  }	  
		//操作日志
		  TicketLog ticketLog=new TicketLog();
		  ticketLog.setOrderNo(statement.getStatementNo());//订单号
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
		  	 
		
	}
	// 出票
	public void ticket(AirticketOrder airticketOrderFrom,
			AirticketOrder airticketOrder) throws AppException {

		// 操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());// 订单号
		ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
		ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());// 操作员
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		airticketOrderDAO.update(airticketOrder);// 修改原订单信息

	}
	

	//手动添加 订单
	public void handworkAddTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException {
		
		String[] statement_status=request.getParameterValues("statement_status");
		String[] statement_totalAmount=request.getParameterValues("statement_totalAmount");
		String[] statement_actualAmount=request.getParameterValues("statement_actualAmount");
		String[] statement_type=request.getParameterValues("statement_type");
		
		String[] platformId=request.getParameterValues("platformId");
		String[] companyId=request.getParameterValues("companyId");
		String[] accountId=request.getParameterValues("accountId");
		String statementNo="";
		String groupMarkNo="";
		for(int i=0;i<statement_status.length;i++){
		//结算
		Statement statement=new Statement();
		if(i==0){
			statementNo=noUtil.getStatementNo();
			statement.setStatementNo(statementNo);//结算单号
		}else{
			statement.setStatementNo(statementNo);//结算单号
		}
		
		statement.setSysUser(uri.getUser());//操作员
		statement.setStatus(Long.valueOf(statement_status[i]));//状态
		statement.setType(Long.valueOf(statement_type[i]));//类型
		statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
		statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
	   
		// 设置平台号
		PlatComAccountStore platComAccountStore = new PlatComAccountStore();
		PlatComAccount platComAccount = null;
		if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
			platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
		}

		if (Long.valueOf(statement_type[i])==Statement.type_1	&& platComAccount != null) { // 买入
			System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (Long.valueOf(statement_type[i])==Statement.type_2&& platComAccount != null) {// 卖出
			statement.setToPCAccount(platComAccount);// 收款帐号
		}
	
		statement.setUnsettledAccount(new java.math.BigDecimal(0));// 未结款
		statement.setCommission(new java.math.BigDecimal(0));// 现返佣金
		statement.setRakeOff(new java.math.BigDecimal(0));// 后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statementDAO.save(statement);
		
		
		String[] drawPnr=request.getParameterValues("drawPnr");
		String[] subPnr=request.getParameterValues("subPnr");
		String[] bigPnr=request.getParameterValues("bigPnr");
		String[] airportPrice=request.getParameterValues("airportPrice");
		String[] fuelPrice=request.getParameterValues("fuelPrice");
		String[] ticketPrice=request.getParameterValues("ticketPrice");
		String[] handlingCharge=request.getParameterValues("handlingCharge");
		String[] rebate=request.getParameterValues("rebate");
		String[] tranType=request.getParameterValues("tranType");
		String[] status=request.getParameterValues("status");
		String[] airOrderNo=request.getParameterValues("airOrderNo");
		String[] ticketType=request.getParameterValues("ticketType");
		
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(drawPnr[i]);//出票pnr
		  ao.setSubPnr(subPnr[i]);//预订pnr
		  ao.setBigPnr(bigPnr[i]);//大pnr
		 
		  ao.setStatement(statement);
		  ao.setTicketPrice(new java.math.BigDecimal(ticketPrice[i]));//票面价格
		  ao.setAirportPrice(new java.math.BigDecimal(airportPrice[i]));//机建费
		  ao.setFuelPrice(new java.math.BigDecimal(fuelPrice[i]));//燃油税
		  ao.setAgent(null); //购票客户
		  ao.setHandlingCharge(new java.math.BigDecimal(handlingCharge[i]));//手续费
		  ao.setDocumentPrice(new java.math.BigDecimal(0));//行程单费用
		  ao.setInsurancePrice(new java.math.BigDecimal(0));//保险费
		  ao.setRebate(new java.math.BigDecimal(rebate[i]));//政策
		  ao.setAirOrderNo(airOrderNo[i]);//机票订单号
		  if(i==0){
			  groupMarkNo=noUtil.getAirticketGroupNo();
			  ao.setGroupMarkNo(groupMarkNo);//订单组编号  
		  }else{
			  ao.setGroupMarkNo(groupMarkNo);//订单组编号
		  }
		  
		  ao.setStatus(Long.valueOf(status[i])); //订单状态
		  ao.setTicketType(Long.valueOf(ticketType[i]));//机票类型
		  ao.setTranType(Long.valueOf(tranType[i]));//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  airticketOrderDAO.save(ao);
		
		
		  //乘机人
		 String[] passNames=request.getParameterValues("passNames");//乘客姓名
         String[] passTypes=request.getParameterValues("passTypes");//类型
         String[] passTicketNumbers=request.getParameterValues("passTicketNumbers");//证件号
         String[] passAirorderIds=request.getParameterValues("passAirorderIds");//票号
		  for(int p=0;p<passNames.length;p++){
			  
			  Passenger passenger=new Passenger();
			  passenger.setAirticketOrder(ao);
			  passenger.setName(passNames[p]); //乘机人姓名
			  passenger.setCardno(passTicketNumbers[p]);//证件号
			  passenger.setType(Long.valueOf(passTypes[p])); //类型
			  passenger.setStatus(1L);//状态
			  passenger.setTicketNumber(passAirorderIds[p]); //票号
			  passengerDAO.save(passenger);
		}
			
		 
		  //航班
          
		  String[] startPoints=request.getParameterValues("startPoints");//出发地
		  String[] endPoints=request.getParameterValues("endPoints");//目的地
		  String[] boardingTimes=request.getParameterValues("boardingTimes");//出发时间
		  String[] flightCodes=request.getParameterValues("flightCodes");//航班号
		  String[] flightClasss=request.getParameterValues("flightClasss");//舱位
		  String[] discounts=request.getParameterValues("discounts");//折扣
		  
		  for(int j=0;j<flightCodes.length;j++){
			 
			  Flight flight=new Flight();
			  flight.setAirticketOrder(ao);
			  flight.setFlightCode(flightCodes[j].toString());//航班号
			  flight.setStartPoint(startPoints[j].toString()); //出发地
			  flight.setEndPoint(endPoints[j].toString());//目的地
			  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
			  flight.setDiscount(discounts[j].toString());//折扣
			  flight.setFlightClass(flightClasss[j].toString());//舱位
			  flight.setStatus(1L);  //状态
			  flightDAO.save(flight);
			  
		  }
		//操作日志
		  TicketLog ticketLog=new TicketLog();
		  ticketLog.setOrderNo(statement.getStatementNo());//订单号
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
	}  
		
	}

	
	//编辑 订单
	public void editTradingOrder(AirticketOrder airticketOrderFrom,HttpServletRequest request,UserRightInfo uri)throws AppException {
		
		String[] airticketOrderIds=request.getParameterValues("airticketOrderIds");
		String[] statement_status=request.getParameterValues("statement.status");
		String[] statement_totalAmount=request.getParameterValues("statement_totalAmount");
		String[] statement_actualAmount=request.getParameterValues("statement_actualAmount");
		String[] statement_type=request.getParameterValues("statement.type");
		
		String[] platformId=request.getParameterValues("platformId");
		String[] companyId=request.getParameterValues("companyId");
		String[] accountId=request.getParameterValues("accountId");
		String statementNo="";
		String groupMarkNo="";
		for(int i=0;i<statement_status.length;i++){
			
		if(Long.valueOf(airticketOrderIds[i])>0){
			System.out.println("===="+airticketOrderIds[i]);
			AirticketOrder ao=	airticketOrderDAO.getAirticketOrderById(Long.valueOf(airticketOrderIds[i]));
			
			//结算
			Statement statement=statementDAO.getStatementById(ao.getStatement().getId());
			
			statement.setStatementNo(ao.getStatement().getStatementNo());//结算单号
			statement.setSysUser(uri.getUser());//操作员
			statement.setStatus(Long.valueOf(statement_status[i]));//状态
			statement.setType(Long.valueOf(statement_type[i]));//类型
			statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
			statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
		    
			// 设置平台号
			PlatComAccountStore platComAccountStore = new PlatComAccountStore();
			PlatComAccount platComAccount = null;
			if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
				platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
			}

			if (Long.valueOf(statement_type[i])==Statement.type_1	&& platComAccount != null) { // 买入
				//System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
				statement.setToPCAccount(null);// 收款帐号
				statement.setFromPCAccount(platComAccount);// 付款帐号

			} else if (Long.valueOf(statement_type[i])==Statement.type_2&& platComAccount != null) {// 卖出
				statement.setToPCAccount(platComAccount);// 收款帐号
				statement.setFromPCAccount(null);// 付款帐号
			}
		
			statement.setUnsettledAccount(new java.math.BigDecimal(0));// 未结款
			statement.setCommission(new java.math.BigDecimal(0));// 现返佣金
			statement.setRakeOff(new java.math.BigDecimal(0));// 后返佣金
			statement.setOrderType(Statement.ORDERTYPE_1);
			statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			statementDAO.update(statement);
			
			
			String[] drawPnr=request.getParameterValues("drawPnr");
			String[] subPnr=request.getParameterValues("subPnr");
			String[] bigPnr=request.getParameterValues("bigPnr");
			String[] airportPrice=request.getParameterValues("airportPrice");
			String[] fuelPrice=request.getParameterValues("fuelPrice");
			String[] ticketPrice=request.getParameterValues("ticketPrice");
			String[] handlingCharge=request.getParameterValues("handlingCharge");
			String[] rebate=request.getParameterValues("rebate");
			String[] tranType=request.getParameterValues("tranType");
			String[] status=request.getParameterValues("status");
			String[] airOrderNo=request.getParameterValues("airOrderNo");
			String[] ticketType=request.getParameterValues("ticketType");
			
			//机票订单
			  ao.setDrawPnr(drawPnr[i]);//出票pnr
			  ao.setSubPnr(subPnr[i]);//预订pnr
			  ao.setBigPnr(bigPnr[i]);//大pnr
			 
			  ao.setStatement(statement);
			  ao.setTicketPrice(new java.math.BigDecimal(ticketPrice[i]));//票面价格
			  ao.setAirportPrice(new java.math.BigDecimal(airportPrice[i]));//机建费
			  ao.setFuelPrice(new java.math.BigDecimal(fuelPrice[i]));//燃油税
			  ao.setAgent(null); //购票客户
			  ao.setHandlingCharge(new java.math.BigDecimal(handlingCharge[i]));//手续费
			  ao.setDocumentPrice(new java.math.BigDecimal(0));//行程单费用
			  ao.setInsurancePrice(new java.math.BigDecimal(0));//保险费
			  ao.setRebate(new java.math.BigDecimal(rebate[i]));//政策
			  ao.setAirOrderNo(airOrderNo[i]);//机票订单号
			 //ao.setGroupMarkNo(groupMarkNo);//订单组编号  
			  ao.setStatus(Long.valueOf(status[i])); //订单状态
			  ao.setTicketType(Long.valueOf(ticketType[i]));//机票类型
			  ao.setTranType(Long.valueOf(tranType[i]));//交易类型
			  ao.setMemo(airticketOrderFrom.getMemo());
			  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			  airticketOrderDAO.update(ao);
			
			
			  //乘机人
			 String[] passids=request.getParameterValues("passids");//id
			 String[] passNames=request.getParameterValues("passNames");//乘客姓名
	         String[] passTypes=request.getParameterValues("passTypes");//类型
	         String[] passTicketNumbers=request.getParameterValues("passTicketNumbers");//证件号
	         String[] passAirorderIds=request.getParameterValues("passAirorderIds");//票号
			  for(int p=0;p<passNames.length;p++){
				  long pid=Long.valueOf(passids[p].toString());
				  if(pid==0){
				  Passenger passenger=new Passenger();
				  passenger.setAirticketOrder(ao);
				  passenger.setName(passNames[p]); //乘机人姓名
				  passenger.setCardno(passTicketNumbers[p]);//证件号
				  passenger.setType(Long.valueOf(passTypes[p])); //类型
				  passenger.setStatus(1L);//状态
				  passenger.setTicketNumber(passAirorderIds[p]); //票号
				  passengerDAO.save(passenger);
				  System.out.println("passengerDAO  ok！");
				  }else if(pid>0){
				  Passenger passenger=passengerDAO.passengerById(pid);
				  //passenger.setAirticketOrder(ao);
				  passenger.setName(passNames[p]); //乘机人姓名
				  passenger.setCardno(passTicketNumbers[p]);//证件号
				  passenger.setType(Long.valueOf(passTypes[p])); //类型
				  passenger.setStatus(1L);//状态
				  passenger.setTicketNumber(passAirorderIds[p]); //票号
				  passengerDAO.update(passenger);
				  System.out.println("update  ok！"+ao.getId());
				  }
			}
				
			 
			  //航班
			  String[] flightIds=request.getParameterValues("flightIds");//id
			  String[] startPoints=request.getParameterValues("startPoints");//出发地
			  String[] endPoints=request.getParameterValues("endPoints");//目的地
			  String[] boardingTimes=request.getParameterValues("boardingTimes");//出发时间
			  String[] flightCodes=request.getParameterValues("flightCodes");//航班号
			  String[] flightClasss=request.getParameterValues("flightClasss");//舱位
			  String[] discounts=request.getParameterValues("discounts");//折扣
			  
			  for(int j=0;j<flightCodes.length;j++){
				  long fid=Long.valueOf(flightIds[j].toString());
				  if(fid==0){
				  Flight flight=new Flight();
				  flight.setAirticketOrder(ao);
				  System.out.println("-----"+ao.getId());
				  flight.setFlightCode(flightCodes[j].toString());//航班号
				  flight.setStartPoint(startPoints[j].toString()); //出发地
				  flight.setEndPoint(endPoints[j].toString());//目的地
				  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
				  flight.setDiscount(discounts[j].toString());//折扣
				  flight.setFlightClass(flightClasss[j].toString());//舱位
				  flight.setStatus(1L);  //状态
				  flightDAO.save(flight);
				  }else if(fid>0){
				  Flight flight=flightDAO.getFlightById(fid);
				  //flight.setAirticketOrder(ao);
				  flight.setFlightCode(flightCodes[j].toString());//航班号
				  flight.setStartPoint(startPoints[j].toString()); //出发地
				  flight.setEndPoint(endPoints[j].toString());//目的地
				  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
				  flight.setDiscount(discounts[j].toString());//折扣
				  flight.setFlightClass(flightClasss[j].toString());//舱位
				  flight.setStatus(1L);  //状态
				  flightDAO.update(flight);  
				  }
				  
			  }
			//操作日志
			  TicketLog ticketLog=new TicketLog();
			  ticketLog.setOrderNo(statement.getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
			  ticketLog.setStatus(1L);
			  ticketLogDAO.save(ticketLog);
			  
			  
		}else{
			
		//结算
		Statement statement=new Statement();
		if(airticketOrderFrom.getStatement().getStatementNo()!=null){
		
			statement.setStatementNo(airticketOrderFrom.getStatement().getStatementNo());//结算单号
		}
		
		statement.setSysUser(uri.getUser());//操作员
		statement.setStatus(Long.valueOf(statement_status[i]));//状态
		statement.setType(Long.valueOf(statement_type[i]));//类型
		statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
		statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
	
		// 设置平台号
		PlatComAccountStore platComAccountStore = new PlatComAccountStore();
		PlatComAccount platComAccount = null;
		if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
			platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
		}

		if (Long.valueOf(statement_type[i])==Statement.type_1	&& platComAccount != null) { // 买入
			System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (Long.valueOf(statement_type[i])==Statement.type_2&& platComAccount != null) {// 卖出
			statement.setToPCAccount(platComAccount);// 收款帐号
		}
	
		statement.setUnsettledAccount(new java.math.BigDecimal(0));// 未结款
		statement.setCommission(new java.math.BigDecimal(0));// 现返佣金
		statement.setRakeOff(new java.math.BigDecimal(0));// 后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statementDAO.save(statement);
		
		
		String[] drawPnr=request.getParameterValues("drawPnr");
		String[] subPnr=request.getParameterValues("subPnr");
		String[] bigPnr=request.getParameterValues("bigPnr");
		String[] airportPrice=request.getParameterValues("airportPrice");
		String[] fuelPrice=request.getParameterValues("fuelPrice");
		String[] ticketPrice=request.getParameterValues("ticketPrice");
		String[] handlingCharge=request.getParameterValues("handlingCharge");
		String[] rebate=request.getParameterValues("rebate");
		String[] tranType=request.getParameterValues("tranType");
		String[] status=request.getParameterValues("status");
		String[] airOrderNo=request.getParameterValues("airOrderNo");
		String[] ticketType=request.getParameterValues("ticketType");
		
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(drawPnr[i]);//出票pnr
		  ao.setSubPnr(subPnr[i]);//预订pnr
		  ao.setBigPnr(bigPnr[i]);//大pnr
		 
		  ao.setStatement(statement);
		  ao.setTicketPrice(new java.math.BigDecimal(ticketPrice[i]));//票面价格
		  ao.setAirportPrice(new java.math.BigDecimal(airportPrice[i]));//机建费
		  ao.setFuelPrice(new java.math.BigDecimal(fuelPrice[i]));//燃油税
		  ao.setAgent(null); //购票客户
		  ao.setHandlingCharge(new java.math.BigDecimal(handlingCharge[i]));//手续费
		  ao.setDocumentPrice(new java.math.BigDecimal(0));//行程单费用
		  ao.setInsurancePrice(new java.math.BigDecimal(0));//保险费
		  ao.setRebate(new java.math.BigDecimal(rebate[i]));//政策
		  ao.setAirOrderNo(airOrderNo[i]);//机票订单号
		  if(airticketOrderFrom.getGroupMarkNo()!=null){
			  ao.setGroupMarkNo(airticketOrderFrom.getGroupMarkNo());//订单组编号
		  }
		  
		  ao.setStatus(Long.valueOf(status[i])); //订单状态
		  ao.setTicketType(Long.valueOf(ticketType[i]));//机票类型
		  ao.setTranType(Long.valueOf(tranType[i]));//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  airticketOrderDAO.save(ao);
		
		
		  //乘机人
		 String[] passNames=request.getParameterValues("passNames");//乘客姓名
         String[] passTypes=request.getParameterValues("passTypes");//类型
         String[] passTicketNumbers=request.getParameterValues("passTicketNumbers");//证件号
         String[] passAirorderIds=request.getParameterValues("passAirorderIds");//票号
		  for(int p=0;p<passNames.length;p++){
			  
			  Passenger passenger=new Passenger();
			  passenger.setAirticketOrder(ao);
			  passenger.setName(passNames[p]); //乘机人姓名
			  passenger.setCardno(passTicketNumbers[p]);//证件号
			  passenger.setType(Long.valueOf(passTypes[p])); //类型
			  passenger.setStatus(1L);//状态
			  passenger.setTicketNumber(passAirorderIds[p]); //票号
			  passengerDAO.save(passenger);
		}
			
		 
		  //航班
          
		  String[] startPoints=request.getParameterValues("startPoints");//出发地
		  String[] endPoints=request.getParameterValues("endPoints");//目的地
		  String[] boardingTimes=request.getParameterValues("boardingTimes");//出发时间
		  String[] flightCodes=request.getParameterValues("flightCodes");//航班号
		  String[] flightClasss=request.getParameterValues("flightClasss");//舱位
		  String[] discounts=request.getParameterValues("discounts");//折扣
		  
		  for(int j=0;j<flightCodes.length;j++){
			 
			  Flight flight=new Flight();
			  flight.setAirticketOrder(ao);
			  flight.setFlightCode(flightCodes[j].toString());//航班号
			  flight.setStartPoint(startPoints[j].toString()); //出发地
			  flight.setEndPoint(endPoints[j].toString());//目的地
			  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
			  flight.setDiscount(discounts[j].toString());//折扣
			  flight.setFlightClass(flightClasss[j].toString());//舱位
			  flight.setStatus(1L);  //状态
			  flightDAO.save(flight);
			  
		  }
		//操作日志
		  TicketLog ticketLog=new TicketLog();
		  ticketLog.setOrderNo(statement.getStatementNo());//订单号
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
	}  
  }
}

	

	// 团队订单录入
	public void saveAirticketOrderTemp(AirticketOrder airticketOrderFrom,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {

		String[] airOrderNo =request.getParameterValues("airOrderNo");//页面获取的数组		
		String[] total=request.getParameterValues("totalAmount");		
		String[] flightCode =request.getParameterValues("flightCode");
		String[] startPoint=request.getParameterValues("startPoint");
		String[] endPoint=request.getParameterValues("endPoint");
		String[] boardingTime =request.getParameterValues("boardingTime");
		String[] flightClass=request.getParameterValues("flightClass");
		String[] discount =request.getParameterValues("discount");

		// 结算表
		Agent agent = agentDAO.getAgentByid(airticketOrderFrom.getAgentNo());
		Statement statement = new Statement();
		statement.setTotalAmount(airticketOrderFrom.getTotalAmount());		
		statement.setActualAmount(BigDecimal.valueOf(0));//默认给0钱
		statement.setUnsettledAccount(BigDecimal.valueOf(0));
		statement.setCommission(BigDecimal.valueOf(0));
		statement.setRakeOff(BigDecimal.valueOf(0));
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setStatus(Statement.STATUS_0);//未结算
		statement.setType(Statement.type_2);//收入
		statement.setSysUser(uri.getUser());
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statementDAO.save(statement);
		
		// 机票订单表
		AirticketOrder air = new AirticketOrder();
		air.setAgent(agent);
		air.setDrawPnr(airticketOrderFrom.getDrawPnr());
		air.setAdultCount(airticketOrderFrom.getAdultCount());
		air.setTicketPrice(airticketOrderFrom.getTotlePrice());//总票面价
		air.setChildCount(airticketOrderFrom.getChildCount());
		air.setBabyCount(airticketOrderFrom.getBabyCount());
		air.setAirportPrice(airticketOrderFrom.getAirportPrice());//总机建税
		air.setFuelPrice(airticketOrderFrom.getFuelPrice());//总燃油税	
		air.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 订单号
		air.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		air.setTranType(AirticketOrder.TRANTYPE_2);//默认卖出
		air.setTicketType(AirticketOrder.TICKETTYPE_2);// 类型:团队
		air.setStatus(AirticketOrder.STATUS_2);//状态:申请成功，等待支付		
		air.setDrawer(airticketOrderFrom.getDrawer());//客票类型(出票人)
		air.setStatement(airticketOrderFrom.getStatement());
		air.setStatement(statement);
		airticketOrderDAO.save(air);
		
		
		for(int i=0;i<flightCode.length;i++)
		{
			// 航班表
			Flight flight = new Flight();
			flight.setAirticketOrder(air);
			flight.setFlightCode(flightCode[i].toString());// 航班号			
			flight.setStartPoint(startPoint[i].toString());//出发地
			flight.setEndPoint(endPoint[i].toString());//终点地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTime[i].toString(), "yyyy-MM-dd"));// 出发时间
			flight.setFlightClass(flightClass[i].toString());// 舱位
			flight.setDiscount(discount[i].toString());// 折扣
			flight.setStatus(1L);
			flightDAO.save(flight);
			
			//操作日志
			TicketLog ticketLog = new TicketLog();
			ticketLog.setOrderNo(statement.getStatementNo());
			ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
			ticketLog.setSysUser(uri.getUser());//操作员
			ticketLog.setIp(request.getRemoteAddr());//IP					 
			ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
			ticketLog.setType(TicketLog.TYPE_31);
			ticketLog.setStatus(1L);
			ticketLogDAO.save(ticketLog);
		}
	}	
		
	
	//显示要修改的团队订单信息
	public void updateAirticketOrderTempPage(String airticketOrderId,HttpServletRequest request, HttpServletResponse response) throws AppException {
			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
			List<AirticketOrder> airticketOrderList = airticketOrderDAO.getAirticketOrderListByGroupMarkNo(airticketOrder.getGroupMarkNo());
			List<Statement> statementList = new ArrayList<Statement>();
			for(int i=0;i<airticketOrderList.size();i++)
			{
				AirticketOrder air = airticketOrderList.get(i);
				if(air.getStatement()!=null)
				{
					Statement statement = statementDAO.getStatementById(air.getStatement().getId());
					statementList.add(statement);
				}
			}
			request.setAttribute("statementList", statementList);	
			request.setAttribute("statementSize", statementList.size());
			List<Agent> agentList = agentDAO.getAgentList();
			List<Flight> flightList = flightDAO.getFlightListByOrderId(Long.parseLong(airticketOrderId));
			request.setAttribute("flightList", flightList);
			request.setAttribute("agentList", agentList);
		
	}
	
	//修改团队订单信息
	public void updateAirticketOrderTemp(AirticketOrder airticketOrderForm,UserRightInfo uri,long agentNo,
			HttpServletRequest request, HttpServletResponse response) throws AppException {
		String[] flightCode = request.getParameterValues("flightCode");//获取页面航班号数组
		String[] flightId = request.getParameterValues("flightId");//航班ID(页面隐藏域里的ID)
		String[] startPoint =request.getParameterValues("startPoint");
		String[] endPoint = request.getParameterValues("endPoint");
		String[] boardingTime = request.getParameterValues("boardingTime");
		String[] flightClass = request.getParameterValues("flightClass");
		String[] discount = request.getParameterValues("discount");
		if(agentNo>0)
		{
			AirticketOrder airticketOrder =airticketOrderDAO.getAirticketOrderById(airticketOrderForm.getId());
			Statement statement = statementDAO.getStatementById(airticketOrder.getStatement().getId());
			statement.setTotalAmount(airticketOrderForm.getTotalAmount());//总金额
			statement.setSysUser(uri.getUser());
			statement.setType(statement.getType());
			statementDAO.update(statement);
			Agent agent = agentDAO.getAgentByid(agentNo);
			airticketOrder.setAgent(agent);
			airticketOrder.setStatement(statement);
			
			airticketOrder.setAirOrderNo(airticketOrderForm.getAirOrderNo());//订单号
			airticketOrder.setGroupMarkNo(airticketOrder.getGroupMarkNo());//订单组编号
			airticketOrder.setDrawPnr(airticketOrderForm.getDrawPnr());
			airticketOrder.setSubPnr(airticketOrderForm.getSubPnr());
			airticketOrder.setBigPnr(airticketOrderForm.getBigPnr());
			airticketOrder.setTicketPrice(airticketOrderForm.getTotlePrice());//总票面价
			airticketOrder.setRebate(airticketOrderForm.getRebate());
			airticketOrder.setAdultCount(airticketOrder.getAdultCount());
			airticketOrder.setChildCount(airticketOrder.getChildCount());
			airticketOrder.setBabyCount(airticketOrder.getBabyCount());
			airticketOrder.setAirportPrice(airticketOrderForm.getAirportPrice());
			airticketOrder.setFuelPrice(airticketOrderForm.getFuelPrice());
			airticketOrder.setDocumentPrice(airticketOrderForm.getDocumentPrice());
			airticketOrder.setInsurancePrice(airticketOrderForm.getInsurancePrice());
			airticketOrder.setHandlingCharge(airticketOrderForm.getHandlingCharge());
			airticketOrder.setTicketType(airticketOrder.getTicketType());//机票类型
			airticketOrder.setTranType(AirticketOrder.TRANTYPE_2);//交易类型:卖出
			airticketOrder.setDrawer(airticketOrderForm.getDrawer());	//个出票人
			airticketOrder.setStatus(airticketOrder.getStatus());
			airticketOrderDAO.update(airticketOrder);
			
			//航班信息
			for(int i=0;i<flightCode.length;i++)
			{
				Flight flight = flightDAO.getFlightById(Long.parseLong(flightId[i]));
				flight.setFlightCode(flightCode[i].toString());//航班号
				flight.setStartPoint(startPoint[i].toString());//出发地
				flight.setEndPoint(endPoint[i].toString());
				flight.setBoardingTime(DateUtil.getTimestamp(boardingTime[i].toString(), "yyyy-MM-dd"));
				flight.setFlightClass(flightClass[i].toString());
				flight.setDiscount(discount[i].toString());
				flight.setStatus(flight.getStatus());//状态
				flight.setAirticketOrder(airticketOrder);
				flightDAO.update(flight);
			}
			
			//操作日志
			TicketLog ticketLog = new TicketLog();
			ticketLog.setOrderNo(statement.getStatementNo());
			ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
			ticketLog.setSysUser(uri.getUser());//操作员
			ticketLog.setIp(request.getRemoteAddr());//IP					 
			ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
			ticketLog.setType(TicketLog.TYPE_33);
			ticketLog.setStatus(1L);
			ticketLogDAO.save(ticketLog);
		}
		
	}
	
	//删除团队订单票(改变状态)
	public void deleteAirticketOrderTempByStateus(String airticketOrderId) throws AppException {
		
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
			Statement statement = statementDAO.getStatementById(airticketOrder.getStatement().getId());
			Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
			airticketOrder.setStatus(AirticketOrder.STATUS_88);//将团队订单状态变为已废弃
			airticketOrder.setStatement(statement);
			airticketOrder.setAgent(agent);
			airticketOrderDAO.update(airticketOrder);
		
	}
	
	//添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderFrom,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
		
			PlatComAccount toplatComAccount=PlatComAccountStore.getPlatComAccountById(90000);//收款
			PlatComAccount fromplatComAccount=PlatComAccountStore.getPlatComAccountById(90001);//付款
			long airticketOrderId = airticketOrderFrom.getAirticketOrderId();//机票订单ID
			if(airticketOrderId >0)
			{
				AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
				if(airticketOrder.getStatement() != null && airticketOrder.getAgent() != null)
				{
					Statement statement = statementDAO.getStatementById(airticketOrder.getStatement().getId());
					Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
				
					//收入
					if(toplatComAccount.getId()>0)
					{
						
						statement.setStatementNo(statement.getStatementNo());//结算单号
						statement.setToPCAccount(toplatComAccount);//收款
						statement.setOrderType(1L);//订单类型
						//BigDecimal totalAmount =airticketOrderFrom.getTxtSAmount();
						BigDecimal totalAmount =airticketOrderFrom.getTxtTotalAmount();//(应收金额暂时放的实收金额代总金额)
						BigDecimal actualAmount =airticketOrderFrom.getTxtTotalAmount();
					
						if(totalAmount !=null  && actualAmount !=null )
						{
							statement.setTotalAmount(totalAmount);//总金额
							statement.setActualAmount(actualAmount);//实收金额
							BigDecimal unsettledAccount = totalAmount.subtract(actualAmount);//未结款=总金额-实收金额
							statement.setUnsettledAccount(unsettledAccount);//
						}
						statement.setCommission(airticketOrderFrom.getTxtAgentFeeTeams());//现返佣金
						statement.setRakeOff(BigDecimal.valueOf(airticketOrderFrom.getTxtUnAgentFeeTeams()));//后返佣金
						statement.setSysUser(uri.getUser());//操作员ID
						statement.setType(Statement.type_2);//收入
						statement.setStatus(Statement.STATUS_1);//已结算
						statementDAO.update(statement);//修改原结算(收入)数据
					}
			
					//支出
					if(fromplatComAccount.getId()>0)
					{
						Statement stat = new Statement();
						stat.setStatementNo(statement.getStatementNo());//结算单号
						stat.setFromPCAccount(fromplatComAccount);
						stat.setOrderType(1L);//订单类型
						//BigDecimal txtTUnAmount =airticketOrderFrom.getTxtTUnAmount();
						BigDecimal txtTUnAmount =airticketOrderFrom.getTxtTAmount();//(应收金额暂时放的实收金额代总金额)
						BigDecimal txtTAmount =airticketOrderFrom.getTxtTAmount();
						
							if(txtTUnAmount !=null && txtTAmount !=null )
							{
								stat.setTotalAmount(txtTAmount);//总金额(总金额暂时先用实收金额代)
								stat.setActualAmount(txtTAmount);//实收金额
								BigDecimal unsettledAccount = txtTUnAmount.subtract(txtTAmount);//未结款=总金额-实收金额
								stat.setUnsettledAccount(unsettledAccount);//
							}
							stat.setCommission(BigDecimal.valueOf(0));//现返佣金
							stat.setRakeOff(BigDecimal.valueOf(0));//后返佣金
							stat.setSysUser(uri.getUser());//操作员ID
							stat.setType(Statement.type_1);//付出
							stat.setStatus(Statement.STATUS_1);//已结算
							stat.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
							statementDAO.save(stat);//保存新的结算数据(付出)
							
							//创建机票订单表(所对应的结算表状态是付出)
							AirticketOrder teamairticketOrder = new AirticketOrder();
							teamairticketOrder.setAirOrderNo(airticketOrder.getAirOrderNo());//订单号
							teamairticketOrder.setGroupMarkNo(airticketOrder.getGroupMarkNo());//订单组编号
							teamairticketOrder.setDrawPnr(airticketOrder.getDrawPnr());
							teamairticketOrder.setSubPnr(airticketOrder.getSubPnr());
							teamairticketOrder.setBigPnr(airticketOrder.getBigPnr());
							teamairticketOrder.setTicketPrice(airticketOrder.getTicketPrice());//总票面价
							teamairticketOrder.setRebate(airticketOrder.getRebate());
							teamairticketOrder.setAdultCount(airticketOrder.getAdultCount());
							teamairticketOrder.setChildCount(airticketOrder.getChildCount());
							teamairticketOrder.setBabyCount(airticketOrder.getBabyCount());
							teamairticketOrder.setAirportPrice(airticketOrder.getAirportPrice());
							teamairticketOrder.setFuelPrice(airticketOrder.getFuelPrice());
							teamairticketOrder.setDocumentPrice(airticketOrder.getDocumentPrice());
							teamairticketOrder.setInsurancePrice(airticketOrder.getInsurancePrice());
							teamairticketOrder.setHandlingCharge(airticketOrder.getHandlingCharge());
							teamairticketOrder.setTicketType(airticketOrder.getTicketType());//机票类型
							teamairticketOrder.setTranType(AirticketOrder.TRANTYPE_1);//交易类型:买入
							teamairticketOrder.setDrawer(airticketOrder.getDrawer());	//个出票人
							teamairticketOrder.setStatus(airticketOrder.getStatus());
							
							teamairticketOrder.setStatement(stat);
							teamairticketOrder.setAgent(agent);
							airticketOrderDAO.save(teamairticketOrder);//复制一张订单数据(付出)
							long airOrderId=teamairticketOrder.getId();//机票订单ID
							//航班表
							if(airOrderId>0)
							{
								Flight flight =flightDAO.getFlightByAirticketOrderID(airticketOrderId);
								AirticketOrder air = airticketOrderDAO.getAirticketOrderById(airOrderId);
								Flight fl = new Flight();
								fl.setAirticketOrder(air);
								fl.setFlightCode(flight.getFlightCode());
								fl.setStartPoint(flight.getStartPoint());
								fl.setEndPoint(flight.getEndPoint());
								fl.setBoardingTime(flight.getBoardingTime());
								fl.setFlightClass(flight.getFlightClass());
								fl.setDiscount(flight.getDiscount());
								fl.setStatus(flight.getStatus());
								flightDAO.save(fl);
							}
						}
						//操作日志
						TicketLog ticketLog = new TicketLog();
						ticketLog.setOrderNo(statement.getStatementNo());
						ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
						ticketLog.setSysUser(uri.getUser());//操作员
						ticketLog.setIp(request.getRemoteAddr());//IP					 
						ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
						ticketLog.setType(TicketLog.TYPE_34);
						ticketLog.setStatus(1L);
						ticketLogDAO.save(ticketLog);
				}
		}
	}
	
	//修改图队利润(改 结算表)
	public void updateTeamStatement(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
			long statementId =airticketOrderForm.getStatementId();
			Statement statement = statementDAO.getStatementById(statementId);
			BigDecimal totalAmount =airticketOrderForm.getTxt_TotalAmount();
			BigDecimal actualAmount =airticketOrderForm.getTxt_ActualAmount();
			BigDecimal m =BigDecimal.valueOf(0);
			if(totalAmount !=null  && actualAmount !=null )
			{
				if(totalAmount.compareTo(actualAmount) ==1 && actualAmount.compareTo(m)==1)//总金额>实收款 且 实收款 >0
				{
					BigDecimal unsettledAccount =totalAmount.subtract(actualAmount);//未结款=总金额-实收款;
					statement.setTotalAmount(totalAmount);//总金额
					statement.setActualAmount(actualAmount);//实收款
					statement.setUnsettledAccount(unsettledAccount);//未结款
					statement.setStatus(Statement.STATUS_2);//部分结算
				}
				if(totalAmount.compareTo(actualAmount) ==-1 || totalAmount.compareTo(actualAmount) ==0)//总金额<=实收款
				{
					statement.setTotalAmount(totalAmount);//总金额
					statement.setActualAmount(actualAmount);//实收款
					statement.setUnsettledAccount(BigDecimal.valueOf(0));//未结款
					statement.setStatus(Statement.STATUS_1);//已结算
				}
				if(actualAmount.compareTo(m)==0)//实收款=0
				{
					statement.setTotalAmount(totalAmount);//总金额
					statement.setActualAmount(actualAmount);//实收款
					statement.setUnsettledAccount(totalAmount);//未结款=总金额
					statement.setStatus(Statement.STATUS_0);//未结算
				}
			}
			statement.setCommission(airticketOrderForm.getTxt_Commission());//现返佣金
			statement.setRakeOff(airticketOrderForm.getTxt_RakeOff());//后返佣金
			statement.setSysUser(uri.getUser());
			statementDAO.update(statement);
			
			//操作日志
			TicketLog ticketLog = new TicketLog();
			ticketLog.setOrderNo(statement.getStatementNo());
			ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
			ticketLog.setSysUser(uri.getUser());//操作员
			ticketLog.setIp(request.getRemoteAddr());//IP					 
			ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
			ticketLog.setType(TicketLog.TYPE_35);
			ticketLog.setStatus(1L);
			ticketLogDAO.save(ticketLog);
	}
	
	public AirticketOrderDAO getAirticketOrderDAO() {
		return airticketOrderDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public List list(AirticketOrderListForm rlf) throws AppException {

		return airticketOrderDAO.list(rlf);
	}

	public List list() throws AppException {

		return airticketOrderDAO.list();
	}

	// 删除
	public void delete(long id) throws AppException {
		airticketOrderDAO.delete(id);
	}

	// 添加保存
	public long save(AirticketOrder airticketOrder) throws AppException {
		return airticketOrderDAO.save(airticketOrder);
	}

	// 修改
	public long update(AirticketOrder airticketOrder) throws AppException {
		return airticketOrderDAO.update(airticketOrder);
	}

	public List<AirticketOrder> listBygroupMarkNo(String groupMarkNo,String tranType)
			throws AppException {
		return airticketOrderDAO.listBygroupMarkNo(groupMarkNo, tranType);
	}

	// 根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderBysubPnr(subPnr);
	}
	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException{
		return airticketOrderDAO.getListByOrder(airticketOrder);
	}
	public FlightDAO getFlightDAO() {
		return flightDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public PassengerDAO getPassengerDAO() {
		return passengerDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public StatementDAO getStatementDAO() {
		return statementDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}

	public NoUtil getNoUtil() {
		return noUtil;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public AgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public TicketLogDAO getTicketLogDAO() {
		return ticketLogDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

}

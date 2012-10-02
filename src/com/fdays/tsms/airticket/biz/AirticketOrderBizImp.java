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
import com.fdays.tsms.transaction.dao.AccountDAO;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.PlatComAccountDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.fdays.tsms.user.SysUser;
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
	public PlatComAccountDAO platComAccountDAO;
	public AccountDAO accountDAO;
	
	public long resetStatementUserByAirticketOrder(AirticketOrder order,SysUser sysUser)throws AppException{
//		order=airticketOrderDAO.getAirticketOrderById(order.getId());
		Statement statement=statementDAO.getStatementById(order.getStatement().getId());
		statement.setSysUser(sysUser);
		return statementDAO.merge(statement);	
	}
	
	public void createPNR(AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			UserRightInfo uri) throws AppException {
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
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

		if (airticketOrderFrom.getStatement_type() == 2
				&& platComAccount != null) { // 买入		
			statement.setFromPCAccount(platComAccount);// 付款帐号
		} else if (airticketOrderFrom.getStatement_type() == 1
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
		ao.setSubPnr(tempPNR.getPnr());// 预订pnr
		ao.setBigPnr(tempPNR.getB_pnr());// 大pnr
		if(airticketOrderFrom.getBigPnr()!=null&&!"".equals(airticketOrderFrom.getBigPnr().trim())){
			ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		}
		ao.setStatement(statement);
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
		ao.setBusinessType(airticketOrderFrom.getStatement_type());//业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
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
		ticketLog.setOrderNo(ao.getGroupMarkNo());
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
	public void createApplyTickettOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
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

		if (airticketOrderFrom.getStatement_type() == 2
				&& platComAccount != null) { // 买入			
			statement.setFromPCAccount(platComAccount);// 付款帐号
		} else if (airticketOrderFrom.getStatement_type() == 1
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
		ao.setSubPnr(airticketOrderFrom.getPnr());// 预订pnr
		ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
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
		ao.setBusinessType(airticketOrderFrom.getStatement_type());//业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
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
		ticketLog.setOrderNo(ao.getGroupMarkNo());
		ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
		ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());// 操作员
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);

		airticketOrderDAO.update(airticketOrder);// 修改原订单信息

	}

	
	//通过外部pnr信息创建退废票
	public void createOutRetireTradingOrder(AirticketOrder airticketOrderFrom,TempPNR tempPNR,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setStatus(airticketOrderFrom.getStatement().getStatus());//状态
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statement.setTotalAmount(airticketOrderFrom.getStatement().getTotalAmount());//总金额
		statement.setActualAmount(airticketOrderFrom.getStatement().getActualAmount());//实收款
		statement.setUnsettledAccount(airticketOrderFrom.getStatement().getUnsettledAccount());//未结款
		statement.setCommission(airticketOrderFrom.getStatement().getCommission());//现返佣金
		statement.setRakeOff(airticketOrderFrom.getStatement().getRakeOff());//后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		
		if (airticketOrderFrom.getStatement().getFromPCAccount()!=null) { // 买入			
			statement.setFromPCAccount(airticketOrderFrom.getStatement().getFromPCAccount());// 付款帐号
		} else if (airticketOrderFrom.getStatement().getToPCAccount() != null) {// 卖出			
			statement.setToPCAccount(airticketOrderFrom.getStatement().getToPCAccount());// 收款帐号
		}
		statementDAO.save(statement);
		
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
		  ao.setSubPnr(airticketOrderFrom.getSubPnr());//预订pnr
		  ao.setBigPnr(airticketOrderFrom.getBigPnr());//大pnr
		  ao.setStatement(statement);
		  ao.setTicketPrice(airticketOrderFrom.getTicketPrice());//票面价格
		  ao.setAirportPrice(airticketOrderFrom.getAirportPrice());//机建费
		  ao.setFuelPrice(airticketOrderFrom.getFuelPrice());//燃油税
		  ao.setAgent(airticketOrderFrom.getAgent()); //购票客户
		  ao.setHandlingCharge(airticketOrderFrom.getHandlingCharge());//手续费
		  ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());//行程单费用
		  ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());//保险费
		  ao.setRebate(airticketOrderFrom.getRebate());//政策
		  ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());//机票订单号
		  if(airticketOrder!=null&&airticketOrder.getGroupMarkNo()!=null&&!"".equals(airticketOrder.getGroupMarkNo().trim())){
			  ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());
		  }else{
			  ao.setGroupMarkNo(noUtil.getAirticketGroupNo());//订单组编号
		  }
		  ao.setStatus(airticketOrderFrom.getStatus()); //订单状态
		  ao.setTicketType(airticketOrderFrom.getTicketType());//机票类型
		  ao.setTranType(airticketOrderFrom.getTranType());//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  ao.setBusinessType(airticketOrderFrom.getStatement().getType());//业务类型
		  ao.setReturnReason(airticketOrderFrom.getReturnReason());//退废票原因
		  ao.setEntryOperator(uri.getUser().getUserNo());
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
		  airticketOrderDAO.save(ao);
		  airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		  // 创建退费
			if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_24||airticketOrderFrom.getStatus()==AirticketOrder.STATUS_34){
		
			  //乘机人
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
			
			  //审核退废 并且创建（ 收款订单） 外部
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
			  ticketLog.setOrderNo(ao.getGroupMarkNo());
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
			  ticketLog.setStatus(1L);
			  ticketLogDAO.save(ticketLog);
	}

	
	//创建退废票
	public void createRetireTradingOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(noUtil.getStatementNo());//结算单号
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		//设置平台号 
		if(airticketOrder.getStatement().getType()==Statement.type__2){ //收入
			
		if(airticketOrder.getStatement().getFromPCAccount()!=null){			
			statement.setFromPCAccount(airticketOrder.getStatement().getFromPCAccount());//收款帐号			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){			
			statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
		 }
		}
		if(airticketOrder.getStatement().getType()==Statement.type__1){ //支出			
			if(airticketOrder.getStatement().getFromPCAccount()!=null){				
				statement.setFromPCAccount(airticketOrder.getStatement().getFromPCAccount());//付款帐号				
			}else if(airticketOrder.getStatement().getToPCAccount()!=null){				
				statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//付款帐号
			 }
		}
		  String[] passengerId= airticketOrderFrom.getPassengerIds();
		  if(passengerId!=null&&passengerId.length>0){
			 
			  BigDecimal totalAmount=airticketOrder.getStatement().getTotalAmount();
			  BigDecimal passengersCount=new BigDecimal(airticketOrder.getPassengersCount());
			  BigDecimal  passengersNum=new BigDecimal(passengerId.length);
			  if(totalAmount!=null&&passengersCount!=null&&passengersNum!=null){
				  System.out.println("===passengersNum"+passengersNum);
				   totalAmount=   totalAmount.divide(passengersCount,2,BigDecimal.ROUND_HALF_UP);
				   totalAmount= totalAmount.multiply(passengersNum);
				   statement.setTotalAmount(totalAmount);//总金额
				   statement.setActualAmount(airticketOrder.getStatement().getActualAmount());//实收款
			  }
			  
		  }else{
			  statement.setTotalAmount(airticketOrder.getStatement().getTotalAmount());//总金额
			  statement.setActualAmount(airticketOrder.getStatement().getActualAmount());//实收款
		  }
		
		  
		statement.setUnsettledAccount(airticketOrder.getStatement().getUnsettledAccount());//未结款
		statement.setCommission(airticketOrder.getStatement().getCommission());//现返佣金
		statement.setRakeOff(airticketOrder.getStatement().getRakeOff());//后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
		statementDAO.save(statement);
	
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
		  ao.setSubPnr(airticketOrder.getSubPnr());//预订pnr
		  if(airticketOrder.getBigPnr()!=null){
			  ao.setBigPnr(airticketOrder.getBigPnr());//大pnr
			  }
		  if(airticketOrderFrom.getBigPnr()!=null){
		  ao.setBigPnr(airticketOrderFrom.getBigPnr());//大pnr
		  }
		  ao.setOldOrderNo(airticketOrder.getOldOrderNo());
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
		  ao.setReturnReason(airticketOrderFrom.getReturnReason());//退票原因
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  if(airticketOrderFrom.getTotalAmount()!=null&& airticketOrderFrom.getTotalAmount().compareTo(BigDecimal.ZERO) > 0){
		  ao.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());
		  }
		  ao.setBusinessType(airticketOrderFrom.getBusinessType());//业务类型
		  ao.setReturnReason(airticketOrderFrom.getReturnReason());//退废票原因
		  ao.setEntryOperator(uri.getUser().getUserNo());
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
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
			  int rowCount=Integer.valueOf(passengerIds[p]);
			  Passenger passenger=new Passenger();
			  passenger.setAirticketOrder(ao);
			  passenger.setName(passengerNames[rowCount]); //乘机人姓名
			  passenger.setCardno(passengerCardno[rowCount]);//证件号
			  passenger.setType(1L); //类型
			  passenger.setStatus(1L);//状态
			  passenger.setTicketNumber(passengerTicketNumber[rowCount]); //票号
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
		  		  
		  for(int f=0;f<flightIds.length;f++){
			  int fCount=Integer.valueOf(flightIds[f]);
			  Flight flight=new Flight();
			  flight.setAirticketOrder(ao);
			  flight.setFlightCode(flightCodes[fCount]);//航班号
			  flight.setStartPoint(startPoints[fCount]); //出发地
			  flight.setEndPoint(endPoints[fCount]);//目的地
			  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[fCount].toString(), "yyyy-MM-dd"));//起飞时间
			  flight.setDiscount(discounts[fCount]);//折扣
			  flight.setFlightClass(flightClasss[fCount]);//舱位
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
		  ticketLog.setOrderNo(ao.getGroupMarkNo());
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
		  	 
		
	}

	
	//通过外部pnr信息创建改签票
	public void createOutWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,TempPNR tempPNR,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setStatus(airticketOrderFrom.getStatement().getStatus());//状态
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statement.setTotalAmount(airticketOrderFrom.getStatement().getTotalAmount());//总金额
		statement.setActualAmount(airticketOrderFrom.getStatement().getActualAmount());//实收款
		statement.setUnsettledAccount(airticketOrderFrom.getStatement().getUnsettledAccount());//未结款
		statement.setCommission(airticketOrderFrom.getStatement().getCommission());//现返佣金
		statement.setRakeOff(airticketOrderFrom.getStatement().getRakeOff());//后返佣金
		statement.setOrderType(Statement.ORDERTYPE_1);
	    if (airticketOrderFrom.getStatement().getFromPCAccount()!=null) { // 买入
	        
	        statement.setFromPCAccount(airticketOrderFrom.getStatement().getFromPCAccount());// 付款帐号

	      } else if (airticketOrderFrom.getStatement().getToPCAccount() != null) {// 卖出
	        
	  			statement.setToPCAccount(airticketOrderFrom.getStatement().getToPCAccount());// 收款帐号
	  		}
		statementDAO.save(statement);
		
		//机票订单
		  AirticketOrder ao=new AirticketOrder();
		  ao.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
		  ao.setSubPnr(tempPNR.getPnr());//预订pnr
		  ao.setBigPnr(airticketOrderFrom.getBigPnr());//大pnr
		  ao.setUmbuchenPnr(airticketOrderFrom.getUmbuchenPnr());//改签pnr
		  ao.setStatement(statement);
		  ao.setTicketPrice(airticketOrderFrom.getTicketPrice());//票面价格
		  ao.setAirportPrice(airticketOrderFrom.getAirportPrice());//机建费
		  ao.setFuelPrice(airticketOrderFrom.getFuelPrice());//燃油税
		  ao.setAgent(airticketOrderFrom.getAgent()); //购票客户
		 
		  ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());//行程单费用
		  ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());//保险费
		  ao.setRebate(airticketOrderFrom.getRebate());//政策
		  ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());//机票订单号
		  if(airticketOrder!=null&&airticketOrder.getGroupMarkNo()!=null&&!"".equals(airticketOrder.getGroupMarkNo().trim())){
			 ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());
		   }else{
			   ao.setGroupMarkNo(noUtil.getAirticketGroupNo());//订单组编号
		   }
		  ao.setStatus(airticketOrderFrom.getStatus()); //订单状态
		  ao.setTicketType(airticketOrderFrom.getTicketType());//机票类型
		  ao.setTranType(airticketOrderFrom.getTranType());//交易类型
		  ao.setMemo(airticketOrderFrom.getMemo());
		  ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		  ao.setBusinessType(airticketOrderFrom.getBusinessType());//业务类型
		  ao.setReturnReason(airticketOrderFrom.getReturnReason());//退废票原因
		  ao.setEntryOperator(uri.getUser().getUserNo());
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
		  airticketOrderDAO.save(ao);
          airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		  if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_46){
		    //乘机人
			  String[] passengerIds=airticketOrderFrom.getPassengerIds();
			  String[] passengerNames= airticketOrderFrom.getPassengerNames();
			  String[] passengerCardno= airticketOrderFrom.getPassengerCardno();
			  String[] passengerTicketNumber= airticketOrderFrom.getPassengerTicketNumber();
			  
			  for(int p=0;p<passengerIds.length;p++){				  
				  int rowCount=Integer.valueOf(passengerIds[p]);
				  Passenger passenger=new Passenger();
				  passenger.setAirticketOrder(ao);
				  passenger.setName(passengerNames[rowCount]); //乘机人姓名
				  passenger.setCardno(passengerCardno[rowCount]);//证件号
				  passenger.setType(1L); //类型
				  passenger.setStatus(1L);//状态
				  passenger.setTicketNumber(passengerTicketNumber[rowCount]); //票号
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
			  
			  for(int f=0;f<flightIds.length;f++){
				  int fCount=Integer.valueOf(flightIds[f]);
				  Flight flight=new Flight();
				  flight.setAirticketOrder(ao);
				  flight.setFlightCode(flightCodes[fCount]);//航班号
				  flight.setStartPoint(startPoints[fCount]); //出发地
				  flight.setEndPoint(endPoints[fCount]);//目的地
				  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[fCount].toString(), "yyyy-MM-dd"));//起飞时间
				  flight.setDiscount(discounts[fCount]);//折扣
                  System.out.println("discounts[f]==="+discounts[fCount]);
				  flight.setFlightClass(flightClasss[f]);//舱位
				  flight.setStatus(1L);  //状态
				  flightDAO.save(flight);	
			  }
			  
			  //审核改签 并且创建（ 收款订单） 外部
	      } else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_41){			  
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
		  ticketLog.setOrderNo(ao.getGroupMarkNo());
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
		  	 
	}
	
	//创建改签票
	public void createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,AirticketOrder airticketOrder,UserRightInfo uri) throws AppException{
		//结算
		Statement statement=new Statement();
		statement.setStatementNo(airticketOrder.getStatement().getStatementNo());//结算单号
		statement.setSysUser(airticketOrderFrom.getStatement().getSysUser());//操作员
		statement.setStatus(airticketOrderFrom.getStatement().getStatus());//状态
		statement.setType(airticketOrderFrom.getStatement().getType());//类型
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		
		//设置平台号 
		if(airticketOrderFrom.getStatement().getType()==Statement.type__2){
		if(airticketOrder.getStatement().getFromPCAccount()!=null){			
			statement.setFromPCAccount(airticketOrder.getStatement().getFromPCAccount());//付款帐号			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){			
			statement.setFromPCAccount(airticketOrder.getStatement().getToPCAccount());//付款帐号
		}
		}
		
		//设置平台号 
		if(airticketOrderFrom.getStatement().getType()==Statement.type__1){
		if(airticketOrder.getStatement().getFromPCAccount()!=null){			
			statement.setToPCAccount(airticketOrder.getStatement().getFromPCAccount());//收款帐号			
		}else if(airticketOrder.getStatement().getToPCAccount()!=null){			
			statement.setToPCAccount(airticketOrder.getStatement().getToPCAccount());//收款帐号
		}
		}
		//手动设置平台
        if (airticketOrderFrom.getStatement().getFromPCAccount()!=null) { // 买入
	        
	        statement.setFromPCAccount(airticketOrderFrom.getStatement().getFromPCAccount());// 付款帐号

	      } else if (airticketOrderFrom.getStatement().getToPCAccount() != null) {// 卖出
	        
	  			statement.setToPCAccount(airticketOrderFrom.getStatement().getToPCAccount());// 收款帐号
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
		  ao.setUmbuchenPnr(airticketOrderFrom.getUmbuchenPnr());//改签pnr
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
		  ao.setBusinessType(airticketOrderFrom.getBusinessType());//业务类型
		  ao.setEntryOperator(uri.getUser().getUserNo());
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
		  airticketOrderDAO.save(ao);
		
		  if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_39){
		    //乘机人
			//  Set tempPassengerList=airticketOrder.getPassengers();
			  String[] passengerIds=airticketOrderFrom.getPassengerIds();
			  String[] passengerNames= airticketOrderFrom.getPassengerNames();
			  String[] passengerCardno= airticketOrderFrom.getPassengerCardno();
			  String[] passengerTicketNumber= airticketOrderFrom.getPassengerTicketNumber(); 		
			  
			  for(int p=0;p<passengerIds.length;p++){
				  
				  int rowCount=Integer.valueOf(passengerIds[p]);
				  Passenger passenger=new Passenger();
				  passenger.setAirticketOrder(ao);
				  passenger.setName(passengerNames[rowCount]); //乘机人姓名
				  passenger.setCardno(passengerCardno[rowCount]);//证件号
				  passenger.setType(1L); //类型
				  passenger.setStatus(1L);//状态
				  passenger.setTicketNumber(passengerTicketNumber[rowCount]); //票号
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
				  int fCount=Integer.valueOf(flightIds[f]);
				  Flight flight=new Flight();
				  flight.setAirticketOrder(ao);
				  flight.setFlightCode(flightCodes[fCount]);//航班号
				  flight.setStartPoint(startPoints[fCount]); //出发地
				  flight.setEndPoint(endPoints[fCount]);//目的地
				  flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[fCount].toString(), "yyyy-MM-dd"));//起飞时间
				  flight.setDiscount(discounts[fCount]);//折扣
                  System.out.println("discounts[f]==="+discounts[fCount]);
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
		  ticketLog.setOrderNo(ao.getGroupMarkNo());
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
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
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
		
		//String[] statement_status=request.getParameterValues("statement_status");
		String[] statement_totalAmount=request.getParameterValues("statement_totalAmount");
		String[] statement_actualAmount=request.getParameterValues("statement_actualAmount");
		String[] statement_type=request.getParameterValues("statement_type");
		
		String[] platformId=request.getParameterValues("platformId");
		String[] companyId=request.getParameterValues("companyId");
		String[] accountId=request.getParameterValues("accountId");
		String statementNo="";
		String groupMarkNo="";
		for(int i=0;i<statement_type.length;i++){
		//结算
		Statement statement=new Statement();
		if(i==0){
			statementNo=noUtil.getStatementNo();
			statement.setStatementNo(statementNo);//结算单号
		}else{
			statement.setStatementNo(statementNo);//结算单号
		}
		
		statement.setSysUser(uri.getUser());//操作员
		//statement.setStatus(Long.valueOf(statement_status[i]));//状态
		statement.setType(Long.valueOf(statement_type[i]));//类型
		statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
		statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
	   
		// 设置平台号
		PlatComAccountStore platComAccountStore = new PlatComAccountStore();
		PlatComAccount platComAccount = null;
		if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
			platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
		}

		if (Long.valueOf(statement_type[i])==Statement.type__2	&& platComAccount != null) { // 买入
			System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (Long.valueOf(statement_type[i])==Statement.type__1&& platComAccount != null) {// 卖出
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
		  ao.setBusinessType(Long.valueOf(statement_type[i]));//业务类型
		  ao.setEntryOperator(uri.getUser().getUserNo());
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
		  airticketOrderDAO.save(ao);
		  airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		
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
		  ticketLog.setOrderNo(ao.getGroupMarkNo());
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
		//String[] statement_status=request.getParameterValues("statement.status");
		String[] statement_totalAmount=request.getParameterValues("statement_totalAmount");
		String[] statement_actualAmount=request.getParameterValues("statement_actualAmount");
		String[] statement_type=request.getParameterValues("statement.type");
		
		String[] platformId=request.getParameterValues("platformId");
		String[] companyId=request.getParameterValues("companyId");
		String[] accountId=request.getParameterValues("accountId");
		String statementNo="";
		String groupMarkNo="";
		for(int i=0;i<airticketOrderIds.length;i++){
			
		if(Long.valueOf(airticketOrderIds[i])>0){
			System.out.println("===="+airticketOrderIds[i]);
			AirticketOrder ao=	airticketOrderDAO.getAirticketOrderById(Long.valueOf(airticketOrderIds[i]));
			
			//结算
			Statement statement=statementDAO.getStatementById(ao.getStatement().getId());
			
			statement.setStatementNo(ao.getStatement().getStatementNo());//结算单号
			statement.setSysUser(uri.getUser());//操作员
			//statement.setStatus(Long.valueOf(statement_status[i]));//状态
			statement.setType(Long.valueOf(statement_type[i]));//类型
			statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
			statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
		    
			// 设置平台号
			PlatComAccountStore platComAccountStore = new PlatComAccountStore();
			PlatComAccount platComAccount = null;
			if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
				platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
			}

			if (Long.valueOf(statement_type[i])==Statement.type__2	&& platComAccount != null) { // 买入
				//System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
				statement.setToPCAccount(null);// 收款帐号
				statement.setFromPCAccount(platComAccount);// 付款帐号

			} else if (Long.valueOf(statement_type[i])==Statement.type__1&& platComAccount != null) {// 卖出
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
			  ao.setBusinessType(Long.valueOf(statement_type[i]));//业务类型
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
			  ticketLog.setOrderNo(ao.getGroupMarkNo());
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
		//statement.setStatus(Long.valueOf(statement_status[i]));//状态
		statement.setType(Long.valueOf(statement_type[i]));//类型
		statement.setTotalAmount(new java.math.BigDecimal(statement_totalAmount[i]));//总金额
		statement.setActualAmount(new java.math.BigDecimal(statement_actualAmount[i]));//实收金额
	
		// 设置平台号
		PlatComAccountStore platComAccountStore = new PlatComAccountStore();
		PlatComAccount platComAccount = null;
		if (accountId[i] != null&& companyId[i] != null&& platformId[i] != null) {
			platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId[i]), Long.valueOf(companyId[i]),Long.valueOf(accountId[i]));
		}

		if (Long.valueOf(statement_type[i])==Statement.type__2	&& platComAccount != null) { // 买入
			System.out.println("airticketOrderFrom.getAccoun==="+ airticketOrderFrom.getAccountId());
			statement.setFromPCAccount(platComAccount);// 付款帐号

		} else if (Long.valueOf(statement_type[i])==Statement.type__1&& platComAccount != null) {// 卖出
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
		  ao.setBusinessType(Long.valueOf(statement_type[i]));//业务类型
		  ao.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入订单时间
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
		  ticketLog.setOrderNo(ao.getGroupMarkNo());
		  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
		  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
		  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		  ticketLog.setType(airticketOrderFrom.getTicketLog().getType());// 类型
		  ticketLog.setStatus(1L);
		  ticketLogDAO.save(ticketLog);
	}  
  }
}

	
	
	/**
	 * 团队专用
	 */
	//------------销售---------------
	//修改状态（新订单,待统计利润--->>新订单,待统申请）
	public void editTeamAirticketOrder(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_111);//状态：新订单,待统申请
		airticketOrder.setStatement(statement);
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);
		
		//操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(uri.getUser());//操作员
		ticketLog.setIp(request.getRemoteAddr());//IP					 
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_111);//新订单,待统计利润
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}
	
	//修改状态（新订单,待统计申请--->>申请成功，等待支付）
	public void editTeamAirticketOrderT(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_102);//状态：申请成功，等待支付
		airticketOrder.setStatement(statement);
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);
		
		//操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(uri.getUser());//操作员
		ticketLog.setIp(request.getRemoteAddr());//IP					 
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_102);//申请成功，等待支付
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}
	
	//修改状态（支付成功，等待出票--->>出票成功，交易结束）
	public void editTeamAirticketOrderOver(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_105);//状态：出票成功，交易结束
		airticketOrder.setStatement(statement);
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);
		
		//操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(uri.getUser());//操作员
		ticketLog.setIp(request.getRemoteAddr());//IP					 
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_105);//出票成功，交易结束
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}
	
	
	//------------退票---------------
	//修改状态（新退票订单--->>退票审核通过，等待退款）
	public void editTeamRefundAirticketOrder(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_108);//状态：退票审核通过，等待退款
		airticketOrder.setStatement(statement);
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);
		
		//操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(uri.getUser());//操作员
		ticketLog.setIp(request.getRemoteAddr());//IP					 
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_108);//退票审核通过，等待退款
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}
	
	//修改状态（退票审核通过，等待退款--->>已经退款，交易结束）
	public void editTeamRefundAirticketOrderOver(String airticketOrderId,UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_109);//状态：已经退款，交易结束
		airticketOrder.setStatement(statement);
		airticketOrder.setAgent(agent);
		
		airticketOrderDAO.update(airticketOrder);
		statement.setSysUser(uri.getUser());
		statementDAO.merge(statement);
		
		//操作日志
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(uri.getUser());//操作员
		ticketLog.setIp(request.getRemoteAddr());//IP					 
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_109);//已经退款，交易结束
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
	}
	
	
	
	//申请支付 显示购票客户、账号信息
	public void editTeamAirticketOrderAgentName(AirticketOrderListForm alf, HttpServletRequest request, HttpServletResponse response) throws AppException{
		List<Agent> agentList = agentDAO.getAgentList();
		List<PlatComAccount> platComAccountList =platComAccountDAO.getPlatComAccountByPlatformId(99999);//专查询团队
		request.setAttribute("platComAccountList", platComAccountList);
		request.setAttribute("agentList", agentList);
		
	}
	
	//团队确认支付（修改）
	public void editTeamAirticketOrderOK(AirticketOrder airticketOrderForm,long airticketOrderId,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response)throws AppException{
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

			long rptOrderItem_ctl01_selAccount =airticketOrderForm.getRptOrderItem_ctl01_selAccount();//		
			PlatComAccount platComAccount =platComAccountDAO.getPlatComAccountById(rptOrderItem_ctl01_selAccount);
			
			Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
			statement.setSysUser(uri.getUser());
			statement.setFromPCAccount(platComAccount);
			statementDAO.update(statement);
			
			Agent agent =agentDAO.getAgentByid(airticketOrder.getAgent().getId());
			airticketOrder.setAgent(agent);
			airticketOrder.setStatement(statement);
			airticketOrder.setStatus(AirticketOrder.STATUS_103);//支付成功，等待出票
			airticketOrder.setPayTime(airticketOrderForm.getTxtConfirmTime());//收付款时间
			//airticketOrder.setOptTime(airticketOrderForm.getTxtConfirmTime());//时间
			airticketOrder.setMemo(airticketOrderForm.getRptOrderItem_ctl01_txtOrderRemark());//备注
			airticketOrderDAO.update(airticketOrder);	
			
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
		String[] discount = request.getParameterValues("discount");//折扣
		String[] ticketPrice =request.getParameterValues("ticketPrice");//票面价
		String[] adultAirportPrice=request.getParameterValues("adultAirportPrice");//成人机建
		String[] adultFuelPrice=request.getParameterValues("adultFuelPrice");
		String[] childAirportPrice=request.getParameterValues("childAirportPrice");
		String[] childfuelPrice=request.getParameterValues("childfuelPrice");
		String[] babyAirportPrice=request.getParameterValues("babyAirportPrice");
		String[] babyfuelPrice=request.getParameterValues("babyfuelPrice");

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
		statement.setType(Statement.type__1);//收入
		statement.setSysUser(uri.getUser());
		statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		statementDAO.save(statement);
		
		// 机票订单表
		AirticketOrder air = new AirticketOrder();
		air.setAgent(agent);
		air.setDrawPnr(airticketOrderFrom.getDrawPnr());
		air.setAdultCount(airticketOrderFrom.getAdultCount());
		air.setTotalTicketPrice(airticketOrderFrom.getTotlePrice());//总票面价
		air.setChildCount(airticketOrderFrom.getChildCount());
		air.setBabyCount(airticketOrderFrom.getBabyCount());
		air.setAirportPrice(BigDecimal.valueOf(0));//机建税(团队订单录入给0)
		air.setFuelPrice(BigDecimal.valueOf(0));//燃油税
		air.setTotalAirportPrice(airticketOrderFrom.getAirportPrice());//总机建税
		air.setTotalFuelPrice(airticketOrderFrom.getFuelPrice());//总燃油税
		air.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 订单号
		air.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		air.setEntryTime(new Timestamp(System.currentTimeMillis()));//录单时间
		air.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
		air.setTeamaddPrice(airticketOrderFrom.getTeamAddPrice());//团队加价
		air.setAgentaddPrice(airticketOrderFrom.getAgentAddPrice());//客户加价
		air.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型：买入
		air.setTranType(airticketOrderFrom.getTranType());//交易类型		
		air.setTicketType(AirticketOrder.TICKETTYPE_2);// 类型:团队
		air.setEntryOperator(uri.getUser().getUserNo());
		if(airticketOrderFrom.getTranType() ==AirticketOrder.TRANTYPE__2)//买入
		{
			air.setStatus(AirticketOrder.STATUS_101);//状态:新订单
		}
		if(airticketOrderFrom.getTranType() ==AirticketOrder.TRANTYPE_3)//退票
		{
			air.setStatus(AirticketOrder.STATUS_107);//状态:退票订单，等待审核
		}
		air.setDrawer(airticketOrderFrom.getDrawer());//客票类型(出票人)
		air.setStatement(airticketOrderFrom.getStatement());
		air.setStatement(statement);
		airticketOrderDAO.save(air);
		airticketOrderFrom.setAirticketOrderId(air.getId());
		
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
			flight.setDiscount(discount[i].toString());//折扣
			
			long fliTicketPrice = Long.parseLong(ticketPrice[i]);
			long airportPriceAdult=Long.parseLong(adultAirportPrice[i]);
			long fuelPriceAdult =Long.parseLong(adultFuelPrice[i]);
			long airportPriceChild=Long.parseLong(childAirportPrice[i]);
			long fuelPriceChild =Long.parseLong(childfuelPrice[i]);
			long airportPriceBaby=Long.parseLong(babyAirportPrice[i]);
			long fuelPriceBaby =Long.parseLong(babyfuelPrice[i]);
			flight.setTicketPrice(BigDecimal.valueOf(fliTicketPrice));//票面价
			flight.setAirportPriceAdult(BigDecimal.valueOf(airportPriceAdult));
			flight.setFuelPriceAdult(BigDecimal.valueOf(fuelPriceAdult));
			flight.setAirportPriceChild(BigDecimal.valueOf(airportPriceChild));
			flight.setFuelPriceChild(BigDecimal.valueOf(fuelPriceChild));
			flight.setAirportPriceBaby(BigDecimal.valueOf(airportPriceBaby));
			flight.setFuelPriceBaby(BigDecimal.valueOf(fuelPriceBaby));
			flight.setStatus(1L);
			flightDAO.save(flight);
			
			//操作日志
			TicketLog ticketLog = new TicketLog();
			ticketLog.setOrderNo(air.getGroupMarkNo());
			ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
			ticketLog.setSysUser(uri.getUser());//操作员
			ticketLog.setIp(request.getRemoteAddr());//IP					 
			ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
			if(airticketOrderFrom.getTranType() ==AirticketOrder.TRANTYPE__2)//买入
			{
				ticketLog.setType(TicketLog.TYPE_101);//新订单录入
			}
			if(airticketOrderFrom.getTranType() ==AirticketOrder.TRANTYPE_3)//退票
			{
				ticketLog.setType(TicketLog.TYPE_107);//退票订单，等待审核
			}
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
			//AirticketOrder airticketOrderTeam1 =airticketOrderList.get(0);
			//AirticketOrder airticketOrderTeam2 =airticketOrderList.get(1);
			
			List<Agent> agentList = agentDAO.getAgentList();
			List<Flight> flightList = flightDAO.getFlightListByOrderId(Long.parseLong(airticketOrderId));
			Flight flight = flightDAO.getFlightByAirticketOrderID(Long.parseLong(airticketOrderId));
			request.setAttribute("flight", flight);
			request.setAttribute("flightSize", flightList.size());
			request.setAttribute("flightList", flightList);
			request.setAttribute("agentList", agentList);
		
	}
	
	//修改的团队利润统计信息（客户，航空）
	public void updateAirticketOrderAgentAvia(AirticketOrder airticketOrderForm,long airticketOrderId,UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
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
		if(statementList.size()==1)//利润统计添加
		{
			insertTeamTradingOrder(airticketOrderForm, uri, request, response); 
		}
		if(statementList.size()>1)//利润统计修改
		{
			updateTeamAirticketOrderAgentAvia(airticketOrderForm, airticketOrderId, uri, request, response);
		}
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
		String[] ticketPrice =request.getParameterValues("ticketPrice");//票面价
		String[] adultAirportPrice=request.getParameterValues("adultAirportPrice");
		String[] adultFuelPrice=request.getParameterValues("adultFuelPrice");
		String[] childAirportPrice=request.getParameterValues("childAirportPrice");
		String[] childfuelPrice=request.getParameterValues("childfuelPrice");
		String[] babyAirportPrice=request.getParameterValues("babyAirportPrice");
		String[] babyfuelPrice=request.getParameterValues("babyfuelPrice");
		
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
			airticketOrder.setTotalTicketPrice(airticketOrderForm.getTotlePrice());//总票面价
			airticketOrder.setTicketPrice(airticketOrder.getTicketPrice());//票面价
			airticketOrder.setRebate(airticketOrderForm.getRebate());
			airticketOrder.setAdultCount(airticketOrderForm.getTeamAdultCount());//成人数
			airticketOrder.setChildCount(airticketOrderForm.getTeamChildCount());//儿童数
			airticketOrder.setBabyCount(airticketOrderForm.getTeamBabyCount());//婴儿数
			airticketOrder.setAirportPrice(airticketOrder.getAirportPrice());//机建税
			airticketOrder.setFuelPrice(airticketOrder.getFuelPrice());//燃油税
			airticketOrder.setTotalAirportPrice(airticketOrderForm.getAirportPrice());//总机建税
			airticketOrder.setTotalFuelPrice(airticketOrderForm.getFuelPrice());//总燃油税
			airticketOrder.setEntryTime(airticketOrder.getEntryTime());//录单时间
			airticketOrder.setOptTime(airticketOrder.getOptTime());//操作时间
			airticketOrder.setTeamaddPrice(airticketOrderForm.getTeamAddPrice());//团队加价
			airticketOrder.setAgentaddPrice(airticketOrderForm.getAgentAddPrice());//客户加价
			
			airticketOrder.setDocumentPrice(airticketOrderForm.getDocumentPrice());
			airticketOrder.setInsurancePrice(airticketOrderForm.getInsurancePrice());
			airticketOrder.setHandlingCharge(airticketOrderForm.getHandlingCharge());
			airticketOrder.setTicketType(airticketOrder.getTicketType());//机票类型
			airticketOrder.setTranType(airticketOrderForm.getTranType());//交易类型
			airticketOrder.setDrawer(airticketOrderForm.getDrawer());	//个出票人
			
			if(airticketOrderForm.getTranType() == AirticketOrder.TRANTYPE_3)//退票
			{
				airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);
			}
			if(airticketOrderForm.getTranType() == AirticketOrder.TRANTYPE__1)//销售
			{
				airticketOrder.setTranType(AirticketOrder.TRANTYPE__1);
			}
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
				
				long fliTicketPrice = Long.parseLong(ticketPrice[i]);
				long airportPriceAdult=Long.parseLong(adultAirportPrice[i]);
				long fuelPriceAdult =Long.parseLong(adultFuelPrice[i]);
				long airportPriceChild=Long.parseLong(childAirportPrice[i]);
				long fuelPriceChild =Long.parseLong(childfuelPrice[i]);
				long airportPriceBaby=Long.parseLong(babyAirportPrice[i]);
				long fuelPriceBaby =Long.parseLong(babyfuelPrice[i]);
				
				flight.setTicketPrice(BigDecimal.valueOf(fliTicketPrice));//票面价
				flight.setAirportPriceAdult(BigDecimal.valueOf(airportPriceAdult));
				flight.setFuelPriceAdult(BigDecimal.valueOf(fuelPriceAdult));
				flight.setAirportPriceChild(BigDecimal.valueOf(airportPriceChild));
				flight.setFuelPriceChild(BigDecimal.valueOf(fuelPriceChild));
				flight.setAirportPriceBaby(BigDecimal.valueOf(airportPriceBaby));
				flight.setFuelPriceBaby(BigDecimal.valueOf(fuelPriceBaby));
				flight.setStatus(flight.getStatus());//状态
				flight.setAirticketOrder(airticketOrder);
				flightDAO.update(flight);
			}
			
			//操作日志
			TicketLog ticketLog = new TicketLog();
			ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
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
	public void deleteAirticketOrder(String airticketOrderId) throws AppException {
		
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(airticketOrderId));
			Statement statement = statementDAO.getStatementById(airticketOrder.getStatement().getId());
			statement.setStatus(Statement.STATUS_88);//已废弃
			airticketOrder.setStatus(AirticketOrder.STATUS_88);//将团队订单状态变为已废弃
			airticketOrder.setStatement(statement);
			airticketOrderDAO.update(airticketOrder);
			statementDAO.update(statement);		
	}
	
	//添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
		
			PlatComAccount toplatComAccount=PlatComAccountStore.getPlatComAccountById(90000);//收款
			PlatComAccount fromplatComAccount=PlatComAccountStore.getPlatComAccountById(90001);//付款
			long airticketOrderId = airticketOrderForm.getAirticketOrderId();//机票订单ID
			if(airticketOrderId >0)
			{
				AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
				
				if(airticketOrder.getStatement() != null && airticketOrder.getAgent() != null)
				{
					Statement statement = statementDAO.getStatementById(airticketOrder.getStatement().getId());
					Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
				
					//支出（买入）
					if(fromplatComAccount.getId()>0)
					{
						
						
						//Statement stat = new Statement();
						//stat.setStatementNo(noUtil.getStatementNo());//结算单号
						statement.setFromPCAccount(fromplatComAccount);
						statement.setOrderType(1L);//订单类型
						//BigDecimal txtTUnAmount =airticketOrderFrom.getTxtTUnAmount();
						BigDecimal txtTUnAmount =airticketOrderForm.getTxtTAmount();//(应收金额暂时放的实收金额代总金额)
						BigDecimal txtTAmount =airticketOrderForm.getTxtTAmount();
						
							if(txtTUnAmount !=null && txtTAmount !=null )
							{
								statement.setTotalAmount(txtTAmount);//总金额(总金额暂时先用实收金额代)
								statement.setActualAmount(txtTAmount);//实收金额
								BigDecimal unsettledAccount = txtTUnAmount.subtract(txtTAmount);//未结款=总金额-实收金额
								statement.setUnsettledAccount(unsettledAccount);//
							}
							statement.setCommission(BigDecimal.valueOf(0));//现返佣金
							statement.setRakeOff(airticketOrderForm.getTxtAgentFeeCarrier());//后返佣金---月底返代理费
							statement.setSysUser(uri.getUser());//操作员ID
							statement.setType(Statement.type__2);//付出
							statement.setStatus(Statement.STATUS_1);//已结算
							statement.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
							statementDAO.update(statement);//修改结算数据(付出)
						
						airticketOrder.setTotalAirportPrice(airticketOrder.getTotalAirportPrice());//总建税
						airticketOrder.setTotalFuelPrice(airticketOrder.getTotalFuelPrice());//总燃油税
						airticketOrder.setOverTicketPrice(BigDecimal.valueOf(0));  //多收票价
						airticketOrder.setOverAirportfulePrice(BigDecimal.valueOf(0));//多收税
						airticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());//付退票手续费
						airticketOrder.setCommissonCount(airticketOrderForm.getTxtAgentT());//返点
						airticketOrder.setRakeoffCount(airticketOrderForm.getTxtAgent());//月底返点
						airticketOrder.setHandlingCharge(airticketOrderForm.getTxtCharge());//手续费
						airticketOrder.setProxyPrice(BigDecimal.valueOf(0));// 应付出团代理费（未返）
						airticketOrder.setEntryTime(airticketOrder.getEntryTime());//录单时间
						airticketOrder.setOptTime(airticketOrder.getOptTime());//操作时间 
						airticketOrder.setDocumentPrice(airticketOrder.getDocumentPrice());
						airticketOrder.setInsurancePrice(airticketOrder.getInsurancePrice());
						airticketOrder.setTicketType(airticketOrder.getTicketType());//机票类型
						airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型：买入
						airticketOrder.setDrawer(airticketOrder.getDrawer());	//个出票人
						if(airticketOrder.getTranType() ==AirticketOrder.TRANTYPE__2){//买入
							airticketOrder.setStatus(AirticketOrder.STATUS_111);//新订单,等待申请
						}
						if(airticketOrder.getTranType() ==3)//退票
						{
							airticketOrder.setStatus(AirticketOrder.STATUS_107);//退票订单，等待审核
						}
						airticketOrder.setStatement(statement);
						airticketOrder.setAgent(agent);
						airticketOrderDAO.update(airticketOrder);//修改客户（多收票价）
					}
					
			
					//收入（卖出）
					if(toplatComAccount.getId()>0)
					{
						Statement stat = new Statement();
						stat.setStatementNo(noUtil.getStatementNo());//结算单号
						stat.setToPCAccount(toplatComAccount);
						stat.setToPCAccount(toplatComAccount);//收款
						stat.setOrderType(1L);//订单类型
						//BigDecimal totalAmount =airticketOrderFrom.getTxtSAmount();
						BigDecimal totalAmount =airticketOrderForm.getTxtTotalAmount();//(暂时用实收金额代总金额)
						BigDecimal actualAmount =airticketOrderForm.getTxtTotalAmount();
					
						if(totalAmount !=null  && actualAmount !=null )
						{
							stat.setTotalAmount(totalAmount);//总金额
							stat.setActualAmount(actualAmount);//实收金额
							BigDecimal unsettledAccount = totalAmount.subtract(actualAmount);//未结款=总金额-实收金额
							stat.setUnsettledAccount(unsettledAccount);//
						}
						stat.setCommission(airticketOrderForm.getTxtAgentFeeTeams());//现返佣金
						stat.setRakeOff(BigDecimal.valueOf(0));//后返佣金
						stat.setSysUser(uri.getUser());//操作员ID
						stat.setType(Statement.type__1);//收入
						stat.setStatus(Statement.STATUS_1);//已结算
						stat.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
						statementDAO.save(stat);//添加结算(收入)数据
						
							
							//创建机票订单表(所对应的结算表状态是收入)--客户
							AirticketOrder teamairticketOrder = new AirticketOrder();
							teamairticketOrder.setAirOrderNo(airticketOrder.getAirOrderNo());//订单号
							teamairticketOrder.setGroupMarkNo(airticketOrder.getGroupMarkNo());//订单组编号
							teamairticketOrder.setDrawPnr(airticketOrder.getDrawPnr());
							teamairticketOrder.setSubPnr(airticketOrder.getSubPnr());
							teamairticketOrder.setBigPnr(airticketOrder.getBigPnr());
							teamairticketOrder.setTotalTicketPrice(airticketOrder.getTotalTicketPrice());//总票面价
							teamairticketOrder.setRebate(airticketOrder.getRebate());
							teamairticketOrder.setAdultCount(airticketOrder.getAdultCount());
							teamairticketOrder.setChildCount(airticketOrder.getChildCount());
							teamairticketOrder.setBabyCount(airticketOrder.getBabyCount());
							teamairticketOrder.setMemo(airticketOrderForm.getTxtRemark());//备注
							teamairticketOrder.setTotalAirportPrice(airticketOrder.getTotalAirportPrice());//总建税
							teamairticketOrder.setTotalFuelPrice(airticketOrder.getTotalFuelPrice());//总燃油税
							teamairticketOrder.setOverTicketPrice(airticketOrderForm.getTxtAmountMore());  //多收票价
							teamairticketOrder.setOverAirportfulePrice(airticketOrderForm.getTxtTaxMore());//多收税
							teamairticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtSourceTGQFee());//收退票手续费
							teamairticketOrder.setCommissonCount(airticketOrderForm.getTxtAgents());//返点
							teamairticketOrder.setRakeoffCount(airticketOrderForm.getTxtAgent());//月底返点
							teamairticketOrder.setProxyPrice(airticketOrderForm.getTxtUnAgentFeeTeams());// 应付出团代理费（未返）
							teamairticketOrder.setTeamaddPrice(airticketOrder.getTeamaddPrice());//团队加价
							teamairticketOrder.setAgentaddPrice(airticketOrder.getAgentaddPrice());//客户加价
							teamairticketOrder.setEntryTime(airticketOrder.getEntryTime());//录单时间
							teamairticketOrder.setOptTime(airticketOrder.getOptTime());//操作时间 
							teamairticketOrder.setDocumentPrice(airticketOrder.getDocumentPrice());
							teamairticketOrder.setInsurancePrice(airticketOrder.getInsurancePrice());
							teamairticketOrder.setTicketType(airticketOrder.getTicketType());//机票类型
							teamairticketOrder.setTranType(AirticketOrder.TRANTYPE__1);//交易类型:卖出(销售)
							teamairticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型：卖出
							teamairticketOrder.setDrawer(airticketOrder.getDrawer());	//个出票人
							
							if(airticketOrder.getTranType() ==AirticketOrder.TRANTYPE__2){//买入
								teamairticketOrder.setStatus(AirticketOrder.STATUS_111);//新订单,等待申请
							}
							if(airticketOrder.getTranType() ==AirticketOrder.TRANTYPE_3)//退票
							{
								teamairticketOrder.setStatus(AirticketOrder.STATUS_107);//退票订单，等待审核
							}
							teamairticketOrder.setStatement(stat);
							teamairticketOrder.setAgent(agent);
							teamairticketOrder.setEntryOperator(uri.getUser().getUserNo());
							airticketOrderDAO.save(teamairticketOrder);//复制一张订单数据(付出)
							long airOrderId=teamairticketOrder.getId();//机票订单ID
							
							//航班表
							if(airOrderId>0)
							{
								AirticketOrder air = airticketOrderDAO.getAirticketOrderById(airOrderId);
								List<Flight> flightList = flightDAO.getFlightListByOrderId(airticketOrderId);
								for(int i=0;i<flightList.size();i++)
								{
									Flight flight =flightList.get(i);
									Flight fl = new Flight();
									fl.setAirticketOrder(air);
									fl.setFlightCode(flight.getFlightCode());
									fl.setStartPoint(flight.getStartPoint());
									fl.setEndPoint(flight.getEndPoint());
									fl.setBoardingTime(flight.getBoardingTime());
									fl.setFlightClass(flight.getFlightClass());
									fl.setDiscount(flight.getDiscount());
									fl.setTicketPrice(flight.getTicketPrice());//票面价
									fl.setAirportPriceAdult(flight.getAirportPriceAdult());//机建税（成人）
									fl.setFuelPriceAdult(flight.getFuelPriceAdult());//燃油税（成人）
									fl.setAirportPriceChild(flight.getAirportPriceChild());//机建税（儿童）
									fl.setFuelPriceChild(flight.getFuelPriceChild());//燃油税（儿童）
									fl.setAirportPriceBaby(flight.getAirportPriceBaby());//机建税（婴儿）
									fl.setFuelPriceBaby(flight.getFuelPriceBaby());//燃油税（婴儿）
									fl.setStatus(flight.getStatus());
									flightDAO.save(fl);
								}
							}
						}
						//操作日志
						TicketLog ticketLog = new TicketLog();
						ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
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
	
	//编辑团队机票订单利润(显示)
	public void updaTempAirticketOrderPrice(AirticketOrder airticketOrderForm,long airticketOrderId,
			HttpServletRequest request, HttpServletResponse response) throws AppException {
		if(airticketOrderId>0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
			List<AirticketOrder> airticketOrderList = airticketOrderDAO.getAirticketOrderListByGroupMarkNo(airticketOrder.getGroupMarkNo());
			for (int i = 0; i < airticketOrderList.size(); i++) {
				AirticketOrder airticketOrderTeam=airticketOrderList.get(i);
				Statement statement =statementDAO.getStatementById(airticketOrderTeam.getStatement().getId());
				if(airticketOrderTeam.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
				{
					request.setAttribute("airticketOrderTeamAgent", airticketOrderTeam); 
					request.setAttribute("statementTeamAgent", statement);
				}else if(airticketOrderTeam.getTranType() ==AirticketOrder.TRANTYPE__2)//买入
				{
					request.setAttribute("airticketOrderTeamAvia", airticketOrderTeam);
					request.setAttribute("statementTeamAvia", statement);
				}else if(airticketOrderTeam.getTranType() ==AirticketOrder.TRANTYPE_3)//退票
				{
					request.setAttribute("airticketOrderRefundTeamAgent", airticketOrderTeam);
					request.setAttribute("statementTeamAvia", statement);
				}else
				{
					request.setAttribute("airticketOrderTeam", airticketOrderTeam);
				}
			}
		}
		
	}
	
	//修改图队利润统计(编辑利润统计)
	public void updateTeamAirticketOrderAgentAvia(AirticketOrder airticketOrderForm,long airticketOrderId,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
			
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
		
		List<AirticketOrder> airticketOrderList = airticketOrderDAO.getAirticketOrderListByGroupMarkNo(airticketOrder.getGroupMarkNo());
		for (int i = 0; i < airticketOrderList.size(); i++) {
			AirticketOrder airticketOrderAgentAvia=airticketOrderList.get(i);
			Statement statement = statementDAO.getStatementById(airticketOrderAgentAvia.getStatement().getId());
			Agent agent = agentDAO.getAgentByid(airticketOrderAgentAvia.getAgent().getId());
			if(airticketOrderAgentAvia.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
			{
				//机票订单表
				airticketOrderAgentAvia.setProxyPrice(airticketOrderForm.getTxtUnAgentFeeTeams());// 应付出团代理费（未返）
				airticketOrderAgentAvia.setOverAirportfulePrice(airticketOrderForm.getTxtTaxMore());//多收票价
				airticketOrderAgentAvia.setOverTicketPrice(airticketOrderForm.getTxtAmountMore());//多收税
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgents());//返点
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtSourceTGQFee());//收退票手续费
				airticketOrderAgentAvia.setMemo(airticketOrderForm.getTxtRemark());//备注
				
				//结算表
				statement.setActualAmount(airticketOrderForm.getTxtTotalAmount());//实收款
				statement.setTotalAmount(airticketOrderForm.getTxtTotalAmount());//总金额（暂时用实收款替代）
				
				airticketOrderAgentAvia.setStatement(statement);
				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			}else if(airticketOrderAgentAvia.getTranType() ==AirticketOrder.TRANTYPE__2)//买入-----航空公司
			{
				//机票订单表
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgentT());//返点
				airticketOrderAgentAvia.setRakeoffCount(airticketOrderForm.getTxtAgent());//月底返点
				airticketOrderAgentAvia.setHandlingCharge(airticketOrderForm.getTxtCharge());//手续费
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());//收退票手续费
				airticketOrderAgentAvia.setMemo(airticketOrderForm.getTxtRemark());//备注
				//结算表
				statement.setActualAmount(airticketOrderForm.getTxtTAmount());//实付款
				statement.setCommission(airticketOrderForm.getTxtAgentFeeTeams());//现返佣金
				statement.setTotalAmount(airticketOrderForm.getTxtTAmount());//总金额（暂时用实付款替代）
				statement.setRakeOff(airticketOrderForm.getTxtAgentFeeCarrier());//后返佣金---月底返代理费
				
				airticketOrderAgentAvia.setStatement(statement);
				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			}else if(airticketOrderAgentAvia.getTranType() ==AirticketOrder.TRANTYPE_3)//退票-----航空公司
			{
				//机票订单表
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgentT());//返点
				airticketOrderAgentAvia.setRakeoffCount(airticketOrderForm.getTxtAgent());//月底返点
				airticketOrderAgentAvia.setHandlingCharge(airticketOrderForm.getTxtCharge());//手续费
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());//收退票手续费
				airticketOrderAgentAvia.setMemo(airticketOrderForm.getTxtRemark());//备注
				//结算表
				statement.setActualAmount(airticketOrderForm.getTxtTAmount());//实付款
				statement.setCommission(airticketOrderForm.getTxtAgentFeeTeams());//现返佣金
				statement.setTotalAmount(airticketOrderForm.getTxtTAmount());//总金额（暂时用实付款替代）
				statement.setRakeOff(airticketOrderForm.getTxtAgentFeeCarrier());//后返佣金---月底返代理费
				
				airticketOrderAgentAvia.setStatement(statement);
				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			}
		}
	}
	
	
	
	//修改图队利润(改 结算表)
	public void updateTeamStatement(AirticketOrder airticketOrderForm,
			UserRightInfo uri,HttpServletRequest request, HttpServletResponse response) throws AppException {
			long statementId =airticketOrderForm.getStatementId();
			Statement statement = statementDAO.getStatementById(statementId);
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderBystatementId(statementId);
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
			ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
			ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
			ticketLog.setSysUser(uri.getUser());//操作员
			ticketLog.setIp(request.getRemoteAddr());//IP					 
			ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
			ticketLog.setType(TicketLog.TYPE_35);
			ticketLog.setStatus(1L);
			ticketLogDAO.save(ticketLog);
	}
	
	
	//显示订单详细信息
	public AirticketOrder viewAirticketOrderPage(String aircketOrderId,String groupMarkNo,String tranType,
			HttpServletRequest request, HttpServletResponse response) throws AppException {
		AirticketOrder airticket=new AirticketOrder();
		List<AirticketOrder> airticketOrderList = new ArrayList<AirticketOrder>();
		List<AirticketOrder> airticketList =airticketOrderDAO.getAirticketOrderListByGroupMarkNo(groupMarkNo);
		AirticketOrder air1 = new AirticketOrder();
		AirticketOrder air2 = new AirticketOrder();
		for(int i=0;i<airticketList.size();i++)
		{
			 airticket= airticketList.get(i);
			if(Long.parseLong(tranType) == AirticketOrder.TRANTYPE__2 || Long.parseLong(tranType) == AirticketOrder.TRANTYPE__1)//买入/卖出
			{
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE__2 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
				{
					air1=airticket;
				}
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE__1 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
				{
					air2=airticket;
				}
			}
			if(Long.parseLong(tranType) == AirticketOrder.TRANTYPE_3)//退票
			{
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE_3 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
				{
					air1=airticket;
				}
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE_3 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
				{
					air2=airticket;
				}
			}
			if(Long.parseLong(tranType) == AirticketOrder.TRANTYPE_5)//改签
			{
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE_5 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
				{
					air1=airticket;
				}
				if(airticket.getTranType() ==AirticketOrder.TRANTYPE_5 && airticket.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
				{
					air2=airticket;
				}
			}
		}
		airticketOrderList.add(air1);
		airticketOrderList.add(air2);
		
		List<Passenger> passengerList = passengerDAO.listByairticketOrderId(Long.parseLong(aircketOrderId));
		
		List<Flight> flightList = flightDAO.getFlightListByOrderId(Long.parseLong(aircketOrderId));
		request.setAttribute("airticketOrderList", airticketOrderList);
		request.setAttribute("flightList", flightList);
		request.setAttribute("passengerList", passengerList);
		return airticket;
	}
	
	//------dwr
	public AirticketOrder getAirticketOrderByMarkNo(String markNo,String tranType) throws AppException{
		
		AirticketOrder ao=new AirticketOrder();
		List airticketOrderList= airticketOrderDAO.listBygroupMarkNo(markNo,tranType);
		 if(airticketOrderList!=null&&airticketOrderList.size()>0){
			 ao=(AirticketOrder)airticketOrderList.get(0);
			System.out.println("ao"+ao.getStatement().getTotalAmount());
		 }
		 return ao;
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

	//B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf) throws AppException
	{
		return airticketOrderDAO.b2cAirticketOrderList(rlf);
	}
	
	//团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf) throws AppException
	{
		return airticketOrderDAO.teamAirticketOrderList(rlf);
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

	//根据订单组编号返加List集合
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo) throws AppException
	{
		return airticketOrderDAO.getAirticketOrderListByGroupMarkNo(groupMarkNo);
	}
	//根据结算ID查询
	public AirticketOrder getAirticketOrderBystatementId(long statementId) throws AppException
	{
		return airticketOrderDAO.getAirticketOrderBystatementId(statementId);
	}
	// 根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderBysubPnr(subPnr);
	}
	
	//根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String  subPnr,long businessType,long tranType) throws AppException{
		return airticketOrderDAO.getAirticketOrderForRetireUmbuchen(subPnr, businessType, tranType);
	}
	
	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException{
		return airticketOrderDAO.getListByOrder(airticketOrder);
	}
	
	public boolean checkPnrisToday(AirticketOrder airticketOrder)throws AppException{
		return airticketOrderDAO.checkPnrisToday(airticketOrder);
	}
	
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)throws AppException{
		 return airticketOrderDAO.checkPnrisMonth(airticketOrder);
	}
	
	public AirticketOrder getAirticketOrderByGroupMarkNor(String  groupMarkNo,long tranType) throws AppException{
		return airticketOrderDAO.getAirticketOrderByGroupMarkNor(groupMarkNo, tranType);
	}

	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType)throws AppException{
		return airticketOrderDAO.getAirticketOrderListByPNR(subPnr, tranType);
	}
	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}
	
	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public void setPlatComAccountDAO(PlatComAccountDAO platComAccountDAO) {
		this.platComAccountDAO = platComAccountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

}

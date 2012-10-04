package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.StatisticsOrderListForm;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.StatisticsOrderDAO;
import com.fdays.tsms.policy.SaleStatistics;
import com.neza.exception.AppException;

public class StatisticsOrderBizImp implements StatisticsOrderBiz {
	public static int currentCount = 0;					//用于创建后返报表对象时的进度条显示，记录目前已经创建好信息条数
	private TransactionTemplate transactionTemplate;
	private StatisticsOrderDAO statisticsOrderDAO;
	private AirticketOrderDAO airticketOrderDAO;
	

	/**
	 * 根据ID删除StatisticsOrder对象
	 */
	public boolean deleteStatisticsOrder(long id) throws AppException {
		return statisticsOrderDAO.deleteById(id);
	}

	/**
	 * 删除所有
	 * @return
	 */
	public boolean deleteAll() {
		return statisticsOrderDAO.deleteAll();
	}

	/**
	 * 获取所有StatisticsOrder对象，并支持分页
	 */
	public List<StatisticsOrder> getStatisticsOrder(StatisticsOrderListForm solf)
			throws AppException {
		return statisticsOrderDAO.listStatisticsOrder(solf);
	}

	/**
	 * 根据ID获取StatisticsOrder对象
	 */
	public StatisticsOrder getStatisticsOrderById(long id) throws AppException {
		return statisticsOrderDAO.queryById(id);
	}

	/**
	 * 增加或更新StatisticsOrder对象
	 */
	public void saveOrUpdate(StatisticsOrder statisticsOrder)
			throws AppException {
		statisticsOrderDAO.saveOrUpdate(statisticsOrder);
	}

	/**
	 * 批量插入、修改
	 * @param soList
	 * @return
	 */
	public boolean batchSaveOrUpdate(List<StatisticsOrder> soList) throws AppException{
		
		return statisticsOrderDAO.batchSaveOrUpdate(soList);
	}
	
	/**
	 * 修改StatisticsOrder
	 */
	public void update(StatisticsOrder statisticsOrder) throws AppException{
		statisticsOrderDAO.update(statisticsOrder);
	}
	
	/**
	 * 根据SaleStatistics获取所有StatisticsOrder对象
	 */
	public List<StatisticsOrder> listBySaleStatistics(
			SaleStatistics saleStatistics) throws AppException {
		return statisticsOrderDAO.listBySaleStatistics(saleStatistics);
	}

	/**
	 * 创建后返报表信息
	 * @param saleStatistics
	 * @return
	 * @throws AppException
	 */
	public List<StatisticsOrder> createStatistics(SaleStatistics saleStatistics,int startRow,int rowCount)
			throws AppException {
		StringBuffer startEnd ;					//起止城市
		StringBuffer passengerName ;			//乘客姓名
		StringBuffer ticketNumber;				//票号
		StringBuffer boardingTime ;				//乘机时间
		StringBuffer flightCode;				//航班号
		StatisticsOrder so;
		List<StatisticsOrder> soList = new ArrayList<StatisticsOrder>();
		List<AirticketOrder> airticketOrderList = new ArrayList<AirticketOrder>();
		
		airticketOrderList = airticketOrderDAO.listByCarrier(saleStatistics.getCarrier(),saleStatistics.getBeginDate(),
				saleStatistics.getEndDate(),startRow,rowCount);
		for(AirticketOrder ao : airticketOrderList){
			so = new StatisticsOrder();
			boardingTime = new StringBuffer();							//起飞时间
			Set<Flight> flightSet = ao.getFlights();					//订单机票集合
			Set<Passenger> passengerSet = ao.getPassengers();			//订单乘机人员集合
			passengerName = new StringBuffer();							//乘客姓名
			startEnd = new StringBuffer();								//起止城市
			ticketNumber = new StringBuffer();							//票号
			flightCode = new StringBuffer();							//航班号
			for(Passenger p : passengerSet){
				passengerName.append(p.getName()+"、");
				ticketNumber.append(p.getTicketNumber()+"、");
			}
			for(Flight f : flightSet){
				startEnd.append(f.getStartPoint()+"-"+f.getEndPoint()+"、");		
				boardingTime.append(f.getBoardingTime().toString().replaceAll(" ","/")+"、");
				flightCode.append(f.getFlightCode()+"、");
			}
			so.setOrderNo(ao.getOrderNo());									//流水号?
			so.setFlightCode(subComma(flightCode.toString()));
			so.setStartEnd(subComma(startEnd.toString()));					//起止城市
			so.setBoardingTime(subComma(boardingTime.toString()));			//起飞时间
			so.setPassengerName(subComma(passengerName.toString()));		//乘客姓名			
			so.setTicketNumber(subComma(ticketNumber.toString()));			//票号
			so.setTotalAmount(ao.getTicketPrice());							//票面价
			so.setProfit(ao.getProfit());									//利润
			so.setRate(ao.getRateAfter());									//后返政策
			so.setProfitAfter(ao.getProfitAfter());							//后返利润
			so.setTranType(ao.getTranType());
			so.setGroupId(ao.getOrderGroup().getId());
			so.setSaleStatistics(saleStatistics);
			soList.add(so);
			currentCount = startRow+soList.size();
		}
			
			
		return soList;
	}
	

	/**
	 * 获取总记录条数
	 */
	public int getTotalCount() throws AppException{
		return statisticsOrderDAO.getRowCount();
	}

	
	/**
	 * 去除结尾处的中文顿号
	 * @param source
	 * @return
	 */
	private String subComma(String source){
		if(source == null || "".equals(source)){
			return "*";
		}
		if(source.endsWith("、")){
			source = source.substring(0,source.lastIndexOf("、"));
		}
		return source;
	}

	//-----------------------set get---------------------------------//
	public void setStatisticsOrderDAO(StatisticsOrderDAO statisticsOrderDAO) {
		this.statisticsOrderDAO = statisticsOrderDAO;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}
}

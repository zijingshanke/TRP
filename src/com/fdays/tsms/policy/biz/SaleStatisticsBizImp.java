package com.fdays.tsms.policy.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.fdays.tsms.policy.dao.SaleStatisticsDAO;
import com.neza.exception.AppException;

/**
 * SaleStatistics业务接口实现类
 * @author chenqx 
 * 2010-12-24
 */
public class SaleStatisticsBizImp implements SaleStatisticsBiz
{
	private AirticketOrderDAO airticketOrderDAO;
	private SaleStatisticsDAO saleStatisticsDAO;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;
	private TransactionTemplate transactionTemplate;

	// 根据ID删除SaleStatistics对象
	public long deleteSaleStatistics(long id) throws AppException{
		try{
			saleStatisticsDAO.deleteById(id);
		}
		catch (RuntimeException e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 动态参数获取对象（支持并分页）
	public List getSaleStatistics(SaleStatisticsListForm sslf)
	    throws AppException{
		return saleStatisticsDAO.list(sslf);
	}

	// 根据ID获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsById(long id)
	    throws AppException{
		return saleStatisticsDAO.getSaleStatisticsById(id);
	}

	// 保存或更改对象
	public void save(SaleStatistics saleStatistics) throws AppException{
		saleStatisticsDAO.save(saleStatistics);
	}

	// 修改对象
	public void update(SaleStatistics saleStatistics){
		try{
			saleStatisticsDAO.update(saleStatistics);
		}
		catch (AppException e){
			System.out.println(e.getMessage());
		}
	}
	
	// 获取所有对象
	public List<SaleStatistics> listSaleStatistics(){
		try{
			return saleStatisticsDAO.list();
		}
		catch (AppException e){
			e.printStackTrace();
		}
		return new ArrayList<SaleStatistics>();
	}
	
//	/**
//	 * 分段下载后返报表
//	 */
//	public ArrayList<ArrayList<Object>> downloadStatistics(SaleStatistics saleStatistics)
//			throws AppException {
//		boolean flag = true;
//		
//		ArrayList<ArrayList<Object>> statisticsList = new ArrayList<ArrayList<Object>>();
//		ArrayList<Object> title = new ArrayList<Object>();		//标题
//		title.add("流水号");
//		title.add("航班号");
//		title.add("起止城市");
//		title.add("乘机时间");
//		title.add("乘客");
//		title.add("票号");
//		title.add("订单金额");
//		title.add("利润");
//		title.add("后返政策");
//		title.add("后返利润");
//		title.add("tranType");
//		title.add("orderGroupId");
//		statisticsList.add(title);
//		ArrayList<Object> content;				//内容
//		StringBuffer startEnd ;					//起止城市
//		StringBuffer passengerName ;			//乘客姓名
//		StringBuffer ticketNumber;				//票号
//		StringBuffer boardingTime ;				//乘机时间
//		StringBuffer flightCode;				//航班号
//		List<AirticketOrder> airticketOrderList = new ArrayList<AirticketOrder>();
//		int startRow = 0;
//		int rowCount = 1000;
//		while(flag){
//			System.out.println("从第"+startRow+"开始");
//			long start = System.currentTimeMillis();
//			if(startRow == 0 || airticketOrderList.size() >= rowCount){
//				airticketOrderList = airticketOrderDAO.listByCarrier(saleStatistics.getCarrier(),saleStatistics.getBeginDate(),
//						saleStatistics.getEndDate(),startRow,rowCount);
//				
//				for(AirticketOrder ao : airticketOrderList){
//					content = new ArrayList<Object>();
//					boardingTime = new StringBuffer();							//起飞时间
//					Set<Flight> flightSet = ao.getFlights();					//订单机票集合
//					Set<Passenger> passengerSet = ao.getPassengers();			//订单乘机人员集合
//					passengerName = new StringBuffer();							//乘客姓名
//					startEnd = new StringBuffer();								//起止城市
//					ticketNumber = new StringBuffer();							//票号
//					flightCode = new StringBuffer();							//航班号
//					
//					for(Passenger p : passengerSet){
//						passengerName.append(p.getName()+"，");
//						ticketNumber.append(p.getTicketNumber()+"，");
//					}
//					for(Flight f : flightSet){
//						startEnd.append(f.getStartPoint()+"-"+f.getEndPoint()+"，");		
//						boardingTime.append(f.getBoardingTime().toString().replaceAll(" ","/")+"，");
//						flightCode.append(f.getFlightCode()+"，");
//					}
//					content.add(ao.getOrderNo());								//流水号?
//					content.add(subComma(flightCode.toString()));
//					content.add(subComma(startEnd.toString()));					//起止城市
//					content.add(subComma(boardingTime.toString()));				//起飞时间
//					content.add(subComma(passengerName.toString()));			//乘客姓名			
//					content.add(subComma(ticketNumber.toString()));				//票号
//					content.add(ao.getTotalAmount());							//ao.getTicketPrice();?
//					content.add(ao.getProfit());
//					content.add(0);												//后返政策
//					content.add(ao.getProfitAfter());							//后返利润
//					content.add(ao.getTranType());
//					content.add(ao.getOrderGroup().getId());
//					
//					statisticsList.add(content);
//				}
//				long end = System.currentTimeMillis();
//				System.out.println("第"+startRow+"开始所用时间"+(end-start));
//				startRow = startRow+rowCount;
//			}else{
//				flag = false;
//			}
//		}
//		
//		return statisticsList;
//	}
	
	/**
	 * 去除结尾处的中文逗号
	 * @param source
	 * @return
	 */
	private String subComma(String source){
		if(source == null || "".equals(source)){
			return "*";
		}
		if(source.endsWith("，")){
			source = source.substring(0,source.lastIndexOf("，"));
		}
		return source;
	}

	// ------------------------------------set get----------------------------//

	public TransactionTemplate getTransactionTemplate(){
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate){
		this.transactionTemplate = transactionTemplate;
	}

	public void setSaleStatisticsDAO(SaleStatisticsDAO saleStatisticsDAO) {
		this.saleStatisticsDAO = saleStatisticsDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

}

package com.fdays.tsms.policy.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.SaleResult;
import com.fdays.tsms.policy.dao.AirlinePolicyAfterDAO;
import com.neza.exception.AppException;

/**
 * AirlinePolicyAfter业务接口实现类
 * 
 * @author chenqx 2010-12-10
 */
public class AirlinePolicyAfterBizImp implements AirlinePolicyAfterBiz
{

	private AirlinePolicyAfterDAO airlinePolicyAfterDAO;
	private TransactionTemplate transactionTemplate;

	// 根据ID删除AirlinePolicyAfter对象
	public long deleteAirlinePolicyAfter(long id) throws AppException
	{
		try
		{
			airlinePolicyAfterDAO.deleteById(id);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 动态参数获取对象（支持并分页）
	public List getAirlinePolicyAfter(AirlinePolicyAfterListForm apalf)
	    throws AppException
	{
		return airlinePolicyAfterDAO.list(apalf);
	}

	// 根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAirlinePolicyAfterById(long id)
	    throws AppException
	{
		return airlinePolicyAfterDAO.getAirlinePolicyAfterById(id);
	}

	// 保存或更改对象
	public void save(AirlinePolicyAfter airlinePolicyAfter) throws AppException
	{
		airlinePolicyAfterDAO.save(airlinePolicyAfter);
	}

	// 修改对象
	public void update(AirlinePolicyAfter airlinePolicyAfter)
	{
		try
		{
			airlinePolicyAfterDAO.update(airlinePolicyAfter);
		}
		catch (AppException e)
		{
			System.out.println(e.getMessage());
		}

	}

	// 获取所有对象
	public List<AirlinePolicyAfter> listAirlinePolicyAfter()
	{
		try
		{
			return airlinePolicyAfterDAO.list();
		}
		catch (AppException e)
		{
			e.printStackTrace();
		}
		return new ArrayList<AirlinePolicyAfter>();
	}

	// 根据承运人获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAppropriatePolicy(String carrier)
	{

		return airlinePolicyAfterDAO.getAppropriatePolicy(carrier);
	}
	
	/**
	 * 获取指定订单的后返情况
	 * @param apa
	 * @param order
	 * @param saleTotalAmount
	 * @return
	 * @throws AppException
	 */
	public SaleResult getSaleResultByOrder(AirlinePolicyAfter apa,
			AirticketOrder order, BigDecimal saleTotalAmount) throws AppException
	{
		SaleResult sr = new SaleResult();
		BigDecimal profits = BigDecimal.ZERO;
		if (!apa.getCarrier().equalsIgnoreCase(order.getCyr()))
		{
			profits = BigDecimal.ZERO;
		}
		else
		{
			Set<Flight> flights = order.getFlights();
			for (Flight flight : flights)
			{
				if (flights != null && flights.size() == 1) // 先考虑只有一个航班的情况，
				{
					PolicyAfter pa = getFitPolicyAfter(apa, flight, saleTotalAmount);
					BigDecimal rate = pa.getRate();
					BigDecimal ticketTotalPrice = order.getTicketPrice().multiply(BigDecimal.valueOf(order.getPassengerSize()));
					if (rate.compareTo(BigDecimal.ZERO) > 0)
					{
						profits = order.getTicketPrice().multiply(rate).divide(
						    BigDecimal.valueOf(100));
						sr.addAfterAmounts(ticketTotalPrice.multiply(rate).divide(BigDecimal.valueOf(100)));
						sr.setRateAfter(rate);
						sr.addSaleAmount(order.getAirportPrice());
						sr.addTicketNums(order.getPassengerSize());
						
					}
				}
				else
				{
					// 两个航班是往返和中转航班，后面再考虑

				}
			}
		}
		
		return sr;
	}
	


	/**
	 * 获取指定订单的指标情况
	 * @param apa
	 * @param order
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public SaleResult getSaleResultByOrder(AirlinePolicyAfter apa,
			AirticketOrder order) throws AppException {
		SaleResult sr = new SaleResult();
		Set<Flight> flightSet = order.getFlights();
		if(flightSet != null && flightSet.size() == 1){		// 先考虑只有一个航班的情况，
			for(Flight flight : flightSet){
				IndicatorStatistics is = getRightIndicatorStatistics(apa, flight);
				BigDecimal ticketTotalPrice = order.getTicketPrice().multiply(BigDecimal.valueOf(order.getPassengerSize()));
				if(is != null){
					if(is.getIsAmount() == 1){				//是计量额
						sr.addAmounts(ticketTotalPrice);
					}
					if(is.getIsAward() == 1){				//是计奖额
						sr.addAwardAmounts(ticketTotalPrice);
					}
					if(is.getIsHighClass() == 1){			//是高舱票
						sr.addHighClassTicketNums(BigDecimal.ONE);
					}
				}
			}
		}else{												//两个航班以上的情况
			
		}
		
		return sr;
	}
	
	/**
	 * 获取适合指定机票的指标计算政策
	 * @param apa
	 * @param flight
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IndicatorStatistics getRightIndicatorStatistics(AirlinePolicyAfter apa, Flight flight) throws AppException{
		if (!apa.getCarrier().equalsIgnoreCase(flight.getCyr())) {
			return null; 
		}
		Set<IndicatorStatistics> indicatorStatisticses = apa.getIndicatorStatisticss();
		for(IndicatorStatistics is : indicatorStatisticses){
			if(is.agreeFlightCode(flight.getFlightCode())){
				if(is.agreeFlightCodeExcept(flight.getFlightCode())){
					if(is.agreeFlightPoint(flight.getStartPoint() + "-" + flight.getEndPoint())){
						if(is.agreeFlightPointExcept(flight.getStartPoint() + "-" + flight.getEndPoint())){
							if(is.agreeFlightClass(flight.getFlightClass())){
								if(is.agreeFlightClassExcept(flight.getFlightClass())){
									if(is.agreeDate(flight.getBoardingTime())){
										return is;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return null;
	}



	/**
	 * 获取适合指定机票的后返政策
	 * @param apa
	 * @param flight
	 * @param saleTotalAmount
	 * @return
	 * @throws AppException
	 */
	private PolicyAfter getFitPolicyAfter(AirlinePolicyAfter apa, Flight flight,
	    BigDecimal saleTotalAmount) throws AppException
	{
		if (!apa.getCarrier().equalsIgnoreCase(flight.getCyr())) {
			return new PolicyAfter(); 
		}
		Set<PolicyAfter> policyAfters = apa.getPolicyAfters();
		for (PolicyAfter pa : policyAfters)
		{
			if (pa.agreeDate(flight.getBoardingTime())) // 在有效时间内
			{
				if (pa.agreeStartEndExcept(flight.getStartPoint() + "-"
				    + flight.getEndPoint())) // 不在限制的航段外
				{
					if (pa.agreeStartEnd(flight.getStartPoint() + "-"
					    + flight.getEndPoint())) // 符合航段
					{
						if (pa.agreeFlightCodeExcept(flight.getFlightCode())) // 不在限制的航班外
						{
							if (pa.agreeFlightCode(flight.getFlightCode())) // 航班号符合政策后返
							{
								if (pa.agreeFlightClassExcept(flight.getFlightClass())) // 不在限制的舱位外
								{
									if (pa.agreeFlightClass(flight.getFlightClass())) // 舱位符合政策后返
									{
//										if (pa.agreeDiscount(flight.getFlightClass())) // 折扣符合政策后返
//										{
//											 System.out.println("AirlinePolicyAfterBiz.rateAfterByFlight: "+saleTotalAmount+"---"+apa.getQuota());
											if (saleTotalAmount.compareTo(apa.getQuota()) >= 0 || saleTotalAmount.intValue() == 0)
											{ // 任务额度
												return pa;
											}
											else
											{
												 System.out.println("任务未完成");
											}
//										}
									}
								}
							}
						}
					}
				}
			}
		}
		return new PolicyAfter();
	}
	
	// ------------------------------------set get----------------------------//

	public void setAirlinePolicyAfterDAO(
	    AirlinePolicyAfterDAO airlinePolicyAfterDAO)
	{
		this.airlinePolicyAfterDAO = airlinePolicyAfterDAO;
	}

	public TransactionTemplate getTransactionTemplate()
	{
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate)
	{
		this.transactionTemplate = transactionTemplate;
	}

}

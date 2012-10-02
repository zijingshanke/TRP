package com.fdays.tsms.airticket.biz;

import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.airticket.util.GDSUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.neza.exception.AppException;

public class TempPNRBizImp implements TempPNRBiz {
	private LogUtil myLog;

	// 根据pnr获取定单信息
	public TempPNR getTempPNRByPnr(String pnr) throws AppException {
		myLog = new AirticketLogUtil(true, false, TempPNRBizImp.class, "");

		TempPNR tempPNR = new TempPNR();// PRN
		tempPNR = GDSUtil.getTempPNRByGDSInterface(pnr, tempPNR);
		
		return tempPNR;
	}

	/*
	 * public Long savePNR(String pnr) throws AppException{ TempPNRBizImp tf=new
	 * TempPNRBizImp(); TempPNR tempPNR =tf.getTempPNRByPnr(pnr); Long check=0L;
	 * if (tempPNR != null && tempPNR.getRt_parse_ret_value()!=0L) {
	 * AirticketOrder airticketOrder=airticketOrderDAO.airticketOrderByPNR(pnr);
	 * if(airticketOrder!=null&&airticketOrder.getId()>0){ check=2L;//已存在 }else{
	 * //机票订单 AirticketOrder ao=new AirticketOrder();
	 * //ao.setDrawPnr(tempPNR.getPnr());//出票pnr
	 * ao.setSubPnr(tempPNR.getPnr());//预订pnr
	 * ao.setBigPnr(tempPNR.getB_pnr());//大pnr ao.setAgent(new Agent());
	 * ao.setStatement(new Statement());
	 * ao.setTicketPrice(tempPNR.getFare());//票面价格 airticketOrderDAO.save(ao);
	 * 
	 * //乘机人 List<TempPassenger>
	 * tempPassengerList=tempPNR.getTempPassengerList(); for(TempPassenger
	 * tempPassenger:tempPassengerList){ Passenger passenger=new Passenger();
	 * passenger.setAirticketOrder(ao);
	 * passenger.setName(tempPassenger.getName());
	 * passenger.setCardno(tempPassenger.getNi()); passengerDAO.save(passenger); }
	 * 
	 * //航班 List<TempFlight> tempFlightList=tempPNR.getTempFlightList();
	 * for(TempFlight tempFlight: tempFlightList){ Flight flight=new Flight();
	 * flight.setAirticketOrder(ao);
	 * flight.setFlightCode(tempFlight.getAirline());//航班号
	 * flight.setStartPoint(tempFlight.getDepartureCity()); //出发地
	 * flight.setEndPoint(tempFlight.getDestineationCity());//目的地
	 * flight.setBoardingTime(tempFlight.getStarttime());//起飞时间
	 * flight.setStatus(1L); flightDAO.save(flight); check=ao.getId();//添加成功 } }
	 * 
	 * }else{ check=0L;//PNR 错误 System.out.println("PNR 错误"); } return check; }
	 */

	public static void main(String[] args) {
		TempPNRBizImp tf = new TempPNRBizImp();
		try {
			LogUtil myLog = new AirticketLogUtil(true, false,
					AirticketOrderBizImp.class, "");
			myLog.info("444");
			myLog.info("666");
			// TBP3G EEV53 QWNH0 N2R5D
			tf.getTempPNRByPnr("SNRPJ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

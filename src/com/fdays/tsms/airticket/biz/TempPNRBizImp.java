package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import com.fdays.tsms.airticket.AirlineStore;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.airticket.util.FdaysGDSUtil;
import com.fdays.tsms.airticket.util.GDSUtil;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.neza.exception.AppException;

/**
 * @author yanrui
 */
public class TempPNRBizImp implements TempPNRBiz {
	private LogUtil myLog;

	/**
	 * 通过黑屏信息解析机票订单数据
	 */
	public TempPNR getTempPNRByBlackInfo(String blackInfo) throws AppException {
		TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(blackInfo,
				ParseBlackUtil.Type_Content);// 黑屏解析 PNR、乘客、行程

		tempPNR = AirlineStore.setTempPNRPrice(tempPNR);// 调用内部数据设置票价折扣

		tempPNR = getChildPrice(tempPNR);// 设置儿童价

		ParseBlackUtil.printTempPNRInfo(tempPNR);

		return tempPNR;
	}

	/**
	 * 适用儿童票价（儿童票是否为单独的PNR）
	 */
	public static TempPNR getChildPrice(TempPNR tempPNR) throws AppException {
		try{
			if (tempPNR.getPassengers_count() != null
					&& tempPNR.getPassengers_count() > 0) {
				TempPassenger tempPassenger = tempPNR.getTempPassengerList().get(0);
				if (tempPassenger != null) {
					String passengerName = tempPassenger.getName();
					if (passengerName != null && "".equals(passengerName) == false) {
						int flag = passengerName.toUpperCase().indexOf("CHD");
						if (flag > 0) {
							tempPNR.setFare(tempPNR.getFare().divide(
									new BigDecimal(2),3));
							tempPNR.setTax(BigDecimal.ZERO);// 机建费
							tempPNR.setYq(tempPNR.getYq()
											.divide(new BigDecimal(2),3));// 燃油税
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}

	public static void main(String[] args) {
		
		System.out.println(new BigDecimal(300).divide(new BigDecimal(3),3));
		
//		TempPNRBizImp tf = new TempPNRBizImp();
//		try {
//			// LogUtil myLog = new AirticketLogUtil(true,
//			// false,AirticketOrderBizImp.class, "");
//			// // TBP3G EEV53 QWNH0 N2R5D
//			// tf.getTempPNRByPnr("SNRPJ");
//
//			String blackInfo = "E:\\tsms\\doc\\PNRSample\\BlackSample18.txt";
//
//			TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(blackInfo,
//					ParseBlackUtil.Type_Path);// 黑屏解析 PNR、乘客、行程
//			// tempPNR = IBEUtil.setTicketPriceByIBEInterface(tempPNR);// IBE接口
//			// // 基准票价、燃油、机建
//			// tempPNR = CabinUtil.setTicketPriceByIBEInterface(tempPNR);// 品尚接口
//			// 舱位折扣
//
//			// tempPNR = setPricePolicy(tempPNR);
//			ParseBlackUtil.printTempPNRInfo(tempPNR);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 设置票价
	 */
	public static TempPNR setPricePolicy(TempPNR tempPNR) throws AppException {
		try{
		BigDecimal standPrice = tempPNR.getFare();
		String discountString = "100";

		if (tempPNR.getTempFlightList() != null
				&& tempPNR.getTempFlightList().get(0) != null
				&& tempPNR.getTempFlightList().get(0).getDiscount() != null) {
			discountString = tempPNR.getTempFlightList().get(0).getDiscount()
					.trim();
			if ("0".equals(discountString) || "".equals(discountString)) {
				discountString = "100";
			}
		}

		BigDecimal discount = new BigDecimal(discountString);

		if (standPrice != null && "".equals(standPrice) == false
				&& discount != null && "".equals(discount) == false) {
//			System.out.println("基准价：" + standPrice);
			discount = discount.divide(new BigDecimal(100),3);
			standPrice = standPrice.multiply(discount);
			tempPNR.setFare(standPrice);
//			System.out.println("折扣：" + discount + "--折后价：" + standPrice);
		}

		tempPNR = getChildPrice(tempPNR);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}

	// GDS接口 根据PNR获取订单信息
	public TempPNR getTempPNRByPnr(String pnr) throws AppException {
		myLog = new AirticketLogUtil(true, false, TempPNRBizImp.class, "");

		TempPNR tempPNR = new TempPNR();// PNR
		tempPNR = GDSUtil.getTempPNRByGDSInterface(pnr, tempPNR);

		return tempPNR;
	}

	// 旧系统接口 根据PNR获取订单信息
	public TempPNR getTempPNRByOldSystem(String pnr) throws AppException {
		myLog = new AirticketLogUtil(true, false, TempPNRBizImp.class, "");
		TempPNR tempPNR = FdaysGDSUtil.getTempPNRByFdaysGDSInterface(pnr);
		return tempPNR;
	}
}

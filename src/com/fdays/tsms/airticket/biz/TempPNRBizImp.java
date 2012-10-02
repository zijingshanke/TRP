package com.fdays.tsms.airticket.biz;

import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.airticket.util.CabinUtil;
import com.fdays.tsms.airticket.util.FdaysGDSUtil;
import com.fdays.tsms.airticket.util.GDSUtil;
import com.fdays.tsms.airticket.util.IBEUtil;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.neza.exception.AppException;

public class TempPNRBizImp implements TempPNRBiz {
	private LogUtil myLog;
	
	
	/**
	 * 通过黑屏信息解析机票订单数据
	 */
	public TempPNR getTempPNRByBlackInfo(String blackInfo)
	    throws AppException
	{
		TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(blackInfo,
		    ParseBlackUtil.Type_Content);//黑屏解析 PNR、乘客、行程
		tempPNR = IBEUtil.setTicketPriceByIBEInterface(tempPNR);//IBE接口 基准票价、燃油、机建
		tempPNR = CabinUtil.setTicketPriceByIBEInterface(tempPNR);//品尚接口 舱位折扣
		return tempPNR;
	}
	
	

	//GDS接口 根据PNR获取订单信息
	public TempPNR getTempPNRByPnr(String pnr) throws AppException {
		myLog = new AirticketLogUtil(true, false, TempPNRBizImp.class, "");

		TempPNR tempPNR = new TempPNR();// PNR
		tempPNR = GDSUtil.getTempPNRByGDSInterface(pnr, tempPNR);
		
		return tempPNR;
	}

	//旧系统接口 根据PNR获取订单信息
	public TempPNR getTempPNRByOldSystem(String pnr) throws AppException {
		myLog = new AirticketLogUtil(true, false, TempPNRBizImp.class, "");
		TempPNR tempPNR = FdaysGDSUtil.getTempPNRByFdaysGDSInterface(pnr);		
		return tempPNR;
	}	
	


	public static void main(String[] args) {
		TempPNRBizImp tf = new TempPNRBizImp();
		try {
			LogUtil myLog = new AirticketLogUtil(true, false,
					AirticketOrderBizImp.class, "");
			// TBP3G EEV53 QWNH0 N2R5D
			tf.getTempPNRByPnr("SNRPJ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

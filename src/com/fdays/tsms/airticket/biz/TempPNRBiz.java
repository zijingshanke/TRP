package com.fdays.tsms.airticket.biz;

import com.fdays.tsms.airticket.TempPNR;
import com.neza.exception.AppException;

public interface TempPNRBiz {
	public TempPNR getTempPNRByBlackInfo(String blackInfo) throws AppException;

	public TempPNR getTempPNRByPnr(String pnr) throws AppException;

	// 旧系统接口 根据PNR获取订单信息
	public TempPNR getTempPNRByOldSystem(String pnr) throws AppException;
}

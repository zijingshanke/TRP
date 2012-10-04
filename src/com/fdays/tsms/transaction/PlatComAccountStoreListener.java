package com.fdays.tsms.transaction;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.system.biz.SysInitBiz;

/**
 * 监听
 * 
 * @author YanRui
 */
public class PlatComAccountStoreListener implements Runnable {
	private SysInitBiz sysInitBiz;
	private int type = 0;

	private LogUtil myLog;

	/**
	 * 监听器(默认) SysInitBiz int type 0:全部更新 1：支付工具 2：平台 3：公司 4：帐号 5:客户 6:平台账号 7:用户
	 * 11:航线基准价 12：舱位折扣
	 */
	public PlatComAccountStoreListener(SysInitBiz sysInitBiz, int type) {
		super();
		this.sysInitBiz = sysInitBiz;
		this.type = type;
	}

	public void run() {
		myLog = new AirticketLogUtil(true, true,
				PlatComAccountStoreListener.class, "");
		try {
			myLog.info("----------------------run() begin----");
			if (type == 0) {
				sysInitBiz.initPlatComAccountStore();
				myLog.info("initPlatComAccountStore success!");
			} else if (type == 1) {
				sysInitBiz.updatePCAStore_PaymentTool();
				myLog.info("updatePCAStore_PaymentTool success!");
			} else if (type == 2) {
				sysInitBiz.updatePCAStore_Platform();
				myLog.info("updatePCAStore_Platform success!");
			} else if (type == 3) {
				sysInitBiz.updatePCAStore_Company();
				myLog.info("updatePCAStore_Company success!");
			} else if (type == 4) {
				sysInitBiz.updatePCAStore_Account();
				myLog.info("updatePCAStore_Account success!");
			} else if (type == 5) {
				sysInitBiz.updatePCAStore_Agent();
				myLog.info("updatePCAStore_Agent success!");
			} else if (type == 6) {
				sysInitBiz.initPlatComAccountStore();
				myLog.info("initPlatComAccountStore success!");
			} else if (type == 7) {
				sysInitBiz.updateUserStore();
				myLog.info("updateUserStore success!");
			} else if (type == 11) {
				sysInitBiz.updateAirline();
				myLog.info("updateAirline success!");
			} else if (type == 12) {
				sysInitBiz.updateAirlinePlace();
				myLog.info("updateAirlinePlace success!");
			} 
			else {
				myLog.info("未定义的监听类型");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			myLog.error(e.getMessage());
		}
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	public void setType(int type) {
		this.type = type;
	}

}
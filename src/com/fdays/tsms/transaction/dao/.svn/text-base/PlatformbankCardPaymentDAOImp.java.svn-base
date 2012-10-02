package com.fdays.tsms.transaction.dao;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.List;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.PlatformbankCardPaymentListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class PlatformbankCardPaymentDAOImp extends BaseDAOSupport implements
		PlatformbankCardPaymentDAO {
	private LogUtil myLog;
	
	public void createPlaBankCardPayment(PlatformbankCardPaymentListForm pbplistForm)
			throws AppException {
		myLog = new AirticketLogUtil(true, false, PlatformbankCardPaymentDAOImp.class,"");
		Timestamp balanceDate;

		balanceDate = Timestamp.valueOf(DateUtil.getDateString("yyyy-MM-dd HH:mm:ss"));

		try {
			CallableStatement call =
		this.getSessionFactory().getCurrentSession().connection().prepareCall(	"{Call create_plabank_card_payment(?)}");
			
			call.setTimestamp("banlanceDate", balanceDate);
			call.executeUpdate();
			myLog.info("execute procedure create_BANK_CARD_PAYMENT success!");
		} catch (Exception ex) {
			myLog.info("execute procedure fails:" + ex.getMessage());
		}
	}

	// 分页查询
	public List list(PlatformbankCardPaymentListForm pbplistForm)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from PlatformbankCardPayment p where 1=1");
		
		return this.list(hql, pbplistForm);

	}

}

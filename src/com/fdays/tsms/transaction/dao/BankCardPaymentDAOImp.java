package com.fdays.tsms.transaction.dao;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class BankCardPaymentDAOImp extends BaseDAOSupport implements
		BankCardPaymentDAO {
	private LogUtil myLog;

	public void createBankCardPayment(BankCardPaymentListForm bcplistForm,String sessionId)
			throws AppException {
		myLog = new AirticketLogUtil(true, false, BankCardPaymentDAOImp.class,"");
		Timestamp balanceDate;
		// if (alf.getDownloadDate() == null ||
		// alf.getDownloadDate().equals(""))
		// alf.setDownloadDate(com.neza.tool.DateUtil
		// .getDateString("yyyy-MM-dd HH:mm:ss"));

		balanceDate = Timestamp.valueOf(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"));

		try {
			CallableStatement call = this.getSessionFactory()
					.getCurrentSession().connection().prepareCall(
							"{Call create_BANK_CARD_PAYMENT(?,?)}");
			
			// call.setInt("userId", userId);
			call.setTimestamp("banlanceDate", balanceDate);
			call.setString("sessionId", sessionId);
			call.executeUpdate();
			
			myLog.info("execute procedure create_BANK_CARD_PAYMENT success!");
		} catch (Exception ex) {
			myLog.info("execute procedure fails:" + ex.getMessage());
		}
	}

	// 分页查询
	public List list(BankCardPaymentListForm bankCardPaymentListForm)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from BankCardPayment b where 1=1");
		if (bankCardPaymentListForm.getSysName() != null
				&& (!(bankCardPaymentListForm.getSysName().equals("")))) {
			hql.add("and b.userName like '%"
					+ bankCardPaymentListForm.getSysName().trim() + "%'");
		}
		return this.list(hql, bankCardPaymentListForm);

	}

	// 根据id查询
	public BankCardPayment getBankCardPaymentById(long bankCardPaymentId)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from BankCardPayment b where b.id=" + bankCardPaymentId);
		Query query = this.getQuery(hql);
		BankCardPayment bankCardPayment = null;
		if (query != null && query.list() != null) {
			bankCardPayment = (BankCardPayment) query.list().get(0);
		}
		return bankCardPayment;
	}

}

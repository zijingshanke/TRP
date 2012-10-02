package com.fdays.tsms.transaction.dao;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class OptTransactionDAOImp extends BaseDAOSupport implements
		OptTransactionDAO {
	private LogUtil myLog;

	public void createOptTransaction(OptTransactionListForm otf)
			throws AppException {
		myLog = new AirticketLogUtil(true, false, OptTransactionDAOImp.class,
				"");
		Timestamp opt_date;
		// if (alf.getDownloadDate() == null ||
		// alf.getDownloadDate().equals(""))
		// alf.setDownloadDate(com.neza.tool.DateUtil
		// .getDateString("yyyy-MM-dd HH:mm:ss"));

		opt_date = Timestamp.valueOf(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"));

		try {
			CallableStatement call = this.getSessionFactory()
					.getCurrentSession().connection().prepareCall(
							"{Call create_OPT_TRANSACTION(?)}");
			// call.setString("sessionId", sessionId);
			// call.setInt("userId", userId);
			call.setTimestamp("opt_date", opt_date);
			call.executeUpdate();
			
			myLog.info("execute procedure create_OPT_TRANSACTION success!");
		} catch (Exception ex) {
			myLog.info("execute procedure fails:" + ex.getMessage());
		}
	}

	// 分页查询
	public List list(OptTransactionListForm otf) throws AppException {
		Hql hql = new Hql();
		hql.add("from OptTransaction o where 1=1");
		if (otf.getSysName() != null
				&& (!(otf.getSysName().equals("")))) {
			hql.add("and o.userName like '%"
					+ otf.getSysName().trim() + "%'");
		}
		return this.list(hql, otf);

	}


}

package com.fdays.tsms.transaction.dao;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
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
		Timestamp opt_date = Timestamp.valueOf(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"));

		Timestamp beginTimeStr = Timestamp.valueOf(otf.getStartDate()
				+ " 00:00:00.0");
		Timestamp endTimeStr = Timestamp.valueOf(otf.getEndDate() + " 24:00:00.0");	

		try {
			CallableStatement call = this.getSessionFactory()
					.getCurrentSession().connection().prepareCall(
							"{Call create_OPT_TRANSACTION(?,?,?,?,?)}");

			// call.setInt("userId", userId);
			call.setTimestamp("opt_date", opt_date);			
			call.setString("sessionId", otf.getSessionId());
			call.setLong("userDepart", otf.getUserDepart());
			call.setTimestamp("beginTimeStr", beginTimeStr);
			call.setTimestamp("endTimeStr", endTimeStr);
			
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
		if (otf.getSysName() != null && (!(otf.getSysName().equals("")))) {
			hql.add("and o.userName like '%" + otf.getSysName().trim() + "%'");
		}
		if (otf.getSessionId() != null && (!(otf.getSessionId().equals("")))) {
			hql.add("and o.sessionId = '" + otf.getSessionId().trim() + "'");
		}

		return this.list(hql, otf);
	}
}

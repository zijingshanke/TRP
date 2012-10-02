package com.fdays.tsms.system;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.fdays.tsms.transaction.biz.StatementBiz;

public class StatementTaskJob extends QuartzJobBean {
	private StatementBiz statementBiz;

	private boolean runing = false;

	// @Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {

			if (runing) {
				return;
			} else {
				runing = true;
			}
			
			statementBiz.excuteSynOrderStatement();

			runing = false;
		} catch (Exception ex) {
			System.out.print("StatementTaskJob:" + ex.getMessage());
			runing = false;
		}
	}

	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}

}

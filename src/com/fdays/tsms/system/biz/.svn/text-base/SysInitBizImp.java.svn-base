package com.fdays.tsms.system.biz;

import java.util.List;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.dao.AccountDAO;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.CompanyDAO;
import com.fdays.tsms.transaction.dao.PaymentToolDAO;
import com.fdays.tsms.transaction.dao.PlatComAccountDAO;
import com.fdays.tsms.transaction.dao.PlatformDAO;

public class SysInitBizImp implements SysInitBiz {
	private PlatComAccountDAO platComAccountDAO;
	private PlatformDAO platformDAO;
	private CompanyDAO companyDAO;
	private AccountDAO accountDAO;
	private PaymentToolDAO paymentToolDAO;
	private AgentDAO agentDAO;

	private LogUtil myLog;

	public void initPlatComAccountStore() {
		myLog = new AirticketLogUtil(false, false, SysInitBizImp.class, "");
		
		try {
			updatePCAStore_PlatComAccount();

			updatePCAStore_Platform();
			updatePCAStore_Company();
			updatePCAStore_Account();

			updatePCAStore_PaymentTool();

			updatePCAStore_Agent();

			
		} catch (Exception e) {
			e.printStackTrace();
			myLog.error(e.getMessage());
		}
	}

	public void updatePCAStore_PlatComAccount() {
		try {
			List<PlatComAccount> platComAccountList = platComAccountDAO
					.getPlatComAccountList();
			if (platComAccountList != null) {
				PlatComAccountStore.platComAccountList = platComAccountList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatePCAStore_Account() {
		try {
			List<Account> accountList = accountDAO.getAccountList();
			if (accountList != null) {
				PlatComAccountStore.accountList = accountList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatePCAStore_Company() {
		try {
			List<Company> companyList = companyDAO.getCompanyList();

			if (companyList != null) {
				PlatComAccountStore.companyList = companyList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_Platform() {
		try {
			List<Platform> platFormList = platformDAO.getPlatformList();
			if (platFormList != null) {
				PlatComAccountStore.platFormList = platFormList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_PaymentTool() {
		try {
			List<PaymentTool> paymentToolList = paymentToolDAO
					.getPaymentToolList();
			if (paymentToolList != null) {
				PlatComAccountStore.paymentToolList = paymentToolList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_Agent() {
		try {
			List<Agent> agentList = agentDAO.getAgentList();
			if (agentList != null) {
				PlatComAccountStore.teamAgentList = agentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initMainTask() {
		try {
			MainTask task = MainTask.getInstance();
			Thread thread = new Thread(task);
			thread.start();
			System.out.println("init MainTask success! ");
		} catch (Exception ex) {
			System.out.println("init MainTask fails... " + ex.getMessage());
		}
	}

	public void setPlatComAccountDAO(PlatComAccountDAO platComAccountDAO) {
		this.platComAccountDAO = platComAccountDAO;
	}

	public void setPlatformDAO(PlatformDAO platformDAO) {
		this.platformDAO = platformDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setPaymentToolDAO(PaymentToolDAO paymentToolDAO) {
		this.paymentToolDAO = paymentToolDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

}

package com.fdays.tsms.system.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlineStore;
import com.fdays.tsms.airticket.dao.AirlineDAO;
import com.fdays.tsms.airticket.dao.AirlinePlaceDAO;
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
import com.fdays.tsms.user.SysUser;
import com.fdays.tsms.user.UserStore;
import com.fdays.tsms.user.dao.UserDAO;

public class SysInitBizImp implements SysInitBiz {
	private PlatComAccountDAO platComAccountDAO;
	private PlatformDAO platformDAO;
	private CompanyDAO companyDAO;
	private AccountDAO accountDAO;
	private PaymentToolDAO paymentToolDAO;
	private AgentDAO agentDAO;
	private UserDAO userDAO;
	private AirlineDAO airlineDAO;
	private AirlinePlaceDAO airlinePlaceDAO;	

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
	
	public void updateAirlineStroe(){
		updateAirline();
		updateAirlinePlace();
	}
	
	public void updateAirline() {
		try {
			List<Airline> airlineList = airlineDAO.getValidList();
			if (airlineList != null) {
				AirlineStore.airlineList = airlineList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateAirlinePlace() {
		try {
			List<AirlinePlace> airlinePlaceList = airlinePlaceDAO.getValidList();
			if (airlinePlaceList != null) {
				AirlineStore.airlinePlaceList = airlinePlaceList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserStore() {
		try {
			List<SysUser> userList = userDAO.list();
			if (userList != null) {
				UserStore.userList = userList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_PlatComAccount() {
		try {
			List<PlatComAccount> platComAccountList = platComAccountDAO
					.getValidPlatComAccountList();
			List<PlatComAccount> temPlatComAccountList=new  ArrayList<PlatComAccount>();
			
			
			for(int i=0;i<platComAccountList.size();i++)
			{
				PlatComAccount pca=platComAccountList.get(i);
				Account a=platComAccountList.get(i).getAccount();
				pca.setAccount((Account)a.clone());
				Platform pf=platComAccountList.get(i).getPlatform();
				pca.setPlatform((Platform)pf.clone());
				Company c=platComAccountList.get(i).getCompany();
				pca.setCompany((Company)c.clone());				
				temPlatComAccountList.add(pca);
			}
			
			if (platComAccountList != null) {
				PlatComAccountStore.platComAccountList = temPlatComAccountList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_Account() {
		try {
			List<Account> accountList = accountDAO.getValidAccountList();
			if (accountList != null) {
				PlatComAccountStore.accountList = accountList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatePCAStore_Company() {
		try {
			List<Company> companyList = companyDAO.getValidCompanyList();
			if (companyList != null) {
				PlatComAccountStore.companyList = companyList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_Platform() {
		try {
			List<Platform> platFormList = platformDAO.getValidPlatformList();
			System.out.println("-------------------------");
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
					.getValidPaymentToolList();
			if (paymentToolList != null) {
				PlatComAccountStore.paymentToolList = paymentToolList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePCAStore_Agent() {
		try {
			List<Agent> agentList = agentDAO.getValidAgentList();
			if (agentList != null) {
				PlatComAccountStore.agentList = agentList;
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

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setAirlineDAO(AirlineDAO airlineDAO) {
		this.airlineDAO = airlineDAO;
	}

	public void setAirlinePlaceDAO(AirlinePlaceDAO airlinePlaceDAO) {
		this.airlinePlaceDAO = airlinePlaceDAO;
	}
	
	

}

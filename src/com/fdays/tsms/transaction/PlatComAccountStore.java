package com.fdays.tsms.transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author yanrui
 */
public class PlatComAccountStore {
	public static List<PlatComAccount> platComAccountList = new ArrayList<PlatComAccount>();
	public static List<Platform> platFormList = new ArrayList<Platform>();
	public static List<Company> companyList = new ArrayList<Company>();
	public static List<Account> accountList = new ArrayList<Account>();
	public static List<PaymentTool> paymentToolList = new ArrayList<PaymentTool>();
	public static List<Agent> agentList = new ArrayList<Agent>();

	// 买入平台(平台) BSP 2
	public static List<Platform> getFormPlatformByBSP() {
		List<Platform> platformList = new ArrayList<Platform>();
		for (int i = 0; i < platFormList.size(); i++) {
			Platform pf = platFormList.get(i);
			if ((pf.getType() == 1 && pf.getDrawType() == 2)
					|| pf.getType() == 3) {
				platformList.add(pf);
			}
		}
		return platformList;
	}

	// 买入平台(网电) B2B网电 1
	public static List<Platform> getFormPlatformByB2B() {

		List<Platform> platformList = new ArrayList<Platform>();
		for (int i = 0; i < platFormList.size(); i++) {
			Platform pf = platFormList.get(i);
			if (pf.getType() == 1 && pf.getDrawType() == 1) {
				platformList.add(pf);
			}
		}
		return platformList;
	}

	// 买入平台
	public static List<Platform> getFormPlatform() {

		List<Platform> platformList = new ArrayList<Platform>();
		for (int i = 0; i < platFormList.size(); i++) {
			Platform pf = platFormList.get(i);
			if (pf.getType() == 1 || pf.getType() == 3) {// 买入和买卖平台
				platformList.add(pf);
			}
		}
		return platformList;
	}

	// 卖出平台
	public static List<Platform> getToPlatform() {

		List<Platform> platformList = new ArrayList<Platform>();
		for (int i = 0; i < platFormList.size(); i++) {
			Platform pf = platFormList.get(i);
			if (pf.getType() == 2 || pf.getType() == 3) {// 卖出和买卖平台
				platformList.add(pf);
			}
		}
		return platformList;
	}

	// 付款账号
	public static List<Account> getFormAccount() {
		List<Account> formAccountList = new ArrayList<Account>();
		for (int i = 0; i < accountList.size(); i++) {
			Account ac = accountList.get(i);
			if (ac.getType() == 1 || ac.getType() == 3) {
				formAccountList.add(ac);
			}
		}
		return formAccountList;
	}

	// 收款账号
	public static List<Account> getToAccount() {

		List<Account> toAccountList = new ArrayList<Account>();
		for (int i = 0; i < accountList.size(); i++) {
			Account ac = accountList.get(i);
			if (ac.getTranType() == 2 || ac.getTranType() == 3) {
				toAccountList.add(ac);
			}
		}
		return toAccountList;
	}

	// B2C散客
	public static List<Agent> getB2CAgentList() {
		List<Agent> B2CAgentList = new ArrayList<Agent>();
		for (int i = 0; i < agentList.size(); i++) {
			Agent agent = (Agent) agentList.get(i);
			if (agent.getType() == Agent.type_1)// B2C散客
			{
				B2CAgentList.add(agent);
			}
		}
		return B2CAgentList;
	}

	// 客户公司
	public static List<Company> getTeamCompnayList() {
		List<Company> teamList = new ArrayList<Company>();
		for (int i = 0; i < companyList.size(); i++) {
			Company company = (Company) companyList.get(i);
			if (company.getType() == Company.type_2) {
				teamList.add(company);
			}
		}
		return teamList;
	}

	// 根据外键 交易平台表ID(dwr)
	public List<PlatComAccount> getPlatComAccountListByPlatformId(
			long platformId) {
		List<PlatComAccount> tempList = new ArrayList<PlatComAccount>();
		Set set = new HashSet();
		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount platComAccount = platComAccountList.get(i);

			if (platComAccount.getPlatform().getId() == platformId) {
				if (set.add(platComAccount.getCompany().getId())) {
					tempList.add(platComAccount);
				}
			}
		}
		return tempList;
	}

	// 公司表ID,交易平台ID,买卖类别(dwr)
	public List<PlatComAccount> getPlatComAccountListByCompanyIdType(
			long companyId, long platformId, long type) {
		List<PlatComAccount> tempList = new ArrayList<PlatComAccount>();

		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount platComAccount = platComAccountList.get(i);

			if (platComAccount.getCompany().getId() == companyId
					&& platComAccount.getPlatform().getId() == platformId) {

				Account account = platComAccount.getAccount();
				long accountTranType = account.getTranType();

				if (type == 1) {// 卖出收款
					if (accountTranType == 2 || accountTranType == 3) {
						tempList.add(platComAccount);
					}
				} else if (type == 2) {// 买入付款
					if (accountTranType == 1 || accountTranType == 3) {
						tempList.add(platComAccount);
					}
				}
			}
		}
		return tempList;
	}

	// 根据外键 公司表ID,交易平台ID(dwr)
	public List<PlatComAccount> getPlatComAccountListByCompanyId(
			long companyId, long platformId) {
		List<PlatComAccount> tempList = new ArrayList<PlatComAccount>();

		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount platComAccount = platComAccountList.get(i);

			if (platComAccount.getCompany().getId() == companyId
					&& platComAccount.getPlatform().getId() == platformId) {
				tempList.add(platComAccount);
			}
		}
		return tempList;
	}

	// 根据外键 公司表ID,交易平台ID,支付账号ID(dwr)
	public List<PlatComAccount> getPlatComAccountListbyAllId(long platformId,
			long companyId, long accountId) {
		List<PlatComAccount> tempList = new ArrayList<PlatComAccount>();
		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount platComAccount = platComAccountList.get(i);
			if (platComAccount.getCompany().getId() == companyId
					&& platComAccount.getPlatform().getId() == platformId
					&& platComAccount.getAccount().getId() == accountId) {
				tempList.add(platComAccount);
			}
		}
		return tempList;
	}

	// 根据类型查询客户信息
	public List<Agent> getTempAgentListBytype(long type) {
		List<Agent> tempList = new ArrayList<Agent>();
		for (int i = 0; i < agentList.size(); i++) {
			Agent agent = agentList.get(i);
			if (agent.getType() == type) {
				tempList.add(agent);
			}
		}
		return tempList;
	}

	// 根据平台账号表ID查询
	public static PlatComAccount getPlatComAccountById(long id) {
		PlatComAccount platComAccount = new PlatComAccount();
		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount pAccount = platComAccountList.get(i);
			if (pAccount.getId() == id) {
				platComAccount = pAccount;
			}
		}
		return platComAccount;
	}

	public PlatComAccount getPlatComAccountByAllId(long platformId,
			long companyId, long accountId) {
		PlatComAccount platComAccount = null;
		try {
			List<PlatComAccount> platComAccountList = getPlatComAccountListbyAllId(
					platformId, companyId, accountId);
			if (platComAccountList != null && platComAccountList.size() > 0) {

				platComAccount = platComAccountList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platComAccount;
	}

	public List<PlatComAccount> getPlatComAccountList() {
		return platComAccountList;
	}

	public void setPlatComAccountList(List<PlatComAccount> platComAccountList) {
		PlatComAccountStore.platComAccountList = platComAccountList;
	}

	public List<Platform> getPlatFormList() {
		return platFormList;
	}

	public void setPlatFormList(List<Platform> platFormList) {
		PlatComAccountStore.platFormList = platFormList;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		PlatComAccountStore.companyList = companyList;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		PlatComAccountStore.accountList = accountList;
	}

	public static List<Agent> getAgentList() {
		return agentList;
	}

	public static void setAgentList(List<Agent> agentList) {
		PlatComAccountStore.agentList = agentList;
	}

	public static List<PaymentTool> getPaymentToolList() {
		return paymentToolList;
	}

	public static void setPaymentToolList(List<PaymentTool> paymentToolList) {
		PlatComAccountStore.paymentToolList = paymentToolList;
	}

}

package com.fdays.tsms.transaction;

import java.util.ArrayList;
import java.util.List;

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
	public static List<Agent> teamAgentList = new ArrayList<Agent>();

	// 根据外键 交易平台表ID(dwr)
	public List<PlatComAccount> getPlatComAccountListByPlatformId(
			long platformId) {
		List<PlatComAccount> tempList = new ArrayList<PlatComAccount>();

		for (int i = 0; i < platComAccountList.size(); i++) {
			PlatComAccount platComAccount = platComAccountList.get(i);

			if (platComAccount.getPlatform().getId() == platformId) {
				tempList.add(platComAccount);
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
	
	
	//根据类型查询客户信息（团队）
	public List<Agent> getTempAgentListBytype(long type)
	{
		List<Agent> tempList = new ArrayList<Agent>();
		for(int i=0;i<teamAgentList.size();i++)
		{
			Agent agent = teamAgentList.get(i);
			if(agent.getType() ==type)
			{
				tempList.add(agent);
			}
		}
		return tempList;
	}
	
	//根据平台账号表ID查询
	public static PlatComAccount getPlatComAccountById(long id)
	{
		PlatComAccount platComAccount =new PlatComAccount();
		for(int i=0;i<platComAccountList.size();i++)
		{
			PlatComAccount pAccount=platComAccountList.get(i);
			if(pAccount.getId() == id)
			{
				platComAccount=pAccount;
			}
		}
		return platComAccount;
	}
	
	public PlatComAccount getPlatComAccountByAllId(long platformId,
			long companyId, long accountId)
	{
		PlatComAccount platComAccount=null;
		try {
			List<PlatComAccount> platComAccountList = getPlatComAccountListbyAllId(platformId,companyId,accountId);
			if(platComAccountList!=null&&platComAccountList.size()>0){
		
			 platComAccount = platComAccountList.get(0);	
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	public List<Agent> getTeamAgentList() {
		return teamAgentList;
	}

	public void setTeamAgentList(List<Agent> teamAgentList) {
		PlatComAccountStore.teamAgentList = teamAgentList;
	}

	public static List<PaymentTool> getPaymentToolList() {
		return paymentToolList;
	}

	public static void setPaymentToolList(List<PaymentTool> paymentToolList) {
		PlatComAccountStore.paymentToolList = paymentToolList;
	}

}

package com.fdays.tsms.policy.biz;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.fdays.tsms.policy.dao.PolicyAfterDAO;
import com.neza.exception.AppException;

public class PolicyAfterBizImp implements PolicyAfterBiz {
	private PolicyAfterDAO policyAfterDAO;
	private TransactionTemplate transactionTemplate;

	//根据ID获取PolicyAfter对象
	public PolicyAfter getPolicyAfterById(long id) throws AppException {
		return policyAfterDAO.getPolicyAfterById(id);
	}


	//增加或更新PolicyAfter对象
	public void saveOrUpdate(PolicyAfter policyAfter) throws AppException {
		policyAfterDAO.saveOrUpdate(policyAfter);
	}
	
	//修改PolicyAfter
	public void update(PolicyAfter policyAfter) {
		try {
			policyAfterDAO.update(policyAfter);
		} catch (AppException e) {
			System.out.println(e.getMessage());
		}
		
	}

	//根据ID删除PolicyAfter对象
	public long deletePolicyAfter(long id) throws AppException {
		try {
			policyAfterDAO.deleteById(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	//获取记录动态参数并分页
	public List getPolicyAfter(PolicyAfterListForm palf) throws AppException {
		
		return policyAfterDAO.list(palf);
	}


	//-----------------------set get----------------------//
	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public PolicyAfterDAO getPolicyAfterDAO() {
		return policyAfterDAO;
	}

	public void setPolicyAfterDAO(PolicyAfterDAO policyAfterDAO) {
		this.policyAfterDAO = policyAfterDAO;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}



}

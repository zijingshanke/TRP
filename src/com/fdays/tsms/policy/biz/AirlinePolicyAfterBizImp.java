package com.fdays.tsms.policy.biz;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.dao.AirlinePolicyAfterDAO;
import com.neza.exception.AppException;

/**
 * AirlinePolicyAfter业务接口实现类
 * @author chenqx
 *  2010-12-10
 */
public class AirlinePolicyAfterBizImp implements AirlinePolicyAfterBiz {

	private AirlinePolicyAfterDAO airlinePolicyAfterDAO;
	private TransactionTemplate transactionTemplate;
	
	//根据ID删除AirlinePolicyAfter对象
	public long deleteAirlinePolicyAfter(long id) throws AppException {
		try {
			airlinePolicyAfterDAO.deleteById(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	//动态参数获取对象（支持并分页）
	public List getAirlinePolicyAfter(AirlinePolicyAfterListForm apalf)
			throws AppException {
		return airlinePolicyAfterDAO.list(apalf);
	}

	//根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAirlinePolicyAfterById(long id)
			throws AppException {
		return airlinePolicyAfterDAO.getAirlinePolicyAfterById(id);
	}

	//保存或更改对象
	public void save(AirlinePolicyAfter airlinePolicyAfter)
			throws AppException {
		airlinePolicyAfterDAO.save(airlinePolicyAfter);
	}
	
	//修改对象
	public void update(AirlinePolicyAfter airlinePolicyAfter) {
		try {
			airlinePolicyAfterDAO.update(airlinePolicyAfter);
		} catch (AppException e) {
			System.out.println(e.getMessage());
		}
		
	}


	//------------------------------------set get----------------------------//

	public void setAirlinePolicyAfterDAO(AirlinePolicyAfterDAO airlinePolicyAfterDAO) {
		this.airlinePolicyAfterDAO = airlinePolicyAfterDAO;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	
}

package com.fdays.tsms.policy.biz;

import java.math.BigDecimal;
import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.neza.exception.AppException;

public interface PolicyAfterBiz {
	
	//根据ID获取PolicyAfter对象
	public PolicyAfter getPolicyAfterById(long id) throws AppException;
	
	//获取所有PolicyAfter对象，并支持分页
	public List getPolicyAfter(PolicyAfterListForm palf) throws AppException;
	
	//增加或更新PolicyAfter对象
	public void saveOrUpdate(PolicyAfter policyAfter) throws AppException;
	
	//修改PolicyAfter
	public void update(PolicyAfter policyAfter);
	
	//根据ID删除PolicyAfter对象
	public long deletePolicyAfter(long id) throws AppException;

}

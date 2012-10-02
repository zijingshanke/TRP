package com.fdays.tsms.policy.dao;

import java.util.List;
import java.util.Set;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.fdays.tsms.user.SysUser;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface PolicyAfterDAO extends BaseDAO {
	
	//获取所有PolicyAfter对象，并支持分页
	public List list(PolicyAfterListForm palf) throws AppException;

	//增加或更新PolicyAfter对象
	public long saveOrUpdate(PolicyAfter policyAfter) throws AppException;

	//根据ID获取PolicyAfter对象
	public PolicyAfter queryById(long id) throws AppException;
	
	public long merge(PolicyAfter policyAfter) throws AppException;

	//更改PolicyAfter对象
	public void update(PolicyAfter policyAfter) throws AppException;

	//根据ID获取PolicyAfter对象
	public PolicyAfter getPolicyAfterById(long id);

	//根据ID删除PolicyAfter对象
	public void deleteById(long id) throws AppException;

	//获取所有记录
	public List list() throws AppException;
	
	//根据AirlinePolicyAfter获取PolicyAfterList
	public List<PolicyAfter> getPolicyAfgerList(PolicyAfterListForm palf);

}

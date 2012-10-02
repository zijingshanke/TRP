package com.fdays.tsms.policy.biz;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.fdays.tsms.user.SysUser;
import com.fdays.tsms.user.UserListForm;
import com.neza.exception.AppException;

/**
 * AirlinePolicyAfter业务类接口
 * @author Administrator
 *
 */
public interface AirlinePolicyAfterBiz {
	
	//根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAirlinePolicyAfterById(long id) throws AppException;
	
	//动态参数获取对象（支持并分页）
	public List getAirlinePolicyAfter(AirlinePolicyAfterListForm apalf) throws AppException;
	
	//保存或更改对象
	public void save(AirlinePolicyAfter airlinePolicyAfter) throws AppException;
	
	//修改对象
	public void update(AirlinePolicyAfter airlinePolicyAfter);

	//根据ID删除AirlinePolicyAfter对象
	public long deleteAirlinePolicyAfter(long id) throws AppException;
}

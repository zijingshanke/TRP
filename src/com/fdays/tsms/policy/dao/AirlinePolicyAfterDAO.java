package com.fdays.tsms.policy.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AirlinePolicyAfterDAO extends BaseDAO {

	//保存或更改对象
	public long save(AirlinePolicyAfter airlinePolicyAfter) throws AppException;

	//HQL根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter queryById(long id) throws AppException;

	public long merge(AirlinePolicyAfter airlinePolicyAfter) throws AppException;

	//更改对象
	public long update(AirlinePolicyAfter airlinePolicyAfter) throws AppException;

	//根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAirlinePolicyAfterById(long id);

	//根据ID删除AirlinePolicyAfter对象
	public void deleteById(long id) throws AppException;
	
	//动态参数获取对象（支持并分页）
	public List list(AirlinePolicyAfterListForm apalf) throws AppException;
	
	//获取指定承运人某个日期内有效的AirlinePolicyAfter集合
	public List<AirlinePolicyAfter> getAirLinePolicyAfterInTime(String carrier,Timestamp ts);
	
	//获取所有对象
	public List<AirlinePolicyAfter> list() throws AppException;
	
	//根据承运人获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAppropriatePolicy(String carrier);
	
}

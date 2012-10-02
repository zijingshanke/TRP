package com.fdays.tsms.policy.dao;

import java.util.List;

import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.IndicatorStatisticsListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface IndicatorStatisticsDAO extends BaseDAO {
	
	//获取所有IndicatorStatistics对象，并支持分页
	public List list(IndicatorStatisticsListForm islf) throws AppException;

	//增加或更新IndicatorStatistics对象
	public long saveOrUpdate(IndicatorStatistics indicatorStatistics) throws AppException;

	//根据ID获取IndicatorStatistics对象
	public IndicatorStatistics queryById(long id) throws AppException;
	
	public long merge(IndicatorStatistics indicatorStatistics) throws AppException;

	//更改IndicatorStatistics对象
	public void update(IndicatorStatistics indicatorStatistics) throws AppException;

	//根据ID获取IndicatorStatistics对象
	public IndicatorStatistics getIndicatorStatisticsById(long id);

	//根据ID删除IndicatorStatistics对象
	public void deleteById(long id) throws AppException;

	//获取所有记录
	public List list() throws AppException;
	
	//根据AirlinePolicyAfter获取IndicatorStatisticsList
	public List<IndicatorStatistics> getIndicatorStatisticsList(IndicatorStatisticsListForm islf);

}

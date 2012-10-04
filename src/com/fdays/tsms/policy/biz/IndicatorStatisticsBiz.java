package com.fdays.tsms.policy.biz;

import java.util.List;

import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.IndicatorStatisticsListForm;
import com.neza.exception.AppException;

public interface IndicatorStatisticsBiz {
	
	//根据ID获取IndicatorStatistics对象
	public IndicatorStatistics getIndicatorStatisticsById(long id) throws AppException;
	
	//获取所有IndicatorStatistics对象，并支持分页
	public List getIndicatorStatistics(IndicatorStatisticsListForm islf) throws AppException;
	
	//增加或更新IndicatorStatistics对象
	public void saveOrUpdate(IndicatorStatistics indicatorStatistics) throws AppException;
	
	//修改IndicatorStatistics
	public void update(IndicatorStatistics indicatorStatistics);
	
	//根据ID删除IndicatorStatistics对象
	public long deleteIndicatorStatistics(long id) throws AppException;

}

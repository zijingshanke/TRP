package com.fdays.tsms.policy.dao;

import java.sql.Timestamp;
import java.util.List;

import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

/**
 * SaleStatisticsDAO
 * @author Administrator
 * 2010-12-24
 */
public interface SaleStatisticsDAO extends BaseDAO {

	//保存或更改对象
	public long save(SaleStatistics saleStatistics) throws AppException;

	//HQL根据ID获取SaleStatistics对象
	public SaleStatistics queryById(long id) throws AppException;

	public long merge(SaleStatistics saleStatistics) throws AppException;

	//更改对象
	public long update(SaleStatistics saleStatistics) throws AppException;

	//根据ID获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsById(long id);

	//根据ID删除SaleStatistics对象
	public void deleteById(long id) throws AppException;
	
	//动态参数获取对象（支持并分页）
	public List list(SaleStatisticsListForm apalf) throws AppException;
	
	//获取所有对象
	public List<SaleStatistics> list() throws AppException;
	
	//根据承运人获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsByCarrier(String carrier);
}

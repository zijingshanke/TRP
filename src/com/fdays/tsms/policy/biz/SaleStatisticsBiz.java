package com.fdays.tsms.policy.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.neza.exception.AppException;

/**
 * SaleStatistics业务类接口
 * @author Administrator
 * 2010-12-24
 */
public interface SaleStatisticsBiz
{

	// 根据ID获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsById(long id)
	    throws AppException;

	// 动态参数获取对象（支持并分页）
	public List getSaleStatistics(SaleStatisticsListForm sslf)
	    throws AppException;

	// 保存或更改对象
	public void save(SaleStatistics saleStatistics) throws AppException;

	// 修改对象
	public void update(SaleStatistics saleStatistics);

	// 根据ID删除SaleStatistics对象
	public long deleteSaleStatistics(long id) throws AppException;

	// 获取所有SaleStatistics对象
	public List<SaleStatistics> listSaleStatistics();

//	//下载后返报表
//	public ArrayList<ArrayList<Object>> downloadStatistics(SaleStatistics saleStatistics) throws AppException;
}

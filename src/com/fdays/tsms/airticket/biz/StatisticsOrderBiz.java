package com.fdays.tsms.airticket.biz;

import java.util.List;

import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.StatisticsOrderListForm;
import com.fdays.tsms.policy.SaleStatistics;
import com.neza.exception.AppException;

public interface StatisticsOrderBiz {
	
	/**
	 * 根据ID获取StatisticsOrder对象
	 */
	public StatisticsOrder getStatisticsOrderById(long id) throws AppException;
	
	/**
	 * 获取所有StatisticsOrder对象，并支持分页
	 */
	public List<StatisticsOrder> getStatisticsOrder(StatisticsOrderListForm solf) throws AppException;
	
	/**
	 * 根据SaleStatistics获取所有StatisticsOrder对象
	 */
	public List<StatisticsOrder> listBySaleStatistics(SaleStatistics saleStatistics) throws AppException;
	
	/**
	 * 增加或更新StatisticsOrder对象
	 */
	public void saveOrUpdate(StatisticsOrder statisticsOrder) throws AppException;
	
	/**
	 * 批量插入、修改
	 * @param soList
	 * @return
	 */
	public boolean batchSaveOrUpdate(List<StatisticsOrder> soList)throws AppException;
	
	/**
	 * 修改StatisticsOrder
	 */
	public void update(StatisticsOrder statisticsOrder)throws AppException;
	
	/**
	 * 根据ID删除StatisticsOrder对象
	 */
	public boolean deleteStatisticsOrder(long id) throws AppException;
	
	/**
	 * 删除所有
	 * @return
	 */
	public boolean deleteAll();
	
	/**
	 * 获取所有记录条数
	 * @return
	 */
	public int getTotalCount()throws AppException;
	
	/**
	 * 创建后返报表信息
	 * @param saleStatistics
	 * @return
	 * @throws AppException
	 */
	public List<StatisticsOrder> createStatistics(SaleStatistics saleStatistics,int startRow,int rowCount) throws AppException;

}

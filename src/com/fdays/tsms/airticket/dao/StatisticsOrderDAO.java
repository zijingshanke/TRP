package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.StatisticsOrderListForm;
import com.fdays.tsms.policy.SaleStatistics;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

/**
 * 
 * @author chenqx
 * 2011-01-13
 */
public interface StatisticsOrderDAO extends BaseDAO {
	/**
	 * 分页获取所有StatisticsOrder
	 * @return
	 */
	public List<StatisticsOrder> listStatisticsOrder(StatisticsOrderListForm solf) throws AppException;
	
	/**
	 * 增加或更新StatisticsOrder对象
	 */
	public long saveOrUpdate(StatisticsOrder statisticsOrder) throws AppException;
	
	/**
	 * 批量插入
	 * @param soList
	 * @return
	 */
	public boolean batchSaveOrUpdate(List<StatisticsOrder> soList)throws AppException;
	
	/**
	 * 根据ID获取StatisticsOrder对象
	 */
	public StatisticsOrder queryById(long id) throws AppException;
	
	public long merge(StatisticsOrder statisticsOrder) throws AppException;
	
	/**
	 * 更改StatisticsOrder对象
	 */
	public void update(StatisticsOrder statisticsOrder) throws AppException;
	
	/**
	 * 根据ID删除StatisticsOrder对象
	 */
	public boolean deleteById(long id) throws AppException;
	
	/**
	 * 删除所有记录
	 * @return
	 */
	public boolean deleteAll();
	
	/**
	 * 获取所有记录
	 */
	public List<StatisticsOrder> list() throws AppException;
	
	/**
	 * 根据SaleStatistics获取StatisticsOrder
	 */
	public List<StatisticsOrder> getStatisticsOrderList(SaleStatistics saleStatistics);
}

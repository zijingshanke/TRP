package com.fdays.tsms.policy.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.SaleResult;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.fdays.tsms.policy.dao.SaleStatisticsDAO;
import com.neza.exception.AppException;

/**
 * SaleStatistics业务接口实现类
 * @author chenqx 
 * 2010-12-24
 */
public class SaleStatisticsBizImp implements SaleStatisticsBiz
{

	private SaleStatisticsDAO saleStatisticsDAO;
	private TransactionTemplate transactionTemplate;

	// 根据ID删除SaleStatistics对象
	public long deleteSaleStatistics(long id) throws AppException{
		try{
			saleStatisticsDAO.deleteById(id);
		}
		catch (RuntimeException e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 动态参数获取对象（支持并分页）
	public List getSaleStatistics(SaleStatisticsListForm sslf)
	    throws AppException{
		return saleStatisticsDAO.list(sslf);
	}

	// 根据ID获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsById(long id)
	    throws AppException{
		return saleStatisticsDAO.getSaleStatisticsById(id);
	}

	// 保存或更改对象
	public void save(SaleStatistics saleStatistics) throws AppException{
		saleStatisticsDAO.save(saleStatistics);
	}

	// 修改对象
	public void update(SaleStatistics saleStatistics){
		try{
			saleStatisticsDAO.update(saleStatistics);
		}
		catch (AppException e){
			System.out.println(e.getMessage());
		}

	}
	
	// 获取所有对象
	public List<SaleStatistics> listSaleStatistics(){
		try{
			return saleStatisticsDAO.list();
		}
		catch (AppException e){
			e.printStackTrace();
		}
		return new ArrayList<SaleStatistics>();
	}
	
 
	

	// ------------------------------------set get----------------------------//

	public TransactionTemplate getTransactionTemplate(){
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate){
		this.transactionTemplate = transactionTemplate;
	}

	public void setSaleStatisticsDAO(SaleStatisticsDAO saleStatisticsDAO) {
		this.saleStatisticsDAO = saleStatisticsDAO;
	}



}

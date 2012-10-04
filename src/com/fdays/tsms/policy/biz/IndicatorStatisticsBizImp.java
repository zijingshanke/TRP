package com.fdays.tsms.policy.biz;

import java.util.List;

import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.IndicatorStatisticsListForm;
import com.fdays.tsms.policy.dao.IndicatorStatisticsDAO;
import com.neza.exception.AppException;

public class IndicatorStatisticsBizImp implements IndicatorStatisticsBiz {
	private IndicatorStatisticsDAO indicatorStatisticsDAO;
	private TransactionTemplate transactionTemplate;

	//根据ID获取IndicatorStatistics对象
	public IndicatorStatistics getIndicatorStatisticsById(long id) throws AppException {
		return indicatorStatisticsDAO.getIndicatorStatisticsById(id);
	}


	//增加或更新IndicatorStatistics对象
	public void saveOrUpdate(IndicatorStatistics indicatorStatistics) throws AppException {
		indicatorStatisticsDAO.saveOrUpdate(indicatorStatistics);
	}
	
	//修改IndicatorStatistics
	public void update(IndicatorStatistics indicatorStatistics) {
		try {
			indicatorStatisticsDAO.update(indicatorStatistics);
		} catch (AppException e) {
			System.out.println(e.getMessage());
		}
		
	}

	//根据ID删除IndicatorStatistics对象
	public long deleteIndicatorStatistics(long id) throws AppException {
		try {
			indicatorStatisticsDAO.deleteById(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public List getIndicatorStatistics(IndicatorStatisticsListForm islf)throws AppException {
		return indicatorStatisticsDAO.list(islf);
	}
	
	//-----------------------set get----------------------//
	public IndicatorStatisticsDAO getIndicatorStatisticsDAO() {
		return indicatorStatisticsDAO;
	}


	public void setIndicatorStatisticsDAO(
			IndicatorStatisticsDAO indicatorStatisticsDAO) {
		this.indicatorStatisticsDAO = indicatorStatisticsDAO;
	}


	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}


	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}


}

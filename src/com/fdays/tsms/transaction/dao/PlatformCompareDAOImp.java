package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.dao.OrderStatementHqlUtil;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.fdays.tsms.transaction.ReportCompareUtil;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatformCompareDAOImp extends BaseDAOSupport implements
		PlatformCompareDAO {
	
public List<PlatformCompare> listCompareOrder(Long platformId,String startDate,String endDate,String businessType,String tranType,String ticketType)
    throws AppException
{
	long a = System.currentTimeMillis();
	Hql hql = new Hql();
	hql.add("select new com.fdays.tsms.transaction.PlatformCompare");
	hql.add(" ( ");
		hql.add(" a ");
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getStatementAccount("a.id",Statement.SUBTYPE_10));
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getStatementAccount("a.id",Statement.SUBTYPE_20));
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getStatementAmount("a.id",Statement.SUBTYPE_10));
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getStatementAmount("a.id",Statement.SUBTYPE_20));		
	hql.add(")");
	hql.add(" from AirticketOrder a where 1=1 ");
	
	if ("".equals(Constant.toString(ticketType))==false)	{// 机票类型
		hql.add("and a.ticketType in(" + ticketType +") ");
	}

	if ("".equals(startDate) == false && "".equals(endDate) == false){
		hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
		hql.addParamter(startDate);
		hql.addParamter(endDate);
	}
	
	if(Constant.toLong(platformId)>0){
		hql.add("and a.platform.id=" +platformId);
	}
	
	if ("".equals(Constant.toString(businessType))==false)	{
		hql.add("and a.businessType in(" + businessType+")");
	}

	if ("".equals(Constant.toString(tranType))==false)	{
		hql.add("and a.tranType in(" + tranType+")");
	}
	
	hql.add(" and a.status not in (" + AirticketOrder.STATUS_88 + ")");	
																																												// desc,
	System.out.println("query list>>>"+hql.getSql());

	List<PlatformCompare> tempList = new ArrayList<PlatformCompare>();
		Query query = this.getQuery(hql);
		if (query != null)	{
			tempList = query.list();
			if(tempList != null){
				if(tempList.size() > 0){
//					List<PlatformCompare> list =getCompareListAsTicketNumber(tempList);
//					return list;
					return tempList;
				}
			}		
		}
	return tempList;
}

	public List<PlatformCompare> getCompareListAsTicketNumber(List<PlatformCompare> tempList){		
		List<PlatformCompare> compareList = new ArrayList<PlatformCompare>();
		String tempTicketNumber="";
		for (int i = 1; i < tempList.size(); i++) {
				PlatformCompare currentCompare = tempList.get(i);
				String ticketNumber =currentCompare.getTicketNumber();
				ticketNumber = ticketNumber.replaceAll("[^A-Z0-9\\|]|[\\s]", "");
				if(ticketNumber.length()>=13){
					currentCompare.setTicketNumber(ticketNumber.substring(0,13));		
				}
																			
				if(ticketNumber.length()>20){
					tempTicketNumber=ticketNumber;
				}
				
				compareList.add(currentCompare);
				
				if("".equals(Constant.toString(tempTicketNumber))==false){
					try {
						compareList=ReportCompareUtil.getCompareListByTempTicket(compareList, currentCompare, tempTicketNumber);
					} catch (AppException e) {
						e.printStackTrace();
					}
					tempTicketNumber="";
				}
				
			}								
		return compareList;
	}
	
	public List<AirticketOrder> listAsOrder(PlatformCompareListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrder a where 1=1 ");
		hql.add(" and a.ticketType =1 ");
		hql.add(" and  a.entryTime  between to_date('2010-12-01 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		hql.add(" and to_date('2010-12-01 22:59:59','yyyy-mm-dd hh24:mi:ss') ");
		
		hql.add(" and a.businessType in(" + 1 +")");		
		hql.add(" and a.tranType in(" + 1 +")");
		hql.add(" and a.status in(" + AirticketOrder.STATUS_5 +")");

		hql.add(" and a.platform.id=74 ");
		hql.add(" and a.status not in (" + AirticketOrder.STATUS_88 + ")");	
		
		// hql.add(" order by a.id ");
																																													// desc,
		System.out.println("query list>>>"+hql.getSql());
		
		List<AirticketOrder> list=new ArrayList<AirticketOrder>();
		Query query = this.getQuery(hql);
		if (query != null)	{
			list = query.list();
			if(list != null){
				if(list.size() > 0){
					return list;
				}
			}
		}
		return list;
	}
}

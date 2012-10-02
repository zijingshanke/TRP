package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.dao.OrderStatementHqlUtil;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.util.ReportCompareUtil;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportCompareDAOImp extends BaseDAOSupport implements
		ReportCompareDAO {

	public List<ReportCompare> listCompareOrder(String platformId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType) throws AppException {
		long a = System.currentTimeMillis();
		Hql hql = new Hql();
		hql.add("select new com.fdays.tsms.transaction.ReportCompare");
		hql.add(" ( ");
		hql.add(" a ");
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getOrderStatementHql("a.id"));
		hql.add(")");
		hql.add(" from AirticketOrder a where 1=1 ");

		if ("".equals(Constant.toString(ticketType)) == false) {// 机票类型
			hql.add("and a.ticketType in(" + ticketType + ") ");
		}

		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		if ("".equals(Constant.toString(platformId)) == false) {
			hql.add("and a.platform.id in(" + platformId + " ) ");
		}

		if ("".equals(Constant.toString(businessType)) == false) {
			hql.add("and a.businessType in(" + businessType + ")");
		}

		if ("".equals(Constant.toString(tranType)) == false) {
			hql.add("and a.tranType in(" + tranType + ")");
		}

		hql.add(" and a.status not in (" + AirticketOrder.STATUS_88 + ")");

		hql.add(" order by a.entryTime desc ");
		System.out.println("query list>>>" + hql.getSql());

		List<ReportCompare> tempList = new ArrayList<ReportCompare>();
		Query query = this.getQuery(hql);
		if (query != null) {
			tempList = query.list();
			if (tempList != null) {
				if (tempList.size() > 0) {
					// List<PlatformCompare> list
					// =getCompareListAsTicketNumber(tempList);
					// return list;
					return tempList;
				}
			}
		}
		return tempList;
	}

	public List<ReportCompare> listCompareOrderByAccount(String accountId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType, String statementSubType)
			throws AppException {
		long a = System.currentTimeMillis();
		Hql hql = new Hql();
		hql.add("select new com.fdays.tsms.transaction.ReportCompare");
		hql.add(" ( ");
		hql.add(" a ");
		hql.add(",");
		hql.addHql(OrderStatementHqlUtil.getOrderStatementHql("a.id"));
		hql.add(")");
		hql.add(" from AirticketOrder a where 1=1 ");

		if ("".equals(Constant.toString(ticketType)) == false) {// 机票类型
			hql.add("and a.ticketType in(" + ticketType + ") ");
		}

		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		if ("".equals(Constant.toString(businessType)) == false) {
			hql.add("and a.businessType in(" + businessType + ")");
		}

		if ("".equals(Constant.toString(tranType)) == false) {
			hql.add("and a.tranType in(" + tranType + ")");
		}

		hql.add(" and a.status not in (" + AirticketOrder.STATUS_88 + ")");

		hql.add(" and a.id in( ");
		hql.add(" select s.orderId from Statement s where 1=1 ");

		// 按日期搜索
		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  s.statementDate  between to_date(?,'yyyy-MM-dd HH24:mi:ss') and to_date(?,'yyyy-MM-dd HH24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		String subTypes=Constant.toString(statementSubType);
		if (!"".equals(subTypes)) {
			hql.add(" and s.orderSubtype in( " + statementSubType + " ) ");
		}
		
		hql.add("  and( ");
		String accounts=Constant.toString(accountId);		
		if (StringUtil.containsExistString(Statement.SUBTYPE_10+"", subTypes)
				||StringUtil.containsExistString(Statement.SUBTYPE_11+"", subTypes)) {
			hql.add(" s.toAccount.id in ( " + accounts + " ) ");
		}
		
		if (StringUtil.containsExistString(Statement.SUBTYPE_20+"", subTypes)
				||StringUtil.containsExistString(Statement.SUBTYPE_21+"", subTypes)) {
			hql.add("  or s.fromAccount.id in ( " + accounts + " ) ");
		}
		hql.add(" ) ");
		

		hql.add(" ) ");

		hql.add(" order by a.entryTime desc ");
		System.out.println("query list>>>" + hql.getSql());

		List<ReportCompare> tempList = new ArrayList<ReportCompare>();
		Query query = this.getQuery(hql);
		if (query != null) {
			tempList = query.list();
			if (tempList != null) {
				if (tempList.size() > 0) {
					// List<PlatformCompare> list
					// =getCompareListAsTicketNumber(tempList);
					// return list;
					return tempList;
				}
			}
		}
		return tempList;
	}

	public List<ReportCompare> getCompareListAsTicketNumber(
			List<ReportCompare> tempList) {
		List<ReportCompare> compareList = new ArrayList<ReportCompare>();
		String tempTicketNumber = "";
		for (int i = 1; i < tempList.size(); i++) {
			ReportCompare currentCompare = tempList.get(i);
			String ticketNumber = currentCompare.getTicketNumber();
			ticketNumber = ticketNumber.replaceAll("[^A-Z0-9\\|]|[\\s]", "");
			if (ticketNumber.length() >= 13) {
				currentCompare.setTicketNumber(ticketNumber.substring(0, 13));
			}

			if (ticketNumber.length() > 20) {
				tempTicketNumber = ticketNumber;
			}

			compareList.add(currentCompare);

			if ("".equals(Constant.toString(tempTicketNumber)) == false) {
				try {
					compareList = ReportCompareUtil.getCompareListByTempTicket(
							compareList, currentCompare, tempTicketNumber);
				} catch (AppException e) {
					e.printStackTrace();
				}
				tempTicketNumber = "";
			}

		}
		return compareList;
	}

	public long save(ReportCompare compare) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(compare);
		return compare.getId();
	}

	public long merge(ReportCompare compare) throws AppException {
		this.getHibernateTemplate().merge(compare);
		return compare.getId();
	}

	public long update(ReportCompare compare) throws AppException {
		if (compare.getId() > 0)
			return save(compare);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			ReportCompare compare = (ReportCompare) this.getHibernateTemplate()
					.get(ReportCompare.class, new Long(id));
			this.getHibernateTemplate().delete(compare);

		}
	}

	public ReportCompare getReportCompareById(long id) {
		ReportCompare compare;
		try {
			if (id > 0) {

				compare = (ReportCompare) this.getHibernateTemplate().get(
						ReportCompare.class, new Long(id));
				return compare;
			} else
				return new ReportCompare();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ReportCompare();
	}

	public ReportCompare queryById(long id) throws AppException {
		Hql hql = new Hql("from ReportCompare where id=" + id);

		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (ReportCompare) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List<ReportCompare> getCompareListByResultId(long resultId)
			throws AppException {
		Hql hql = new Hql(" from ReportCompare r where 1=1 ");
		hql.add(" and r.reportCompareResult.id=" + resultId);
		hql.add(" and r.status not in( " + ReportCompare.STATUS_0+" ) ");
		

		Query query = this.getQuery(hql);
		List<ReportCompare> tempList =new ArrayList<ReportCompare>();
		try {
			if (query != null) {
				tempList = query.list();
				if (tempList != null) {
					if (tempList.size() > 0) {
						return tempList;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tempList;
	}

	public List<ReportCompare> getCompareListByResultIdType(long resultId, String types)
			throws AppException {
		Hql hql = new Hql(" from ReportCompare r where 1=1 ");
		hql.add(" and r.reportCompareResult.id=" + resultId);
		hql.add(" and r.type in(" + types+")");
		hql.add(" and r.status not in( " + ReportCompare.STATUS_0+" ) ");

		System.out.println("getCompareListByResultIdType:"+resultId+"--types:"+types);
		System.out.println(hql.getSql());
		
		Query query = this.getQuery(hql);
		
		List<ReportCompare> tempList = new ArrayList<ReportCompare>();
		try {
			if (query != null) {				
				tempList = query.list();
				if (tempList != null) {
					if (tempList.size() > 0) {
						return tempList;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tempList;
	}

	public List list() throws AppException {
		String hql = "from ReportCompare where 1=1";
		return this.list(hql);
	}

	public List getValidReportCompareList() throws AppException {
		String hql = "from ReportCompare where 1=1 and status=1 ";
		return this.list(hql);
	}

	public List list(ReportCompareListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportCompare where 1=1");
		if (ulf.getPlatformId() > 0) {
			hql.add(" and platformId=? ");
			hql.addParamter(ulf.getPlatformId());
		}

		if (ulf.getType() > 0) {
			hql.add(" and type=? ");
			hql.addParamter(ulf.getType());
		}

		if (ulf.getStatus() > 0) {
			hql.add(" and status=? ");
			hql.addParamter(ulf.getStatus());
		}

		return this.list(hql, ulf);
	}


}

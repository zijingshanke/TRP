package com.fdays.tsms.airticket.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.OrderGroup;
import com.fdays.tsms.base.NoUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Constant;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AirticketOrderDAOImp extends BaseDAOSupport implements
    AirticketOrderDAO
{
	private NoUtil noUtil;


	public List list(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException
	{
		long a = System.currentTimeMillis();
		Hql hql = new Hql();
		hql
		    .add("from AirticketOrder b where exists(select distinct orderGroup.id ");
		hql.add("from AirticketOrder a where 1=1 and ");

		// //查看订单 权限
		if (rlf != null && uri != null)
		{
			hql.add("(");
			if (uri.hasRight("sb91"))
			{
				// 所有正常订单
				if (rlf.getSysName().trim().equals(""))
					hql.add("  a.status in (" + AirticketOrder.GROUP_1 + ")");
				else
				{
					hql
					    .add(" (exists(from TicketLog t where (t.sysUser.userNo like ? or t.sysUser.userName like ?) and  t.orderId=a.id   and t.orderType=?  and t.type in ("
					        + TicketLog.GROUP_1 + ")))");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter(TicketLog.ORDERTYPE_1);
				}

			}
			else
			{
				hql
				    .add(" (exists(from TicketLog t where t.sysUser.id=?  and  t.orderId=a.id   and t.orderType=?  and t.type in ("
				        + TicketLog.GROUP_1 + ")))");
				hql.addParamter(uri.getUser().getUserId());
				hql.addParamter(TicketLog.ORDERTYPE_1);
			}

			if (uri.hasRight("sb93"))
			{
				// 所有退废订单
				if (rlf.getSysName().trim().equals(""))
					hql.add(" or  a.status in (" + AirticketOrder.GROUP_2 + ")");
				else
				{
					hql
					    .add("or  (exists(from TicketLog t where (t.sysUser.userNo like ? or t.sysUser.userName like ?)  and  t.orderId=a.id  and t.orderType=?  and t.type in ("
					        + TicketLog.GROUP_2 + ")))");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter(TicketLog.ORDERTYPE_1);
				}

			}
			else
			{
				hql
				    .add("or  (exists(from TicketLog t where t.sysUser.id=?  and  t.orderId=a.id  and t.orderType=?  and t.type in ("
				        + TicketLog.GROUP_2 + ")))");
				hql.addParamter(uri.getUser().getUserId());
				hql.addParamter(TicketLog.ORDERTYPE_1);
			}

			if (uri.hasRight("sb92"))
			{ // 所有改签订单
				if (rlf.getSysName().trim().equals(""))
					hql.add(" or  a.status in (" + AirticketOrder.GROUP_3 + ")");
				else
				{
					hql
					    .add("or  (exists(from TicketLog t where (t.sysUser.userNo like ? or t.sysUser.userName like ?)  and  t.orderId=a.id   and t.orderType=?  and t.type in ("
					        + TicketLog.GROUP_3 + ")))");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter(TicketLog.ORDERTYPE_1);
				}
			}
			else
			{
				hql
				    .add("or  (exists(from TicketLog t where t.sysUser.id=?  and  t.orderId=a.id   and t.orderType=?  and t.type in ("
				        + TicketLog.GROUP_3 + ")))");
				hql.addParamter(uri.getUser().getUserId());
				hql.addParamter(TicketLog.ORDERTYPE_1);
			}
			hql.add(")");
		}

		// PNR
		if (rlf.getPnr() != null && !"".equals(rlf.getPnr().trim()))
		{
			hql.add(" and (");
			hql.add(" a.drawPnr like ?");
			hql.addParamter("%" + rlf.getPnr().trim() + "%");

			hql.add(" or LOWER(a.subPnr) like LOWER(?)");
			hql.addParamter("%" + rlf.getPnr().trim() + "%");

			hql.add(" or a.bigPnr like ?");
			hql.addParamter("%" + rlf.getPnr().trim() + "%");
			hql.add(")");
		}

		// 订单号
		if (rlf.getOrderNo() != null && !"".equals(rlf.getOrderNo().trim()))
		{
			hql
			    .add("and (Lower(a.airOrderNo) like Lower(?) or Lower(a.orderNo) like Lower(?) or Lower(a.orderGroup.no) like Lower(?))");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
		}
		// flightCode;//航班号
		if (rlf.getFlightCode() != null && !"".equals(rlf.getFlightCode().trim()))
		{
			hql.add(" and exists(from Flight f where f.flightCode like '%"
			    + rlf.getFlightCode().trim() + "%' and f.airticketOrder.id=a.id)");
		}
		// cyr//承运人
		if (rlf.getCyr() != null && !"".equals(rlf.getCyr().trim()))
		{
			hql.add(" and exists(from Flight f where f.flightCode like '%"
			    + rlf.getCyr().trim() + "%' and f.airticketOrder.id=a.id)");
		}
		// ticketNumber;//票号
		if (rlf.getTicketNumber() != null
		    && !"".equals(rlf.getTicketNumber().trim()))
		{
			hql.add(" and exists(from Passenger p where  p.ticketNumber like '%"
			    + rlf.getTicketNumber().trim() + "%' and p.airticketOrder.id=a.id)");
		}
		// agentName;//乘客
		if (rlf.getAgentName() != null && !"".equals(rlf.getAgentName().trim()))
		{
			hql.add(" and exists(from Passenger p where  p.name like '%"
			    + rlf.getAgentName().trim() + "%' and p.airticketOrder.id=a.id)");
		}

		// 购票客户
		if (rlf.getAgentNo() != null && !"".equals(rlf.getAgentNo()))
		{
			hql.add(" and a.agent.id=" + rlf.getAgentNo());
		}
		/*
		 * // 出票人 if (rlf.getDrawer() != null && !"".equals(rlf.getDrawer().trim()))
		 * { hql.add(" and a.drawer like ?"); hql.addParamter("%" +
		 * rlf.getDrawer().trim() + "%");
		 * 
		 * }
		 */
		// 平台网电
		if (rlf.getDrawType() != null && rlf.getDrawType() != 99)
		{
			hql.add(" and a.platform.drawType= ? and a.businessType="
			    + AirticketOrder.BUSINESSTYPE__2);
			hql.addParamter(rlf.getDrawType());

		}

		// 操作人(录单人)
		/*
		 * if (rlf.getSysName() != null && !"".equals(rlf.getSysName().trim())) {
		 * hql.add("and a.entryOperator like ?"); hql.addParamter("%" +
		 * rlf.getSysName().trim() + "%"); }
		 */

		// 单个订单状态
		if (rlf.getAirticketOrder_status() > 0)
		{
			hql.add(" and a.status=" + rlf.getAirticketOrder_status());
		}
		// 多个订单状态
		if (rlf.getMoreStatus() != null && !"".equals(rlf.getMoreStatus().trim()))
		{
			hql.add(" and a.status  in (" + rlf.getMoreStatus() + ") ");
		}

		/*
		 * if (rlf.getScrap_status() == AirticketOrder.STATUS_88) {// 已废弃
		 * hql.add("and a.status not in (" + rlf.getScrap_status() + ")"); }
		 */
		hql.add(" and a.status not in (" + AirticketOrder.STATUS_88 + ")");
		if (rlf.getTicketType() > 0)
		{// 机票类型
			hql.add("and a.ticketType=" + rlf.getTicketType());
		}

		// 买入平台
		if (rlf.getFromPlatformId() > 0)
		{
			hql.add(" and a.platform.id=" + rlf.getFromPlatformId()
			    + " and businessType=" + AirticketOrder.BUSINESSTYPE__2);
		}
		// 卖出平台
		if (rlf.getToPlatformId() > 0)
		{
			hql.add(" and a.platform.id=" + rlf.getToPlatformId()
			    + " and businessType=" + AirticketOrder.BUSINESSTYPE__1);
		}

		// 付款
		if (rlf.getFromAccountId() > 0)
		{
			hql.add(" and a.account.id=" + rlf.getFromAccountId()
			    + " and businessType=" + AirticketOrder.BUSINESSTYPE__2);
		}

		// 收款
		if (rlf.getToAccountId() > 0)
		{
			hql.add(" and a.account.id=" + rlf.getToAccountId()
			    + " and businessType=" + AirticketOrder.BUSINESSTYPE__1);
		}

		// 最近N天

		if (rlf.getRecentlyDay() != null && rlf.getRecentlyDay().intValue() > 0)
		{
			hql.add(" and  trunc(sysdate -to_date(a.entryTime))<= "
			    + rlf.getRecentlyDay());
		}

		// 按日期搜索
		String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();

		if ("".equals(startDate) == false && "".equals(endDate) == true)
		{
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate) == true && "".equals(endDate) == false)
		{
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate) == false && "".equals(endDate) == false)
		{
			hql
			    .add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		// 多个 交易类型

		if (rlf.getOrderType() != null && !rlf.getOrderType().equals(""))
		{
			hql.add(" and a.tranType  in (" + rlf.getMoreTranType() + ")");
		}
		hql
		    .add(" and b.orderGroup.id=a.orderGroup.id and b.subGroupMarkNo=a.subGroupMarkNo)");// a.optTime
		// desc,

		hql.add(" and b.status not in (" + AirticketOrder.STATUS_88 + ")");
		if (rlf.getOrderBy().intValue() == 0)
			hql
			    .add(" order by  b.orderGroup.lastDate desc,b.orderGroup.id,b.subGroupMarkNo,b.tranType");// a.optTime
		// desc,
		else
			hql
			    .add(" order by  b.orderGroup.id desc,b.orderGroup.id,b.subGroupMarkNo,b.tranType");// a.optTime
		// desc,
		// System.out.println("query list>>>");
		// System.out.println(hql.getSql());
		Hql hql1 = new Hql();
		hql1.add("select count(distinct b.orderGroup.id) ");
		hql1.addHql(hql);

//		System.out.println(hql1.getSql());
		List list = this.list(hql1);
		if (list != null && list.size() > 0)
		{
			Object obj = list.get(0);
			Long count = (Long) obj;
			rlf.setGroupCount(count);
		}
		// long b = System.currentTimeMillis();
		// System.out.println(" over get  count time:" + ((b - a) / 1000) + "s");
//		System.out.println(hql);
		return this.list(hql, rlf);
	}

	public List listTeam(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException
	{
		Hql hql = new Hql();

		hql
		    .add("from AirticketOrder b where exists(select distinct a.orderGroup.id ");
		hql.add("from AirticketOrder a where 1=1 and ");

		// //查看订单 权限
		if (rlf != null && uri != null)
		{
			hql.add("(");
			if (uri.hasRight("sb94"))
			{
				// 所有正常订单
				if (rlf.getSysName().trim().equals(""))
					hql.add("  a.status in (" + AirticketOrder.GROUP_7 + ")");
				else
				{
					hql
					    .add(" (exists(from TicketLog t where (t.sysUser.userNo like ? or t.sysUser.userName like ?)  and  t.orderId=a.id   and t.orderType=?  and t.type in ("
					        + TicketLog.GROUP_7 + ")))");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter(TicketLog.ORDERTYPE_1);
				}
			}
			else
			{
				hql
				    .add(" (exists(from TicketLog t where t.sysUser.id=?  and  t.orderId=a.id   and t.orderType=?  and t.type in ("
				        + TicketLog.GROUP_7 + ")))");
				hql.addParamter(uri.getUser().getUserId());
				hql.addParamter(TicketLog.ORDERTYPE_1);
			}

			if (uri.hasRight("sb94"))
			{
				// 所有退废订单

				if (rlf.getSysName().trim().equals(""))
					hql.add(" or  a.status in (" + AirticketOrder.GROUP_8 + ")");
				else
				{
					hql
					    .add("or  (exists(from TicketLog t where (t.sysUser.userNo like ? or t.sysUser.userName like ?) and  t.orderId=a.id  and t.orderType=?  and t.type in ("
					        + TicketLog.GROUP_8 + ")))");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter("%" + rlf.getSysName().trim() + "%");
					hql.addParamter(TicketLog.ORDERTYPE_1);
				}
			}
			else
			{
				hql
				    .add("or  (exists(from TicketLog t where t.sysUser.id=?  and  t.orderId=a.id  and t.orderType=?  and t.type in ("
				        + TicketLog.GROUP_8 + ")))");
				hql.addParamter(uri.getUser().getUserId());
				hql.addParamter(TicketLog.ORDERTYPE_1);
			}
			hql.add(")");
		}

		// 订单号
		if (rlf.getOrderNo() != null && !"".equals(rlf.getOrderNo().trim())
		    && rlf.getAgentNo() != null && !"".equals(rlf.getAgentNo()))
		{
			hql
			    .add("and (Lower(a.airOrderNo) like Lower(?) or Lower(a.orderNo) like Lower(?) or Lower(a.orderGroup.no) like Lower(?) or  a.agent.id=?)");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter(rlf.getAgentNo());
		}
		else if (rlf.getOrderNo() != null && !"".equals(rlf.getOrderNo().trim())
		    && (rlf.getAgentNo() == null || "".equals(rlf.getAgentNo())))
		{
			hql
			    .add("and (Lower(a.airOrderNo) like Lower(?) or Lower(a.orderNo) like Lower(?) or Lower(a.orderGroup.no) like Lower(?) )");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
			hql.addParamter("%" + rlf.getOrderNo().trim() + "%");
		}
		else if (rlf.getOrderNo() == null || "".equals(rlf.getOrderNo().trim())
		    && (rlf.getAgentNo() != null && !"".equals(rlf.getAgentNo())))
		{
			hql.add("and a.agent.id=?");
			hql.addParamter(rlf.getAgentNo());

		}

		// cyr//承运人
		hql.add(" and exists(from Flight f where f.airticketOrder.id=a.id ");
		if (!rlf.getFlightCode().equals("") || !"".equals(rlf.getCyr().trim()))
		{
			hql.add(" and (Lower(f.flightCode) like Lower('%"
			    + rlf.getFlightCode().trim() + rlf.getCyr().trim() + "%')) ");
		}

		if (!rlf.getStartPoint().trim().equals(""))
		{
			hql.add(" and (Lower(f.startPoint)=Lower('" + rlf.getStartPoint().trim()
			    + "'))");
		}
		else if (!rlf.getEndPoint().trim().equals(""))
		{
			hql.add(" and (Lower(f.endPoint)=Lower('" + rlf.getEndPoint().trim()
			    + "'))");
		}

		hql.add(")");

		// 单个订单状态
		if (rlf.getAirticketOrder_status() > 0)
		{
			hql.add("and a.status=" + rlf.getAirticketOrder_status());
		}
		// 多个订单状态
		if (rlf.getMoreStatus() != null && !"".equals(rlf.getMoreStatus().trim()))
		{
			hql.add("and a.status  in (" + rlf.getMoreStatus() + ")");
		}

		/*
		 * if (rlf.getScrap_status() == AirticketOrder.STATUS_88) {// 已废弃
		 * hql.add("and a.status not in (" + rlf.getScrap_status() + ")"); }
		 */
		hql.add("and a.status not in (" + AirticketOrder.STATUS_88 + ")");
		if (rlf.getTicketType() > 0)
		{// 机票类型
			hql.add("and a.ticketType=" + rlf.getTicketType());
		}
		// 多个 交易类型

		if (rlf.getOrderType() != null && !rlf.getOrderType().equals(""))
		{
			hql.add(" and a.tranType  in (" + rlf.getMoreTranType() + ")");
		}

		// 出票类型
		if (rlf.getDrawer() != null && !"".equals(rlf.getDrawer().trim()))
		{
			hql.add(" and a.drawer=?");
			hql.addParamter(rlf.getDrawer().trim());

		}

		// 最近N天

		if (rlf.getRecentlyDay() != null && rlf.getRecentlyDay().intValue() > 0)
		{
			hql.add(" and  trunc(sysdate -to_date(a.entryTime))<= "
			    + rlf.getRecentlyDay());
		}

		// 按日期搜索
		String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();

		if ("".equals(startDate) == false && "".equals(endDate) == true)
		{
			hql.add(" and  a.optTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate) == true && "".equals(endDate) == false)
		{
			hql.add(" and  a.optTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate) == false && "".equals(endDate) == false)
		{
			hql
			    .add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		// 多个 交易类型
		hql
		    .add(" and b.orderGroup.id=a.orderGroup.id and b.subGroupMarkNo=a.subGroupMarkNo ");//
		hql.add(" and b.status not in (" + AirticketOrder.STATUS_88 + "))");

		if (rlf.getOrderBy() == 0)
			hql
			    .add(" order by  b.orderGroup.lastDate desc,b.orderGroup.id,b.subGroupMarkNo, b.tranType");// a.optTime
		// desc,
		else
			hql
			    .add(" order by  b.orderGroup.firstDate desc,b.orderGroup.id,b.subGroupMarkNo,b.tranType");// a.optTime
		// desc,
		// System.out.println("query list>>>");
		// System.out.println(hql.getSql());
		Hql hql1 = new Hql();
		hql1.add("select count(distinct b.orderGroup.id) ");
		hql1.addHql(hql);

//		System.out.println(hql1.getSql());
		List list = this.list(hql1);
		if (list != null && list.size() > 0)
		{
			Object obj = list.get(0);
			Long count = (Long) obj;
			rlf.setGroupCount(count);
		}
		rlf.setPerPageNum(20);
		return this.list(hql, rlf);
	}

	public List list() throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder where 1=1");
		return this.list(hql);
	}

	public void delete(long id) throws AppException
	{
		if (id > 0)
		{
			AirticketOrder airticketOrder = (AirticketOrder) this
			    .getHibernateTemplate().get(AirticketOrder.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(airticketOrder);
		}

	}

	public long save(AirticketOrder airticketOrder) throws AppException
	{
		if (airticketOrder.getId() <= 0)
		{
			airticketOrder.setOrderNo(noUtil.getOrderNo());
			airticketOrder.setCreateDate(new Timestamp(System.currentTimeMillis()));
		}
		this.getHibernateTemplate().save(airticketOrder);
		return airticketOrder.getId();
	}

	public long update(AirticketOrder airticketOrder) throws AppException
	{
		if (airticketOrder.getId() <= 0)
		{
			airticketOrder.setOrderNo(noUtil.getOrderNo());
			airticketOrder.setCreateDate(new Timestamp(System.currentTimeMillis()));
		}
		this.getHibernateTemplate().saveOrUpdate(airticketOrder);
		return airticketOrder.getId();
	}

	// 根据 drawPnr 查询
	public AirticketOrder airticketOrderByPNR(String pnr) throws AppException
	{
		Hql hql = new Hql("from AirticketOrder where drawPnr=?");
		hql.addParamter(pnr);

		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;
	}

	// 根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
	    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where a.id=" + airtickeOrderId);
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;
	}

	// 根据结算ID查询
	public AirticketOrder getAirticketOrderByStatementId(long statementId)
	    throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from AirticketOrder a,Statement s where a.id=s.orderId and s.orderType="
		        + Statement.ORDERTYPE_1 + " and s.id=" + statementId);

		Query query = this.getQuery(hql);

		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;
	}
	
	// 由订单ID查询GroupID
	public long getGroupIdByOrderId(long orderId)throws AppException
	{
		Hql hql = new Hql();
		hql .add("select a.orderGroup.id from AirticketOrder a where a.id=" + orderId);

		Query query = this.getQuery(hql);

		Long groupId = new Long(0);
		if (query != null ){
			List list=query.list();
			if(list!=null){
				if(list.size()>0){
					groupId=(Long) list.get(0);
				}
			}
		}

		return groupId;
	}

	// 订单组
	public List<AirticketOrder> listByGroupId(long groupId) throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where a.orderGroup.id=" + groupId);
		hql.add(" and a.status not in(" + AirticketOrder.STATUS_88 + ")");
		hql.add(" order by a.tranType ");
		// System.out.println("listByGroupId:"+groupId+"\n"+hql);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}

	public List<AirticketOrder> listByGroupIdAndTranType(long orderGroupId,
	    String tranType) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);
		if (tranType != null && !"".equals(tranType.trim()))
		{
			hql.add("and tranType in(" + tranType + ")");
		}
//		System.out.println(hql);
		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if (list != null && list.size() > 0)
				return list;

		}
		return list;
	}

	public List<AirticketOrder> listByGroupIdAndBusinessType(long orderGroupId,
	    String businessType) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);
		if (businessType != null && !"".equals(businessType.trim()))
		{
			hql.add("and businessType in(" + businessType + ")");
		}
//		System.out.println(hql);
		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if (list != null && list.size() > 0)
				return list;

		}
		return list;
	}

	// 根据订单组号 查询
	public List<AirticketOrder> listByGroupIdAndTranTypeStatus(long orderGroupId,
	    String tranType, String status) throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);

		if (tranType != null && !"".equals(tranType.trim()))
		{
			hql.add(" and tranType in(" + tranType + ")");
		}
		if (status != null && !"".equals(status.trim()))
		{
			hql.add(" and status in(" + status + ")");
		}

//		System.out.println("listByGroupIdAndTranTypeStatus>>" + hql);

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
		}
		return list;
	}

	// 根据订单组、小组号 查询
	public List<AirticketOrder> listBySubGroupAndGroupId(long orderGroupId,
	    Long subMarkNo) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql(" from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);

		hql.add(" and subGroupMarkNo =" + subMarkNo);

		hql.add(" and status not in(" + AirticketOrder.STATUS_88 + " ) ");

		hql.add(" order by a.id  ");

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if (list != null)
			{
				if (list.size() > 0) { return list; }
			}
		}
		return list;
	}
	

	// 根据订单组、小组号 查询
	public List<AirticketOrder> listBySubGroupByGroupIdAndType(long orderGroupId,
	    long subMarkNo, long businessType) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql(" from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);

		hql.add(" and subGroupMarkNo =" + subMarkNo);

		hql.add(" and businessType=" + businessType);

//		System.out.println(hql);

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
		}
		return list;
	}
	
	// 查询小组的订单IDList
	public List listIDBySubGroupAndGroupId(long orderGroupId,
	    Long subMarkNo) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql(" select a.id from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);

		hql.add(" and subGroupMarkNo =" + subMarkNo);

		hql.add(" and status not in(" + AirticketOrder.STATUS_88 + " ) ");

		hql.add(" order by a.id  ");

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if (list != null)
			{
				if (list.size() > 0) { return list; }
			}
		}
		return list;
	}
	
	// 查询整个大组的订单IDList
	public List listIDByGroupId(long orderGroupId) throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql(" select a.id from AirticketOrder a where 1=1 ");
		hql.add(" and a.orderGroup.id=" + orderGroupId);
		hql.add(" and status not in(" + AirticketOrder.STATUS_88 + " ) ");
		hql.add(" order by a.id  ");

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if (list != null)
			{
				if (list.size() > 0) { return list; }
			}
		}
		return list;
	}

	// 根据订单组号 业务、交易类型 查询
	public List<AirticketOrder> listByGroupIdAndBusinessTranType(
	    long orderGroupId, String tranType, String businessType)
	    throws AppException
	{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add("and a.orderGroup.id=" + orderGroupId);

		if (tranType != null && !"".equals(tranType.trim()))
		{
			hql.add("and tranType in(" + tranType + ")");
		}

		if (businessType != null && !"".equals(businessType.trim()))
		{
			hql.add("and businessType in(" + businessType + ")");
		}

		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
		}
		return list;
	}

	/**
	 * 取子分组号
	 */
	public long getNewSubGroupMarkNo(long orderGroupId)
	{
		try
		{
			Hql hql = new Hql(
			    "select max(subGroupMarkNo) from AirticketOrder a where 1=1 and status<>88");
			hql.add("and a.orderGroup.id=" + orderGroupId);

			Query query = this.getQuery(hql);
			if (query != null)
			{
				Object temp = query.uniqueResult();
				if (temp != null)
				{
					long x = (((Long) temp).intValue()) + 1;
					return x;
				}
			}
			return 0;
		}
		catch (Exception ex)
		{
			System.out.print(ex.getMessage());
			return -1;
		}
	}

	// 根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
	    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		if (subPnr != null && !"".equals(subPnr.trim()))
		{
			hql.add("and  LOWER(a.subPnr) =LOWER(?)");
			hql.addParamter(subPnr.trim());
		}
		hql.add("and a.tranType =" + AirticketOrder.TRANTYPE__2);
		hql.add("and a.status =" + AirticketOrder.STATUS_5);
		hql.add("and a.optTime is not null");
		hql.add("order by a.optTime desc");
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;
	}

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
	    long businessType, long tranType) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		if (subPnr != null && !"".equals(subPnr.trim()))
		{
			hql.add("and a.subPnr =?");
			hql.addParamter(subPnr.trim());
		}

		hql.add("and a.businessType =" + businessType);
		hql.add("and a.tranType =" + tranType);

		hql.add("and a.status =" + AirticketOrder.STATUS_5);
		hql.add("and a.entryTime is not null");
		hql.add("order by a.entryTime desc");

//		System.out.println("getAirticketOrderForRetireUmbuchen hql>>>>");
//		System.out.println(hql);

		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;
	}

	/**
	 * 
	 * 根据 预定编组号查询查询出票成功的订单
	 */
	public AirticketOrder getDrawedAirticketOrderByGroupId(long groupId,
	    long tranType) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");

		hql.add("and a.orderGroup.id=?");
		hql.addParamter(groupId);

		hql.add("and a.tranType =" + tranType);
		hql.add("and a.status =" + AirticketOrder.STATUS_5);

//		System.out.println("getDrawedAirticketOrderByGroupId>>>>>groupId:"
//		    + groupId);
		// System.out.println(hql);

		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null)
		{
			List list=query.list();
			if(list!=null){
				if(list.size()>0){
					airticketOrder = (AirticketOrder) query.list().get(0);
					if (airticketOrder != null && airticketOrder.getPlatform() != null)
					{
						airticketOrder.getPlatform().getName();
//						System.out.println(airticketOrder.getPlatform().getName());
					}
					if (airticketOrder != null && airticketOrder.getCompany() != null)
					{
						airticketOrder.getCompany().getName();
//						System.out.println(airticketOrder.getCompany().getName());						
					}
					if (airticketOrder != null && airticketOrder.getAccount() != null)
					{
						airticketOrder.getAccount().getName();
//						System.out.println(airticketOrder.getAccount().getName());
					}
				}
			}
		}
		return airticketOrder;
	}
	// 根据PNR获取出票订单（卖出）
	public List<AirticketOrder> getSaleDrawedOrderListByPNR(String subPnr) throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		if (subPnr != null && !"".equals(subPnr.trim()))
		{
			hql.add("and (LOWER(a.subPnr) =LOWER(?) or LOWER(a.drawPnr) =LOWER(?) or LOWER(a.bigPnr) =LOWER(?))");
			hql.addParamter(subPnr.trim());
			hql.addParamter(subPnr.trim());
			hql.addParamter(subPnr.trim());
		}
		hql.add("and a.tranType =" + AirticketOrder.TRANTYPE__1);
		hql.add("and a.status =" + AirticketOrder.STATUS_5);
		hql.add("and a.entryTime is not null");
		hql.add("order by a.entryTime desc");
		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if(list!=null){
				if(list.size()>0){
					return list;
				}
			}
		}
		return list;
	}

	// 根据PNR获取出票订单（买入）
	public List<AirticketOrder> getDrawedOrderListByPNR(String subPnr) throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		if (subPnr != null && !"".equals(subPnr.trim()))
		{
			hql.add("and (LOWER(a.subPnr) =LOWER(?) or LOWER(a.drawPnr) =LOWER(?) or LOWER(a.bigPnr) =LOWER(?))");
			hql.addParamter(subPnr.trim());
			hql.addParamter(subPnr.trim());
			hql.addParamter(subPnr.trim());
		}
		hql.add("and a.tranType =" + AirticketOrder.TRANTYPE__2);
		hql.add("and a.status =" + AirticketOrder.STATUS_5);
		hql.add("and a.entryTime is not null");
		hql.add("order by a.entryTime desc");
		Query query = this.getQuery(hql);
		if (query != null)
		{
			list = query.list();
			if(list!=null){
				if(list.size()>0){
					return list;
				}
			}
		}
		return list;
	}

	// 验证pnr是一天内否重复添加
	public boolean checkPnrisToday(AirticketOrder airticketOrder)
	    throws AppException
	{
		boolean bole = true;
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		if (airticketOrder.getSubPnr() != null
		    && airticketOrder.getTranType() != null)
		{
			hql.add("and a.subPnr =?");
			hql.addParamter(airticketOrder.getSubPnr().trim());
			hql.add("and a.tranType=?");
			hql.addParamter(airticketOrder.getTranType());
			hql.add("and a.entryTime like sysdate");
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				bole = false;
			}
		}
		return bole;
	}

	// 验证一个PNR是否存在多个退费 改签
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
	    throws AppException
	{
		boolean bole = true;
		Hql hql = new Hql("from AirticketOrder a where 1=1 and status<>"
		    + AirticketOrder.STATUS_88);
		if (airticketOrder.getSubPnr() != null
		    && airticketOrder.getTranType() != null)
		{
			hql.add(" and LOWER(a.subPnr) =LOWER(?)");
			hql.addParamter(airticketOrder.getSubPnr().trim());
			hql.add(" and a.tranType=?");
			hql.addParamter(airticketOrder.getTranType());
			hql
			    .add(" and  to_char(a.entryTime,'yyyy-mm-dd')  between to_char(add_months(sysdate,-1),'yyyy-mm-dd') and   to_char(sysdate,'yyyy-mm-dd')");
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				bole = false;
			}

		}
		return bole;
	}

	public List<AirticketOrder> listByCarrier(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		Hql hql = new Hql(" from AirticketOrder a  where a.status=5 and a.platform.drawType=1 and a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id ");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id))");
		hql.addParamter(sd);
		hql.addParamter(ed);
//		System.out.println("listByCarrier"+hql);
		return list(hql);
	}
	


	/**
	 * 初始化后返政策的信息，将利润、后返政策、后返利润设为零。
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean iniProfitAfterInformation(String carrier,Timestamp startDate, Timestamp endDate) throws AppException{
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		String hql = "update AirticketOrder a set a.profitAfter=0,a.rateAfter=0,profit=0 where 1=1"+
				" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date('"+sd+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+ed+"','yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id)";
		Query query;
		int row;
		try {
			query = this.getSession().createQuery(hql);
			row = query.executeUpdate();
			System.out.println("AirticketOrderDaoImp.iniProfitAfterInformation初始化修改行数:"+row);
			return true;
		} catch (DataAccessResourceFailureException e) {
			System.out.println(e.getMessage());
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	
	
	/**
	 * 分段获取
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @param startRow
	 * @param rowCount
	 * @return
	 * @throws AppException
	 */
	
	public List<AirticketOrder> listByCarrier(String carrier,Timestamp startDate,Timestamp endDate,
			int startRow,int rowCount) throws AppException
	{
		getHibernateTemplate().clear();
		List<AirticketOrder> aoList;
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));

		Hql hql = new Hql("from AirticketOrder a  where a.status=5 and platform.drawType=1 and  a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id)");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id) order by a.orderNo");
		hql.addParamter(sd);
		hql.addParamter(ed);
		Query query = getQuery(hql);
		query.setFirstResult(startRow);
		query.setMaxResults(rowCount);
		aoList = new ArrayList<AirticketOrder>();
//		System.out.println("listByCarrier: "+hql);
		if(query != null){
			aoList = query.list();
			if(aoList != null){
				return aoList;
			}
			return new ArrayList();
		}
		return  new ArrayList();
	}
	


	/**
	 * 查询符合条件的总记录数
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @param startRow
	 * @param rowCount
	 * @return
	 * @throws AppException
	 */
	public int rowCountByCarrier(String carrier,Timestamp startDate, Timestamp endDate) throws AppException {
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		Hql hql = new Hql("select count(*) from AirticketOrder a  where a.status=5 and platform.drawType=1 and  a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id)");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id) order by a.orderNo");
		hql.addParamter(sd);
		hql.addParamter(ed);
		Query query = getQuery(hql);
		if(query != null){
			Object ob = query.uniqueResult();
			return Integer.parseInt(ob.toString());
		}
		return 0;
	}
	
	public int sumTicketNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{

		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		Hql hql = new Hql();
		hql.add("select count(*) from Passenger p");
		//platform.drawType 交易平台  AirticketOrder.BUSINESSTYPE__2:业务类型为买入
		hql.add(" where exists(from AirticketOrder a where p.airticketOrder.id=a.id  and a.status=5 and platform.drawType= 1 " +
				"and  a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id) ");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and  airticketOrder.id is not null and f.airticketOrder.id=a.id))");
		hql.addParamter(sd);
		hql.addParamter(ed);
		Query query=this.getQuery(hql);
		if(query!=null)
		{
			Object obj=query.uniqueResult();
			if(obj!=null)
			{
				return Constant.toInt(obj.toString());
			}
		}
		return 0;
	}

	public int sumOrderNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		Hql hql = new Hql();
		hql.add("select count(*) from AirticketOrder a where a.status=5 and platform.drawType= 1 and   a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id) ");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id)");
		Query query=this.getQuery(hql);
		if(query!=null)
		{
			Object obj=query.uniqueResult();
			if(obj!=null)
			{
				return Constant.toInt(obj.toString());
			}
		}
		return 0;
	}
	
	
	public BigDecimal sumSaleAmount(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		Hql hql = new Hql();
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		hql.add("select sum(totalAmount) from AirticketOrder a where  a.status=5 and platform.drawType=1 and   a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id) ");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id)");
		hql.addParamter(sd);
		hql.addParamter(ed);
		Query query = this.getQuery(hql);
		if(query!=null)
		{
			Object obj=query.uniqueResult();
			if(obj!=null)
			{
				return new BigDecimal(obj.toString());
			}
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 获取后返佣金
	 */
	public List<BigDecimal> sumProfitAfter(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		Hql hql = new Hql();
		String sdTemp = startDate.toString();
		String sd = sdTemp.substring(0,sdTemp.lastIndexOf("."));
		String edTemp = endDate.toString();
		String ed = edTemp.substring(0, edTemp.lastIndexOf("."));
		hql.add("select sum(profitAfter), sum(profit) from AirticketOrder a where a.status=5 and profitAfter>0" +
				" and  platform.drawType=1 and  a.tranType="+AirticketOrder.TRANTYPE__2);
//		hql.add(" and not exists (from AirticketOrder b where b.tranType in(3,4,5) and b.referenceId=a.id) ");
		hql.add(" and  not exists ( from AirticketOrder b where b.tranType in(3,4,5) and a.orderGroup.id = b.orderGroup.id)");
		hql.add(" and exists(from Flight f where f.flightCode like '%"+carrier+"%'" +
				" and  f.boardingTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')"+
				" and f.airticketOrder.id=a.id)");
		hql.addParamter(sd);
		hql.addParamter(ed);
		Query query=this.getQuery(hql);
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		if(query!=null)
		{
			Object[] obj = (Object[]) query.uniqueResult();
			for(int i=0;i<obj.length;i++){
				if(obj[i] != null && !"".equals(obj[i])){
					list.add(new BigDecimal(obj[i].toString()));
				}else{
					list.add(BigDecimal.ZERO);
				}
				
			}
		}
		return list;
	}
	
	public long saveOrderGroup(OrderGroup og) throws AppException
	{

		this.getHibernateTemplate().saveOrUpdate(og);
		return og.getId();
	}

	public OrderGroup getOrderGroupById(long id) throws AppException
	{
		if (id > 0)
		{
			OrderGroup orderGroup = (OrderGroup) this.getHibernateTemplate().get(
			    OrderGroup.class, Long.valueOf(id));
			return orderGroup;
		}
		return null;
	}
	
	public void setNoUtil(NoUtil noUtil)
	{
		this.noUtil = noUtil;
	}
}

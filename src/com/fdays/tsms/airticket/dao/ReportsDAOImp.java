package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportsDAOImp extends BaseDAOSupport implements ReportsDAO{
	
	//原始销售报表
	public List marketReportsList(AirticketOrderListForm rlf) throws AppException {

		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		//出票PNR
		if(rlf.getDrawPnr()!=null&&!"".equals(rlf.getDrawPnr().trim())){
			hql.add("and a.drawPnr like ?");
			hql.addParamter( "%" + rlf.getDrawPnr().trim()+ "%");	
		}
		//预定PNR
        if(rlf.getSubPnr()!=null&&!"".equals(rlf.getSubPnr().trim())){
        	hql.add("and a.subPnr like ?");
        	hql.addParamter( "%" + rlf.getSubPnr().trim()+ "%");
		}
        //大PNR
        if(rlf.getBigPnr()!=null&&!"".equals(rlf.getBigPnr().trim())){
        	hql.add("and a.bigPnr like ?");
        	hql.addParamter( "%" + rlf.getBigPnr().trim()+ "%");
		}
        //关联订单号
        if(rlf.getGroupMarkNo()!=null&&!"".equals(rlf.getGroupMarkNo().trim())){
        	hql.add("and a.groupMarkNo like ?");
        	hql.addParamter( "%" + rlf.getGroupMarkNo().trim()+ "%");
		}
        //订单号
        if(rlf.getAirOrderNo()!=null&&!"".equals(rlf.getAirOrderNo().trim())){
        	hql.add("and a.airOrderNo like ?");
        	hql.addParamter( "%" + rlf.getAirOrderNo().trim()+ "%");
		}
        //flightCode;//航班号
        if(rlf.getFlightCode()!=null&&!"".equals(rlf.getFlightCode().trim())){
        	hql.add(" and exists(from Flight f where f.flightCode like '%"+rlf.getFlightCode().trim()+"%' and f.airticketOrder.id=a.id)");
		}
        //ticketNumber;//票号
        if(rlf.getTicketNumber()!=null&&!"".equals(rlf.getTicketNumber().trim())){
        	hql.add(" and exists(from Passenger p where  p.ticketNumber like '%"+rlf.getTicketNumber().trim()+"%' and p.airticketOrder.id=a.id)");
		}
        //agentName;//乘客
        if(rlf.getAgentName()!=null&&!"".equals(rlf.getAgentName().trim())){
        	hql.add(" and exists(from Passenger p where  p.name like '%"+rlf.getAgentName().trim()+"%' and p.airticketOrder.id=a.id)");
		}
        //操作人
        if(rlf.getSysName()!=null&&!"".equals(rlf.getSysName().trim())){
        	hql.add("and a.statement.sysUser.userName like ?");
        	hql.addParamter( "%" + rlf.getSysName().trim()+ "%");		
        }
        //订单状态
        if(rlf.getAirticketOrder_status()>0){
        	hql.add("and a.status="+rlf.getAirticketOrder_status());
		}
        //机票类型
        if(rlf.getTicketType()>0){
        	hql.add("and a.ticketType="+rlf.getTicketType());
		}
        //结算类型
        if(rlf.getStatement().getType()>0){
        	hql.add("and a.statement.type="+rlf.getStatement().getType());
        }
        //平台
        if(rlf.getPlatformId()>0){
        	if(rlf.getStatement().getType()==Statement.type_1){
        	hql.add("and a.statement.fromPCAccount.platform.id="+rlf.getPlatformId());
        	}else if(rlf.getStatement().getType()==Statement.type_2){
        	hql.add("and a.statement.toPCAccount.platform.id="+rlf.getPlatformId());
        	}
        }
        hql.add("order by a.groupMarkNo ,a.tranType");
		return this.list(hql, rlf);
	}
	
	//平台帐号表
	public List platComAccountList(AirticketOrderListForm rlf) throws AppException {

		Hql hql = new Hql();
		hql.add("from PlatComAccount p where 1=1");
		return this.list(hql, rlf); 	
	}
	
	//银行卡付款统计
	public List getStatementList(StatementListForm statementForm) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1");
		return this.list(hql,statementForm);
		
	}
	
}

create or replace procedure create_OPT_TRANSACTION(opt_date TIMESTAMP,sessionId String,userDepart long) is

begin
  delete from OPT_TRANSACTION o where o.session_id=sessionId;

  insert into OPT_TRANSACTION
    (
      id,
      USER_NAME,
      SELLORDERSTOTAL,
      NORMALORDER,--正常有效订单数      
      REFUNDORDER,--退票
      INVALIDORDER,--废票
      ALTEREDORDER,--改签订单数
      CANCELORDER,--取消订单数
      SOLDTICKET_COUNT,--卖出机票数量
      INAMOUNT,
      OUTAMOUNT,
      PROFIT,
      REFUNDAMOUNTRECEIVED,--收退废退款
      REFUNDAMOUNTPAID,--付退废退款
      CANCELTICKETCOLLECTION,
      CANCELTICKETREFUND,
      session_id,
      OPT_DATE
    )
   select
     SEQ_OPTTRANSACTION.Nextval,
     u.user_name,
     0,
     cal_airticketOrder_count(u.user_id,1),    
     cal_airticketOrder_count(u.user_id,2),
     cal_airticketOrder_count(u.user_id,3),
     cal_airticketOrder_count(u.user_id,4),
     cal_airticketOrder_count(u.user_id,5),     
     cal_airticketnumber_by_user(u.user_id,1),
     cal_airticketOrder_amount(u.user_id,1),
     cal_airticketOrder_amount(u.user_id,2),
     0,
     cal_airticketOrder_amount(u.user_id,3),
     cal_airticketOrder_amount(u.user_id,4),
     0,
     0,
     sessionId,
     cast(opt_date as date)
     from sys_user u,dual d
     where u.user_depart =userDepart;
     commit;
     -----------
     update OPT_TRANSACTION set SELLORDERSTOTAL=(NORMALORDER+REFUNDORDER+INVALIDORDER+ALTEREDORDER+CANCELORDER) where 1=1 ;
     update OPT_TRANSACTION set profit=(INAMOUNT-OUTAMOUNT) where 1=1 ;
     -------------
     commit;

end create_OPT_TRANSACTION;
/

create or replace function cal_airticketOrder_count(userId integer,ptype integer)
  return NUMBER is
  result  NUMBER(15,2);
  normalcount  NUMBER(15,2):=0;--正常有效订单总数
  retirecount  NUMBER(15,2):=0;--退票订单总数
  abolishcount number(15,2):=0;--废票订单总数
  umbuchencount  NUMBER(15,2):=0;--改签订单总数
  cancelcount  NUMBER(15,2):=0;--取消订单总数
  totalcount  NUMBER(15,2):=0;--卖出订单总数
begin
    if ptype=1 then               
       select count(distinct o.group_mark_no) into normalcount from airticket_order o
       where  o.status not in(22,32,45)  --退、废、改签完成
       and  exists(select t.order_no from ticket_log t where o.group_mark_no=t.order_no 
                  and t.user_id=userId and o.status in(5,22,32,45) and  t.type=7) ;                 
       totalcount :=normalcount;
    end if;
     if ptype=2 then
       select count(distinct o.group_mark_no) into retirecount from airticket_order o,ticket_log t
       where o.group_mark_no=t.order_no and t.user_id=userId and o.status in(22) and  t.type=12;  
       totalcount :=retirecount;
    end if;
     if ptype=3 then
       select count(distinct o.group_mark_no) into abolishcount from airticket_order o,ticket_log t
       where o.group_mark_no=t.order_no and t.user_id=userId and o.status in(32) and  t.type=17;  
      totalcount :=abolishcount;
    end if;
     if ptype=4 then
       select count(distinct o.group_mark_no) into abolishcount from airticket_order o,ticket_log t
       where o.group_mark_no=t.order_no and t.user_id=userId and o.status in(45) and  t.type=26;  
      totalcount :=umbuchencount;
    end if;
     if ptype=5 then
      --买入、卖出取消
       select count(distinct o.group_mark_no) into cancelcount from airticket_order o,ticket_log t
       where o.group_mark_no=t.order_no and t.user_id=userId and o.status in(5) and  t.type in(4,6);  
      totalcount :=cancelcount;
    end if;   
     if ptype=6 then
        ----卖出订单总数
       select count(distinct o.group_mark_no) into totalcount from airticket_order o,ticket_log t
       where o.group_mark_no=t.order_no and t.user_id=userId and o.status in(5,22,32,45) and t.type in(5,12,17); 
      totalcount :=totalcount;
    end if;

    result :=totalcount;
    return result;

  end cal_airticketOrder_count;
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
/

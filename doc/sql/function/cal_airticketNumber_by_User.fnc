create or replace function cal_airticketNumber_by_User(userId integer,ptype integer)
  return NUMBER is
  result  NUMBER(15,2);
  ticket_number  NUMBER(15,2):=0;--正常卖出机票数
begin
    if ptype=1 then
      select count(distinct t.order_no) into ticket_number from ticket_log t,airticket_order o
        where  t.order_no=o.group_mark_no
        and o.status in(5,22,32,45)--交易结束的订单状态
        and  t.type in(5,12,17)--正常出票完成
        and t.user_id=userId;--操作员
        
        ticket_number:=ticket_number;
    end if;

    result :=ticket_number;
    return result;

  end cal_airticketNumber_by_User;
/

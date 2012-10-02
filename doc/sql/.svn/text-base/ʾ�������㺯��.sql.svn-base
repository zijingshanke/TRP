create or replace function cal_curr_bal_by_date(spittorId integer,
                                                         todate DATE)
  return NUMBER is
  result  NUMBER(15,2);
  inamount      NUMBER(15,2):=0;
  outamount      NUMBER(15,2):=0;

begin

    select sum(amount)
      into inamount
      from SPITTOR_ACCOUNT t
     where FINISH_DATE <=todate
       and to_spittor_id = spittorId;
    select sum(amount)
      into outamount
    from SPITTOR_ACCOUNT t
     where FINISH_DATE <=todate
       and from_spittor_id = spittorId;

    if inamount is null then
      inamount:=0;
    end if;
   if outamount is null then
      outamount:=0;
    end if;

    result :=inamount-outamount;
    return result;

  end cal_curr_bal_by_date;


---µ÷ÓÃÊ¾Àý
select cal_curr_bal_by_date(68,sysdate) as banlance from dual;
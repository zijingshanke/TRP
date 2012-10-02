create or replace function cal_passengerCount(orderId integer,ptype integer)
  return NUMBER is
  result  NUMBER(15,2);
  totalcount  NUMBER(15,2):=0;
  adultcount  NUMBER(15,2):=0;
  childcount  NUMBER(15,2):=0;
  babycount  NUMBER(15,2):=0;
begin
    if ptype=1 then
        select count(*) into adultcount from passenger p where p.air_order_id=orderId and p.type=1;
        totalcount :=adultcount;
    end if;
     if ptype=2 then
       select count(*) into childcount from passenger p where p.air_order_id=orderId and p.type=2;
       totalcount :=childcount;
    end if;
     if ptype=3 then
      select count(*) into babycount from passenger p where p.air_order_id=orderId and p.type=3;
      totalcount :=babycount;
    end if;
     if ptype=4 then
       select count(*) into adultcount from passenger p where p.air_order_id=orderId and p.type=1;
       select count(*) into childcount from passenger p where p.air_order_id=orderId and p.type=2;
       select count(*) into babycount from passenger p where p.air_order_id=orderId and p.type=3;       
      totalcount :=adultcount+childcount+babycount;
    end if;

    result :=totalcount;
    return result;

  end cal_passengerCount;
/

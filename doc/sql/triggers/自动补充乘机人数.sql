select * from airticket_order o;
select * from passenger p;

select count(*) from passenger p where p.air_order_id=581;

select cal_passengercount(581,4) from dual;

update airticket_order o 
       set o.adult_count=(select cal_passengercount(581,4) from dual)
       where o.id=581;   

create or replace trigger trig_airorder_passenger     
       after insert or update or delete
       of id,air_order_id
       on passenger
       for each row    
       begin
         if inserting then
            update airticket_order o 
                   set o.adult_count=(select cal_passengercount(:new.air_order_id,1) from dual),
                       o.child_count=(select cal_passengercount(:new.air_order_id,2) from dual),
                       o.baby_count=(select cal_passengercount(:new.air_order_id,3) from dual) 
             where o.id=:new.air_order_id;
         
       elsif updating then
           update airticket_order o 
                   set o.adult_count=(select cal_passengercount(:new.air_order_id,1) from dual),
                       o.child_count=(select cal_passengercount(:new.air_order_id,2) from dual),
                       o.baby_count=(select cal_passengercount(:new.air_order_id,3) from dual) 
             where o.id=:new.air_order_id;
       elsif deleting then
             update airticket_order o 
                   set o.adult_count=(select cal_passengercount(:new.air_order_id,1) from dual),
                       o.child_count=(select cal_passengercount(:new.air_order_id,2) from dual),
                       o.baby_count=(select cal_passengercount(:new.air_order_id,3) from dual) 
             where o.id=:new.air_order_id;
       end if;
end;

























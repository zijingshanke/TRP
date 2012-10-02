create or replace function cal_rakeoff_by_userIdmyType(userId integer,myType integer) ---²éÑ¯¶©µ¥Êý
  return INTEGER is
  result  INTEGER;
  myCount  INTEGER:=0;


begin
  if myType=1 then       ---???
     select count(*)
       into myCount from ticket_log t
        where t.user_id=userId and
           t.type=myType;
     end if;
  if myType=2 then      ---???
     select count(*)
       into myCount from ticket_log t
        where t.user_id=userId and
           t.type=myType;
     end if;
  if myType=3 then     ---???
     select count(*)
       into myCount from ticket_log t
        where t.user_id=userId and
           t.type=myType;
     end if;


    if myCount<0 then
      myCount:=0;
    end if;


    result :=myCount;
    return result;

  end cal_rakeoff_by_userIdmyType;
/

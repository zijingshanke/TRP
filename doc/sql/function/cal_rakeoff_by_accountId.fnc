create or replace function cal_rakeoff_by_accountId(accountId integer)  ---²éÑ¯ºó·µÓ¶½ð
  return NUMBER is
  result  NUMBER(15,2);
  inamount      NUMBER(15,2):=0;
  outamount      NUMBER(15,2):=0;

begin
     select sum(s.rake_off)
     into inamount from statement s
      where s.status in (1,2) and
       s.type=2 and
        s.to_account_id in
         (select p.id from plat_com_account p where p.account_id=accountId);

    select sum(s.rake_off)
     into outamount from statement s
      where s.status in (1,2) and
       s.type=1 and
        s.from_account_id in
        (select p.id from plat_com_account p where p.account_id=accountId);


    if inamount is null then
      inamount:=0;
    end if;
   if outamount is null then
      outamount:=0;
    end if;

    result :=inamount-outamount;
    return result;

  end cal_rakeoff_by_accountId;
/

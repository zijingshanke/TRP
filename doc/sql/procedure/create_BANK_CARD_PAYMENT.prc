create or replace procedure create_BANK_CARD_PAYMENT(banlanceDate TIMESTAMP,sessionId String) is

begin
  delete from BANK_CARD_PAYMENT b where b.session_id=sessionId;
  
  insert into BANK_CARD_PAYMENT
    (
      id,
      USER_NAME,
      ACCOUNT1,
      ACCOUNT2,
      ACCOUNT3,
      ACCOUNT4,
      ACCOUNT5,
      ACCOUNT6,
      ACCOUNT7,
      ACCOUNT8,
      ACCOUNT9,
      ACCOUNT10,
      ACCOUNT11,
      ACCOUNT12,
      ACCOUNT13,
      ACCOUNT14,
      ACCOUNT15,
      ACCOUNT16,
      session_id,
      SUBTOTAL,
      TOTAL,
     banlance_date
    )
   select
     seq_BANKCARDPAYMENT.nextval,
     u.user_name,
     cal_payAccount_byAccountUserId(58,u.user_id,1), ---1代表只查询付款金额
     cal_payAccount_byAccountUserId(34,u.user_id,1),
     cal_payAccount_byAccountUserId(40,u.user_id,1),
     cal_payAccount_byAccountUserId(27,u.user_id,1),
     cal_payAccount_byAccountUserId(39,u.user_id,1),
     cal_payAccount_byAccountUserId(38,u.user_id,1),
     cal_payAccount_byAccountUserId(37,u.user_id,1),
     cal_payAccount_byAccountUserId(36,u.user_id,1),
     cal_payAccount_byAccountUserId(35,u.user_id,1),
     cal_payAccount_byAccountUserId(44,u.user_id,1),
     cal_payAccount_byAccountUserId(59,u.user_id,1),
     cal_payAccount_byAccountUserId(45,u.user_id,1),
     cal_payAccount_byAccountUserId(60,u.user_id,1),
     cal_payAccount_byAccountUserId(61,u.user_id,1),
     cal_payAccount_byAccountUserId(62,u.user_id,1),
     cal_payAccount_byAccountUserId(54,u.user_id,1),
      
     ---cal_payAccount_byAccountUserId(89000,u.user_id,1),
     ---cal_payAccount_byAccountUserId(89001,u.user_id,1),
    --- cal_payAccount_byAccountUserId(88888,u.user_id,1),
    sessionId,
     0,
     0,
     cast(banlanceDate as date)
     from sys_user u,dual d;
     commit;
     -----------
     update BANK_CARD_PAYMENT   set subtotal=(ACCOUNT1+ACCOUNT2+ACCOUNT3+ACCOUNT4+ACCOUNT5+ACCOUNT6+ACCOUNT7+ACCOUNT8+ACCOUNT9+ACCOUNT10+ACCOUNT11+ACCOUNT12+ACCOUNT13+ACCOUNT14+ACCOUNT15) where 1=1 ;   
     -------------
     commit;

end create_BANK_CARD_PAYMENT;
/

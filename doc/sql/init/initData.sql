

insert into platform (ID, NAME, TYPE, STATUS)
values (87000, '珠海品尚物流平台(系统)', 2, 0);

insert into company (ID, NAME, TYPE, STATUS)
values (88888, '珠海泰申发展有限公司(系统)', 1, 0);

insert into payment_tool (ID, NAME, TYPE, STATUS)
values (88888, '现金(系统)', 2, 0);


------------------B2C------------------------

insert into platform (ID, NAME, TYPE, STATUS)
values (88888, 'B2C散客(系统)', 2, 0);

insert into account (ID, PAY_TOOL_ID, NAME, ACCOUNT_NO, TYPE, STATUS)
values (88888, 88888, 'B2C欠款(系统)', 'test@qq.com', 1, 0);

insert into plat_com_account (ID, COMPANY_ID, PLATFORM_ID, ACCOUNT_ID, STATUS, TYPE)
values (88888, 88888, 88888, 88888, 0, 3);


------------------团队票-----------------------------

insert into account (ID, PAY_TOOL_ID, NAME, ACCOUNT_NO, TYPE, STATUS)
values (89000, 89000, '团队票收款(系统)', '123@qq.com', 1, 0);

insert into account (ID, PAY_TOOL_ID, NAME, ACCOUNT_NO, TYPE, STATUS)
values (89001, 89000, '团队票付款(系统)', '456@qq.com', 1, 0);


insert into plat_com_account (ID, COMPANY_ID, PLATFORM_ID, ACCOUNT_ID, STATUS, TYPE)
values (90000, 88888, 87000, 89000, 0, 2);

insert into plat_com_account (ID, COMPANY_ID, PLATFORM_ID, ACCOUNT_ID, STATUS, TYPE)
values (90001, 88888, 87000, 89001, 0, 1);


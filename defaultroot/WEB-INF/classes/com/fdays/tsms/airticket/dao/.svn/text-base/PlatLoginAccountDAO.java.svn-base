package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.PlatLoginAccount;
import com.fdays.tsms.airticket.PlatLoginAccountListForm;
import com.neza.exception.AppException;

public interface PlatLoginAccountDAO {

	public List list(PlatLoginAccountListForm plf) throws AppException;
	public void delete(long id)  throws AppException;
	public long save(PlatLoginAccount platLoginAccount) throws AppException;
	public long update(PlatLoginAccount platLoginAccount) throws AppException;
	//平台登录帐号ID 查询
	public PlatLoginAccount getPlatLoginAccountById(long platLoginAccountId) throws AppException;
	
}

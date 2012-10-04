package com.fdays.tsms.airticket.biz;

import java.util.List;

import com.fdays.tsms.airticket.PlatLoginAccount;
import com.fdays.tsms.airticket.PlatLoginAccountListForm;
import com.fdays.tsms.airticket.dao.PlatLoginAccountDAO;
import com.neza.exception.AppException;

public class PlatLoginAccountBizImp implements PlatLoginAccountBiz {

	
	PlatLoginAccountDAO platLoginAccountDAO;
	
	public List list(PlatLoginAccountListForm plf) throws AppException
	{
		return platLoginAccountDAO.list(plf);
	}
	
	public long delete(long id)  throws AppException
	{
		try {
			platLoginAccountDAO.delete(id);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
	public long save(PlatLoginAccount platLoginAccount) throws AppException
	{
		return platLoginAccountDAO.save(platLoginAccount);
	}
	
	public long update(PlatLoginAccount platLoginAccount) throws AppException
	{
		return platLoginAccountDAO.update(platLoginAccount);
	}
	
	//平台登录帐号ID 查询
	public PlatLoginAccount getPlatLoginAccountById(long platLoginAccountId) throws AppException
	{
		return platLoginAccountDAO.getPlatLoginAccountById(platLoginAccountId);
	}
	
	public PlatLoginAccountDAO getPlatLoginAccountDAO() {
		return platLoginAccountDAO;
	}
	public void setPlatLoginAccountDAO(PlatLoginAccountDAO platLoginAccountDAO) {
		this.platLoginAccountDAO = platLoginAccountDAO;
	}

}

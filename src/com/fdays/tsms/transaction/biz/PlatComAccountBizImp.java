package com.fdays.tsms.transaction.biz;

import java.util.List;



import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountListForm;
import com.fdays.tsms.transaction.dao.PlatComAccountDAO;
import com.neza.exception.AppException;

public class PlatComAccountBizImp implements PlatComAccountBiz{

	PlatComAccountDAO platComAccountDAO;

	public PlatComAccountDAO getPlatComAccountDAO() {
		return platComAccountDAO;
	}

	public void setPlatComAccountDAO(PlatComAccountDAO platComAccountDAO) {
		this.platComAccountDAO = platComAccountDAO;
	}
	
	
	//分页查询
	public List list(PlatComAccountListForm platComAccountForm) throws AppException
	{
		return platComAccountDAO.list(platComAccountForm);
	}
	// 删除
	public long delete(long id) throws AppException
	{
		try {
			platComAccountDAO.delete(id);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	// 添加保存
	public long save(PlatComAccount platComAccount) throws AppException
	{
		return platComAccountDAO.save(platComAccount);
	}
	// 修改
	public long update(PlatComAccount platComAccount) throws AppException
	{
		return platComAccountDAO.update(platComAccount);
	}
	//根据id查询
	public PlatComAccount getPlatComAccountById(long platComAccountId) throws AppException
	{
		return platComAccountDAO.getPlatComAccountById(platComAccountId);
	}
	//查询 返回一个list集合
	public List<PlatComAccount> getPlatComAccountList() throws AppException
	{
		return platComAccountDAO.getPlatComAccountList();
	}

	
}

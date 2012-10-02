package com.fdays.tsms.transaction.biz;

import java.util.List;



import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.fdays.tsms.transaction.dao.PlatformDAO;
import com.neza.exception.AppException;

public class PlatformBizImp implements PlatformBiz{

	
	PlatformDAO platformDAO;

	public PlatformDAO getPlatformDAO() {
		return platformDAO;
	}

	public void setPlatformDAO(PlatformDAO platformDAO) {
		this.platformDAO = platformDAO;
	}
	

	//分页查询
	public List list(PlatformListForm platformForm) throws AppException
	{
		return platformDAO.list(platformForm);
	}
	// 删除
	public long delete(long id) throws AppException
	{
		try {
			platformDAO.delete(id);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	// 添加保存
	public long save(Platform platform) throws AppException
	{
		return platformDAO.save(platform);
	}
	// 修改
	public long update(Platform platform) throws AppException
	{
		return platformDAO.update(platform);
	}
	//根据id查询
	public Platform getPlatformByid(long platformId) throws AppException
	{
		return platformDAO.getPlatformByid(platformId);
	}
	//查询 返回一个list集合
	public List<Platform> getPlatformList() throws AppException
	{
		return platformDAO.getPlatformList();
	}
}

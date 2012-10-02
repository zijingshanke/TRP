package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.neza.exception.AppException;

public interface PlatformDAO {

	
	//分页查询
	public List list(PlatformListForm platformForm) throws AppException;
	// 删除
	public void delete(long id) throws AppException;
	// 添加保存
	public long save(Platform platform) throws AppException;
	// 修改
	public long update(Platform platform) throws AppException;
	//根据id查询
	public Platform getPlatformByid(long platformId) throws AppException;
	//查询 返回一个list集合
	public List<Platform> getPlatformList() throws AppException;
}

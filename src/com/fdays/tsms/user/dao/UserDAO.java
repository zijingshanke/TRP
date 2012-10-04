package com.fdays.tsms.user.dao;

import java.util.List;

import com.fdays.tsms.user.SysUser;
import com.fdays.tsms.user.UserListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface UserDAO extends BaseDAO {
	public SysUser login(String userNo, String userPassword)
			throws AppException;

	public List list(UserListForm ulf) throws AppException;

	public long save(SysUser user) throws AppException;

	public SysUser queryById(long id) throws AppException;

	public long merge(SysUser user) throws AppException;

	public long update(SysUser user) throws AppException;

	public SysUser getUserById(long id);

	public void deleteById(long id) throws AppException;

	public List list() throws AppException;

	public SysUser getUserByNo(SysUser user) throws AppException;
	public SysUser getUserByNo(String userNo) throws AppException;

	public SysUser getUserByName(String userName) throws AppException;
}

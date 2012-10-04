package com.fdays.tsms.system.biz;



import java.util.List;

import com.fdays.tsms.system.LoginLog;
import com.fdays.tsms.system.LoginLogListForm;
import com.neza.exception.AppException;

public interface LoginLogBiz {
	public void saveLoginLog(LoginLog loginlog) throws AppException;
	public List getLoginLogs(LoginLogListForm lllf) throws AppException;
}
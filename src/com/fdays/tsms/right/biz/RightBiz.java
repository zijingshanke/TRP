package com.fdays.tsms.right.biz;



import com.fdays.tsms.right.UserRightInfo;
import com.neza.exception.AppException;


public interface RightBiz {
	public void setRights(UserRightInfo uri,long userId) throws AppException;
}

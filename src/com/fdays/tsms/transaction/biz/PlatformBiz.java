package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.neza.exception.AppException;

public interface PlatformBiz {

	public List list(PlatformListForm platformForm) throws AppException;

	public long delete(long id) throws AppException;

	public long save(Platform platform) throws AppException;

	public long update(Platform platform) throws AppException;

	public Platform getPlatformById(long platformId) throws AppException;

	public List<Platform> getPlatformList() throws AppException;

	public List<Platform> getValidPlatformList() throws AppException;

}

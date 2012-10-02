package com.fdays.tsms.transaction.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.neza.exception.AppException;

public interface PlatformCompareBiz {

	public List<PlatformCompare> insertReport(PlatformCompare platformCompare,
			HttpServletRequest request) throws AppException;

	public List list(PlatformCompareListForm ulf) throws AppException;

	public long save(PlatformCompare platformCompare) throws AppException;

	public PlatformCompare queryById(long id) throws AppException;

	public long merge(PlatformCompare platformCompare) throws AppException;

	public long update(PlatformCompare platformCompare) throws AppException;

	public PlatformCompare getPlatformCompareById(long id);

	public void deleteById(long id) throws AppException;

	public long deletePlatformCompare(long id) throws AppException;

	public List list() throws AppException;

	public List getValidPlatformCompareList() throws AppException;

}

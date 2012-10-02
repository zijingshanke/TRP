package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.PlatformReportIndexListForm;
import com.neza.exception.AppException;

public interface PlatformReportIndexBiz {
	public PlatformReportIndex getReportIndexByPlatformIdType(Long platformId,
			Long compareType) throws AppException;

	public List list(PlatformReportIndexListForm ulf) throws AppException;

	public long save(PlatformReportIndex platformReportIndex)
			throws AppException;

	public PlatformReportIndex queryById(long id) throws AppException;

	public long merge(PlatformReportIndex platformReportIndex)
			throws AppException;

	public long update(PlatformReportIndex platformReportIndex)
			throws AppException;

	public PlatformReportIndex getPlatformReportIndexById(long id);

	public void deleteById(long id) throws AppException;

	public long deletePlatformReportIndex(long id) throws AppException;

	public List list() throws AppException;

	public List getValidPlatformReportIndexList() throws AppException;

}

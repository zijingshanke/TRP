package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.PlatformReportIndexListForm;
import com.neza.exception.AppException;

public interface PlatformReportIndexDAO {
	public List list(PlatformReportIndexListForm ulf) throws AppException;

	public long save(PlatformReportIndex platformReportIndex)
			throws AppException;

	public PlatformReportIndex queryById(long id) throws AppException;

	public long merge(PlatformReportIndex platformReportIndex)
			throws AppException;

	public long update(PlatformReportIndex platformReportIndex)
			throws AppException;

	public PlatformReportIndex getPlatformReportIndexById(long id);

	public PlatformReportIndex getReportIndexByPlatformIdType(Long platformId,
			Long compareType) throws AppException;

	public PlatformReportIndex getReportIndexByCompareType(Long compareType)
			throws AppException;

	public void deleteById(long id) throws AppException;

	public List list() throws AppException;

	public List getValidPlatformReportIndexList() throws AppException;

}

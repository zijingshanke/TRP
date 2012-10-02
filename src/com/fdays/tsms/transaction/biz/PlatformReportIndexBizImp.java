package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.PlatformReportIndexListForm;
import com.fdays.tsms.transaction.dao.PlatformReportIndexDAO;
import com.neza.exception.AppException;

public class PlatformReportIndexBizImp implements PlatformReportIndexBiz {
	private PlatformReportIndexDAO platformReportIndexDAO;

	public long save(PlatformReportIndex platformReportIndex)
			throws AppException {
		return platformReportIndexDAO.save(platformReportIndex);
	}

	public long merge(PlatformReportIndex platformReportIndex) throws AppException {
		return platformReportIndexDAO.merge(platformReportIndex);
	}

	public long update(PlatformReportIndex platformReportIndex)
			throws AppException {
		return platformReportIndexDAO.update(platformReportIndex);
	}

	public void deleteById(long id) throws AppException {
		platformReportIndexDAO.deleteById(id);
	}

	public long deletePlatformReportIndex(long id) throws AppException {
		PlatformReportIndex tempAirline = platformReportIndexDAO
				.getPlatformReportIndexById(id);
		if (tempAirline == null) {
			return 0;
		} else {
			tempAirline.setStatus(PlatformReportIndex.STATES_0);
			platformReportIndexDAO.update(tempAirline);
			return 1;
		}
	}

	public PlatformReportIndex getPlatformReportIndexById(long id) {
		return platformReportIndexDAO.getPlatformReportIndexById(id);
	}

	public PlatformReportIndex queryById(long id) throws AppException {
		return platformReportIndexDAO.queryById(id);
	}

	public List list() throws AppException {
		return platformReportIndexDAO.list();
	}

	public List getValidPlatformReportIndexList() throws AppException {
		return platformReportIndexDAO.getValidPlatformReportIndexList();
	}

	public List list(PlatformReportIndexListForm ulf) throws AppException {
		return platformReportIndexDAO.list(ulf);
	}

	public PlatformReportIndex getReportIndexByPlatformIdType(Long platformId,
			Long reportType) throws AppException {
		return platformReportIndexDAO.getReportIndexByPlatformIdType(
				platformId, reportType);
	}

	public PlatformReportIndex getReportIndexByCompareType(Long compareType)
			throws AppException {
		return platformReportIndexDAO.getReportIndexByCompareType(compareType);
	}

	public void setPlatformReportIndexDAO(
			PlatformReportIndexDAO platformReportIndexDAO) {
		this.platformReportIndexDAO = platformReportIndexDAO;
	}

}

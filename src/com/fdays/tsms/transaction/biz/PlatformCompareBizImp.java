package com.fdays.tsms.transaction.biz;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.dao.PlatformCompareDAO;
import com.fdays.tsms.transaction.dao.PlatformReportIndexDAO;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class PlatformCompareBizImp implements PlatformCompareBiz {
	private PlatformCompareDAO platformCompareDAO;
	private PlatformReportIndexDAO platformReportIndexDAO;

	public List<PlatformCompare> insertReport(PlatformCompare platformCompare,
			HttpServletRequest request) throws AppException {
		Long platformId = platformCompare.getPlatformId();
		Long compareType = platformCompare.getType();

		PlatformReportIndex reportIndex = platformReportIndexDAO
				.getReportIndexByPlatformIdType(platformId, compareType);

		List<PlatformCompare> compareList = getPlatformCompareList(
				platformCompare, reportIndex, request);

		return compareList;
	}

	public List<PlatformCompare> getPlatformCompareList(
			PlatformCompare platformCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) {
		List<PlatformCompare> compareList = new ArrayList<PlatformCompare>();

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Long platformId = Constant.toLong(platformCompare.getPlatformId());
		Long type = Constant.toLong(platformCompare.getType());
		Long status = PlatformCompare.STATES_1;

		String beginDateStr = platformCompare.getBeginDateStr();
		String endDateStr = platformCompare.getEndDateStr();

		Timestamp beginTime = null;
		if (beginDateStr != null && "".equals(beginDateStr) == false) {
			beginTime = DateUtil.getTimestamp(beginDateStr,
					"yyyy-MM-dd HH:mm:ss");
		}
		Timestamp endTime = null;
		if (endDateStr != null && "".equals(endDateStr) == false) {
			endTime = DateUtil.getTimestamp(endDateStr, "yyyy-MM-dd HH:mm:ss");
		}
		String userNo = uri.getUser().getUserNo();
		String SessionID=request.getSession().getId();

		File reportFile = new File("D:" + File.separator
				+ Constant.PROJECT_PLATFORMREPORTS_PATH + File.separator
				+ platformCompare.fileName);

		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet("今");// getSheet(1)得到第1个sheet
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							for (int i = 1; i < rownum; i++) {
								PlatformCompare compare = new PlatformCompare();
								int tempIndex = reportIndex
										.getIndexValueByName("flightCode");
								if (tempIndex >= 0) {
									String flightCode = sheet.getCell(
											tempIndex, i).getContents(); // 第i行的第1列
									flightCode = Constant.toUpperCase(
											flightCode, new Long(5));
									compare.setFlightCode(flightCode);
								}
								tempIndex = reportIndex
										.getIndexValueByName("flightClass");
								if (tempIndex >= 0) {
									String flightClass = sheet
											.getCell(
													reportIndex
															.getIndexValueByName("flightClass"),
													i).getContents();// 第i行的第4列
									flightClass = Constant.toUpperCase(
											flightClass, new Long(5));
									compare.setFlightClass(flightClass);
								}
								tempIndex = reportIndex
										.getIndexValueByName("ticketNumber");
								if (tempIndex >= 0) {
									String ticketNumber = sheet.getCell(
											tempIndex, i).getContents();
									ticketNumber = Constant.toUpperCase(
											ticketNumber, new Long(300));
									compare.setTicketNumber(ticketNumber);
								}
								tempIndex = reportIndex
										.getIndexValueByName("startPoint");
								if (tempIndex >= 0) {
									String startPoint = sheet.getCell(
											tempIndex, i).getContents();
									startPoint = Constant.toUpperCase(
											startPoint, new Long(3));
									compare.setStartPoint(startPoint);
								}
								tempIndex = reportIndex
										.getIndexValueByName("endPoint");
								if (tempIndex >= 0) {
									String endPoint = sheet.getCell(tempIndex,
											i).getContents();
									endPoint = Constant.toUpperCase(endPoint,
											new Long(3));
									compare.setEndPoint(endPoint);
								}
								tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									compare.setSubPnr(subPnr);
								}
								tempIndex = reportIndex
										.getIndexValueByName("discount");
								if (tempIndex >= 0) {
									String discount = sheet.getCell(tempIndex,
											i).getContents();
									discount = Constant.toUpperCase(discount,
											new Long(2));
									compare.setDiscount(discount);
								}
								compare.setPlatformId(platformId);
								compare.setType(type);
								compare.setStatus(status);
								compare.setBeginDate(beginTime);
								compare.setEndDate(endTime);
								compare.setUserNo(userNo);
								compare.setSessionId(SessionID);

								compareList.add(compare);
							}
						}
					}
					book.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compareList;
	}

	public long save(PlatformCompare platformCompare) throws AppException {
		return platformCompareDAO.save(platformCompare);
	}

	public long merge(PlatformCompare platformCompare) throws AppException {
		return platformCompareDAO.merge(platformCompare);
	}

	public long update(PlatformCompare platformCompare) throws AppException {
		return platformCompareDAO.update(platformCompare);
	}

	public void deleteById(long id) throws AppException {
		platformCompareDAO.deleteById(id);
	}

	public long deletePlatformCompare(long id) throws AppException {
		PlatformCompare tempCompare = platformCompareDAO
				.getPlatformCompareById(id);
		if (tempCompare == null) {
			return 0;
		} else {
			tempCompare.setStatus(PlatformCompare.STATES_0);
			platformCompareDAO.update(tempCompare);
			return 1;
		}
	}

	public PlatformCompare getPlatformCompareById(long id) {
		return platformCompareDAO.getPlatformCompareById(id);
	}

	public PlatformCompare queryById(long id) throws AppException {
		return platformCompareDAO.queryById(id);
	}

	public List list() throws AppException {
		return platformCompareDAO.list();
	}

	public List getValidPlatformCompareList() throws AppException {
		return platformCompareDAO.getValidPlatformCompareList();
	}

	public List list(PlatformCompareListForm ulf) throws AppException {
		return platformCompareDAO.list(ulf);
	}

	public void setPlatformCompareDAO(PlatformCompareDAO platformCompareDAO) {
		this.platformCompareDAO = platformCompareDAO;
	}

	public void setPlatformReportIndexDAO(
			PlatformReportIndexDAO platformReportIndexDAO) {
		this.platformReportIndexDAO = platformReportIndexDAO;
	}
}

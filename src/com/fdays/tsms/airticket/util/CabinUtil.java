package com.fdays.tsms.airticket.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.neza.exception.AppException;

/**
 * 舱位折扣工具类
 * 
 * 访问品尚平台接口，根据承运人、舱位获取折扣
 * 
 * @author yanrui
 */
public class CabinUtil {
	public static void main(String[] args) {
		String carrier = "CZ";
		String cabin = "T";

		getCabinInfoByFdaysInterface(carrier, cabin);
	}

	/**
	 * 更新TempPNR的舱位折扣
	 */
	public static TempPNR setTicketPriceByIBEInterface(TempPNR tempPnr)
			throws AppException {
		LogUtil myLog = new AirticketLogUtil(true, false, CabinUtil.class, "");
		try {
			List<TempFlight> tempFlightList = tempPnr.getTempFlightList();

			if (tempFlightList != null) {
				List<TempFlight> newFlightList = new ArrayList<TempFlight>();
				for (int i = 0; i < tempFlightList.size(); i++) {
					TempFlight tempFlight = tempFlightList.get(i);
					String airline = tempFlight.getFlightNo();
					if (airline != null && airline.length() > 5) {
						String carrier = airline.substring(0, 2);

						String cabin = tempFlight.getCabin();
						if (cabin != null && "".equals(cabin) == false) {
							cabin = cabin.trim();

							String discount = getCabinInfoByFdaysInterface(
									carrier, cabin);
							tempFlight.setDiscount(discount);

							newFlightList.add(tempFlight);
						}
						tempPnr.setTempFlightList(newFlightList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			myLog.error(e.getMessage());
		}
		return tempPnr;
	}

	/**
	 * 查询航班舱位折扣
	 * 
	 * @param String
	 *            carrier 承运人，航班号前两位
	 * @param String
	 *            cabin 舱位
	 * @return agio 折扣
	 */
	public static String getCabinInfoByFdaysInterface(String carrier,
			String cabin) {
		LogUtil myLog = new AirticketLogUtil(true, false, CabinUtil.class, "");

		String interfacePath = "http://jp.fdays.com/Airplane/Share/QueryCabinInfo.aspx?";
		interfacePath = "http://air.fdays.com/Airplane/Share/QueryCabinInfo.aspx?";// 现在使用

		String url = interfacePath;
		String xmlInfo = "";

		url += "carrier=" + carrier + "&cabin=" + cabin;

		myLog.info("carrierCabin url:" + url);

		try {
			xmlInfo = HttpInvoker.readContentFromGet(url);
			myLog.info("cabin xmlInfo:" + "\n" + xmlInfo);

			Document document = DocumentHelper.parseText(xmlInfo);

			List<Element> elementList = document.selectNodes("/Cabins");
			for (Element e : elementList) {
				List<Element> flightList = e.selectNodes("Cabin");
				for (Element flight : flightList) {
					Element tempflightCode = flight.element("Cabin");
					String agio = flight.element("Agio").getTextTrim();// 
					String newAgio = flight.element("NewAgio").getTextTrim();// 
					String isNewAgio = flight.element("IsNewAgio")
							.getTextTrim();// 

					String startDate = flight.element("StartDate")
							.getTextTrim();
					String endDate = flight.element("EndDate").getTextTrim();// 
					// String startDate = "2009-11-26 16:53:38";
					// String endDate = "2010-11-30 0:00:00";//

					if (Long.parseLong(isNewAgio) == 1) {
						if (startDate != null && (!startDate.equals(""))
								&& endDate != null && (!endDate.equals(""))) {
							Timestamp DateTime = new Timestamp(System
									.currentTimeMillis());
							if (Timestamp.valueOf(startDate).before(DateTime)
									&& Timestamp.valueOf(endDate).after(
											DateTime)) {
								return newAgio;
							} else {
								return agio;
							}
						} else {
							return agio;
						}
					} else if (Long.parseLong(isNewAgio) == 0) {
						return agio;
					} else {
						return "";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	// <?xml version="1.0" encoding="UTF-8" ?>
	// - < <Cabins>
	// - < <Cabin>
	// < <Carrier>CZ</Carrier>
	// < <CarrierName>南方航空</CarrierName>
	// < <IsNewAgio>0</IsNewAgio>
	// < <StartDate>2009-11-26 16:53:38</StartDate>
	// < <EndDate>2010-11-30 0:00:00</EndDate>
	// < <Cabin>Y</Cabin>
	// < <Agio>100</Agio>
	// < <NewAgio>100</NewAgio>
	// < <IsSpecialCabin>0</IsSpecialCabin>
	// </Cabin>
	// </Cabins>

	// Carrier String 航空公司代码
	// CarrierName String 航空公司名称
	// IsNewAgio Int 是否启用新舱位折扣
	// 1为启用新舱位折扣，0为不启用新舱位折扣；当该字段为1时，还要判断航班时间是否在有效日期内(StartDate--
	// EndDate),如果在该时间段内的航班才取新折扣
	// StartDate DateTime 新舱位折扣生效时间
	// EndDate DateTime 新舱位折扣失效时间
	// Cabin String 舱位代码
	// Agio Int 常规折扣
	// NewAgio Int 新舱位折扣 当IsNewAgio字段为1时，并且航班日期在新舱位折扣的有效日期内时，取该折扣
	// IsSpecialCabin Int 是否是特殊舱位 1是特殊舱位，0非特殊舱位；如果当前舱位为特殊舱位时，无法明确其实际折扣

}

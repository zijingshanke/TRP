package com.fdays.tsms.transaction.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.ReportCompare;
import com.neza.exception.AppException;

public class ReportCompareUtil {
	public static List<ReportCompare> getCompareListByTempTicket(List<ReportCompare> compareList,ReportCompare currentCompare,String ticketNumbers)throws AppException{
		ticketNumbers = ticketNumbers.replaceAll("[^A-Z0-9\\.\\*\\-\\|]|[\\s]", "");

		Pattern p = Pattern.compile("([0-9]{13})");
		Matcher m = p.matcher(ticketNumbers);

		
//		System.out.println("----------原始票号信息---------"+ticketNumbers);
		List<String> ticketList=new ArrayList<String>();
		String currentTicket=Constant.toString(currentCompare.getTicketNumber());
		for (int i = 0; i < m.groupCount(); i++) {
			while (m.find()) {
				String tempTicketNo = Constant.toString(m.group());
//				System.out.println(tempTicketNo);
				ticketList.add(tempTicketNo);				
			}
		}		
		
		for (int j = 0; j < ticketList.size(); j++) {
			String ticketNumber=ticketList.get(j);
//			System.out.println(ticketNumber);
			if(currentTicket.equals(ticketNumber)==false){	
			ReportCompare newCompare=new ReportCompare();
			newCompare=(ReportCompare) currentCompare.clone();
			newCompare.setTicketNumber(ticketNumber);
			compareList.add(newCompare);
			}
		}
		return compareList;
	}

}

package com.fdays.tsms.airticket.util;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;

public class AirticketOrderStore {
	public static String orderGroupIdString = "";//

	public static void addOrderString(long orderGroupId) {
		System.out.println("addOrder:"+orderGroupId);
		if (Constant.toLong(orderGroupId) > 0) {
			String orderIds = StringUtil.appendString(orderGroupIdString, orderGroupId
					+ "", ",");

			orderGroupIdString = orderIds;
		}
	}

	public static boolean containsExistOrder_String(String orderGroupId) {
		int flag = orderGroupIdString.indexOf(orderGroupId);

		if (flag >= 0) { // 大于0 则表示存在 为-1 则表示不存在
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除指定的订单
	 */
	public static void removeOrderId(long orderGroupId) {
		String[] orderArray = StringUtil.getSplitString(orderGroupIdString, ",");
		int orderLength = orderArray.length;
		for (int i = 0; i < orderLength; i++) {
			String order = orderArray[i];
			if ((orderGroupId+"").equals(order)) {
				orderArray = StringUtil.delArrayCellByStr(orderArray, orderGroupId+"");
				orderGroupIdString = StringUtil.getStringByArray(orderArray, ",");
				orderLength = orderArray.length;
			}
		}
	}	
}

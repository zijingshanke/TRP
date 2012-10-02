package com.fdays.tsms.transaction;

import java.util.Date;

import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction._entity._Platform;
import com.neza.tool.DateUtil;
import com.neza.utility.PingYin;

public class Platform extends _Platform {
	private static final long serialVersionUID = 1L;

	// 交易类型
	public static final long type_1 = 1;// 买入平台
	public static final long type_2 = 2;// 卖出平台
	public static final long type_3 = 3;// 买卖平台

	// 出票类型
	public static final long draw_type_0 = 0;// 交易平台
	public static final long draw_type_1 = 1;// 网电
	public static final long draw_type_2 = 2;// BSP

	// 状态
	public static final long STATES_0 = 0;// 有效
	public static final long STATES_1 = 1;// 无效
	private String charSet="GBK";
	public String getShowName() {
		if (this.name != null && "".equals(this.name) == false) {
			if(StringUtil.isNumeric(this.name.substring(0,1))||StringUtil.isLetter(this.name.substring(0,1))){
				return this.name;
			}else{
				String myFirstLetter = PingYin.getFirstLetter(this.name,charSet);
				if (myFirstLetter != null && myFirstLetter.length() > 1) {
					myFirstLetter = myFirstLetter.substring(0, 1);
					return myFirstLetter + "-" + this.name;
				}
			}			
		}
		return this.name;
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType().intValue() == type_1) {
				return "买入平台";
			} else if (this.getType().intValue() == type_2) {
				return "卖出平台";
			} else if (this.getType().intValue() == type_3) {
				return "买卖平台";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getDrawTypeInfo() {
		if (this.getDrawType() != null) {
			if (this.getDrawType().intValue() == draw_type_0) {
				return "交易平台";
			}else if (this.getDrawType().intValue() == draw_type_1) {
				return "网电";
			} else if (this.getDrawType().intValue() == draw_type_2) {
				return "BSP";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	// 状态
	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATES_0) {
				return "有效";
			} else if (this.getStatus().intValue() == STATES_1) {
				return "无效";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public String getFormatUpdateDate(){
		String mydate = "";
		if (this.updateDate != null && "".equals(updateDate) == false)
		{
			Date tempDate = new Date(updateDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}
}

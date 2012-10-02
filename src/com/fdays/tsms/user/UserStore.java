package com.fdays.tsms.user;

import java.util.ArrayList;
import java.util.List;

public class UserStore {
	public static List<SysUser> userList = new ArrayList<SysUser>();

	public static String getUserNameByNo(String userNo) {
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				SysUser user = userList.get(i);
				if (userNo != null && "".equals(userNo) == false) {
					String tempUserNo = user.getUserNo();
					if (tempUserNo != null && "".equals(tempUserNo) == false) {
						if (userNo.equals(tempUserNo)) {
							return user.getUserName();
						}
					}
				}
			}
		}
		return userNo;
	}
	
	public static String getUserNoByName(String userName) {
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				SysUser user = userList.get(i);
				if (userName != null && "".equals(userName.trim()) == false) {
					String tempUserName = user.getUserName();
					if (tempUserName != null && "".equals(tempUserName.trim()) == false) {
						if (userName.equals(tempUserName)) {
							return user.getUserNo();
						}
					}
				}
			}
		}
		return userName;
	}
	
	

	public static List<SysUser> getUserList() {
		return userList;
	}

	public static void setUserList(List<SysUser> userList) {
		UserStore.userList = userList;
	}

}

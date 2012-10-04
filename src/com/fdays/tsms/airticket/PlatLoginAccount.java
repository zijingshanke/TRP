package com.fdays.tsms.airticket;


import com.fdays.tsms.airticket._entity._PlatLoginAccount;

public class PlatLoginAccount extends _PlatLoginAccount{
  	private static final long serialVersionUID = 1L;
  	
  	
  	private long platformId;//平台账号ID


	public long getPlatformId() {
		return platformId;
	}


	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}
	
	
	// 状态
	public static final long STATES_0 = 0;// 离线
	public static final long STATES_1 = 1;// 在线

	// 状态
	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATES_0) {
				return "离线";
			} else if (this.getStatus().intValue() == STATES_1) {
				return "在线";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}


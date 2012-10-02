package com.fdays.tsms.system;

/**
 * 对应Oracle系统表dual
 */
public class Dual extends org.apache.struts.action.ActionForm implements
		Cloneable {
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

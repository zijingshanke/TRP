package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class ReportCompareListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id = 0;
	public long platformId = 0;
	public long type = 0;
	public long status = 0;

	public String filePath = "";
	public String fileName = "";
	public String listAttachName = "";
	public String beginDateStr = "";
	public String endDateStr = "";

	// --------------------
	public long resultId=0;
	protected int[] selectedItems2 = new int[0];
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getListAttachName() {
		return listAttachName;
	}

	public void setListAttachName(String listAttachName) {
		this.listAttachName = listAttachName;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public long getResultId() {
		return resultId;
	}

	public void setResultId(long resultId) {
		this.resultId = resultId;
	}

	public int[] getSelectedItems2() {
		return selectedItems2;
	}

	public void setSelectedItems2(int[] selectedItems2) {
		this.selectedItems2 = selectedItems2;
	}
	
	
}

package com.fdays.tsms.airticket;

import java.math.BigDecimal;

import com.fdays.tsms.user.SysUser;

/**
 * 操作员收付款统计
 */
public class OptTransaction {
	private String opterateNo = "";
	private String opterateName = "";
	private Long totalOrderNum = new Long(0);
	private Long normalOrderNum = new Long(0);
	private Long retireOrderNum = new Long(0);
	private Long invalidOrderNum = new Long(0);
	private Long umbuchenOrderNum = new Long(0);
	private Long cancelOrderNum = new Long(0);
	private Long saleOrderNum = new Long(0);
	private Long saleTicketNum = new Long(0);
	private java.math.BigDecimal inAmount = BigDecimal.ZERO;
	private java.math.BigDecimal outAmount = BigDecimal.ZERO;
	private java.math.BigDecimal profits = BigDecimal.ZERO;
	private java.math.BigDecimal inRetireAmount = BigDecimal.ZERO;
	private java.math.BigDecimal outRetireAmount = BigDecimal.ZERO;
	private java.math.BigDecimal inCancelAmount = BigDecimal.ZERO;
	private java.math.BigDecimal outCancelAmount = BigDecimal.ZERO;

	// -----------
	private SysUser user = new SysUser();

	public OptTransaction(SysUser user, Long normalOrderNum) {
		this.opterateNo = user.getUserNo();
		this.opterateName = user.getUserName();
		this.normalOrderNum = normalOrderNum;
	}

	public OptTransaction(SysUser user, Long normalOrderNum,
			Long umbuchenOrderNum, Long retireOrderNum, Long invalidOrderNum,
			Long cancelOrderNum, Long ticketCount) {
		this.opterateNo = user.getUserNo();
		this.opterateName = user.getUserName();
		this.normalOrderNum = normalOrderNum;
		this.umbuchenOrderNum = umbuchenOrderNum;
		this.retireOrderNum = retireOrderNum;
		this.invalidOrderNum = invalidOrderNum;
		this.cancelOrderNum = cancelOrderNum;

		this.totalOrderNum = normalOrderNum + umbuchenOrderNum + retireOrderNum
				+ invalidOrderNum + cancelOrderNum;
		this.saleTicketNum = ticketCount;
	}

	public OptTransaction(SysUser user, Long normalOrderNum,
			Long umbuchenOrderNum, Long retireOrderNum, Long invalidOrderNum,
			Long cancelOrderNum, Long ticketCount, BigDecimal inAmount,
			BigDecimal outAmount) {
		this.opterateNo = user.getUserNo();
		this.opterateName = user.getUserName();
		this.normalOrderNum = normalOrderNum;
		this.umbuchenOrderNum = umbuchenOrderNum;
		this.retireOrderNum = retireOrderNum;
		this.invalidOrderNum = invalidOrderNum;
		this.cancelOrderNum = cancelOrderNum;

		this.totalOrderNum = normalOrderNum + umbuchenOrderNum + retireOrderNum
				+ invalidOrderNum + cancelOrderNum;
		this.saleTicketNum = ticketCount;
		this.inAmount = inAmount;
		this.outAmount = outAmount;
		this.profits = inAmount.subtract(outAmount);
	}

	public OptTransaction(SysUser user, Long normalOrderNum,
			Long umbuchenOrderNum, Long retireOrderNum, Long invalidOrderNum,
			Long cancelOrderNum, Long ticketCount, BigDecimal inAmount,
			BigDecimal outAmount, BigDecimal inRetireAmount,
			BigDecimal outRetireAmount) {
		this.opterateNo = user.getUserNo();
		this.opterateName = user.getUserName();
		this.normalOrderNum = normalOrderNum;
		this.umbuchenOrderNum = umbuchenOrderNum;
		this.retireOrderNum = retireOrderNum;
		this.invalidOrderNum = invalidOrderNum;
		this.cancelOrderNum = cancelOrderNum;

		this.totalOrderNum = normalOrderNum + umbuchenOrderNum + retireOrderNum
				+ invalidOrderNum + cancelOrderNum;
		this.saleTicketNum = ticketCount;
		this.inAmount = inAmount;
		this.outAmount = outAmount;
		this.profits = inAmount.subtract(outAmount);
		
		this.inRetireAmount=inRetireAmount;
		this.outRetireAmount=outRetireAmount;
	}

	public OptTransaction(SysUser user, Long saleOrderNum, Long normalOrderNum,
			Long retireOrderNum, Long invalidOrderNum, Long umbuchenOrderNum,
			Long cancelOrderNum, Long saleTicketNum, BigDecimal inAmount,
			BigDecimal outAmount, BigDecimal profits,
			BigDecimal inRetireAmount, BigDecimal outRetireAmount,
			BigDecimal inCancelAmount, BigDecimal outCancelAmount) {
		this.opterateNo = user.getUserNo();
		this.opterateName = user.getUserName();

		this.normalOrderNum = normalOrderNum;
		this.retireOrderNum = retireOrderNum;
		this.invalidOrderNum = invalidOrderNum;
		this.umbuchenOrderNum = umbuchenOrderNum;
		this.cancelOrderNum = cancelOrderNum;
		this.saleTicketNum = saleTicketNum;
		this.inAmount = inAmount;
		this.outAmount = outAmount;
		this.profits = profits;
		this.inRetireAmount = inRetireAmount;
		this.outRetireAmount = outRetireAmount;
		this.inCancelAmount = inCancelAmount;
		this.outCancelAmount = outCancelAmount;
	}

	public String getOpterateNo() {
		return opterateNo;
	}

	public void setOpterateNo(String optNo) {
		this.opterateNo = optNo;
	}

	public String getOpterateName() {
		return opterateName;
	}

	public void setOpterateName(String opterateName) {
		this.opterateName = opterateName;
	}

	public Long getSaleOrderNum() {
		return saleOrderNum;
	}

	public void setSaleOrderNum(Long saleOrderNum) {
		this.saleOrderNum = saleOrderNum;
	}

	public Long getNormalOrderNum() {
		return normalOrderNum;
	}

	public void setNormalOrderNum(Long normalOrderNum) {
		this.normalOrderNum = normalOrderNum;
	}

	public Long getRetireOrderNum() {
		return retireOrderNum;
	}

	public void setRetireOrderNum(Long retireOrderNum) {
		this.retireOrderNum = retireOrderNum;
	}

	public Long getInvalidOrderNum() {
		return invalidOrderNum;
	}

	public void setInvalidOrderNum(Long invalidOrderNum) {
		this.invalidOrderNum = invalidOrderNum;
	}

	public Long getUmbuchenOrderNum() {
		return umbuchenOrderNum;
	}

	public void setUmbuchenOrderNum(Long umbuchenOrderNum) {
		this.umbuchenOrderNum = umbuchenOrderNum;
	}

	public Long getCancelOrderNum() {
		return cancelOrderNum;
	}

	public void setCancelOrderNum(Long cancelOrderNum) {
		this.cancelOrderNum = cancelOrderNum;
	}

	public Long getSaleTicketNum() {
		return saleTicketNum;
	}

	public void setSaleTicketNum(Long saleTicketNum) {
		this.saleTicketNum = saleTicketNum;
	}

	public java.math.BigDecimal getInAmount() {
		if (this.inAmount == null) {
			return BigDecimal.ZERO;
		}
		return inAmount;
	}

	public void setInAmount(java.math.BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public java.math.BigDecimal getOutAmount() {
		if (this.outAmount == null) {
			return BigDecimal.ZERO;
		}
		return outAmount;
	}

	public void setOutAmount(java.math.BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public java.math.BigDecimal getProfits() {
		if (this.profits == null) {
			return BigDecimal.ZERO;
		}
		return profits;
	}

	public void setProfits(java.math.BigDecimal profits) {
		this.profits = profits;
	}

	public java.math.BigDecimal getInRetireAmount() {
		if (this.inRetireAmount == null) {
			return BigDecimal.ZERO;
		}
		return inRetireAmount;
	}

	public void setInRetireAmount(java.math.BigDecimal inRetireAmount) {
		this.inRetireAmount = inRetireAmount;
	}

	public java.math.BigDecimal getOutRetireAmount() {
		if (this.outRetireAmount == null) {
			return BigDecimal.ZERO;
		}
		return outRetireAmount;
	}

	public void setOutRetireAmount(java.math.BigDecimal outRetireAmount) {
		this.outRetireAmount = outRetireAmount;
	}

	public java.math.BigDecimal getInCancelAmount() {
		if (this.inCancelAmount == null) {
			return BigDecimal.ZERO;
		}
		return inCancelAmount;
	}

	public void setInCancelAmount(java.math.BigDecimal inCancelAmount) {
		this.inCancelAmount = inCancelAmount;
	}

	public java.math.BigDecimal getOutCancelAmount() {
		if (this.outCancelAmount == null) {
			return BigDecimal.ZERO;
		}
		return outCancelAmount;
	}

	public void setOutCancelAmount(java.math.BigDecimal outCancelAmount) {
		this.outCancelAmount = outCancelAmount;
	}

	public Long getTotalOrderNum() {
		return totalOrderNum;
	}

	public void setTotalOrderNum(Long totalOrderNum) {
		this.totalOrderNum = totalOrderNum;
	}

}

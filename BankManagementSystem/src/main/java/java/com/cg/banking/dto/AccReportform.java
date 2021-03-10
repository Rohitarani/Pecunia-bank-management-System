package com.cg.banking.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class AccReportform {

	private String accountId;
	@DateTimeFormat(pattern="yyyy-M-d")
	private LocalDate fromDate;
	@DateTimeFormat(pattern="yyyy-M-d")
	private LocalDate toDate;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	
}

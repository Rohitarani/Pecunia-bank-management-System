package com.cg.banking.entity;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="lpu_acc_txn")
public class AccTransaction {
	@Id
	@Column(name="txn_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long transaccountId;
	@Column(name="txn_type", length=10)
	private String transType;
	@Column(name="txn_amt")
	private double transAmount;
	@Column(name="txn_desc", length=50)
	private String transDescription;
	@Column(name="txn_dt")
	private LocalDate transDate;
	
	@ManyToOne
	@JoinColumn(name="account_id", referencedColumnName = "account_id")
	private Account account = new Account();
	public AccTransaction() {
		
	}

	public long getTransaccountId() {
		return transaccountId;
	}

	public void setTransaccountId(long transaccountId) {
		this.transaccountId = transaccountId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransDescription() {
		return transDescription;
	}

	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}

	public LocalDate getTransDate() {
		return transDate;
	}

	public void setTransDate(LocalDate transDate) {
		this.transDate = transDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	

}

package com.ericliu.billshare.model;

public class PaymentListEntry {
	private String payeeName;
	private int payeePercentage;
	private double payeeAmount;
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public int getPayeePercentage() {
		return payeePercentage;
	}
	public void setPayeePercentage(Integer integer) {
		this.payeePercentage = integer;
	}
	public double getPayeeAmount() {
		return payeeAmount;
	}
	public void setPayeeAmount(double payeeAmount) {
		this.payeeAmount = payeeAmount;
	}
}

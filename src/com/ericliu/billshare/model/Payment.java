package com.ericliu.billshare.model;

import android.net.Uri;

public class Payment extends Model {
	
	private long id;
	private long payment_info_id;
	private long bill_id;
	private long payee_id;
	private int payee_days;
	private String payee_start_date;
	private String payee_end_date;
	private double payee_amount;
	

	@Override
	public Uri save() {

		return null;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPayment_info_id(long payment_info_id) {
		this.payment_info_id = payment_info_id;
	}

	public void setBill_id(long bill_id) {
		this.bill_id = bill_id;
	}

	public void setPayee_id(long payee_id) {
		this.payee_id = payee_id;
	}

	public void setPayee_days(int payee_days) {
		this.payee_days = payee_days;
	}

	public void setPayee_start_date(String payee_start_date) {
		this.payee_start_date = payee_start_date;
	}

	public void setPayee_end_date(String payee_end_date) {
		this.payee_end_date = payee_end_date;
	}

	public void setPayee_amount(double payee_amount) {
		this.payee_amount = payee_amount;
	}


}

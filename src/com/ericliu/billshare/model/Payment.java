package com.ericliu.billshare.model;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;
import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class Payment extends Model {
	
	private long id;
	private long payment_info_serial_number;
	private long bill_id;
	private long payee_id;
	private int payee_days;
	private String payee_start_date;
	private String payee_end_date;
	private double payee_amount;
	

	@Override
	public Uri save() {
		
		ContentValues values = new ContentValues();
		values.put(COL_PAYMENT_INFO_ID, payment_info_serial_number);
		values.put(COL_BILL_ID, bill_id);
		values.put(COL_PAYEE_ID, payee_id);
		values.put(COL_PAYEE_DAYS, payee_days);
		values.put(COL_PAYEE_START_DATE, payee_start_date);
		values.put(COL_PAYEE_END_DATE, payee_end_date);
		values.put(COL_PAYEE_AMOUNT, payee_amount);
		
		

		return MyApplication.getInstance().getContentResolver().insert(BillProvider.PAYMENT_URI, values);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPayment_info_serial_number(long payment_info_id) {
		this.payment_info_serial_number = payment_info_id;
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

	public long getId() {
		return id;
	}

	public long getPayment_info_serial_number() {
		return payment_info_serial_number;
	}

	public long getBill_id() {
		return bill_id;
	}

	public long getPayee_id() {
		return payee_id;
	}

	public int getPayee_days() {
		return payee_days;
	}

	public String getPayee_start_date() {
		return payee_start_date;
	}

	public String getPayee_end_date() {
		return payee_end_date;
	}

	public double getPayee_amount() {
		return payee_amount;
	}



}

package com.ericliu.billshare.model;

import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;

import static com.ericliu.billshare.provider.DatabaseConstants.*;


public class Bill extends Model {
	
	private long id;
	private String type;
	private double amount;
	private String startDate;
	private String endDate;
	private String dueDate;
	private int paid;
	
	

	@Override
	public Uri save() {
		Uri uri;
		
		ContentValues values = new ContentValues();
		values.put(COL_TYPE, type);
		values.put(COL_BILLING_START, startDate);
		values.put(COL_BILLING_END, endDate);
		values.put(COL_DUE_DATE, dueDate);
		values.put(COL_PAID, paid);
		
		if (id > 0) {
		uri = Uri.withAppendedPath(BillProvider.BILL_URI, String.valueOf(id));
		
		getCr().update(uri, values, null, null);
			
		}else {
			
			uri = getCr().insert(BillProvider.BILL_URI, values);
			this.id = Integer.valueOf(uri.getLastPathSegment());
		}
		

		return null;
	}



	public void setId(long id) {
		this.id = id;
	}



	public void setType(String type) {
		this.type = type;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}



	public void setPaid(int paid) {
		this.paid = paid;
	}

}

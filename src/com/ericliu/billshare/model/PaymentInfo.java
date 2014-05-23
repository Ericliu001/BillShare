package com.ericliu.billshare.model;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;
import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class PaymentInfo extends Model {
	
	private final long id;
	
	private final int serialNumber;

	private final String name;
	private final String description;
	private final double totalAmount;
	private final int numberOfMembersPaid;
	private final int numberOfBillsPaid;
	private final String paidTime;
	private final boolean deleted;

	public static class Builder {
		private long id = -1;
		private static int serialNumber = 0;
		private String name;
		private String description;
		private double totalAmount;
		private int numberOfMembersPaid;
		private int numberOfBillsPaid;
		private String paidTime;
		private boolean deleted;

		public Builder() {

		}
		
		public Builder setId(long id){
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder totalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
			return this;
		}

		public Builder numberOfMembersPaid(int number) {
			this.numberOfMembersPaid = number;
			return this;
		}

		public Builder numberOfBillsPaid(int number) {
			this.numberOfBillsPaid = number;
			return this;
		}

		public Builder paidTime(String time) {
			this.paidTime = time;
			return this;
		}

		public Builder deleted(boolean deleted) {
			this.deleted = deleted;
			return this;
		}

		public PaymentInfo build() {
			Builder.serialNumber++;
			return new PaymentInfo(this);
		}

	}

	private PaymentInfo(Builder builder) {
		this.id = builder.id;
		this.serialNumber = Builder.serialNumber;
		this.name = builder.name;
		this.description = builder.description;
		this.totalAmount = builder.totalAmount;
		this.numberOfMembersPaid = builder.numberOfMembersPaid;
		this.numberOfBillsPaid = builder.numberOfBillsPaid;
		this.paidTime = builder.paidTime;
		this.deleted = builder.deleted;

	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public int getNumberOfMembersPaid() {
		return numberOfMembersPaid;
	}

	public int getNumberOfBillsPaid() {
		return numberOfBillsPaid;
	}

	public String getPaidTime() {
		return paidTime;
	}

	@Override
	public Uri save() {

		Uri uri;
		ContentValues values = new ContentValues();
		values.put(COL_SERIAL_NUMBER, serialNumber);
		values.put(COL_NAME, name);
		values.put(COL_DESCRIPTION, description);
		values.put(COL_TOTAL_AMOUNT, totalAmount);
		values.put(COL_NUMBER_OF_MEMBERS_PAID, numberOfMembersPaid);
		values.put(COL_NUMBER_OF_BILLS_PAID, numberOfBillsPaid);
		values.put(COL_PAID_TIME, paidTime);
		values.put(COL_DELETED, deleted?1:0);

		
		if (id < 0) {
			uri = getCr()
					.insert(BillProvider.PAYMENT_INFO_URI, values);
		}else {
			
			uri = Uri.withAppendedPath(BillProvider.PAYMENT_INFO_URI, String.valueOf(id));
			getCr().update(uri, values, null, null);
		}
		return uri;

	}

}

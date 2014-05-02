package com.ericliu.billshare.provider;

public class DatabaseConstants {
	
	
	public static final String COL_ROWID = "_id";
	public static final String COL_DELETED = "deleted";

	
	// constants for table bill
	public static final String TABLE_BILL = "table_bill";
	public static final String COL_TYPE = "bill_type";
	public static final String COL_AMOUNT = "amount";
	public static final String COL_BILLING_START = "billing_period_start_date";
	public static final String COL_BILLING_END = "billing_period_end_date";
	public static final String COL_DUE_DATE = "bill_due_date";
	public static final String COL_PAID = "paid";

	
	// constants for table payment
	public static final String TABLE_PAYMENT = "table_payment";
	public static final String COL_PAID_TIME = "paid_time";
	public static final String COL_BILL_ID = "bill_id";
	public static final String COL_PAYEE_ID = "payee_id";
	public static final String COL_PAYEE_DAYS = "payee_days";
	public static final String COL_PAYEE_START_DATE = "payee_start_days";
	public static final String COL_PAYEE_END_DATE = "payee_end_days";
	
	public static final String COL_PAYEE_AMOUNT = "payee_amount";
	
	// constants for table housemate
	public static final String TABLE_HOUSEMATE = "table_housemate";
	public static final String COL_FIRSTNAME = "firstname";
	public static final String COL_LASTNAME = "lastname";
	public static final String COL_TELEPHONE = "telephone";
	public static final String COL_EMAIL = "email";
	public static final String COL_MOVE_IN_DATE = "move_in_date";
	public static final String COL_MOVE_OUT_DATE = "move_out_date";
	public static final String COL_CURRENT_HOUSEMATE = "current_housemate";
	
	
	
	
}

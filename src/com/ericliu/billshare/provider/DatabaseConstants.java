package com.ericliu.billshare.provider;

public class DatabaseConstants {
	
	
	public static final String COL_ROWID = "_id";
	public static final String COL_DELETED = "deleted";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_AMOUNT = "amount";
	public static final String COL_CHECKED = "checked";
	public static final String VIEW_PAYMENT_FULL = "view_payment_full";
	

	
	// constants for table bill
	public static final String TABLE_BILL = "table_bill";
	public static final String VIEW_BILL = "view_bill";
	public static final String VIEW_BILL_NAME_CREATE = "view_bill_name_create";
	
	public static final String COL_TYPE = "bill_type";
	public static final String COL_BILLING_START = "billing_period_start_date";
	public static final String COL_BILLING_END = "billing_period_end_date";
	public static final String COL_DUE_DATE = "bill_due_date";
	public static final String COL_PAID = "paid";
	
	
	public static final String VIEW_BILL_NAME = "view_bill_name";
	public static final String COL_BILL_NAME = "bill_name";

	
	// constants for table payment
	public static final String TABLE_PAYMENT = "table_payment";
	
	
	
	public static final String COL_PAYMENT_INFO_ID = "info_id";
	public static final String COL_BILL_ID = "bill_id";
	public static final String COL_PAYEE_ID = "payee_id";
	public static final String COL_PAYEE_DAYS = "payee_days";
	public static final String COL_PAYEE_START_DATE = "payee_start_days";
	public static final String COL_PAYEE_END_DATE = "payee_end_days";
	public static final String COL_PAYEE_AMOUNT = "payee_amount";
	
	// constants for table payment_info
	public static final String TABLE_PAYMENT_INFO = "table_payment_info";
	public static final String VIEW_PAYMENT_INFO = "view_payment_info";
	
	public static final String COL_SERIAL_NUMBER = "serial_number";
	public static final String COL_NAME = "payment_name";
	public static final String COL_PAID_TIME = "paid_time";
	public static final String COL_TOTAL_AMOUNT = "total_amount";
	public static final String COL_NUMBER_OF_MEMBERS_PAID = "number_of_members_paid";
	public static final String COL_NUMBER_OF_BILLS_PAID = "number_of_bills_paid";
	
	
	// constants for table housemate
	public static final String TABLE_MEMBER = "table_member";
	public static final String VIEW_MEMBER = "view_member";
	public static final String VIEW_MEMBER_NAME = "view_member_name";
	public static final String COL_MEMBER_FULLNAME = "full_name";
	
	
	public static final String COL_FIRSTNAME = "firstname";
	public static final String COL_LASTNAME = "lastname";
	public static final String COL_PHONE = "telephone";
	public static final String COL_EMAIL = "email";
	public static final String COL_MOVE_IN_DATE = "move_in_date";
	public static final String COL_MOVE_OUT_DATE = "move_out_date";
	public static final String COL_CURRENT_HOUSEMATE = "current_housemate";
	
	
	
	
	
	
	
}

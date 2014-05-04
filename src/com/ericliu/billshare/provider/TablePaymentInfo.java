package com.ericliu.billshare.provider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class TablePaymentInfo {

	
	private static final String TABLE_CREATE = " create table "
			+ TABLE_PAYMENT_INFO
			+ " ( "
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_NAME + "  text, "
			+ COL_DESCRIPTION + " text, "
			+ COL_PAID_TIME + " datetime "
			+";"
			
			
			;

}

package com.ericliu.billshare.provider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
import android.database.sqlite.SQLiteDatabase;
public class TablePaymentInfo {

	
	private static final String TABLE_CREATE = " create table "
			+ TABLE_PAYMENT_INFO
			+ " ( "
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_NAME + "  text, "
			+ COL_DESCRIPTION + " text, "
			+ COL_TOTAL_AMOUNT + " numeric, "
			+ COL_NUMBER_OF_MEMBERS_PAID + " integer, "
			+ COL_NUMBER_OF_BILLS_PAID + " integer, "
			+ COL_PAID_TIME + " datetime "
			
			+");"
			;
	
	
	private static final String VIEW_PAYMENT_FULL_CREATE = " create view "
			+ VIEW_PAYMENT_FULL
			+ " as "
			+ " select "
			+ TABLE_PAYMENT + "." + COL_ROWID + ","
			+ TABLE_PAYMENT + "." + COL_PAYMENT_INFO_ID + ","
			+ TABLE_PAYMENT + "." + COL_BILL_ID + ","
			+ TABLE_PAYMENT + "." + COL_PAYEE_ID + ","
			+ TABLE_PAYMENT + "." + COL_PAYEE_DAYS + ","
			+ TABLE_PAYMENT + "." + COL_PAYEE_START_DATE + ","
			+ TABLE_PAYMENT + "." + COL_PAYEE_END_DATE + ","
			+ TABLE_PAYMENT + "." + COL_PAYEE_AMOUNT + ","
			
			
			+ TABLE_MEMBER + "." + COL_ROWID + ","
			+ TABLE_MEMBER + "." + COL_FIRSTNAME + ","
			+ TABLE_MEMBER + "." + COL_LASTNAME + ","
			+ TABLE_MEMBER + "." + COL_PHONE + ","
			+ TABLE_MEMBER + "." + COL_EMAIL + ","
			+ TABLE_MEMBER + "." + COL_MOVE_IN_DATE + ","
			+ TABLE_MEMBER + "." + COL_MOVE_OUT_DATE + ","
			
			
			+ TABLE_BILL + "." + COL_ROWID + ","
			+ TABLE_BILL + "." + COL_TYPE + ","
			+ TABLE_BILL + "." + COL_AMOUNT + ","
			+ TABLE_BILL + "." + COL_BILLING_START + ","
			+ TABLE_BILL + "." + COL_DUE_DATE + ","
			+ TABLE_BILL + "." + COL_PAID + ","
			
			
			+ TABLE_PAYMENT_INFO + "." + COL_ROWID + ","
			+ TABLE_PAYMENT_INFO + "." + COL_NAME + ","
			+ TABLE_PAYMENT_INFO + "." + COL_DESCRIPTION + ","
			+ TABLE_PAYMENT_INFO + "." + COL_TOTAL_AMOUNT + ","
			+ TABLE_PAYMENT_INFO + "." + COL_NUMBER_OF_MEMBERS_PAID + ","
			+ TABLE_PAYMENT_INFO + "." + COL_NUMBER_OF_BILLS_PAID + ","
			+ TABLE_PAYMENT_INFO + "." + COL_PAID_TIME
			
			+ " from  " + TABLE_PAYMENT + " left join  " + TABLE_MEMBER 
			+ " on " + TABLE_PAYMENT + "." + COL_PAYEE_ID + "=" + TABLE_MEMBER + "." + COL_ROWID
			+ " left join  " + TABLE_BILL 
			+ " on " + TABLE_PAYMENT + "." + COL_BILL_ID + "=" + TABLE_BILL + "." + COL_ROWID
			+ " left join " + TABLE_PAYMENT_INFO 
			+ " on " + TABLE_PAYMENT + "." + COL_PAYMENT_INFO_ID + "=" + TABLE_PAYMENT_INFO + "." + COL_ROWID
			+";"
			;
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
		db.execSQL(VIEW_PAYMENT_FULL_CREATE);
	}
	
	public static void onUpdate(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_INFO);
		onCreate(db);
	}

}

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
			
			+";"
			;
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpdate(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_INFO);
		onCreate(db);
	}

}

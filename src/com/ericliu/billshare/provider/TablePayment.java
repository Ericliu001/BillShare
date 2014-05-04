package com.ericliu.billshare.provider;
import static com.ericliu.billshare.provider.DatabaseConstants.*;
import android.database.sqlite.SQLiteDatabase;


public class TablePayment {



	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table " 
			+ TABLE_PAYMENT
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_BILL_ID +  " integer not null references " + TABLE_BILL + "(" + COL_ROWID+"),"
			+ COL_PAYEE_ID + " integer not null references " + TABLE_MEMBER + "("+ COL_ROWID+"),"
			+ COL_PAYEE_DAYS + " integer, "
			+ COL_PAYEE_START_DATE + " datetime, "
			+ COL_PAYEE_END_DATE + " datetime, "
			+ COL_PAYEE_AMOUNT + " numeric "
			+ " ); "
			;


	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PAYMENT);
		onCreate(db);
	}

}

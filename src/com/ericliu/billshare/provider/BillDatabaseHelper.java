package com.ericliu.billshare.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class BillDatabaseHelper extends SQLiteOpenHelper {
	
	
	private static final String DATABASE_NAME = "billshareDB.db";
	private static final int DATABASE_VERSION = 1;

	public BillDatabaseHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TableBill.onCreate(db);
		TablePayment.onCreate(db);
		TableMember.onCreate(db);
		
		
		ContentValues values = new ContentValues();
		
		// initial values for table member
		values = new ContentValues();
		values.put(COL_FIRSTNAME, "Eric");
		values.put(COL_LASTNAME, "Liu");
		db.insert(TABLE_MEMBER, null, values);
		
		values = new ContentValues();
		values.put(COL_FIRSTNAME, "Simon");
		values.put(COL_LASTNAME, "Zac");
		db.insert(TABLE_MEMBER, null, values);
		
		values = new ContentValues();
		values.put(COL_FIRSTNAME, "Ellis");
		values.put(COL_LASTNAME, "Ally");
		db.insert(TABLE_MEMBER, null, values);
		
		
		// initial values for table bill
		values = new ContentValues();
		values.put(COL_TYPE, "Electricity");
		values.put(COL_AMOUNT, 234.45);
		db.insert(TABLE_BILL, null, values);
		
		values = new ContentValues();
		values.put(COL_TYPE, "water");
		values.put(COL_AMOUNT, 34.25);
		db.insert(TABLE_BILL, null, values);
		
		values = new ContentValues();
		values.put(COL_TYPE, "gas");
		values.put(COL_AMOUNT, 1644.45);
		values.put(COL_PAID, 1);
		db.insert(TABLE_BILL, null, values);
		
		values = new ContentValues();
		values.put(COL_TYPE, "water");
		values.put(COL_AMOUNT, 700.45);
		values.put(COL_PAID, 1);
		db.insert(TABLE_BILL, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableBill.onUpgrade(db);
		TablePayment.onUpgrade(db);
		TableMember.onUpgrade(db);
	}

}

package com.billshare.provider;
import static com.billshare.provider.DatabaseConstants.*;
import android.database.sqlite.SQLiteDatabase;


public class TableHousemate {

	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table " 
			+ TABLE_HOUSEMATE
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_FIRSTNAME + " text not null, "
			+ COL_LASTNAME +  " text, "
			+ COL_TELEPHONE + " text, "
			+ COL_EMAIL + " text, "
			+ COL_MOVE_IN_DATE + " datetime,  "
			+ COL_MOVE_OUT_DATE + " datetime "
			+ COL_CURRENT_HOUSEMATE + " boolean "
			+ COL_DELETED + " boolean "
			+ " ); "
			;


	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_HOUSEMATE);
		onCreate(db);
	}
}

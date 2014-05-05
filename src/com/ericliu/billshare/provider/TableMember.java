package com.ericliu.billshare.provider;
import static com.ericliu.billshare.provider.DatabaseConstants.*;
import android.database.sqlite.SQLiteDatabase;


public class TableMember {

	
	// table creation SQL statement
	private static final String TABLE_CREATE = " create table " 
			+ TABLE_MEMBER
			+ "("
			+ COL_ROWID + " integer primary key autoincrement, "
			+ COL_FIRSTNAME + " text not null, "
			+ COL_LASTNAME +  " text, "
			+ COL_PHONE + " text, "
			+ COL_EMAIL + " text, "
			+ COL_MOVE_IN_DATE + " datetime,  "
			+ COL_MOVE_OUT_DATE + " datetime, "
			+ COL_CURRENT_HOUSEMATE + " boolean, "
			+ COL_DELETED + " boolean not null default 0"
			+ " ); "
			;
	
	
	// create view
		private static final String VIEW_MEMBER_CREATE = "create view "
				+ VIEW_MEMBER
				+ " as select "
				+ COL_ROWID + " , "
				+ COL_FIRSTNAME + " , "
				+ COL_LASTNAME +  " , "
				+ COL_PHONE + " , "
				+ COL_EMAIL + " text, "
				+ COL_MOVE_IN_DATE + " ,  "
				+ COL_MOVE_OUT_DATE + " , "
				+ COL_CURRENT_HOUSEMATE + "  "
				+ " from  "
				+ TABLE_MEMBER
				+ " where  " + COL_DELETED + " = 0"
				+";";
		

		// create member name
		private static final String VIEW_MEMBER_NAME_CREATE = " create view "
				+ VIEW_MEMBER_NAME
				+ " as select "
				+ COL_ROWID + " , "
				+ 1 + " as  " + COL_CHECKED + ","
				+ COL_FIRSTNAME + "|| ' ' ||" + COL_LASTNAME + " as " + COL_MEMBER_FULLNAME + " "
				+ " from  "
				+ TABLE_MEMBER
				+ " where  " + COL_DELETED + " = 0"
				+ ";";

	public static void onCreate(SQLiteDatabase db){
		db.execSQL(TABLE_CREATE);
		db.execSQL(VIEW_MEMBER_CREATE);
		db.execSQL(VIEW_MEMBER_NAME_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_MEMBER);
		onCreate(db);
	}
}

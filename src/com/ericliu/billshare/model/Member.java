package com.ericliu.billshare.model;

import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class Member extends Model {
	
	private long id = -1;
	private String firstName = null;
	private String lastName;
	private String phone;
	private String email;
	private String moveInDate;
	private String moveOutDate;
	private boolean deleted;
	
	
	

	@Override
	public Uri save() {
		
		ContentValues values = new ContentValues();
		values.put(COL_FIRSTNAME, firstName);
		values.put(COL_LASTNAME, lastName);
		values.put(COL_PHONE, phone);
		values.put(COL_EMAIL, email);
		
		if (! TextUtils.isEmpty(moveInDate)) {
			values.put(COL_MOVE_IN_DATE, moveInDate);
		}
		
		
		if (! TextUtils.isEmpty(moveOutDate)) {
			values.put(COL_MOVE_OUT_DATE, moveOutDate);
		}
		
		values.put(COL_DELETED, deleted ? 1:0);
		
		
		Uri uri = null;
		if (id > 0) {
			uri = Uri.withAppendedPath(BillProvider.HOUSEMATE_URI, String.valueOf(id));
			
			getCr().update(uri, values, null, null);
		}else{
			uri = getCr().insert(BillProvider.HOUSEMATE_URI, values);
			this.id = Integer.valueOf(uri.getLastPathSegment());
		}

		return uri;
	}





	public void setId(long id) {
		this.id = id;
	}





	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}





	public void setLastName(String lastName) {
		this.lastName = lastName;
	}





	public void setPhone(String phone) {
		this.phone = phone;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getMoveInDate() {
		return moveInDate;
	}





	public void setMoveInDate(String moveInDate) {
		this.moveInDate = moveInDate;
	}





	public String getMoveOutDate() {
		return moveOutDate;
	}





	public void setMoveOutDate(String moveOutDate) {
		this.moveOutDate = moveOutDate;
	}









	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}

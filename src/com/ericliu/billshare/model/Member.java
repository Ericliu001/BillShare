package com.ericliu.billshare.model;

import com.ericliu.billshare.provider.BillProvider;

import android.content.ContentValues;
import android.net.Uri;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class Member extends Model {
	
	private long id = -1;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	
	
	

	@Override
	public Uri save() {
		
		ContentValues values = new ContentValues();
		values.put(COL_FIRSTNAME, firstName);
		values.put(COL_LASTNAME, lastName);
		values.put(COL_PHONE, phone);
		values.put(COL_EMAIL, email);
		
		
		Uri uri = null;
		if (id > 0) {
			uri = Uri.withAppendedPath(BillProvider.HOUSEMATE_URI, String.valueOf(id));
			
			getCr().update(uri, values, null, null);
		}else{
			uri = getCr().insert(BillProvider.HOUSEMATE_URI, values);
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

}

package com.ericliu.billshare.model;

import android.net.Uri;

public class Member extends Model {
	
	private long id = -1;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	
	
	

	@Override
	public Uri save() {

		return null;
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

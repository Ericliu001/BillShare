package com.ericliu.billshare.model;

import com.ericliu.billshare.MyApplication;

import android.content.ContentResolver;
import android.net.Uri;

public abstract class Model {
	
	public abstract Uri save();
	
	
	public static ContentResolver getCr(){
		return MyApplication.getInstance().getContentResolver();
	}

}

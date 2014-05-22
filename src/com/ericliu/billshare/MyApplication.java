package com.ericliu.billshare;

import android.app.Application;
import android.content.ContentResolver;

public class MyApplication extends Application {
	public static boolean isTesting = false;
	public static MyApplication mApp;
	
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		mApp = this;
	}
	
	public static MyApplication getInstance(){
		return mApp;
	}
	

}

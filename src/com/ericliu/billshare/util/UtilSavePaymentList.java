package com.ericliu.billshare.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.os.AsyncTask;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.model.Payment;
import com.ericliu.billshare.provider.BillProvider;

public class UtilSavePaymentList {
	
	
	private UtilSavePaymentList(){}
	
	
	public static void savePaymentListAsync(ArrayList<Payment> paymentList){
		new SavePaymentListTask().execute(paymentList);
	}
	
	
	private static class SavePaymentListTask extends AsyncTask<ArrayList<Payment>, Void, Void>{

		@Override
		protected Void doInBackground(ArrayList<Payment>... params) {
			ArrayList<Payment> paymentList = params[0];
			
			for (int i = 0; i < paymentList.size(); i++) {
				paymentList.get(i).save();
				
			}
			return null;
		}
		
		
	}

}

package com.ericliu.billshare.util;

import java.util.ArrayList;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.database.Cursor;
import android.os.AsyncTask;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class CalculatorDaysAsync {

	public interface CalculatorDaysListener {

	}

	public static void calculateByDaysAsync(long[] billIds, long[] memberIds,
			CalculatorDaysListener listener) {

		new CalDaysTask(billIds, memberIds, listener).execute();
	}

	private static class CalDaysTask extends
			AsyncTask<Void, Void, ArrayList<Double>> {

		private long[] billIds;
		private long[] memberIds;
		private CalculatorDaysListener listener = null;
		
		private double amountInOneBill = 0d;

		public CalDaysTask(long[] billIds, long[] memberIds,
				CalculatorDaysListener listener) {
			this.billIds = billIds;
			this.memberIds = memberIds;
			this.listener = listener;
		}

		@Override
		protected ArrayList<Double> doInBackground(Void... params) {
			ArrayList<Double> payeeAmountForEachBill = new ArrayList<Double>();
			String selection = COL_ROWID + " =? ";
			String[] selectionArgs = new String[billIds.length];
			for (int i = 0; i < billIds.length; i++) {
				selectionArgs[i] = String.valueOf(billIds[i]);
				if (i < billIds.length -1) {
					selection = selection + " OR  " + COL_ROWID + "=? ";
				}
			}
			
			String[] projectionForBill = {COL_ROWID, COL_AMOUNT};
			Cursor cursorBill = null;
			try {
				cursorBill = MyApplication.getInstance().getContentResolver().query(BillProvider.BILL_URI, projectionForBill, selection, selectionArgs, null);
				cursorBill.moveToPosition(-1);
				while (cursorBill.moveToNext()) {
					amountInOneBill = cursorBill.getDouble(cursorBill.getColumnIndexOrThrow(COL_AMOUNT));
					
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return payeeAmountForEachBill;
		}

	}

}

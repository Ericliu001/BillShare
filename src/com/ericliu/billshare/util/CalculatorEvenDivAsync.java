package com.ericliu.billshare.util;

import java.util.ArrayList;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class CalculatorEvenDivAsync {

	public interface EvenDivListener {
		void setEvenDivResult(ArrayList<Double> payeeAmountForEachBill, double totalAmount, double payeeAmountForTotal);
	}

	public static void evenDivAsync(long[] billIds, long[] memberIds,
			EvenDivListener listener) {
		new EvenDivTask(billIds, memberIds, listener).execute();

	}

	private static class EvenDivTask extends AsyncTask<Void, Void, ArrayList<Double>> {

		EvenDivListener listener = null;

		private long[] billIds;
		private long[] memberIds;
		
		private double amountInOneBill = 0d;
		private double totalAmount = 0d;
		private double payeeAmountForTotal = 0d;

		public EvenDivTask(long[] billIds, long[] memberIds,
				EvenDivListener listener) {
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
				if (i < billIds.length - 1) {
					selection = selection + " OR  " + COL_ROWID + " =? ";
				}
			}
			
			
			String[] projectionForBill = { COL_ROWID, COL_AMOUNT };
			Cursor cursorBill = null;

			try {
				cursorBill = MyApplication.getInstance().getContentResolver()
						.query(BillProvider.BILL_URI, projectionForBill, selection, selectionArgs, null);
				cursorBill.moveToPosition(-1);
				
				while(cursorBill.moveToNext()){
					amountInOneBill = cursorBill.getDouble(cursorBill
							.getColumnIndexOrThrow(COL_AMOUNT));
					totalAmount += amountInOneBill;
					payeeAmountForEachBill.add(amountInOneBill / memberIds.length);
				}
				
					
					payeeAmountForTotal = totalAmount/ memberIds.length;
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cursorBill != null) {
					cursorBill.close();
				}
			}

			return payeeAmountForEachBill;
		}

		@Override
		protected void onPostExecute(ArrayList<Double> payeeAmountForEachBill) {

			super.onPostExecute(payeeAmountForEachBill);

			listener.setEvenDivResult(payeeAmountForEachBill, totalAmount, payeeAmountForTotal);
		}
	}
}

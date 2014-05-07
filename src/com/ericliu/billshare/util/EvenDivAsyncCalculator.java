package com.ericliu.billshare.util;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class EvenDivAsyncCalculator {

	public interface EvenDivListener {
		void setEvenDivResult(double result);
	}

	public static void evenDivAsync(long[] billIds, long[] memberIds,
			EvenDivListener listener) {
		new EvenDivTask(billIds, memberIds, listener).execute();

	}

	private static class EvenDivTask extends AsyncTask<Void, Void, Double> {

		EvenDivListener listener = null;

		private long[] billIds;
		private long[] memberIds;

		public EvenDivTask(long[] billIds, long[] memberIds,
				EvenDivListener listener) {
			this.billIds = billIds;
			this.memberIds = memberIds;
			this.listener = listener;
		}

		@Override
		protected Double doInBackground(Void... params) {
			double result = 0;
			
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
				double amount = 0d;
				while(cursorBill.moveToNext()){
					amount += cursorBill.getDouble(cursorBill
							.getColumnIndexOrThrow(COL_AMOUNT));
				}
				
				
				result = amount / (memberIds.length);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cursorBill != null) {
					cursorBill.close();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(Double result) {

			super.onPostExecute(result);

			listener.setEvenDivResult(result);
		}
	}
}

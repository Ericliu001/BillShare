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

	public static void evenDivAsync(long billId, long[] memberIds,
			EvenDivListener listener) {
		new EvenDivTask(billId, memberIds, listener).execute();

	}

	private static class EvenDivTask extends AsyncTask<Void, Void, Double> {

		EvenDivListener listener = null;

		private long billId;
		private long[] memberIds;

		public EvenDivTask(long billId, long[] memberIds,
				EvenDivListener listener) {
			this.billId = billId;
			this.memberIds = memberIds;
			this.listener = listener;
		}

		@Override
		protected Double doInBackground(Void... params) {
			double result = 0;
			Uri uriBill = Uri.withAppendedPath(BillProvider.BILL_URI,
					String.valueOf(billId));
			String[] projectionForBill = { COL_ROWID, COL_AMOUNT };
			Cursor cursorBill = null;

			try {
				cursorBill = MyApplication.getInstance().getContentResolver()
						.query(uriBill, projectionForBill, null, null, null);
				cursorBill.moveToFirst();
				double amount = cursorBill.getDouble(cursorBill
						.getColumnIndexOrThrow(COL_AMOUNT));
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

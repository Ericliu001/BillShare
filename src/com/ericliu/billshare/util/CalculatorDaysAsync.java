package com.ericliu.billshare.util;

import java.util.ArrayList;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class CalculatorDaysAsync {

	public interface CalculatorDaysListener {
		void setCalDaysResult(ArrayList<Double> amountPayeeForEachBill,
				ArrayList<Double> totalPayeeAmount);
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
		ArrayList<Double> totalPayeeAmount = new ArrayList<Double>();

		public CalDaysTask(long[] billIds, long[] memberIds,
				CalculatorDaysListener listener) {
			this.billIds = billIds;
			this.memberIds = memberIds;
			this.listener = listener;
		}

		@Override
		protected ArrayList<Double> doInBackground(Void... params) {
			ArrayList<Double> payeeAmountForEachBill = new ArrayList<Double>();
			ArrayList<Integer> payeePayingDaysForEachBill = new ArrayList<Integer>();
			ArrayList<Integer> billingDaysForEachBill = new ArrayList<Integer>();
			ArrayList<Double> amountForEachBill = new ArrayList<Double>();

			String selectionBill = COL_ROWID + " =? ";
			String[] selectionBillArgs = new String[billIds.length];
			for (int i = 0; i < billIds.length; i++) {
				selectionBillArgs[i] = String.valueOf(billIds[i]);
				if (i < billIds.length - 1) {
					selectionBill = selectionBill + " OR  " + COL_ROWID + "=? ";
				}
			}

			String selectionMember = COL_ROWID + " =? ";
			String[] selectionMemberArgs = new String[memberIds.length];
			for (int i = 0; i < memberIds.length; i++) {
				selectionMemberArgs[i] = String.valueOf(memberIds[i]);
				if (i < memberIds.length - 1) {
					selectionMember = selectionMember + " OR " + COL_ROWID
							+ " =? ";
				}
			}

			String[] projectionForMember = { COL_ROWID, COL_MOVE_IN_DATE,
					COL_MOVE_OUT_DATE };
			Cursor cursorMember = null;

			String[] projectionForBill = { COL_ROWID, COL_AMOUNT, COL_BILLING_START, COL_BILLING_END };
			Cursor cursorBill = null;
			try {
				cursorBill = MyApplication
						.getInstance()
						.getContentResolver()
						.query(BillProvider.BILL_URI, projectionForBill,
								selectionBill, selectionBillArgs, null);
				cursorMember = MyApplication
						.getInstance()
						.getContentResolver()
						.query(BillProvider.HOUSEMATE_URI, projectionForMember,
								selectionMember, selectionMemberArgs, null);

				for (cursorBill.moveToFirst(); !cursorBill.isAfterLast(); cursorBill.moveToNext()) {
					double amountInOneBill = cursorBill.getDouble(cursorBill
							.getColumnIndexOrThrow(COL_AMOUNT));

					amountForEachBill.add(amountInOneBill);
					for (cursorMember.moveToFirst(); !cursorMember
							.isAfterLast(); cursorMember.moveToNext()) {
						// we need percentage of each member here
						String billStartDate = cursorBill.getString(cursorBill
								.getColumnIndex(COL_BILLING_START));
						String billEndDate = cursorBill.getString(cursorBill
								.getColumnIndex(COL_BILLING_END));
						String memberStartDate = cursorMember
								.getString(cursorMember
										.getColumnIndex(COL_MOVE_IN_DATE));
						String memberEndDate = cursorMember
								.getString(cursorMember
										.getColumnIndex(COL_MOVE_OUT_DATE));

						int payingDays = UtilCompareDates.compareDates(
								memberStartDate, memberEndDate, billStartDate,
								billEndDate);
						if (payingDays >= 0) {
							payeePayingDaysForEachBill.add(payingDays);
						} else {
							payeePayingDaysForEachBill.add(0);
						}

						int billingDays = UtilCompareDates.compareDates(
								billStartDate, billEndDate);
						billingDaysForEachBill.add(billingDays);
					}
				}

				// finish the loop
				payeeAmountForEachBill = calculatePayeeAmount(
						amountForEachBill, billingDaysForEachBill,
						payeePayingDaysForEachBill);

				double[] tempTotalPayeeAmount = new double[cursorMember.getCount()];
				for (int i = 0; i < cursorMember.getCount(); i++) {
					for (int j = 0; j < cursorBill.getCount(); j++) {
						tempTotalPayeeAmount[i] += (payeeAmountForEachBill
								.get((i + 1) * (j + 1) - 1));
					}

				}
				
				for (int i = 0; i < tempTotalPayeeAmount.length; i++) {
					totalPayeeAmount.add(tempTotalPayeeAmount[i]);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				
				if (cursorBill != null) {
					cursorBill.close();
				}
				
				if (cursorMember != null) {
					cursorMember.close();
					
				}
			}

			return payeeAmountForEachBill;
		}

		private ArrayList<Double> calculatePayeeAmount(
				ArrayList<Double> amountForEachBill,
				ArrayList<Integer> billingDaysForEachBill,
				ArrayList<Integer> payeePayingDaysForEachBill) {

			ArrayList<Double> payeeAmountForEachBill = new ArrayList<Double>();

			for (int i = 0; i < amountForEachBill.size(); i++) {
				int totalPayeeLivingDays = 0;
				double percentageOfPayeeForEachBill = 0d;

				for (int j = 0; j < payeePayingDaysForEachBill.size(); j++) {

					totalPayeeLivingDays += payeePayingDaysForEachBill.get(j);
				}
				Log.i("eric", "totalPayeeLivingDays " + totalPayeeLivingDays);

				for (int j = 0; j < payeePayingDaysForEachBill.size(); j++) {
					percentageOfPayeeForEachBill = payeePayingDaysForEachBill
							.get(j) / totalPayeeLivingDays;
					double amountEachPayeeForEachBill = amountForEachBill
							.get(i) * payeePayingDaysForEachBill.get(j)/ totalPayeeLivingDays;
					payeeAmountForEachBill.add(amountEachPayeeForEachBill);
				}

			}
			
			if (MyApplication.isTesting) {
				Log.i("eric", "payeeAmountForEachBill " + payeeAmountForEachBill.toString());
				Log.i("eric", "amountForEachBill " + amountForEachBill.toString());
				Log.i("eric", "billingDaysForEachBill " + billingDaysForEachBill.toString());
				Log.i("eric", "payeePayingDaysForEachBill " + payeePayingDaysForEachBill.toString());
			}

			return payeeAmountForEachBill;
		}

		@Override
		protected void onPostExecute(ArrayList<Double> result) {

			super.onPostExecute(result);

			listener.setCalDaysResult(result,  totalPayeeAmount);
		}

	}

}

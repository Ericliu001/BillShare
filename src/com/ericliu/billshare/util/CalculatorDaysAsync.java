package com.ericliu.billshare.util;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_AMOUNT;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_BILLING_END;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_BILLING_START;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_MOVE_IN_DATE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_MOVE_OUT_DATE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.provider.BillProvider;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class CalculatorDaysAsync {

	public interface CalculatorDaysListener {
		void setCalDaysResult(double[][] payeeAmountBillPerMember,
				double[] sumPayeeAmount, int[] payeePercentage);
	}

	public static void calculateByDaysAsync(long[] billIds, long[] memberIds,
			CalculatorDaysListener listener) {
		new CalDaysTask(billIds, memberIds, listener).execute();
	}

	private static class CalDaysTask extends AsyncTask<Void, Void, Void> {

		private long[] billIds;
		private long[] memberIds;
		private CalculatorDaysListener listener;

		private int[] billingDaysPerBill;
		private int[][] payingDaysBillPerMember;
		private double[] billingAmountPerBill;
		private double[][] payeeAmountBillPerMember;
		private double[] sumPayeeAmount;
		private int[] payeePercentage;

		public CalDaysTask(long[] billIds, long[] memberIds,
				CalculatorDaysListener listener) {
			this.billIds = billIds;
			this.memberIds = memberIds;
			this.listener = listener;

			payingDaysBillPerMember = new int[billIds.length][memberIds.length];
			billingDaysPerBill = new int[billIds.length];
			billingAmountPerBill = new double[billIds.length];
			payeeAmountBillPerMember = new double[billIds.length][memberIds.length];
			sumPayeeAmount = new double[memberIds.length];
			payeePercentage = new int[memberIds.length];
		}

		@Override
		protected Void doInBackground(Void... params) {
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

			String[] projectionForBill = { COL_ROWID, COL_AMOUNT,
					COL_BILLING_START, COL_BILLING_END };
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

				int i = 0;
				for (cursorBill.moveToFirst(); !cursorBill.isAfterLast(); cursorBill
						.moveToNext(), i++) {

					String billStartDate = cursorBill.getString(cursorBill
							.getColumnIndex(COL_BILLING_START));
					String billEndDate = cursorBill.getString(cursorBill
							.getColumnIndex(COL_BILLING_END));

					int j = 0;
					for (cursorMember.moveToFirst(); !cursorMember
							.isAfterLast(); cursorMember.moveToNext(), j++) {
						// we need percentage of each member here

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
							payingDaysBillPerMember[i][j] = payingDays;

						} else {
							payingDaysBillPerMember[i][j] = 0;
						}

					}
					int billingDays = UtilCompareDates.compareDates(
							billStartDate, billEndDate);
					billingDaysPerBill[i] = billingDays;

					billingAmountPerBill[i] = Double.valueOf(cursorBill
							.getString(cursorBill.getColumnIndex(COL_AMOUNT)));
				}

				// finish the loop
				payeeAmountBillPerMember = getPayeeAmountBillPerMember(
						payingDaysBillPerMember, billingDaysPerBill,
						billingAmountPerBill);

				int[] sumPayingDaysPerMember = new int[memberIds.length];
				int sumBillingDaysMultiBills = 0;
				for (int j = 0; j < memberIds.length; j++) {
					for (int ii = 0; ii < billIds.length; ii++) {
						sumPayeeAmount[j] += payeeAmountBillPerMember[ii][j];
						sumPayingDaysPerMember[j] += payingDaysBillPerMember[ii][j];
						
					}
				}
				
				for (int ii = 0; ii < billIds.length; ii++) {
					sumBillingDaysMultiBills += billingDaysPerBill[ii];
				}

				if (MyApplication.isTesting) {

					Log.i("eric",
							"payingDaysBillPerMember "
									+ ArrayToString
											.arrayToString(payingDaysBillPerMember));
					Log.i("eric",
							"billingDaysPerBill "
									+ ArrayToString
											.arrayToString(billingDaysPerBill));
					Log.i("eric",
							"billingAmountPerBill "
									+ ArrayToString
											.arrayToString(billingAmountPerBill));
					
					Log.i("eric",
							"sumPayingDaysPerMember "
									+ ArrayToString
											.arrayToString(sumPayingDaysPerMember));
					
					Log.i("eric",
							"sumBillingDaysMultiBills "
									+ sumBillingDaysMultiBills);
				}
				for (int j = 0; j < memberIds.length; j++) {
					payeePercentage[j] = (int) ((double) sumPayingDaysPerMember[j] * 100 / (double) sumBillingDaysMultiBills);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {

				if (cursorBill != null) {
					cursorBill.close();
				}

				if (cursorMember != null) {
					cursorMember.close();

				}
			}

			return null;
		}

		private double[][] getPayeeAmountBillPerMember(
				int[][] payingDaysBillPerMember, int[] billingDaysPerBill,
				double[] billingAmountPerBill) {

			double[][] payeeAmountBillPerMember = new double[billIds.length][memberIds.length];

			int[] sumMemberPayingDaysPerBill = new int[billIds.length];
			for (int i = 0; i < billIds.length; i++) {
				for (int j = 0; j < memberIds.length; j++) {
					sumMemberPayingDaysPerBill[i] += payingDaysBillPerMember[i][j];
				}

			}

			for (int i = 0; i < billIds.length; i++) {
				for (int j = 0; j < memberIds.length; j++) {
					payeeAmountBillPerMember[i][j] = (double) (billingAmountPerBill[i] * payingDaysBillPerMember[i][j])
							/ (double) sumMemberPayingDaysPerBill[i];
				}
			}

			return payeeAmountBillPerMember;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

			if (MyApplication.isTesting) {

				Log.i("eric",
						"sumPayeeAmount "
								+ ArrayToString.arrayToString(sumPayeeAmount));
				Log.i("eric",
						"payeePercentage "
								+ ArrayToString.arrayToString(payeePercentage));
			}

			listener.setCalDaysResult(payeeAmountBillPerMember, sumPayeeAmount,
					payeePercentage);

		}

	}

}

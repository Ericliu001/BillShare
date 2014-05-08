package com.ericliu.billshare.util;

import java.util.ArrayList;

import android.os.AsyncTask;

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

		public CalDaysTask(long[] billIds, long[] memberIds,
				CalculatorDaysListener listener) {
			this.billIds = billIds;
			this.memberIds = memberIds;
			this.listener = listener;
		}

		@Override
		protected ArrayList<Double> doInBackground(Void... params) {

			return null;
		}

	}

}

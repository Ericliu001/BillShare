package com.ericliu.billshare.activity;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_MEMBER_FULLNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.model.Payment;
import com.ericliu.billshare.model.PaymentListEntry;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.util.CalculatorDaysAsync;
import com.ericliu.billshare.util.CalculatorEvenDivAsync;
import com.ericliu.billshare.util.CalculatorEvenDivAsync.EvenDivListener;

public class PaymentActivity extends DrawerActivity {

	private static final String TAG = "paymentfragment";
	private PaymentFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		frag = (PaymentFragment) getFragmentManager().findFragmentByTag(TAG);
		if (frag == null) {
			frag = new PaymentFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, TAG).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PaymentFragment extends Fragment implements
			EvenDivListener, com.ericliu.billshare.util.CalculatorDaysAsync.CalculatorDaysListener, LoaderCallbacks<Cursor> {
		private Intent receivedIntent = null;

		private TextView tvSum;
		private TextView tvNumBill;
		private TextView tvNumMember;
		private ListView lvPayment;

		private long[] memberIds;
		private long[] billIds;
		private String[] memberNames;

		private ArrayList<Payment> paymentList;
		private ArrayList<PaymentListEntry> entryList;

		private ArrayAdapter<PaymentListEntry> adapter;
		private static final String[] PROJECTION = { COL_ROWID,
				COL_MEMBER_FULLNAME };
		private String selection = COL_ROWID + " =? ";
		private String[] selectionArgs = null;

		private DecimalFormat dollarForum;

		public PaymentFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);

			dollarForum = new DecimalFormat("$###,###,###,###.##");

		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);

			receivedIntent = activity.getIntent();

			memberIds = receivedIntent
					.getLongArrayExtra(CalculationParameterActivity.CHECKED_MEMBER_IDS);
			billIds = receivedIntent
					.getLongArrayExtra(CalculationParameterActivity.CHECKED_BILL_IDS);

			memberNames = new String[memberIds.length];

			if (memberIds != null) {
				selectionArgs = new String[memberIds.length];
				for (int i = 0; i < memberIds.length; i++) {
					selectionArgs[i] = String.valueOf(memberIds[i]);
					if (i < memberIds.length - 1) {
						selection = selection + " OR " + COL_ROWID + " =? ";
					}
				}

			}

			paymentList = new ArrayList<Payment>();
			entryList = new ArrayList<PaymentListEntry>();

			activity.getLoaderManager().initLoader(0, null, this);

		}

		public void calculateEvenDiv() {
			CalculatorEvenDivAsync.evenDivAsync(billIds, memberIds, this);

		}
		
		private void calculateByDays() {
			
			CalculatorDaysAsync.calculateByDaysAsync(billIds, memberIds, this);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_payment,
					container, false);
			tvSum = (TextView) rootView.findViewById(R.id.tvSum);
			tvNumBill = (TextView) rootView.findViewById(R.id.tvNumBill);
			tvNumMember = (TextView) rootView.findViewById(R.id.tvNumMember);
			lvPayment = (ListView) rootView.findViewById(R.id.lvPayment);

			fillTheForm();

			class ViewHolder {
				private TextView tvPayeeFullName;
				private ProgressBar pbPercentage;
				private TextView tvPayeeAmount;

				public ViewHolder(View view) {
					tvPayeeFullName = (TextView) view
							.findViewById(R.id.tvPayeeFullName);
					pbPercentage = (ProgressBar) view
							.findViewById(R.id.pbPercentage);
					tvPayeeAmount = (TextView) view
							.findViewById(R.id.tvPayeeAmout);
				}
			}

			adapter = new ArrayAdapter<PaymentListEntry>(getActivity(),
					R.layout.payment_row, R.id.tvPayeeFullName, entryList) {
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					View result = super.getView(position, convertView, parent);
					ViewHolder viewHolder = (ViewHolder) result.getTag();
					if (viewHolder == null) {
						viewHolder = new ViewHolder(result);
						result.setTag(viewHolder);
					}
					PaymentListEntry entry = getItem(position);
					viewHolder.tvPayeeFullName.setText(entry.getPayeeName());
					viewHolder.pbPercentage.setProgress(entry.getPayeePercentage());
					viewHolder.tvPayeeAmount.setText(dollarForum.format(entry
							.getPayeeAmount()));

					return result;
				}
			};

			lvPayment.setAdapter(adapter);

			return rootView;
		}

		private void fillTheForm() {

			tvNumBill.setText(String.valueOf(billIds.length));
			tvNumMember.setText(String.valueOf(memberIds.length));
		}

		@Override
		public void setEvenDivResult(ArrayList<Double> payeeAmountForEachBill,
				double totalAmount, double payeeAmountForTotal) {

			// PaymentInfo paymentInfo = new PaymentInfo();
			// Uri uri = paymentInfo.save();
			// long paymentInfoID = Long.valueOf(uri.getLastPathSegment());

			for (int j = 0; j < memberIds.length; j++) {
				for (int i = 0; i < billIds.length; i++) {
					Payment payment = new Payment();
					// payment.setPayment_info_id(paymentInfoID);
					payment.setBill_id(billIds[i]);
					payment.setPayee_id(memberIds[j]);
					payment.setPayee_amount(payeeAmountForEachBill.get(i));
					// more fields need to be set here

					paymentList.add(payment);
				}

				PaymentListEntry entry = new PaymentListEntry();
				entry.setPayeeName(memberNames[j]);
				entry.setPayeePercentage(100);
				entry.setPayeeAmount(payeeAmountForTotal);
				entryList.add(entry);
			}

			adapter.notifyDataSetChanged();
			tvSum.setText(dollarForum.format(totalAmount));
		}
		
		@Override
		public void setCalDaysResult(double[][] payeeAmountBillPerMember,
				double[] sumPayeeAmount, int[] payeePercentage) {
			for (int j = 0; j < memberIds.length; j++) {
				
				PaymentListEntry entry = new PaymentListEntry();
				entry.setPayeeName(memberNames[j]);
				entry.setPayeePercentage(payeePercentage[j]);
				entry.setPayeeAmount(sumPayeeAmount[j]);
				entryList.add(entry);
			}
			
			adapter.notifyDataSetChanged();
			
		}
		

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			return new CursorLoader(getActivity(),
					BillProvider.DIALOG_MEMBER_URI, PROJECTION, selection,
					selectionArgs, null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

			cursor.moveToPosition(-1);
			for (int i = 0; cursor.moveToNext(); i++) {
				memberNames[i] = cursor.getString(cursor
						.getColumnIndexOrThrow(COL_MEMBER_FULLNAME));
			}

			if (receivedIntent.getAction().equals(
					DrawerActivity.ACTION_EVEN_DIV)) {
				calculateEvenDiv();
			}else if (receivedIntent.getAction().equals(DrawerActivity.ACTION_CALCULATE_BY_DAYS)) {
				calculateByDays();
			}
		}

		

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
		}

		

		
	}

}

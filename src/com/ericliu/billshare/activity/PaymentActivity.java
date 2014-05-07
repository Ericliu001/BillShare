package com.ericliu.billshare.activity;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.model.Payment;
import com.ericliu.billshare.provider.BillProvider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class PaymentActivity extends DrawerActivity {
	
	private static final String TAG = "paymentfragment";
	public static final int LOADER_ID = 0;
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
	public static class PaymentFragment extends Fragment implements LoaderCallbacks<Cursor> {
		private Intent receivedIntent = null;
		
		private TextView tvSum;
		private TextView tvNumBill;
		private TextView tvNumMember;
		private ListView lvPayment;
		private ArrayList<Payment> paymentList;
		
		
		
		private SimpleCursorAdapter adapter;
		private static final String[] PROJECTION = {COL_ROWID, COL_MEMBER_FULLNAME};
		private  String selection = COL_ROWID + "=?";
		private String[] selectionArgs = null;
		
		public PaymentFragment() {
		}
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}
		
		@Override
		public void onAttach(Activity activity) {
			
			super.onAttach(activity);
			receivedIntent = activity.getIntent();
			long[] memberIds = receivedIntent.getLongArrayExtra(EvenDivisionActivity.CHECKED_MEMBER_IDS);
			long[] billIds = receivedIntent.getLongArrayExtra(EvenDivisionActivity.CHECKED_BILL_IDS);
			
			if (memberIds != null) {
				selectionArgs = new String[memberIds.length];
				for (int i = 0; i < memberIds.length; i++) {
					selectionArgs[i] = String.valueOf(memberIds[i]);
					
				}
				
				for (int i = 0; i < selectionArgs.length - 1; i++) {
					selection = selection + " OR " + COL_ROWID +"=?";
				}
			}
			
			
			
			
			activity.getLoaderManager().initLoader(LOADER_ID, null, this);
			
			
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
			
			String[] from = {COL_MEMBER_FULLNAME};
			int[] to = {R.id.tvPayeeFullName};
			adapter = new SimpleCursorAdapter(getActivity(), R.layout.payment_row, null, from, to, 0);
			lvPayment.setAdapter(adapter);
			
			return rootView;
		}


		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			
			return new CursorLoader(getActivity(), BillProvider.DIALOG_MEMBER_URI, PROJECTION, selection, selectionArgs, null);
		}


		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			adapter.swapCursor(data);
		}


		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			adapter.swapCursor(null);
		}
	}

}

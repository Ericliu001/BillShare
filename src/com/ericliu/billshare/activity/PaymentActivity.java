package com.ericliu.billshare.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
	public static class PaymentFragment extends Fragment {
		private Intent receivedIntent = null;
		
		private TextView tvSum;
		private TextView tvNumBill;
		private TextView tvNumMember;
		private ListView lvPayment;
		
		
		
		private SimpleCursorAdapter adapter;

		
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
			
			String[] from = {};
			int[] to = {};
			adapter = new SimpleCursorAdapter(getActivity(), R.layout.payment_row, null, from, to, 0);
			
			return rootView;
		}
	}

}

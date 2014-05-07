package com.ericliu.billshare.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.model.Payment;
import com.ericliu.billshare.model.PaymentInfo;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.util.EvenDivAsyncCalculator;
import com.ericliu.billshare.util.EvenDivAsyncCalculator.EvenDivListener;

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
	public static class PaymentFragment extends Fragment implements  EvenDivListener {
		private Intent receivedIntent = null;
		
		private TextView tvSum;
		private TextView tvNumBill;
		private TextView tvNumMember;
		private ListView lvPayment;

		
		private long[] memberIds;
		private long[] billIds;
		
		
		private ArrayList<Payment> paymentList;
		
		private ArrayAdapter<Payment> adapter;
		private static final String[] PROJECTION = {COL_ROWID, COL_MEMBER_FULLNAME};
		private  String selection = COL_ROWID + " =? ";
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
			 memberIds = receivedIntent.getLongArrayExtra(EvenDivisionActivity.CHECKED_MEMBER_IDS);
			 billIds = receivedIntent.getLongArrayExtra(EvenDivisionActivity.CHECKED_BILL_IDS);
			
			if (memberIds != null) {
				selectionArgs = new String[memberIds.length];
				for (int i = 0; i < memberIds.length; i++) {
					selectionArgs[i] = String.valueOf(memberIds[i]);
					if (i < memberIds.length - 1) {
						selection = selection + " OR " + COL_ROWID +" =? ";
					}
				}
				
			}
			
			
			paymentList = new ArrayList<Payment>();
			calculateAndSaveToDB();
			
			
			
			
			
			
		}


		public void calculateAndSaveToDB() {
			EvenDivAsyncCalculator.evenDivAsync(billIds, memberIds, this);
			
			
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
			adapter = new ArrayAdapter<Payment>(getActivity(), R.layout.payment_row, R.id.tvPayeeFullName, paymentList);
			lvPayment.setAdapter(adapter);
			
			return rootView;
		}




		@Override
		public void setEvenDivResult(double result) {
			

			PaymentInfo paymentInfo = new PaymentInfo();
			Uri uri = paymentInfo.save();
			long paymentInfoID = Long.valueOf(uri.getLastPathSegment());
			
			for (int i = 0; i < billIds.length; i++) {
				for (int j = 0; j < memberIds.length; j++) {
					Payment payment = new Payment();
					payment.setPayment_info_id(paymentInfoID);
					payment.setBill_id(billIds[i]);
					payment.setPayee_id(memberIds[j]);
					payment.setPayee_amount(result);
					// more fields need to be set here
					
					paymentList.add(payment);
				}
			}
			
		}
	}

}

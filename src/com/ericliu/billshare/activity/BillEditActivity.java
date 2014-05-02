package com.ericliu.billshare.activity;

import com.ericliu.billshare.R;
import com.ericliu.billshare.R.id;
import com.ericliu.billshare.R.layout;
import com.ericliu.billshare.R.menu;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Build;

public class BillEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		long id = getIntent().getLongExtra(DatabaseConstants.COL_ROWID, -1);

		if (getFragmentManager().findFragmentByTag("saved") == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container, BillEditFragment.newInstance(id),
							"saved").commit();
		}
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class BillEditFragment extends Fragment {

		private long id;

		private Spinner spType;
		private EditText etAmount;
		private CheckBox cbPaid;

		public static Fragment newInstance(long id) {
			BillEditFragment frag = new BillEditFragment();
			Bundle args = new Bundle();
			args.putLong(DatabaseConstants.COL_ROWID, id);
			frag.setArguments(args);

			return frag;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			setHasOptionsMenu(true);

			id = getArguments().getLong(DatabaseConstants.COL_ROWID);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bill_edit,
					container, false);

			spType = (Spinner) rootView.findViewById(R.id.spType);
			etAmount = (EditText) rootView.findViewById(R.id.etAmount);
			cbPaid = (CheckBox) rootView.findViewById(R.id.cbPaid);

			
			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
					android.R.layout.simple_spinner_item, getResources()
					.getStringArray(R.array.bill_type_spinner_items));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spType.setAdapter(adapter);

			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			
			
			inflater.inflate(R.menu.bill_edit, menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			switch (item.getItemId()) {
			case R.id.saveBill:
				
				break;

			default:
				break;
			}
			
			return super.onOptionsItemSelected(item);
		}
		
	}

}

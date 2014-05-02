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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bill_edit, menu);
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

			spType.setAdapter(new ArrayAdapter<CharSequence>(getActivity(),
					android.R.layout.simple_spinner_item, getResources()
							.getStringArray(R.array.bill_type_spinner_items)));

			return rootView;
		}
	}

}

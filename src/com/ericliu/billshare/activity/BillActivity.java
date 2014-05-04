package com.ericliu.billshare.activity;

import android.app.Activity;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.R.id;
import com.ericliu.billshare.R.layout;
import com.ericliu.billshare.R.menu;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class BillActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag("saved") == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new BillFragment(), "saved").commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.create) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class BillFragment extends ListFragment implements
			LoaderCallbacks<Cursor> {

		private static final String[] PROJECTION = new String[] { COL_ROWID,
				COL_TYPE, COL_AMOUNT, COL_PAID, COL_DUE_DATE };

		private SimpleCursorAdapter adapter;

		public BillFragment() {
		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);
			activity.getLoaderManager().initLoader(0, null, this);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);

			String[] from = {COL_TYPE, COL_AMOUNT, COL_PAID, COL_DUE_DATE };
			int[] to = { R.id.tvType, R.id.tvAmount, R.id.tvPaid, R.id.tvDueDay };

			adapter = new SimpleCursorAdapter(getActivity(), R.layout.bill_row,
					null, from, to, 0) {
				TextView tvPaid;

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					View result = super.getView(position, convertView, parent);
					tvPaid = (TextView) result.getTag();

					if (tvPaid == null) {
						tvPaid = (TextView) result.findViewById(R.id.tvPaid);
					}
					String paid = tvPaid.getText().toString();
					if (paid != null) {

						if (Integer.valueOf(paid) > 0) {
							tvPaid.setText("Paid");
						} else {
							tvPaid.setText("Unpaid");
						}
					}

					return result;
				}
			};

			setListAdapter(adapter);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

			super.onListItemClick(l, v, position, id);
			
			Intent i = new Intent(getActivity(), BillEditActivity.class);
			
			i.putExtra(DatabaseConstants.COL_ROWID, id);
			startActivity(i);
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			return new CursorLoader(getActivity(), BillProvider.BILL_URI,
					PROJECTION, null, null, null);
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

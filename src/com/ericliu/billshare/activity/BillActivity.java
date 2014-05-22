package com.ericliu.billshare.activity;

import java.text.DecimalFormat;

import android.app.Activity;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
	private static final String TAG = "BillFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag(TAG) == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new BillFragment(), TAG).commit();
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
			Intent i = new Intent(this, BillEditActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class BillFragment extends ListFragment implements
			LoaderCallbacks<Cursor> {

		private static final String[] PROJECTION = { COL_ROWID,
				COL_TYPE, COL_AMOUNT, COL_PAID, COL_DUE_DATE };

		private SimpleCursorAdapter adapter;
		private DecimalFormat dollarForum;		

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
			dollarForum = new DecimalFormat("$###,###,###,###.##");

			String[] from = {COL_TYPE, COL_AMOUNT, COL_PAID, COL_DUE_DATE };
			int[] to = { R.id.tvType, R.id.tvAmount, R.id.tvPaid, R.id.tvDueDay };
			
			
			class ViewHolder{
				private TextView tvAmount;
				private TextView tvPaid;
				private TextView tvDueDay;
				
				public ViewHolder(View view) {
					
					tvAmount = (TextView) view.findViewById(R.id.tvAmount);
					tvPaid = (TextView) view.findViewById(R.id.tvPaid);
					tvDueDay = (TextView) view.findViewById(R.id.tvDueDay);
				}
			}

			adapter = new SimpleCursorAdapter(getActivity(), R.layout.bill_row,
					null, from, to, 0) {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					View result = super.getView(position, convertView, parent);
					ViewHolder viewHolder = (ViewHolder) result.getTag();

					if (viewHolder == null) {
						viewHolder = new ViewHolder(result);
						
						result.setTag(viewHolder);
					}
					
					viewHolder.tvAmount.setText(dollarForum.format(Double.valueOf(viewHolder.tvAmount.getText().toString())));
					
					String dueDayStr = viewHolder.tvDueDay.getText().toString();
					if (! TextUtils.isEmpty(dueDayStr)) {
						viewHolder.tvDueDay.setText("Due "
								+ dueDayStr);
					}
					String paid = viewHolder.tvPaid.getText().toString();
					if (paid != null) {

						if (Integer.valueOf(paid) > 0) {
							viewHolder.tvPaid.setText("Paid");
							viewHolder.tvDueDay.setVisibility(View.GONE);
						} else {
							viewHolder.tvPaid.setText("Unpaid");
						}
					}

					return result;
				}
			};

			setListAdapter(adapter);
			
		}
		
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			
			super.onActivityCreated(savedInstanceState);
			setEmptyText(getResources().getString(R.string.you_havent_created_any_bill_yet));
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

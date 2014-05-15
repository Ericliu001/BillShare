package com.ericliu.billshare.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

import com.ericliu.billshare.R;
import com.ericliu.billshare.R.id;
import com.ericliu.billshare.R.layout;
import com.ericliu.billshare.adapter.HistorySimpleCursorTreeAdapter;
import com.ericliu.billshare.provider.BillProvider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class HistoryActivity extends DrawerActivity {

	private HistoryFragment placeHolderFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		placeHolderFrag = (HistoryFragment) getFragmentManager()
				.findFragmentByTag("saved");
		if (placeHolderFrag == null) {
			placeHolderFrag = new HistoryFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, placeHolderFrag).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class HistoryFragment extends Fragment implements
			LoaderCallbacks<Cursor> {

		private static final int groupLoaderId = -1;

		private ExpandableListView elv;
		private HistorySimpleCursorTreeAdapter adapter;

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);

		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);

			Loader<Cursor> groupLoader = activity.getLoaderManager().getLoader(
					groupLoaderId);
			if (groupLoader != null && !groupLoader.isReset()) {
				activity.getLoaderManager().restartLoader(groupLoaderId, null,
						this);
			} else {
				activity.getLoaderManager().initLoader(groupLoaderId, null,
						this);
			}

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_history,
					container, false);

			elv = (ExpandableListView) rootView.findViewById(R.id.elv);
			String[] groupFrom = { COL_NAME, COL_TOTAL_AMOUNT,
					COL_NUMBER_OF_BILLS_PAID };
			int[] groupTo = { R.id.tvPaymentInfoName, R.id.tvPaymentInfoAmount,
					R.id.tvPaymentInfoBillsPaid };
			String[] childFrom = { COL_TYPE, COL_FIRSTNAME, COL_LASTNAME,
					COL_PAYEE_AMOUNT, COL_PAYEE_START_DATE, COL_PAYEE_END_DATE };
			int[] childTo = { R.id.tvChildType, R.id.tvChildFirstName,
					R.id.tvChildLastName, R.id.tvChildAmount,
					R.id.tvChildStartDate, R.id.tvChildEndDate };

			adapter = new HistorySimpleCursorTreeAdapter(getActivity(), this,
					null, R.layout.history_parent_layout,
					R.layout.history_parent_layout, groupFrom, groupTo,
					R.layout.history_child_layout, childFrom, childTo);
			elv.setAdapter(adapter);

			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			CursorLoader cursorLoader = null;

			if (id != groupLoaderId) {
				String[] childProjection = { COL_ROWID, COL_SERIAL_NUMBER,
						COL_TYPE, COL_FIRSTNAME, COL_LASTNAME,
						COL_PAYEE_AMOUNT, COL_PAYEE_START_DATE,
						COL_PAYEE_END_DATE };

				String selection = COL_SERIAL_NUMBER + " =? ";
				String[] selectionArgs = { String.valueOf(id) };
				cursorLoader = new CursorLoader(getActivity(),
						BillProvider.PAYMENT_FULL_DETAIL, childProjection,
						selection, selectionArgs, null);
			} else {
				String[] groupProjection = { COL_ROWID, COL_SERIAL_NUMBER,
						COL_NAME, COL_TOTAL_AMOUNT, COL_NUMBER_OF_BILLS_PAID };
				cursorLoader = new CursorLoader(getActivity(),
						BillProvider.PAYMENT_INFO_URI, groupProjection, null,
						null, COL_ROWID + " DESC ");
			}

			return cursorLoader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

			if (loader.getId() != groupLoaderId) {
				int groupPos = adapter.getGroupMap().get(loader.getId());
				adapter.setChildrenCursor(groupPos, data);

			} else {
				adapter.setGroupCursor(data);
			}

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			int id = loader.getId();
			if (id != groupLoaderId) {
				int groupPos = adapter.getGroupMap().get(id);

				adapter.setChildrenCursor(groupPos, null);
			} else {
				adapter.setGroupCursor(null);
			}

		}
	}

}

package com.ericliu.billshare.fragment;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;

import com.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MemberListFrag extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String[] PROJECTION = new String[] {
			DatabaseConstants.COL_ROWID, COL_FIRSTNAME, COL_LASTNAME

	};

	private SimpleCursorAdapter adapter = null;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		activity.getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		int[] to = new int[]{
				R.id.tvId
				,R.id.tvFristName
				,R.id.tvLastName
		};

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.member_row, null, PROJECTION, to, 0);

		setListAdapter(adapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return new CursorLoader(getActivity(), BillProvider.HOUSEMATE_URI,
				PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		adapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

}

package com.ericliu.billshare.activity;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

public class MemberActivity extends DrawerActivity {

	private static final String TAG = "MemberFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawerList.setItemChecked(DrawerActivity.ROOMMATES, true);
		
		long id = getIntent().getLongExtra(DatabaseConstants.COL_ROWID, -1);

		if (getFragmentManager().findFragmentByTag(TAG) == null) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.container, new MemberListFrag(), TAG)
					.commit();
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
			Intent i = new Intent(this, MemberEditActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class MemberListFrag extends ListFragment implements
			LoaderCallbacks<Cursor> {

		private static final String[] PROJECTION =  {
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
			setRetainInstance(true);

			int[] to = new int[] { R.id.tvId, R.id.tvFristName, R.id.tvLastName };

			adapter = new SimpleCursorAdapter(getActivity(),
					R.layout.member_row, null, PROJECTION, to, 0);

			setListAdapter(adapter);
			
		}
		
		
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			
			super.onActivityCreated(savedInstanceState);
			setEmptyText(getResources().getString(R.string.you_havent_created_any_member_yet));
		}
		
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			
			super.onListItemClick(l, v, position, id);
			
			Intent i = new Intent(getActivity(), MemberEditActivity.class);
			Bundle extras = new Bundle();
			extras.putLong(DatabaseConstants.COL_ROWID, id);
			i.putExtras(extras);
			startActivity(i);
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

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position != DrawerActivity.ROOMMATES) {
			
			super.onItemClick(parent, view, position, id);
		}
	}
}

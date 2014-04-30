package com.ericliu.billshare.activity;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.billshare.R;
import com.ericliu.billshare.fragment.MemberListFrag;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

public class MemeberActivity extends DrawerActivity {

	private static final String FRAGMENT_TAG = "MemberFragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null) {
			getFragmentManager().beginTransaction()
					.replace(R.id.container, new MemberListFrag(), FRAGMENT_TAG).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memeber, menu);
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

}

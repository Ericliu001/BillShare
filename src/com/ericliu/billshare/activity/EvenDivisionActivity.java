package com.ericliu.billshare.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ericliu.billshare.R;

public class EvenDivisionActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag("saved") == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new EvenDivisionFragment(), "saved").commit();
		}
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class EvenDivisionFragment extends Fragment {

		public EvenDivisionFragment() {
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_even_division,
					container, false);
			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.edit, menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			
			
			return super.onOptionsItemSelected(item);
		}
	}

}

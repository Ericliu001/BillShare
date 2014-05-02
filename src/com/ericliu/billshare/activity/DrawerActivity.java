package com.ericliu.billshare.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ericliu.billshare.R;

public class DrawerActivity extends Activity implements
		OnItemClickListener {

	private static final int QUICK_EVEN_DIVISION = 0;
	private static final int CALCULATE_BY_DAYS = 1;
	private static final int PAYMENT_HISTORY = 2;
	private static final int HOUSEMATES = 3;
	private static final int MANAGING_BILLS = 4;
	
	
	private DrawerLayout drawerLayout = null;
	private ActionBarDrawerToggle toggle;
	private ListView drawerList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.drawerList);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_row, getResources().getStringArray(
						R.array.drawer_rows)));
		drawerList.setOnItemClickListener(this);

		toggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);

		drawerLayout.setDrawerListener(toggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		toggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i;

		switch (position) {
		case QUICK_EVEN_DIVISION:
			Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
			break;

		case CALCULATE_BY_DAYS:

			break;

		case PAYMENT_HISTORY:

			break;

		case HOUSEMATES:
			 i = new Intent(this, MemeberActivity.class);
			startActivity(i);
			break;
			
		case MANAGING_BILLS:
			 i = new Intent(this, BillActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}

		drawerLayout.closeDrawers();
	}

}
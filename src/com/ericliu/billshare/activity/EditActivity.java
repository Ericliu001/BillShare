package com.ericliu.billshare.activity;

import android.app.Activity;
import android.os.Bundle;

import com.ericliu.billshare.fragment.DbWriteFragment;
import com.ericliu.billshare.fragment.DbWriteFragment.DbFragCallBack;
import com.ericliu.billshare.model.Model;

public abstract class EditActivity extends Activity implements DbFragCallBack {

	
	
	private DbWriteFragment dbWriteFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		dbWriteFrag = (DbWriteFragment) getFragmentManager().findFragmentByTag(
				"SAVE");

		if (dbWriteFrag == null) {
			dbWriteFrag = new DbWriteFragment();
			getFragmentManager().beginTransaction().add(dbWriteFrag, "SAVE")
					.commit();
		}
	}

	public void saveToDb() {

			dbWriteFrag.writeToDB();
	}

	@Override
	public abstract Model getModel();


}

package com.ericliu.billshare.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.ericliu.billshare.fragment.DbWriteFragment;
import com.ericliu.billshare.fragment.DbWriteFragment.DbFragCallBack;
import com.ericliu.billshare.model.Model;

public abstract class EditActivity extends ListActivity implements DbFragCallBack {

	private boolean isSaving = false;
	private DbWriteFragment dbWriteFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		dbWriteFrag = (DbWriteFragment) getFragmentManager().findFragmentByTag(
				"SAVE");

		if (dbWriteFrag == null) {
			dbWriteFrag = new DbWriteFragment();
			getFragmentManager().beginTransaction().add(dbWriteFrag, "SAVE")
					.commit();
		}
	}

	public void saveToDb() {
		if (! isSaving) {

			isSaving = true;
			dbWriteFrag.writeToDB();
		}
	}

	@Override
	public abstract Model getModel();

	@Override
	public void onPostExecute(Object result) {
		isSaving = false;
	}

}

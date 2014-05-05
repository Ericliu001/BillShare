package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.ListView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class SelectMembersDialog extends DialogFragment implements
		OnClickListener, LoaderCallbacks<Cursor>, OnMultiChoiceClickListener {

	  private SimpleCursorAdapter adapter;
	private static final String[] PROJECTION = new String[] {
			DatabaseConstants.COL_ROWID, COL_FIRSTNAME, COL_LASTNAME

	};
	
	private int loaderID = 22;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getActivity().getLoaderManager().initLoader(loaderID, null, this);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setView(inflater.inflate(R.layout.test_dialog, null));

		builder.setTitle(R.string.select_members);

		
		int[] to = new int[] { R.id.tvId, R.id.tvFristName, R.id.tvLastName };
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.member_row, null, PROJECTION, to, 0);
		builder.setAdapter(adapter, this);

		builder.setPositiveButton(R.string.done, this);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		// respind to action buttons click
		case DialogInterface.BUTTON_POSITIVE:
			//It's extremely important to destroy loader at this step!!
			getActivity().getLoaderManager().destroyLoader(loaderID);
			break;

		default:
			break;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return new CursorLoader(getActivity(), BillProvider.HOUSEMATE_URI,
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

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// respond to list item click
	}

}

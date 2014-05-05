package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class SelectBillsDialog extends DialogFragment implements OnClickListener, LoaderCallbacks<Cursor>, OnItemSelectedListener {
	private static final int loaderID = 11;
	private SimpleCursorAdapter adapter;
	
	private static final String[] PROJECTION = {
		COL_ROWID,
		COL_CHECKED,
		COL_BILL_NAME
	};
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		activity.getLoaderManager().initLoader(loaderID, null, this);
		
		String[] from = {COL_BILL_NAME};
		int[] to = {android.R.id.text1};
		adapter = new SimpleCursorAdapter(activity, android.R.layout.simple_list_item_multiple_choice, null, from, to, 0);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.select_bills).setPositiveButton(R.string.done, this).setNegativeButton(R.string.cancel, this);
		
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.multi_choice_listview, null);
		builder.setView(dialogView).setAdapter(adapter,new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		ListView lv = (ListView) dialogView.findViewById(R.id.lvMulti);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.setOnItemSelectedListener(this);
		
		return builder.create();
	}
	
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action buttons click
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			
			break;

		default:
			break;
		}
		getActivity().getLoaderManager().destroyLoader(loaderID);
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		return new CursorLoader(getActivity(), BillProvider.DIALOG_URI_BILL, PROJECTION, null, null, null);
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}

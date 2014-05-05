package com.ericliu.billshare.dialog;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_AMOUNT;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_TYPE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_UNPAID;
import android.app.Activity;
import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
<<<<<<< HEAD
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
=======
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
>>>>>>> parent of 9a75014... Implement default check unpaid bills
=======
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
>>>>>>> parent of 9a75014... Implement default check unpaid bills
import static com.ericliu.billshare.provider.DatabaseConstants.*;
/*
 * Try to use Loader to load data into Dialog but failed
 */

public class TestLoaderSelectBillsDialog extends DialogFragment implements OnClickListener, LoaderCallbacks<Cursor>, OnItemSelectedListener {
	
	
	
	private static final int loaderID = 11;
	private SimpleCursorAdapter adapter;
<<<<<<< HEAD
<<<<<<< HEAD
	private ListView lv;
	private SelectBillsDialogListener mCallback;

	public interface SelectBillsDialogListener{
		
	}
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
	
	private static final String[] PROJECTION = {
		COL_ROWID,
		COL_CHECKED,
		COL_BILL_NAME
	};
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		activity.getLoaderManager().initLoader(loaderID, null, this);
<<<<<<< HEAD
<<<<<<< HEAD

		String[] from = {  COL_TYPE,
				COL_AMOUNT, COL_UNPAID };
		int[] to = { R.id.tvMultiChoiceListRow, R.id.tvBillAmount ,R.id.tvChecked };
		adapter = new SimpleCursorAdapter(activity, R.layout.checked_row, null,
				from, to, 0) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {

					convertView = super.getView(position, convertView, parent);
				}
				View result = convertView;

				Holder holder = (Holder) result.getTag();

				if (holder == null) {
					holder = new Holder();
					holder.cktv = (CheckedTextView) result
							.findViewById(R.id.tvMultiChoiceListRow);
					holder.tvChecked = (TextView) result
							.findViewById(R.id.tvChecked);
					result.setTag(holder);
				}
				
				if (holder.tvChecked.getText().toString().equals(String.valueOf(1))) {
					holder.cktv.setChecked(true);
				}

				return result;
			}
		};
		
=======
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
		
		String[] from = {COL_BILL_NAME};
		int[] to = {android.R.id.text1};
		adapter = new SimpleCursorAdapter(activity, android.R.layout.simple_list_item_multiple_choice, null, from, to, 0);
<<<<<<< HEAD
>>>>>>> parent of 9a75014... Implement default check unpaid bills
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.select_bills).setPositiveButton(R.string.done, this).setNegativeButton(R.string.cancel, this);
		
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.multi_choice_listview, null);
		builder.setView(dialogView);
		
		ListView lv = (ListView) dialogView.findViewById(R.id.lvMulti);
		lv.setAdapter(adapter);
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
<<<<<<< HEAD
<<<<<<< HEAD

		return new CursorLoader(getActivity(), BillProvider.BILL_URI,
				PROJECTION, null, null, null);
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
=======
>>>>>>> parent of 9a75014... Implement default check unpaid bills
		
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

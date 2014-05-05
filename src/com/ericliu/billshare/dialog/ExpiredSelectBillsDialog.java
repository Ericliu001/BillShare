package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.os.Bundle;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class ExpiredSelectBillsDialog extends DialogFragment implements OnClickListener, OnMultiChoiceClickListener {

	private static final String[] PROJECTION = {
		COL_ROWID,
		COL_CHECKED,
		COL_BILL_NAME
	};
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		
		builder.setTitle(R.string.select_bills).setPositiveButton(R.string.done, this);
		
		Cursor cursor = getActivity().managedQuery(BillProvider.DIALOG_URI_BILL, PROJECTION, null, null, null);
		builder.setMultiChoiceItems(cursor, COL_CHECKED	, COL_BILL_NAME, this);
		
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action buttons clickes
		switch (which) {
		case AlertDialog.BUTTON_POSITIVE:
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// respond to item checks
	}
}

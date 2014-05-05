package com.ericliu.billshare.dialog;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class SelectBillsDialog extends DialogFragment implements
		OnClickListener, OnMultiChoiceClickListener {

	public interface SelectBillsDialogListener {
		public void onFinishSelectBills(DialogFragment dialog);

	};

	private SelectBillsDialogListener mCallback;
	private static final String[] PROJECTION = { COL_ROWID, COL_CHECKED,
			COL_BILL_NAME };

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		try {
			mCallback = (SelectBillsDialogListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement interface SelectBillsDialogListener. ");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle(R.string.select_bills).setPositiveButton(
				R.string.done, this).setNegativeButton(R.string.cancel, this);

		Cursor cursor = getActivity().managedQuery(
				BillProvider.DIALOG_URI_BILL, PROJECTION, null, null, null);
		builder.setMultiChoiceItems(cursor, COL_CHECKED, COL_BILL_NAME, this);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action buttons clickes
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			mCallback.onFinishSelectBills(this);
			this.dismiss();
			break;

		case DialogInterface.BUTTON_NEGATIVE:
			this.dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// respond to item checks
		
		if (MyApplication.isTesting) {
			Toast.makeText(getActivity(),"position: "+ which, Toast.LENGTH_SHORT).show();
		}
	}
}

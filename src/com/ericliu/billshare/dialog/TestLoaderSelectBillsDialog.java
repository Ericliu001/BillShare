package com.ericliu.billshare.dialog;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_AMOUNT;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_TYPE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_UNPAID;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

/*
 * Try to use Loader to load data into Dialog but failed
 */

public class TestLoaderSelectBillsDialog extends DialogFragment implements
		OnClickListener, LoaderCallbacks<Cursor> {

	public interface SelectBillsDialogListener {
		public void onFinishSelectBills(long[] ids);

	};

	private static final int loaderID = 11;
	private SimpleCursorAdapter adapter;
	private ListView lv;
	private SelectBillsDialogListener mCallback;

	private static final String[] PROJECTION = { COL_ROWID, COL_TYPE,
			COL_AMOUNT, COL_UNPAID };

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

		activity.getLoaderManager().initLoader(loaderID, null, this);

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
	}

	private class Holder {
		CheckedTextView cktv;
		TextView tvChecked;

	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.select_bills)
				.setPositiveButton(R.string.done, this)
				.setNegativeButton(R.string.cancel, this);

		View dialogView = getActivity().getLayoutInflater().inflate(
				R.layout.multi_choice_listview, null);
		builder.setView(dialogView);

		lv = (ListView) dialogView.findViewById(R.id.lvMulti);
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action buttons click
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:

			passChoiceBack();

			break;
		case DialogInterface.BUTTON_NEGATIVE:

			break;

		default:
			break;
		}
		getActivity().getLoaderManager().destroyLoader(loaderID);

	}

	private void passChoiceBack() {
		long[] selectedBillIDs = lv.getCheckedItemIds();
		mCallback.onFinishSelectBills(selectedBillIDs);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		return new CursorLoader(getActivity(), BillProvider.BILL_URI,
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

}

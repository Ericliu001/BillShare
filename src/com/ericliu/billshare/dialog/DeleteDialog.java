package com.ericliu.billshare.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.ericliu.billshare.R;

public class DeleteDialog extends DialogFragment implements OnClickListener {

	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	private DeleteDialogListener mCallback;

	public interface DeleteDialogListener {
		void doPositiveClick();

		void doNegativeClick();
	}

	public static DeleteDialog newInstance(Bundle args) {
		DeleteDialog deleteDialog = new DeleteDialog();
		deleteDialog.setArguments(args);

		return deleteDialog;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		try {
			mCallback = (DeleteDialogListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new IllegalStateException(activity.getClass().getSimpleName()
					+ " does not implement interface for "
					+ this.getClass().getSimpleName());
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String title = args.getString(TITLE);
		String message = args.getString(MESSAGE);

		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle(title).setMessage(message);
		builder.setPositiveButton(R.string.ok, this).setNegativeButton(
				R.string.cancel, this);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case AlertDialog.BUTTON_POSITIVE:
			mCallback.doPositiveClick();
			break;

		case AlertDialog.BUTTON_NEGATIVE:
			mCallback.doNegativeClick();
			break;
		default:
			break;
		}
	}

}

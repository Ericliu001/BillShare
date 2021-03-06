package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.os.Bundle;

public class MessageDialog extends DialogFragment {
	
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	
	
	public static MessageDialog newInstance(Bundle args){
		MessageDialog messageDialog = new MessageDialog();
		messageDialog.setArguments(args);
		return messageDialog;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String title = args.getString(TITLE);
		String message = args.getString(MESSAGE);

		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle(title).setMessage(message);
		builder.setPositiveButton(R.string.ok, null);
		return builder.create();
	}

}

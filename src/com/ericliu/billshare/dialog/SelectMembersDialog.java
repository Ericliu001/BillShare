package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class SelectMembersDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater= getActivity().getLayoutInflater();
		
		builder.setView(inflater.inflate(R.layout.test_dialog, null));
		
		return builder.create();
	}
	
}

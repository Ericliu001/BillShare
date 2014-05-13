package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class MustFillContent extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new Builder(getActivity());
	builder.setTitle(R.string.required_field_empty).setMessage(R.string.select_amount_pick_date);
		
		
		return builder.create();
	}

}

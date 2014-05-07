package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class MustChooseFragment extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		
		builder.setTitle(R.string.no_member_and_bill_selected).setMessage(R.string.you_must_select_member_bill).setPositiveButton(R.string.ok, null);
		
		return builder.create();
	}

}

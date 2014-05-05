package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class SelectMembersDialog extends DialogFragment implements OnClickListener {
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.select_members).setPositiveButton(R.string.done, this);
		
		
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
	}

}

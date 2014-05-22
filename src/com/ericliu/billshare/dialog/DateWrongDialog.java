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

public class DateWrongDialog extends DialogFragment implements OnClickListener {
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	private DateWrongListener mCallback;
	
	public interface DateWrongListener{
		void reSelectDate();
	}
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		try {
			mCallback = (DateWrongListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.getClass().getName() + " does not implement interface DateWrongListner.");
		}
	}
	
	public static DateWrongDialog newInstance(Bundle args){
		 DateWrongDialog dateWrongFrag = new DateWrongDialog();
		 dateWrongFrag.setArguments(args);
		 return dateWrongFrag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String title = args.getString(TITLE);
		String message = args.getString(MESSAGE);

		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setTitle(title).setMessage(message);
		builder.setPositiveButton(R.string.ok, this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		mCallback.reSelectDate();
		this.dismiss();
	}

}

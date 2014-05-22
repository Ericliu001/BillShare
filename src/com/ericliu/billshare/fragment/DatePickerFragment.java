package com.ericliu.billshare.fragment;

import java.util.Calendar;
import java.util.Date;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.DateWrongDialog.DateWrongListener;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	public interface DatePickerListener {

		void onFinishPicking();
	}

	private Date mDate;
	private DatePickerListener mCallback;
	
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		try {
			mCallback = (DatePickerListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.getClass().getName() + " does not implement interface DateWrongListner.");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int monthOfYear = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog picker = new DatePickerDialog(getActivity(), this, year, monthOfYear,
				dayOfMonth);

		return picker;
	}
	
	

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Bundle args = getArguments();
		
		

		mDate = new Date(year - 1900, monthOfYear, dayOfMonth);
		mCallback.onFinishPicking();
	}

	public Date getDate() {

		return mDate;
	}

}

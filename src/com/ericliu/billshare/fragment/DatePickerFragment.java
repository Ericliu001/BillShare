package com.ericliu.billshare.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		OnDateSetListener {
	
	
	public interface DatePickerListener{
		
		void onFinishPicking();
	}
	
	
	private Date mDate;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int monthOfYear = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		
		return new DatePickerDialog(getActivity(), this, year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		mDate = new Date(year, monthOfYear, dayOfMonth);
		DatePickerListener activity = (DatePickerListener) getActivity();
		activity.onFinishPicking();
	}
	
	
	public Date getDate(){
		return mDate;
	}
	
	


}

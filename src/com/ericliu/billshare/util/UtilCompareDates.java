package com.ericliu.billshare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UtilCompareDates {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
			Locale.US);;

	public UtilCompareDates() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	}

	public static int compareDates(String memberStartString,
			String memberEndtring, String billStartString, String billEndString) {

		int interval = 0;

		try {
			java.util.Date memberStartDate = dateFormat
					.parse(memberStartString);
			java.util.Date memberEndDate = dateFormat.parse(memberEndtring);
			java.util.Date billStartDate = dateFormat.parse(billStartString);
			java.util.Date billEndDate = dateFormat.parse(billEndString);

			java.util.Date startDate = memberStartDate.after(billStartDate) ? memberStartDate
					: billStartDate;
			java.util.Date endDate = memberEndDate.before(billEndDate) ? memberEndDate
					: billEndDate;

			if (startDate.after(endDate)) {
				return -1;
			}

			interval = compareDates(startDate, endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return interval;

	}

	public static int compareDates(String startDateString, String endDateString) {
		int interval = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);
		try {
			java.util.Date startDate = dateFormat.parse(startDateString);
			java.util.Date endDate = dateFormat.parse(endDateString);
			
			if (startDate.after(endDate)) {
				return -1;
			}

			interval = compareDates(startDate, endDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return interval;
	}

	public static int compareDates(java.util.Date startDate,
			java.util.Date endDate) {
		int interval = 0;

		Calendar calendarStartDate = Calendar.getInstance();
		calendarStartDate.set(Calendar.YEAR, startDate.getYear());
		calendarStartDate.set(Calendar.MONTH, startDate.getMonth());
		calendarStartDate.set(Calendar.DAY_OF_MONTH, startDate.getDate());

		Calendar calendarEndDate = Calendar.getInstance();
		calendarEndDate.set(Calendar.YEAR, endDate.getYear());
		calendarEndDate.set(Calendar.MONTH, endDate.getMonth());
		calendarEndDate.set(Calendar.DAY_OF_MONTH, endDate.getDate());

		long diff = calendarEndDate.getTimeInMillis()
				- calendarStartDate.getTimeInMillis();
		interval = (int) (diff / (24 * 60 * 60 * 1000) + 1); // plus one day

		return interval;
	}

}

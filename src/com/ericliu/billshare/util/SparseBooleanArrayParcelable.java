package com.ericliu.billshare.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class SparseBooleanArrayParcelable extends SparseBooleanArray implements
		Parcelable {

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

}

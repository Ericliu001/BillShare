package com.ericliu.billshare.dialog;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.ListView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_FIRSTNAME;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_LASTNAME;
import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class ExpiredSelectMembersDialog extends DialogFragment implements
		OnClickListener,  OnMultiChoiceClickListener {

	private static final String[] PROJECTION = new String[] {
			DatabaseConstants.COL_ROWID,  COL_CHECKED, COL_FULL_NAME

	};


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());





		 Cursor cursor = getActivity().managedQuery(BillProvider.DIALOG_URI_MEMBER,
				PROJECTION, null, null, null);

		builder.setMultiChoiceItems(cursor, COL_CHECKED, COL_FULL_NAME, this);

		builder.setTitle(R.string.select_members).setPositiveButton(R.string.done, this);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action button click: positive, neutral, negative
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:

			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// respond to item clicks.
	}


}

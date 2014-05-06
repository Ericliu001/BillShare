package com.ericliu.billshare.dialog;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_MEMBER_FULLNAME;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class SelectMembersDialog extends DialogFragment implements OnClickListener, LoaderCallbacks<Cursor>  {
	
	private static final String[] PROJECTION = {
		COL_ROWID,
		COL_CHECKED,
		COL_MEMBER_FULLNAME
	};
	public interface SelectMemberDialogListener{
		void onFinishSelectMembers(long[] ids);
	}
	
	private int loaderId = 12;
	private SelectMemberDialogListener mCallback;
	private ListView lv;
	private SimpleCursorAdapter adapter;
	
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		try {
			mCallback = (SelectMemberDialogListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + " must implement SelectBillsDialogListener");
		}
		
		String[] from = {COL_MEMBER_FULLNAME};
		int[] to = {android.R.id.text1};
		adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, null, from, to, 0);
		
		activity.getLoaderManager().initLoader(loaderId, null, this);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.multi_choice_listview, null);
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setView(dialogView).setTitle(R.string.select_members).setPositiveButton(R.string.done, this).setNegativeButton(R.string.cancel, this);
		
		lv = (ListView) dialogView.findViewById(R.id.lvMulti);
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		
		
		return builder.create();
	}




	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			mCallback.onFinishSelectMembers(lv.getCheckedItemIds());
			break;
case DialogInterface.BUTTON_NEGATIVE:
			
			break;
		default:
			break;
		}
		
	}
	
	
	@Override
	public void onDetach() {
		getActivity().getLoaderManager().destroyLoader(loaderId);
		super.onDetach();
	}




	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		return new CursorLoader(getActivity(), BillProvider.DIALOG_MEMBER_URI, PROJECTION, null, null, null);
	}




	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}




	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}


}

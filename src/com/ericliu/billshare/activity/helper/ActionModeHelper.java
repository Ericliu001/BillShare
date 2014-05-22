package com.ericliu.billshare.activity.helper;

import android.app.Activity;
import android.app.Fragment;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.ericliu.billshare.R;


public class ActionModeHelper implements Callback, OnItemLongClickListener {
	
	private Fragment host;
	private ActionModeListener listener;
	private ActionMode activeMode;
	private ListView modeView;
	
	
	public interface ActionModeListener{
		boolean performAction(int id, int checkedPosition);
	}
	
	public ActionModeHelper(final Fragment host, ListView modeView) {
		
		try {
			 listener = (ActionModeListener) host;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(host.getClass().getName() + " does not implement ActionModeListener. ");
		}
		
		this.host = host;
		this.modeView = modeView;
	
	}
	
	
	
	

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		modeView.clearChoices();
		modeView.setItemChecked(position, true);
		
		if (activeMode == null) {
			activeMode = host.getActivity().startActionMode(this);
		}
		
		return true;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = host.getActivity().getMenuInflater();
		
		inflater.inflate(R.menu.edit, menu);
		mode.setTitle(R.string.edit_history);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		
		boolean result = listener.performAction(item.getItemId(), modeView.getCheckedItemPosition());
		
		
		activeMode.finish();
		return result;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		activeMode = null;
		modeView.clearChoices();
		modeView.requestLayout();
	}

}

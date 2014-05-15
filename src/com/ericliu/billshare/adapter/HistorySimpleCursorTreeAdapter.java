package com.ericliu.billshare.adapter;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_SERIAL_NUMBER;

import java.text.DecimalFormat;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
public class HistorySimpleCursorTreeAdapter extends SimpleCursorTreeAdapter {
	
	private DecimalFormat dollarForum;
	private Activity mActivity;
	private LoaderCallbacks<Cursor> mCallback;
	protected final HashMap<Integer, Integer> mGroupMap;

	public HistorySimpleCursorTreeAdapter(Activity context,Fragment fragment, Cursor cursor,
			int collapsedGroupLayout, int expandedGroupLayout,
			String[] groupFrom, int[] groupTo, int childLayout,
			 String[] childFrom, int[] childTo) {
		
		super(context, cursor, collapsedGroupLayout, expandedGroupLayout, groupFrom,
				groupTo, childLayout, childFrom, childTo);
		
		dollarForum = new DecimalFormat("$###,###,###,###.##");
		
		mActivity = context;
		try {
			mCallback = (LoaderCallbacks<Cursor>) fragment;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mGroupMap = new HashMap<Integer, Integer>();
		
	}
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		View result = super.getGroupView(groupPosition, isExpanded, convertView, parent);
		TextView tvAmount = (TextView) result.getTag();
		if (tvAmount == null) {
			tvAmount = (TextView) result.findViewById(R.id.tvPaymentInfoAmount);
			result.setTag(tvAmount);
		}
		
		tvAmount.setText(dollarForum.format(Double.valueOf(tvAmount.getText().toString())));
		
		return result;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		

        int groupId = groupCursor.getInt(groupCursor
                .getColumnIndex(COL_SERIAL_NUMBER));
        int groupPos = groupCursor.getPosition();
        
        
        mGroupMap.put(groupId, groupPos);
        
        
        Loader<Cursor> loader = mActivity.getLoaderManager().getLoader(groupId);
        if (loader != null && !loader.isReset()) {
			mActivity.getLoaderManager().restartLoader(groupId, null, mCallback);
		}else {
			mActivity.getLoaderManager().initLoader(groupId, null, mCallback);
		}
        
		return null;
	}
	
	public HashMap<Integer, Integer> getGroupMap(){
		return mGroupMap;
	}

}

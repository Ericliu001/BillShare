package com.ericliu.billshare.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.R.id;
import com.ericliu.billshare.R.layout;
import com.ericliu.billshare.activity.helper.ActionModeHelper;
import com.ericliu.billshare.activity.helper.ActionModeHelper.ActionModeListener;
import com.ericliu.billshare.adapter.HistorySimpleCursorTreeAdapter;
import com.ericliu.billshare.fragment.DbWriteFragment;
import com.ericliu.billshare.fragment.DbWriteFragment.DbFragCallBack;
import com.ericliu.billshare.model.Model;
import com.ericliu.billshare.model.PaymentInfo;
import com.ericliu.billshare.model.PaymentInfo.Builder;
import com.ericliu.billshare.provider.BillProvider;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class HistoryActivity extends DrawerActivity implements DbFragCallBack{

	private static final String DBWRITE_FRAG_TAG = "DBwriteFragment";
	private static final String PLACEHOLDER_TAG = "placeholder";
	private HistoryFragment placeHolderFrag;
	private DbWriteFragment dbWriteFrag;
	private PaymentInfo mPaymentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		placeHolderFrag = (HistoryFragment) getFragmentManager()
				.findFragmentByTag(PLACEHOLDER_TAG);
		if (placeHolderFrag == null) {
			placeHolderFrag = new HistoryFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, placeHolderFrag, PLACEHOLDER_TAG).commit();
		}
		
		dbWriteFrag = (DbWriteFragment) getFragmentManager().findFragmentByTag(DBWRITE_FRAG_TAG);
		if (dbWriteFrag == null) {
			dbWriteFrag = new DbWriteFragment();
			getFragmentManager().beginTransaction().add(dbWriteFrag, DBWRITE_FRAG_TAG).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class HistoryFragment extends Fragment implements
			LoaderCallbacks<Cursor>, ActionModeListener {

		private static final int groupLoaderId = -1;

		private ExpandableListView elv;
		private HistorySimpleCursorTreeAdapter adapter;
		private HistoryActivity mCallback;
		
		

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);

		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);
			
			mCallback = (HistoryActivity) activity;
			

			Loader<Cursor> groupLoader = activity.getLoaderManager().getLoader(
					groupLoaderId);
			if (groupLoader != null && !groupLoader.isReset()) {
				activity.getLoaderManager().restartLoader(groupLoaderId, null,
						this);
			} else {
				activity.getLoaderManager().initLoader(groupLoaderId, null,
						this);
			}

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_history,
					container, false);

			elv = (ExpandableListView) rootView.findViewById(R.id.elv);
			TextView tvEmpty = (TextView) rootView.findViewById(R.id.tvEmptyHistory);
			elv.setEmptyView(tvEmpty);
			String[] groupFrom = { COL_NAME, COL_TOTAL_AMOUNT,
					COL_NUMBER_OF_BILLS_PAID };
			int[] groupTo = { R.id.tvPaymentInfoName, R.id.tvPaymentInfoAmount,
					R.id.tvPaymentInfoBillsPaid };
			String[] childFrom = { COL_TYPE, COL_FIRSTNAME, COL_LASTNAME,
					COL_PAYEE_AMOUNT, COL_PAYEE_START_DATE, COL_PAYEE_END_DATE };
			int[] childTo = { R.id.tvChildType, R.id.tvChildFirstName,
					R.id.tvChildLastName, R.id.tvChildAmount,
					R.id.tvChildStartDate, R.id.tvChildEndDate };

			adapter = new HistorySimpleCursorTreeAdapter(getActivity(), this,
					null, R.layout.history_parent_layout,
					R.layout.history_parent_layout, groupFrom, groupTo,
					R.layout.history_child_layout, childFrom, childTo);
			elv.setAdapter(adapter);
			elv.setLongClickable(true);
			elv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			elv.setOnItemLongClickListener(new ActionModeHelper(this, elv));

			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			CursorLoader cursorLoader = null;

			if (id != groupLoaderId) {
				String[] childProjection = { COL_ROWID, COL_SERIAL_NUMBER,
						COL_TYPE, COL_FIRSTNAME, COL_LASTNAME,
						COL_PAYEE_AMOUNT, COL_PAYEE_START_DATE,
						COL_PAYEE_END_DATE };

				String selection = COL_SERIAL_NUMBER + " =? ";
				String[] selectionArgs = { String.valueOf(id) };
				cursorLoader = new CursorLoader(MyApplication.getInstance(),
						BillProvider.PAYMENT_FULL_DETAIL, childProjection,
						selection, selectionArgs, null);
			} else {
				String[] groupProjection = { COL_ROWID, COL_SERIAL_NUMBER,
						COL_NAME, COL_TOTAL_AMOUNT, COL_NUMBER_OF_BILLS_PAID };
				cursorLoader = new CursorLoader(MyApplication.getInstance(),
						BillProvider.PAYMENT_INFO_URI, groupProjection, null,
						null, COL_ROWID + " DESC ");
			}

			return cursorLoader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

			if (loader.getId() != groupLoaderId) {
				int groupPos = adapter.getGroupMap().get(loader.getId());
				adapter.setChildrenCursor(groupPos, data);

			} else {
				adapter.setGroupCursor(data);
			}

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			int id = loader.getId();
			if (id != groupLoaderId) {
				int groupPos = adapter.getGroupMap().get(id);

				adapter.setChildrenCursor(groupPos, null);
			} else {
				adapter.setGroupCursor(null);
			}

		}

		@Override
		public boolean performAction(int id, int checkedPosition) {
			switch (id) {
			case R.id.delete:
				deletePaymentInfo(checkedPosition);
				
				return true;
			default:
				break;
			}

			return false;
		}

		private void deletePaymentInfo(int checkedPosition) {
			long id = adapter.getGroupId(checkedPosition);
			
			PaymentInfo.Builder builder = new PaymentInfo.Builder();
			builder.setId(id);
			builder.deleted(true);
			PaymentInfo paymentInfo = builder.build();
			mCallback.setPaymentInfo(paymentInfo);
			mCallback.writeToDb();
		}
	}
	
	private void setPaymentInfo(PaymentInfo paymentInfo){
		mPaymentInfo = paymentInfo;
	}

	public void writeToDb() {
		dbWriteFrag.writeToDB();
	}

	@Override
	public Model getModel() {
		
		return mPaymentInfo;
	}

}

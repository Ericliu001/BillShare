package com.ericliu.billshare.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.SelectBillsDialog;
import com.ericliu.billshare.dialog.SelectBillsDialog.SelectBillsDialogListener;
import com.ericliu.billshare.dialog.SelectMembersDialog;
import com.ericliu.billshare.dialog.SelectMembersDialog.SelectMemberDialogListener;

public class EvenDivisionActivity extends DrawerActivity implements
		SelectBillsDialogListener, SelectMemberDialogListener {

	private static final String TAG = "EvenDivisionFragment";
	private EvenDivisionFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		frag = (EvenDivisionFragment) getFragmentManager().findFragmentByTag(
				TAG);
		if (frag == null) {
			frag = new EvenDivisionFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, TAG).commit();
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class EvenDivisionFragment extends Fragment implements
			OnClickListener {

		private long[] checkedBillIds = {} ;
		private long[] checkedMemberIds = {};

		private Button btSelectMembers;
		private Button btSelectBills;
		private Button btCalculate;

		private TextView tvMemberSelected;
		private TextView tvBillSelected;

		public EvenDivisionFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_even_division,
					container, false);

			btSelectBills = (Button) rootView.findViewById(R.id.btSelectBills);
			btSelectMembers = (Button) rootView
					.findViewById(R.id.btSelectMember);
			btCalculate = (Button) rootView.findViewById(R.id.btCalculate);
			tvMemberSelected = (TextView) rootView
					.findViewById(R.id.tvMemberSelected);
			tvBillSelected = (TextView) rootView
					.findViewById(R.id.tvBillSelected);

			btSelectBills.setOnClickListener(this);
			btSelectMembers.setOnClickListener(this);
			btCalculate.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btSelectBills:
				SelectBillsDialog billDialog = new SelectBillsDialog();
				billDialog.show(getFragmentManager(), "billSelect");
				break;
			case R.id.btSelectMember:
				SelectMembersDialog memberDialog = new SelectMembersDialog();
				memberDialog.show(getFragmentManager(), "memberSelect");

				break;
			case R.id.btCalculate:
				if (checkedBillIds.length > 0  && checkedMemberIds.length > 0) {
					
					
					
					
					
					if (MyApplication.isTesting) {
						for (int i = 0; i < checkedBillIds.length; i++) {
							Log.i("eric", "Id of member selected: " + checkedBillIds[i]);
						
						}
						for (int i = 0; i < checkedMemberIds.length; i++) {
							Log.i("eric", "Id of member selected: " + checkedMemberIds[i]);
						}
					}
				}
				
				break;

			default:
				break;
			}
		}

		public void onFinishSelectMembers(long[] ids) {

			checkedMemberIds = ids;
			tvMemberSelected.setText(String.valueOf(ids.length));
			
		}

		public void onFinishSelectBills(long[] ids) {

			checkedBillIds = ids;
			tvBillSelected.setText(String.valueOf(ids.length));

		}

	}

	@Override
	public void onFinishSelectBills(long[] ids) {
		frag.onFinishSelectBills(ids);
	}

	@Override
	public void onFinishSelectMembers(long[] ids) {
		frag.onFinishSelectMembers(ids);
	}

}

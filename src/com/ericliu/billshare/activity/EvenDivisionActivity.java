package com.ericliu.billshare.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.SelectBillsDialog;
import com.ericliu.billshare.dialog.SelectBillsDialog.SelectBillsDialogListener;
import com.ericliu.billshare.dialog.SelectMembersDialog;

public class EvenDivisionActivity extends DrawerActivity implements SelectBillsDialogListener {
	
	private static final String TAG = "EvenDivisionFragment";
	private EvenDivisionFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		frag = (EvenDivisionFragment) getFragmentManager().findFragmentByTag(TAG);
		if (frag == null) {
			frag = new EvenDivisionFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag , TAG)
					.commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class EvenDivisionFragment extends Fragment implements
			OnClickListener {

		private Button btSelectMembers;
		private Button btSelectBills;
		private Button btCalculate;

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
				
				break;

			default:
				break;
			}
		}
		
		public void onFinishSelectBills(){
			
		}

	}

	@Override
	public void onFinishSelectBills(DialogFragment dialog) {
		frag.onFinishSelectBills();
	}


}

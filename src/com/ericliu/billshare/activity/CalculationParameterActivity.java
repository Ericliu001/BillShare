package com.ericliu.billshare.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.MustChooseFragment;
import com.ericliu.billshare.dialog.SelectBillsDialog;
import com.ericliu.billshare.dialog.SelectBillsDialog.SelectBillsDialogListener;
import com.ericliu.billshare.dialog.SelectMembersDialog;
import com.ericliu.billshare.dialog.SelectMembersDialog.SelectMemberDialogListener;
import com.ericliu.billshare.util.CalculatorEvenDivAsync;

public class CalculationParameterActivity extends DrawerActivity implements
		SelectBillsDialogListener, SelectMemberDialogListener {

	private static final String TAG = "EvenDivisionFragment";
	public static final String CHECKED_BILL_IDS = "checked_bill_ids";
	public static final String CHECKED_MEMBER_IDS = "checked_member_ids";
	public static final String CHECKED_RESULT = "checked_result";
	public static final String PAYMENT_NAME = "payment_name";
	public static final String PAYMENT_DESCRIPTION = "payment_description";
	public static final String PAID_TIME = "paid_time";
	private CalculationParameterFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		frag = (CalculationParameterFragment) getFragmentManager().findFragmentByTag(
				TAG);
		if (frag == null) {
			frag = new CalculationParameterFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, TAG).commit();
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class CalculationParameterFragment extends Fragment implements
			OnClickListener {

		private long[] checkedBillIds = {} ;
		private long[] checkedMemberIds = {};

		private Button btSelectMembers;
		private Button btSelectBills;
		private Button btCalculate;
		
		private EditText etPaymentName;
		private EditText etPaymentDescription;
		private TextView tvCalculationTitle;
		private TextView tvMemberSelected;
		private TextView tvBillSelected;
		
		private String intentAction;
		private String mDateString;

		public CalculationParameterFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			intentAction = getActivity().getIntent().getAction();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
			mDateString = dateFormat.format(new Date());
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chose_member_bill,
					container, false);

			btSelectBills = (Button) rootView.findViewById(R.id.btSelectBills);
			btSelectMembers = (Button) rootView
					.findViewById(R.id.btSelectMember);
			btCalculate = (Button) rootView.findViewById(R.id.btCalculate);
			
			
			etPaymentName = (EditText) rootView.findViewById(R.id.etPaymentName);
			etPaymentDescription = (EditText) rootView.findViewById(R.id.etDescription);
			
			tvCalculationTitle = (TextView) rootView.findViewById(R.id.tvCalculationTitle);
			tvMemberSelected = (TextView) rootView
					.findViewById(R.id.tvMemberSelected);
			tvBillSelected = (TextView) rootView
					.findViewById(R.id.tvBillSelected);

			btSelectBills.setOnClickListener(this);
			btSelectMembers.setOnClickListener(this);
			btCalculate.setOnClickListener(this);
			
			
			etPaymentDescription.setText(mDateString);

			if (intentAction.equals(DrawerActivity.ACTION_EVEN_DIV)) {
				btCalculate.setText(R.string.even_division);
				tvCalculationTitle.setText(R.string.even_division_calculation);
				
			}else if (intentAction.equals(DrawerActivity.ACTION_CALCULATE_BY_DAYS)) {
				btCalculate.setText(R.string.calculate_by_days);
				tvCalculationTitle.setText(R.string.calculation_based_on_days_members_lived_in);
			}
			
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
					
					Intent intent = new Intent(getActivity(), PaymentActivity.class);
					intent.setAction(intentAction);
					intent.putExtra(CHECKED_BILL_IDS, checkedBillIds);
					intent.putExtra(CHECKED_MEMBER_IDS, checkedMemberIds);
					intent.putExtra(PAYMENT_NAME, etPaymentName.getText().toString());
					intent.putExtra(PAYMENT_DESCRIPTION, etPaymentDescription.getText().toString());
					intent.putExtra(PAID_TIME, mDateString);
					startActivity(intent);
					
					if (MyApplication.isTesting) {
						for (int i = 0; i < checkedBillIds.length; i++) {
							Log.i("eric", "Id of bill selected: " + checkedBillIds[i]);
						
						}
						for (int i = 0; i < checkedMemberIds.length; i++) {
							Log.i("eric", "Id of member selected: " + checkedMemberIds[i]);
						}
					}
				}else {
					MustChooseFragment mustChooseFragment = new MustChooseFragment();
					mustChooseFragment.show(getFragmentManager(), "mustChooseSth");
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

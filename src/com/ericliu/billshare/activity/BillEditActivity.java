package com.ericliu.billshare.activity;

import static com.ericliu.billshare.provider.DatabaseConstants.COL_AMOUNT;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_BILLING_END;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_BILLING_START;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_DUE_DATE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_PAID;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_TYPE;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.DeleteDialog;
import com.ericliu.billshare.dialog.DeleteDialog.DeleteDialogListener;
import com.ericliu.billshare.dialog.MessageDialog;
import com.ericliu.billshare.fragment.DatePickerFragment;
import com.ericliu.billshare.fragment.DbWriteFragment;
import com.ericliu.billshare.fragment.DatePickerFragment.DatePickerListener;
import com.ericliu.billshare.model.Bill;
import com.ericliu.billshare.model.Model;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
import com.ericliu.billshare.util.UtilCompareDates;

public class BillEditActivity extends EditActivity implements
		DatePickerListener, DeleteDialogListener {
	
	private static final String TAG = "BillEditFragment";
	public static final String PICK_ANOTHER_DATE = "pick_another_date";
	private BillEditFragment frag;
	private Bill mBill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		long id = getIntent().getLongExtra(DatabaseConstants.COL_ROWID, -1);

		
		frag = (BillEditFragment) getFragmentManager().findFragmentByTag(TAG);
		if (frag == null) {
			frag = BillEditFragment.newInstance(id);
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container, frag,
							TAG).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class BillEditFragment extends Fragment implements
			OnClickListener {

		private long id;

		private BillEditActivity mCallBack;
		private DatePickerFragment datePickerFrag;

		private Spinner spType;
		private EditText etAmount;
		private CheckBox cbPaid;
		private TextView tvStartDate;
		private TextView tvEndDate;
		private TextView tvDueDate;

		private Button btStartDate;
		private Button btEndDate;
		private Button btDueDate;

		private  int dateTypeId;
		private static final int DATE_TYPE_START = 1;
		private static final int DATE_TYPE_END = 2;
		private static final int DATE_TYPE_DUE = 3;

		private static final String[] PROJECTION = { COL_ROWID, COL_TYPE,
				COL_AMOUNT, COL_BILLING_START, COL_BILLING_END, COL_DUE_DATE,
				COL_PAID };

		private  String undefined;

		public static BillEditFragment newInstance(long id) {
			BillEditFragment frag = new BillEditFragment();
			Bundle args = new Bundle();
			args.putLong(DatabaseConstants.COL_ROWID, id);
			frag.setArguments(args);

			return frag;
		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);
			mCallBack = (BillEditActivity) activity;

		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			setHasOptionsMenu(true);
			

			datePickerFrag = new DatePickerFragment();
			
			undefined = getResources().getString(R.string.undefined);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bill_edit,
					container, false);

			spType = (Spinner) rootView.findViewById(R.id.spType);
			etAmount = (EditText) rootView.findViewById(R.id.etAmount);
			cbPaid = (CheckBox) rootView.findViewById(R.id.cbPaid);
			tvStartDate = (TextView) rootView.findViewById(R.id.tvStartDate);
			tvEndDate = (TextView) rootView.findViewById(R.id.tvEndDate);
			tvDueDate = (TextView) rootView.findViewById(R.id.tvDueDate);
			btStartDate = (Button) rootView.findViewById(R.id.btStartDate);
			btEndDate = (Button) rootView.findViewById(R.id.btEndDate);
			btDueDate = (Button) rootView.findViewById(R.id.btDueDate);

			btStartDate.setOnClickListener(this);
			btEndDate.setOnClickListener(this);
			btDueDate.setOnClickListener(this);

			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
					getActivity(), android.R.layout.simple_spinner_item,
					getResources().getStringArray(
							R.array.bill_type_spinner_items));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			spType.setAdapter(adapter);

			id = getArguments().getLong(DatabaseConstants.COL_ROWID);

			if (id > 0) {

				fillForm(id);
			}

			return rootView;
		}

		private void fillForm(long id) {
			Cursor c = null;
			try {
				Uri uri = Uri.withAppendedPath(BillProvider.BILL_URI,
						String.valueOf(id));
				c = MyApplication.getInstance().getContentResolver()
						.query(uri, PROJECTION, null, null, null);

				c.moveToFirst();

				int position = 0;
				String type = c.getString(c.getColumnIndexOrThrow(COL_TYPE));

				String[] spinnerItems = getResources().getStringArray(
						R.array.bill_type_spinner_items);
				for (int i = 0; i < spinnerItems.length; i++) {
					if (type.equals(spinnerItems[i])) {
						position = i;
					}
				}

				spType.setSelection(position, true);

				etAmount.setText(c.getString(c.getColumnIndex(COL_AMOUNT)));
				
				
				
				String billStartDateString = c.getString(c.getColumnIndex(COL_BILLING_START));
				if (!billStartDateString.equals("1900-01-01")) {
					
					tvStartDate.setText(billStartDateString);
				}
				
				String billingEndDateString = c.getString(c.getColumnIndex(COL_BILLING_END));
				if (! billingEndDateString.equals("3000-01-01")) {
					
					tvEndDate.setText(billingEndDateString);
				}
				
				tvDueDate.setText( c.getString(c.getColumnIndex(COL_DUE_DATE)));
				
				

				cbPaid.setChecked(c.getInt(c.getColumnIndex(COL_PAID)) > 0 ? true
						: false);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

			inflater.inflate(R.menu.edit, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {

			switch (item.getItemId()) {
			case R.id.save:
				
				if (!checkIsEmpty()) {
					saveBill();
					getActivity().finish();
				}else{
					Bundle args = new Bundle();
					args.putString(MessageDialog.TITLE, getResources().getString(R.string.fields_empty));
					args.putString(MessageDialog.MESSAGE, getResources().getString(R.string.must_fill_fields_bill));
					MessageDialog messageDialog = MessageDialog.newInstance(args);
					messageDialog.show(getFragmentManager(), "message");
				}
				

				break;
			
			case R.id.delete:
				Bundle args = new Bundle();
				args.putString(DeleteDialog.TITLE, getResources().getString(R.string.confirm_delete));
				args.putString(DeleteDialog.MESSAGE, getResources().getString(R.string.delete_bill));
				DeleteDialog deleteDialog = DeleteDialog.newInstance(args);
				deleteDialog.show(getFragmentManager(), "delete");
				break;
				
			case android.R.id.home:
				Intent i = new Intent(getActivity(), BillActivity.class);
				startActivity(i);
				
				return true;

			default:
				break;
			}

			return super.onOptionsItemSelected(item);
		}

		

		private boolean checkIsEmpty() {
			
			if(TextUtils.isEmpty(etAmount.getText()) || TextUtils.isEmpty(tvStartDate.getText()) || TextUtils.isEmpty(tvEndDate.getText()) ){
			
			return true;
			}
			return false;
		}

		private void saveBill() {
			Bill bill = new Bill();

			if (id > 0) {
				bill.setId(id);

			}

			setBillAttributes(bill);

			mCallBack.setBill(bill);
			mCallBack.saveToDb();
		}

		public void setBillAttributes(Bill bill) {
			bill.setType(spType.getSelectedItem().toString());
			bill.setAmount(Double.valueOf(etAmount.getText().toString()));
			
				
				bill.setStartDate(tvStartDate.getText().toString());
			
				
				bill.setEndDate(tvEndDate.getText().toString());
			
				
				bill.setDueDate(tvDueDate.getText().toString());
			
			bill.setPaid(cbPaid.isChecked() ? 1 : 0);
		}
		
		private void deleteBill() {
			if (id > 0) {
				Bill bill = new Bill();
				bill.setId(id);
				setBillAttributes(bill);
				
				bill.setDeleted(true);
				
				mCallBack.setBill(bill);
				mCallBack.saveToDb();
			}
			
			
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btStartDate:
				showDatePickerDialog();
				dateTypeId = DATE_TYPE_START;
				break;
			case R.id.btEndDate:
				showDatePickerDialog();
				dateTypeId = DATE_TYPE_END;

				break;
			case R.id.btDueDate:
				showDatePickerDialog();
				dateTypeId = DATE_TYPE_DUE;
				break;

			default:
				break;
			}

		}

		private void showDatePickerDialog() {

			datePickerFrag.show(getFragmentManager(), "timePicker");

		}

		public void onFinishPicking() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.US);
			String dateString = dateFormat.format(datePickerFrag.getDate());

			switch (dateTypeId) {
			case DATE_TYPE_START:
				
				if (UtilCompareDates.compareDates(dateString, tvEndDate.getText().toString()) < 0) {
					Toast.makeText(getActivity(), " End date must be after the start date ", Toast.LENGTH_SHORT).show();
				}
				tvStartDate.setText(dateString);
				break;

			case DATE_TYPE_END:
				if(UtilCompareDates.compareDates(tvStartDate.getText().toString(), dateString) <0){
					
					Toast.makeText(getActivity(), " End date must be after the start date ", Toast.LENGTH_SHORT).show();
				} ;
				
				tvEndDate.setText(dateString);
				break;

			case DATE_TYPE_DUE:
				tvDueDate.setText(dateString);
				break;

			default:
				break;
			}
		}

		public void yesToDelete() {
			deleteBill();
			getActivity().finish();
		}

	}

	private void setBill(Bill bill) {
		this.mBill = bill;
	}

	@Override
	public Model getModel() {

		return mBill;
	}

	@Override
	public void onFinishPicking() {
		frag.onFinishPicking();
	}

	@Override
	public void doPositiveClick() {
		frag.yesToDelete();
	}

	@Override
	public void doNegativeClick() {
	}

}

package com.ericliu.billshare.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.dialog.DateWrongDialog;
import com.ericliu.billshare.dialog.DeleteDialog;
import com.ericliu.billshare.dialog.MessageDialog;
import com.ericliu.billshare.dialog.DateWrongDialog.DateWrongListener;
import com.ericliu.billshare.dialog.DeleteDialog.DeleteDialogListener;
import com.ericliu.billshare.fragment.DatePickerFragment;
import com.ericliu.billshare.fragment.DbWriteFragment;
import com.ericliu.billshare.fragment.DatePickerFragment.DatePickerListener;
import com.ericliu.billshare.model.Member;
import com.ericliu.billshare.model.Model;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;
import com.ericliu.billshare.util.UtilCompareDates;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class MemberEditActivity extends EditActivity implements
		DatePickerListener, DeleteDialogListener, DateWrongListener {

	private static final String TAG = "MemberEditFragment";
	public static final String DATE_WRONG_TAG = "date_wrong_tag";
	private Member mMember = null;
	private MemberEditFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		Bundle data = getIntent().getExtras();
		long id = -1;
		if (data != null) {

			id = data.getLong(DatabaseConstants.COL_ROWID, -1);
		}

		frag = (MemberEditFragment) getFragmentManager().findFragmentByTag(TAG);
		if (frag == null) {

			frag = MemberEditFragment.newInstance(id);
			getFragmentManager().beginTransaction()
					.add(R.id.container, frag, TAG).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MemberEditFragment extends Fragment implements
			OnClickListener {

		private int chosenDateType = 0;
		private static final int DATE_TYPE_MOVE_IN = 1;
		private static final int DATE_TYPE_MOVE_OUT = 2;

		private EditText etFirstName;
		private EditText etLastName;
		private EditText etPhone;
		private EditText etEmail;
		private TextView tvMoveInDate;
		private TextView tvMoveOutDate;
		private Button btPickMoveIndate;
		private Button btPickMoveOutdate;
		private String moveInDateToSave = null;
		private String moveOutDateToSave = null;

		private long id;

		private MemberEditActivity mCallBack = null;
		private DatePickerFragment datePickerFrag;

		public static MemberEditFragment newInstance(long id) {
			MemberEditFragment frag = new MemberEditFragment();
			Bundle args = new Bundle();
			args.putLong(DatabaseConstants.COL_ROWID, id);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public void onAttach(Activity activity) {

			super.onAttach(activity);
			mCallBack = (MemberEditActivity) activity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);

			setRetainInstance(true);

			datePickerFrag = new DatePickerFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_member_edit,
					container, false);

			etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);
			
			etLastName = (EditText) rootView.findViewById(R.id.etLastName);
			etPhone = (EditText) rootView.findViewById(R.id.etPhone);
			etEmail = (EditText) rootView.findViewById(R.id.etEmail);
			tvMoveInDate = (TextView) rootView.findViewById(R.id.tvMoveInDate);
			tvMoveOutDate = (TextView) rootView
					.findViewById(R.id.tvMoveOutDate);
			btPickMoveIndate = (Button) rootView
					.findViewById(R.id.btPickMoveInDate);
			btPickMoveOutdate = (Button) rootView
					.findViewById(R.id.btPickMoveOutDate);

			btPickMoveIndate.setOnClickListener(this);
			btPickMoveOutdate.setOnClickListener(this);

			id = getArguments().getLong(COL_ROWID);
			if (id > 0) {

				fillForm(id);
			}

			return rootView;
		}

		private void fillForm(long id) {
			Uri uri = Uri.withAppendedPath(BillProvider.HOUSEMATE_URI,
					String.valueOf(id));
			String[] projection = { COL_ROWID, COL_FIRSTNAME, COL_LASTNAME,
					COL_PHONE, COL_EMAIL, COL_MOVE_IN_DATE, COL_MOVE_OUT_DATE

			};

			Cursor c = null;
			try {
				c = MyApplication.getInstance().getContentResolver()
						.query(uri, projection, null, null, null);
				c.moveToFirst();

				etFirstName.setText(c.getString(c
						.getColumnIndexOrThrow(COL_FIRSTNAME)));
				etLastName.setText(c.getString(c
						.getColumnIndexOrThrow(COL_LASTNAME)));
				etPhone.setText(c.getString(c.getColumnIndexOrThrow(COL_PHONE)));
				etEmail.setText(c.getString(c.getColumnIndexOrThrow(COL_EMAIL)));
				
				String moveInDateString = c.getString(c.getColumnIndexOrThrow(COL_MOVE_IN_DATE));
				if (! moveInDateString.equals("1900-01-01")) {
					tvMoveInDate.setText(moveInDateString);
				}
				
				String moveOutDateString = c.getString(c.getColumnIndexOrThrow(COL_MOVE_OUT_DATE));
				if (! moveOutDateString.equals("3000-01-01")) {
					tvMoveOutDate.setText(moveOutDateString);
				}

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
				
				
				
				if (! checkIsEmpty()) {
					saveMember();
					getActivity().finish();
				}else {
					Bundle args = new Bundle();
					args.putString(MessageDialog.TITLE, getResources()
							.getString(R.string.fields_empty));
					args.putString(MessageDialog.MESSAGE, getResources()
							.getString(R.string.must_fill_fields_member));
					MessageDialog messageDialog = MessageDialog
							.newInstance(args);
					messageDialog.show(getFragmentManager(), "message");
				}
				break;
				
				
			case R.id.delete:
				Bundle args = new Bundle();
				args.putString(DeleteDialog.TITLE, getResources().getString(R.string.confirm_delete));
				args.putString(DeleteDialog.MESSAGE, getResources().getString(R.string.delete_member));
				DeleteDialog deleteDialog = DeleteDialog.newInstance(args);
				deleteDialog.show(getFragmentManager(), "delete");
				break;
				
				
			case android.R.id.home:
				Intent i = new Intent(getActivity(), MemberActivity.class);
				startActivity(i);
				return true;

			default:
				break;
			}

			return super.onOptionsItemSelected(item);
		}

		private void deleteMember() {
			if (id > 0) {
				Member member = new Member();
				member.setId(id);
				
				setMemberAttributes(member);
				member.setDeleted(true);
				mCallBack.setMember(member);
				mCallBack.saveToDb();
			}
			
		}

		private void saveMember() {
			Member member = new Member();

			if (id > 0) {
				member.setId(id);
			}

			setMemberAttributes(member);

			mCallBack.setMember(member);
			mCallBack.saveToDb();
		}

		public void setMemberAttributes(Member member) {
			member.setFirstName(etFirstName.getText().toString());
			member.setLastName(etLastName.getText().toString());
			member.setPhone(etPhone.getText().toString());
			member.setEmail(etEmail.getText().toString());
			if (moveInDateToSave != null) {
				member.setMoveInDate(moveInDateToSave);
			}
			
			if (moveOutDateToSave != null) {
				member.setMoveOutDate(moveOutDateToSave);
				
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btPickMoveInDate:
				showDatePicker();
				chosenDateType = DATE_TYPE_MOVE_IN;
				break;

			case R.id.btPickMoveOutDate:
				showDatePicker();
				chosenDateType = DATE_TYPE_MOVE_OUT;
				break;

			default:
				break;
			}
		}

		public void showDatePicker() {
			datePickerFrag.show(getFragmentManager(), "datePicker");
		}

		public void onFinishPicking() {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.US);
			String dateString = dateFormat.format(datePickerFrag.getDate());
			switch (chosenDateType) {
			case DATE_TYPE_MOVE_IN:
				
				
				if (! TextUtils.isEmpty(tvMoveOutDate.getText())) {
					if (UtilCompareDates.compareDates(dateString, tvMoveOutDate.getText().toString()) < 0) {
						Bundle args = new Bundle();
						args.putString(DateWrongDialog.TITLE, getResources().getString(R.string.date_picking_mistake));
						args.putString(DateWrongDialog.MESSAGE, getResources().getString(R.string.move_in_date_must_be_before_move_out_date));
						DateWrongDialog dateWrongdialog = DateWrongDialog.newInstance(args);
						dateWrongdialog.show(getFragmentManager(), DATE_WRONG_TAG);
					}
				}
				
				tvMoveInDate.setText(dateString);
				moveInDateToSave = dateString;
				break;

			case DATE_TYPE_MOVE_OUT:
				
				if (! TextUtils.isEmpty(tvMoveInDate.getText())) {
					if (UtilCompareDates.compareDates(tvMoveInDate.getText().toString(), dateString) < 0) {
						Bundle args = new Bundle();
						args.putString(DateWrongDialog.TITLE, getResources().getString(R.string.date_picking_mistake));
						args.putString(DateWrongDialog.MESSAGE, getResources().getString(R.string.move_out_date_must_be_after_move_in_date));
						DateWrongDialog dateWrongdialog = DateWrongDialog.newInstance(args);
						dateWrongdialog.show(getFragmentManager(), DATE_WRONG_TAG);
					}
				}
				
				
				tvMoveOutDate.setText(dateString);
				moveOutDateToSave = dateString;
				break;

			default:
				break;
			}
		}

		public void yesToDelete() {
			deleteMember();
			getActivity().finish();
		}

		private boolean checkIsEmpty() {
		
					if (TextUtils.isEmpty(etFirstName.getText())) {
		
						return true;
					}
					return false;
				}

	}

	public void setMember(Member member) {
		mMember = member;
	}

	@Override
	public Model getModel() {

		return mMember;
	}

	@Override
	public void onFinishPicking() {
		frag.onFinishPicking();
		DateWrongDialog dateWrongDialog = (DateWrongDialog) getFragmentManager().findFragmentByTag(DATE_WRONG_TAG);
		if (dateWrongDialog != null) {
			dateWrongDialog.dismiss();
		}
	}

	@Override
	public void doPositiveClick() {
		frag.yesToDelete();
	}

	@Override
	public void doNegativeClick() {
	}

	@Override
	public void reSelectDate() {
		frag.showDatePicker();
	}

}

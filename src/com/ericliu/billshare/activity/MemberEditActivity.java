package com.ericliu.billshare.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.ericliu.billshare.fragment.DatePickerFragment;
import com.ericliu.billshare.fragment.DatePickerFragment.DatePickerListener;
import com.ericliu.billshare.model.Member;
import com.ericliu.billshare.model.Model;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class MemberEditActivity extends EditActivity implements
		DatePickerListener {

	private static final String TAG = "MemberEditFragment";
	private Member mMember = null;
	private MemberEditFragment frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
					COL_PHONE, COL_EMAIL

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
				saveMember();
				getActivity().finish();
				break;

			default:
				break;
			}

			return super.onOptionsItemSelected(item);
		}

		private void saveMember() {
			Member member = new Member();

			if (id > 0) {
				member.setId(id);
			}

			member.setFirstName(etFirstName.getText().toString());
			member.setLastName(etLastName.getText().toString());
			member.setPhone(etPhone.getText().toString());
			member.setEmail(etEmail.getText().toString());

			mCallBack.setMember(member);
			mCallBack.saveToDb();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btPickMoveInDate:
				datePickerFrag.show(getFragmentManager(), "datePicker");
				chosenDateType = DATE_TYPE_MOVE_IN;
				break;

			case R.id.btPickMoveOutDate:
				datePickerFrag.show(getFragmentManager(), "datePicker");
				chosenDateType = DATE_TYPE_MOVE_OUT;
				break;

			default:
				break;
			}
		}

		public void onFinishPicking() {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
					Locale.US);
			String dateString = dateFormat.format(datePickerFrag.getDate());
			switch (chosenDateType) {
			case DATE_TYPE_MOVE_IN:
				tvMoveInDate.setText(dateString);
				break;

			case DATE_TYPE_MOVE_OUT:
				tvMoveOutDate.setText(dateString);
				break;

			default:
				break;
			}
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
	}

}

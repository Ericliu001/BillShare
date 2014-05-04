package com.ericliu.billshare.activity;

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
import android.view.ViewGroup;
import android.widget.EditText;

import com.ericliu.billshare.MyApplication;
import com.ericliu.billshare.R;
import com.ericliu.billshare.model.Member;
import com.ericliu.billshare.model.Model;
import com.ericliu.billshare.provider.BillProvider;
import com.ericliu.billshare.provider.DatabaseConstants;

import static com.ericliu.billshare.provider.DatabaseConstants.*;

public class MemberEditActivity extends EditActivity {

	private Member mMember = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle data = getIntent().getExtras();
		long id = -1;
		if (data != null) {

			id = data.getLong(DatabaseConstants.COL_ROWID, -1);
		}

		if (getFragmentManager().findFragmentByTag("saved") == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container, MemberEditFragment.newInstance(id),
							"saved").commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MemberEditFragment extends Fragment {

		private EditText etFirstName;
		private EditText etLastName;
		private EditText etPhone;
		private EditText etEmail;
		private long id;

		private MemberEditActivity mCallBack = null;

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

	}

	public void setMember(Member member) {
		mMember = member;
	}

	@Override
	public Model getModel() {

		return mMember;
	}

}

package com.ericliu.billshare.activity;

import com.ericliu.billshare.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Main extends DrawerActivity {
	private static final String TAG = "savedFrag";
	private MainFragment frag;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		frag = (MainFragment) getFragmentManager().findFragmentByTag(TAG);
		if (frag == null) {
			frag = new MainFragment();
			getFragmentManager().beginTransaction().add(R.id.container, frag, TAG).commit();
		}
	}
	
	
	
	
	
	public static class MainFragment extends Fragment implements OnClickListener {
		
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View result = inflater.inflate(R.layout.fragment_main, null);
			
			ImageButton ibBill = (ImageButton) result.findViewById(R.id.ibBill);
			ImageButton ibMember = (ImageButton) result.findViewById(R.id.ibMember);
			ImageButton ibCalculate = (ImageButton) result.findViewById(R.id.ibCalculate);
			ImageButton ibHistory = (ImageButton) result.findViewById(R.id.ibHistory);
			
			ibBill.setOnClickListener(this);
			ibMember.setOnClickListener(this);
			ibCalculate.setOnClickListener(this);
			ibHistory.setOnClickListener(this);
			
			
			
			return result;
		}

		@Override
		public void onClick(View v) {
			Intent i;
			switch (v.getId()) {

			case R.id.ibCalculate:
				i = new Intent(getActivity(), CalculationParameterActivity.class);
				i.setAction(ACTION_CALCULATE_BY_DAYS);
				startActivity(i);
				break;

			case R.id.ibHistory:
				i = new Intent(getActivity(), HistoryActivity.class);
				startActivity(i);
				break;

			case R.id.ibMember:
				 i = new Intent(getActivity(), MemberActivity.class);
				startActivity(i);
				break;
				
			case R.id.ibBill:
				 i = new Intent(getActivity(), BillActivity.class);
				startActivity(i);
				break;

			default:
				break;
			}
		}
	}
}

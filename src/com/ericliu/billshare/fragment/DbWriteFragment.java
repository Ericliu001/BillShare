package com.ericliu.billshare.fragment;

import com.ericliu.billshare.model.Model;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

public class DbWriteFragment extends Fragment {
	
	private DbFragCallBack mCallBack;
	private DBWriteTask mTask;
	
	public static interface DbFragCallBack{
		
		Model getModel();
		void onPostExecute(Object result);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		try {
			mCallBack = (DbFragCallBack) getActivity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void writeToDB(){
		mTask = new DBWriteTask();
		mTask.execute(mCallBack.getModel());
	}
	
	
	@Override
	public void onDetach() {
		
		super.onDetach();
		mCallBack = null;
	}
	
	
	private class DBWriteTask extends AsyncTask<Model, Void, Void>{

		
		@Override
		protected Void doInBackground(Model... params) {
			
			params[0].save();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
			mCallBack.onPostExecute(result);
		}
	}
	
}

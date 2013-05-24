package ru.helix.prenalyticcalculator.fragment;

import ru.helix.prenalyticcalculator.service.RESTService;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;

public abstract class RESTResponderFragment extends Fragment {
	private ResultReceiver mReceiver;
	
	
	public RESTResponderFragment() {
		mReceiver = new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				if(resultData != null && resultData.containsKey(RESTService.REST_RESULT)) {
					onRESTResult(resultCode, resultData.getString(RESTService.REST_RESULT));
				}
				else {
					onRESTResult(resultCode, null);
				}
			}
		};
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	public ResultReceiver getResultReceiver() {
		return mReceiver;
	}
	
	abstract public void onRESTResult(int code, String result);
}

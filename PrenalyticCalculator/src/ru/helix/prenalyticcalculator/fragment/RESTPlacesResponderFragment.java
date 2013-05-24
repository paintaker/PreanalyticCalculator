package ru.helix.prenalyticcalculator.fragment;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import ru.helix.prenalyticcalculator.PlacesActivity;
import ru.helix.prenalyticcalculator.Data.JsonDataSource;
import ru.helix.prenalyticcalculator.Domain.Place;
import ru.helix.prenalyticcalculator.adapters.PlaceAdapter;
import ru.helix.prenalyticcalculator.service.RESTService;

public class RESTPlacesResponderFragment extends RESTResponderFragment {
	private static String TAG = RESTPlacesResponderFragment.class.getName();
	
	private List<Place> mPlaces;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setPlaces();
	}
	
	private void setPlaces() {
		PlacesActivity activity = (PlacesActivity)getActivity();
		
		if(mPlaces == null && activity != null) {
			Intent intent = new Intent(activity, RESTService.class);
			intent.setData(Uri.parse("http://razor.spb.helix.ru/Preanalystic.Common.Web/Home/GetPlaces"));
			
			intent.putExtra(RESTService.EXTRA_HTTP_VERB, RESTService.POST);
			intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, getResultReceiver());
			
			activity.startService(intent);
		}
		else {
			PlaceAdapter adapter = activity.getPlacesAdapter();
			adapter.clear();
			for(Place place: mPlaces) {
				adapter.add(place);
			}
		}
	}
	
	@Override
	public void onRESTResult(int code, String result) {
		if(code == 200 && result != null) {
			try {
				mPlaces = JsonDataSource.GetPlaces(result);
				setPlaces();
			}
			catch(IOException e) {
				Log.e(TAG, "Ошибка парсинга списка мест забора", e);
			}
		}
	}

}

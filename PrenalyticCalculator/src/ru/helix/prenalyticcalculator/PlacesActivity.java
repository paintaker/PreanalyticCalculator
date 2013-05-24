package ru.helix.prenalyticcalculator;

import java.util.ArrayList;

import ru.helix.prenalyticcalculator.Domain.Place;
import ru.helix.prenalyticcalculator.adapters.PlaceAdapter;
import ru.helix.prenalyticcalculator.fragment.RESTPlacesResponderFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class PlacesActivity extends FragmentActivity {
	private PlaceAdapter mPlacesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_view);
		
		mPlacesAdapter = new PlaceAdapter(this, R.layout.places_list_item, new ArrayList<Place>());
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		ListFragment list = new PlacesListFragment();
		
		ft.add(R.id.fragment_content, list);
		
		list.setListAdapter(mPlacesAdapter);
		
		RESTPlacesResponderFragment responder = (RESTPlacesResponderFragment) fm.findFragmentByTag("RESTPlacesResponder");
		if(responder == null) {
			responder = new RESTPlacesResponderFragment();
			ft.add(responder, "RESTPlacesResponder");
		}
		
		ft.commit();
	}
	
	public PlaceAdapter getPlacesAdapter() {
		return mPlacesAdapter;
	}
	
	public static class PlacesListFragment extends ListFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Place clickedPlace = (Place) l.getItemAtPosition(position);
			PlacesActivity activity = (PlacesActivity)getActivity();
			Intent intent = new Intent();
			Bundle data = new Bundle();
			data.putParcelable("Place", clickedPlace);
			intent.putExtras(data);
			activity.setResult(RESULT_OK, intent);
			activity.finish();
		}
	}
}

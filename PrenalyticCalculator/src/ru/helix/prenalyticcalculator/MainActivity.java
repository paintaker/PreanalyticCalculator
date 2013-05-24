package ru.helix.prenalyticcalculator;

import ru.helix.prenalyticcalculator.Domain.Place;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	public static final int PLACES_REQUEST_CODE = 1;
	public static final int ORDER_ITEMS_REQUEST_CODE = 2;
	
	private static String placesKey = "PlaceName";
	private static String hubKey = "PlaceHubCode";
	private static String placesCodeKey = "PlaceCodeKey";
	
	private Place currentPlace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		currentPlace = loadPlaceFromPrefences(getPreferences(MODE_PRIVATE));
		
		setCurrentPlaceName();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		savePlaceToPrefences(getPreferences(MODE_PRIVATE), currentPlace);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case PLACES_REQUEST_CODE:
			if(resultCode == RESULT_OK) {
				Bundle values = data.getExtras();
				if(values.containsKey("Place")) {
					currentPlace = (Place) values.getParcelable("Place");
					setCurrentPlaceName();
				}
			}
			break;
		case ORDER_ITEMS_REQUEST_CODE:
			
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	public void onSelectPlace(View view) {
		Intent places = new Intent(this, PlacesActivity.class);
		
		Bundle data = new Bundle();
		String placeCode = currentPlace.Name != null ? currentPlace.Name : "";
		data.putString(Constants.PlaceCode, placeCode);
		places.putExtras(data);
		startActivityForResult(places, PLACES_REQUEST_CODE);
	}
	
	private void setCurrentPlaceName()
	{
		Button placeText = (Button) findViewById(R.id.placeName);
		
		if(currentPlace != null)
			placeText.setText(currentPlace.Name);
		else
			placeText.setText(R.string.place_selector);
	}

	private Place loadPlaceFromPrefences(SharedPreferences prefs) {
		Place place = new Place();
		
		if(prefs.contains(placesKey))
			place.Name = prefs.getString(placesKey, null);
		if(prefs.contains(hubKey))
			place.HubCode = prefs.getString(hubKey, null);
		if(prefs.contains(placesCodeKey))
			place.Code = prefs.getString(placesCodeKey, null);
		
		return place;
	}
	
	private void savePlaceToPrefences(SharedPreferences prefs, Place place) {
		if(place != null)
		{
			Editor editor = prefs.edit();
			editor.putString(placesKey, place.Name);
			editor.putString(hubKey, place.HubCode);
			editor.putString(placesCodeKey, place.Code);
			editor.apply();
		}
	}
	
}

package ru.helix.prenalyticcalculator.adapters;

import java.util.ArrayList;

import ru.helix.prenalyticcalculator.R;
import ru.helix.prenalyticcalculator.Domain.Place;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {
	Context context;
	int layoutResourceId;
	ArrayList<Place> data;
	
	
	public PlaceAdapter(Context context, int layoutResourceId, ArrayList<Place> data) {
		
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PlaceHolder holder = null;
		
		if(row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new PlaceHolder();
			holder.txtTitle = (TextView)row.findViewById(R.id.place_item_name);
			
			row.setTag(holder);
		}
		else {
			holder = (PlaceHolder)row.getTag();
		}
		
		Place place = data.get(position);
		holder.txtTitle.setText(place.Name);
		
		return row;
	}
	
	static class PlaceHolder {
		TextView txtTitle;
	}
}

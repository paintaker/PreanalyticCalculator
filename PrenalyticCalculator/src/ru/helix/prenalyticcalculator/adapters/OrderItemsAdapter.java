package ru.helix.prenalyticcalculator.adapters;

import java.util.ArrayList;

import ru.helix.prenalyticcalculator.Domain.OrderItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class OrderItemsAdapter extends ArrayAdapter<OrderItem> {
	Context context;
	int layoutResourceId;
	ArrayList<OrderItem> data;
	
	public OrderItemsAdapter(Context context, int layoutResourceId, ArrayList<OrderItem> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
}

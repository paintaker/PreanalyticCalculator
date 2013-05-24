package ru.helix.prenalyticcalculator;

import ru.helix.prenalyticcalculator.adapters.OrderItemsAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class OrderItemsActivity extends FragmentActivity {
	private OrderItemsAdapter mOrderItemsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public OrderItemsAdapter getOrderItemsAdapter() {
		return mOrderItemsAdapter;
	}
}

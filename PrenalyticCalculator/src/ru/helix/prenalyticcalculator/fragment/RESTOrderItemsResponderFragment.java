package ru.helix.prenalyticcalculator.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import ru.helix.prenalyticcalculator.OrderItemsActivity;
import ru.helix.prenalyticcalculator.Data.JsonDataSource;
import ru.helix.prenalyticcalculator.Domain.NomenclatureItem;
import ru.helix.prenalyticcalculator.Domain.OrderItem;
import ru.helix.prenalyticcalculator.adapters.OrderItemsAdapter;
import ru.helix.prenalyticcalculator.service.RESTService;

public class RESTOrderItemsResponderFragment extends RESTResponderFragment {
	private static String TAG = RESTOrderItemsResponderFragment.class.getName();
	private List<OrderItem> mOrderItems;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setOrderItems();
	}
	
	private void setOrderItems()
	{
		OrderItemsActivity activity = (OrderItemsActivity) getActivity();
		
		if(mOrderItems == null && activity != null) {
			Intent intent = new Intent(activity, RESTService.class);
			intent.setData(Uri.parse("http://razor.spb.helix.ru/Preanalystic.Common.Web/Home/GetNomenclatureItems"));
			
			intent.putExtra(RESTService.EXTRA_HTTP_VERB, RESTService.POST);
			intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, getResultReceiver());
			
			activity.startService(intent);
		}
		else {
			OrderItemsAdapter adapter = activity.getOrderItemsAdapter();
			
			adapter.clear();
			
			for(OrderItem item: mOrderItems) {
				adapter.add(item);
			}
		}
	}
	
	public void toggleSelection(int position, byte value) {
		if(position >= 0 && position < mOrderItems.size()) {
			OrderItem item = mOrderItems.get(position);
			item.Selected = value;
			mOrderItems.set(position, item);
		}
	}
	
	@Override
	public void onRESTResult(int code, String result) {
		if(code == 200 && result != null) {
			try {
				List<NomenclatureItem> nomItems = JsonDataSource.GetNomenclatureItems(result);
				mOrderItems = new ArrayList<OrderItem>();
				for(NomenclatureItem nomItem: nomItems) {
					OrderItem item = new OrderItem();
					item.Code = nomItem.Code;
					item.Name = nomItem.Name;
					item.Selected = 0;
					
					mOrderItems.add(item);
				}
			}
			catch(IOException e)
			{
				Log.e(TAG, "Ошибка парсинга списка исследований", e);
			}
		}
	}

}

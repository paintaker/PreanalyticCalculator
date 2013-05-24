package ru.helix.prenalyticcalculator;

import java.util.ArrayList;
import java.util.List;

import ru.helix.prenalyticcalculator.Domain.OrderItem;
import ru.helix.prenalyticcalculator.Domain.Place;
import ru.helix.prenalyticcalculator.Domain.Variant;

public class MainContext {
	public Place CurrentPlace;
	public List<OrderItem> OrderItems;
	public List<Variant> Variants;
	
	public MainContext() {
		OrderItems = new ArrayList<OrderItem>();
		Variants = new ArrayList<Variant>();
	}
}

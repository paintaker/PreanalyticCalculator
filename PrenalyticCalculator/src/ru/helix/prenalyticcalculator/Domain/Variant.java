package ru.helix.prenalyticcalculator.Domain;

import java.util.ArrayList;
import java.util.List;

public class Variant {
	public List<Sample> Samples;
	public List<RequiredField> RequiredFields;
	
	public Variant() {
		Samples = new ArrayList<Sample>();
		RequiredFields = new ArrayList<RequiredField>();
	}
}

package ru.helix.prenalyticcalculator.Domain;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	public String SampleType;
	public String ContainerType;
	public String Preparation;
	public String Protocol;
	public int MaxVolume;
	public int CurrentVolume;
	public String SampleGroup;
	public int DeadVolume;
	
	public List<Manipulation> Manipulations;
	
	public Sample() {
		Manipulations = new ArrayList<Manipulation>();
	}
}

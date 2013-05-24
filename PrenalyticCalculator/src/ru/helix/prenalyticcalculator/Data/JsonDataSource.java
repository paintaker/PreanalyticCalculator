package ru.helix.prenalyticcalculator.Data;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;

import ru.helix.prenalyticcalculator.Domain.Manipulation;
import ru.helix.prenalyticcalculator.Domain.NomenclatureItem;
import ru.helix.prenalyticcalculator.Domain.Place;
import ru.helix.prenalyticcalculator.Domain.RequiredField;
import ru.helix.prenalyticcalculator.Domain.Sample;
import ru.helix.prenalyticcalculator.Domain.Variant;

public class JsonDataSource {
	private JsonDataSource() {}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public final static List<Place> GetPlaces(String source) throws IOException {
		ArrayList<Place> places = new ArrayList<Place>();
		JsonReader reader = null;
		
		try {
			reader = new JsonReader(new StringReader(source));
			reader.beginArray();
			while(reader.hasNext()) {
				String Code = null;
				String Name = null;
				String HubCode = null;
				
				reader.beginObject();
				while(reader.hasNext()) {
					String name = reader.nextName();
					
					if(name.equals("Code")) {
						Code = reader.nextString();
					} else if (name.equals("Name")) {
						Name = reader.nextString();
					} else if (name.equals("HubCode")) {
						HubCode = reader.nextString();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				
				Place place = new Place();
				place.Code = Code;
				place.Name = Name;
				place.HubCode = HubCode;
				
				places.add(place);
			}
			reader.endArray();
			
		} finally {
			if(reader != null)
				reader.close();
		}
		
		return places;
	}
	
	public final static List<NomenclatureItem> GetNomenclatureItems(String source) throws IOException {
		ArrayList<NomenclatureItem> items = new ArrayList<NomenclatureItem>();
		JsonReader reader = null;
		
		try {
			reader = new JsonReader(new StringReader(source));
			reader.beginArray();
			while(reader.hasNext()) {
				String Code = null;
				String Name = null;
				
				reader.beginObject();
				while(reader.hasNext()) {
					String name = reader.nextName();
					
					if(name.equals("Code")) {
						Code = reader.nextString();
					} else if (name.equals("Name")) {
						Name = reader.nextString();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				
				NomenclatureItem item = new NomenclatureItem();
				item.Code = Code;
				item.Name = Name;
				
				items.add(item);
			}
			reader.endArray();
			
		} finally {
			if(reader != null)
				reader.close();
		}
		
		return items;
	}
	
	public final static List<Variant> GetVariants(String source) throws IOException {
		ArrayList<Variant> variants = new ArrayList<Variant>();
		JsonReader reader = null;
		
		try {
			reader = new JsonReader(new StringReader(source));
			reader.beginArray();
			while(reader.hasNext()) {
				reader.beginObject();
				
				Variant variant = new Variant();
				
				while(reader.hasNext()) {
					String name = reader.nextName();
					
					if(name.equals("Samples")) {
						reader.beginArray();
						while(reader.hasNext()) {
							Sample sample = new Sample();
							reader.beginObject();
							String sName = reader.nextName();
							
							if(sName.equals("SampleType")) {
								sample.SampleType = reader.nextString();
							} else if (sName.equals("ContainerType")) {
								sample.ContainerType = reader.nextString();
							} else if (sName.equals("Preparation")) {
								sample.Preparation = reader.nextString();
							} else if (sName.equals("Protocol")) {
								sample.Protocol = reader.nextString();
							} else if (sName.equals("MaxVolume")) {
								sample.MaxVolume = reader.nextInt();
							} else if (sName.equals("CurrentVolume")) {
								sample.CurrentVolume = reader.nextInt();
							} else if (sName.equals("SampleGroup")) {
								sample.SampleGroup = reader.nextString();
							} else if (sName.equals("DeadVolume")) {
								sample.DeadVolume = reader.nextInt();
							} else if (sName.equals("Manipulations")) {
								reader.beginArray();
								while(reader.hasNext()) {
									Manipulation manipulation = new Manipulation();
									reader.beginObject();
									
									String mName = reader.nextName();
									
									if(mName.equals("Code")) {
										manipulation.Code = reader.nextString();
									} else if (mName.equals("Name")) {
										manipulation.Name = reader.nextName();
									} else if (mName.equals("Preparation")) {
										manipulation.Preparation = reader.nextString();
									} else {
										reader.skipValue();
									}
									
									reader.endObject();
									
									sample.Manipulations.add(manipulation);
								}
								reader.endArray();
							} else {
								reader.skipValue();
							}
					
							reader.endObject();
							
							variant.Samples.add(sample);
						}
						reader.endArray();
					} else if (name.equals("RequiredFields")) {
						reader.beginArray();
						while(reader.hasNext()) {
							RequiredField reqField = new RequiredField();
							reader.beginObject();
							
							while(reader.hasNext()) {
								String rfName = reader.nextName();
								
								if (rfName.equals("Code")) {
									reqField.Code = reader.nextString();
								} else if (rfName.equals("Name")) {
									reqField.Name = reader.nextString();
								} else {
									reader.skipValue();
								}
							}
							
							reader.endObject();
							variant.RequiredFields.add(reqField);
						}
		
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				
				variants.add(variant);
			}
			reader.endArray();
			
		} finally {
			if(reader != null)
				reader.close();
		}
		
		return variants;
	}
}

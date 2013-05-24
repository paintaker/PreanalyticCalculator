package ru.helix.prenalyticcalculator.Domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
	public String Code;
	public String Name;
	public String HubCode;
	
	public Place() {}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Code);
		dest.writeString(HubCode);
		dest.writeString(Name);
	}
	
	public static final Parcelable.Creator<Place> CREATOR = new Creator<Place>() {
		public Place createFromParcel(Parcel source) {
			return new Place(source);
		};
		
		@Override
		public Place[] newArray(int size) {
			return new Place[size];
		}
	};
	
	private Place(Parcel source) {
		Code = source.readString();
		HubCode = source.readString();
		Name = source.readString();
	}
}

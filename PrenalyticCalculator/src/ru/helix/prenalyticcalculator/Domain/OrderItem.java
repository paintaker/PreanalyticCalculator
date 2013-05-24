package ru.helix.prenalyticcalculator.Domain;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
	public String Code;
	public String Name;
	public byte Selected;
	
	public OrderItem() {}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.Code);
		dest.writeString(this.Name);
		dest.writeByte(this.Selected);
	}
	
	public static final Parcelable.Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
		@Override
		public OrderItem createFromParcel(Parcel source) {
			return new OrderItem(source);
		}
		
		@Override
		public OrderItem[] newArray(int size) {
			return new OrderItem[size];
		}
	};
	
	private OrderItem(Parcel source) {
		Code = source.readString();
		Name = source.readString();
		Selected = source.readByte();
	}
}

package com.amap.mapapi.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class PoiItem extends OverlayItem
{
  public static final Parcelable.Creator<PoiItem> CREATOR = new l();
  public static final String DesSplit = " - ";
  private String a;
  private String b;
  private String c;
  private String d;
  private String e = "";
  private String f;
  private int g = -1;

  private PoiItem(Parcel paramParcel)
  {
    super(paramParcel);
    this.a = paramParcel.readString();
    this.d = paramParcel.readString();
    this.c = paramParcel.readString();
    this.b = paramParcel.readString();
    this.e = paramParcel.readString();
    this.g = paramParcel.readInt();
  }

  public PoiItem(String paramString1, GeoPoint paramGeoPoint, String paramString2, String paramString3)
  {
    super(paramGeoPoint, paramString2, paramString3);
    this.a = paramString1;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    PoiItem localPoiItem;
    do
    {
      do
        return false;
      while (paramObject.getClass() != getClass());
      localPoiItem = (PoiItem)paramObject;
    }
    while (this.a != localPoiItem.a);
    return true;
  }

  public String getAdCode()
  {
    return this.d;
  }

  public int getDistance()
  {
    return this.g;
  }

  public String getPoiId()
  {
    return this.a;
  }

  public String getTel()
  {
    return this.c;
  }

  public String getTypeCode()
  {
    return this.b;
  }

  public String getTypeDes()
  {
    return this.e;
  }

  public String getXmlNode()
  {
    return this.f;
  }

  public int hashCode()
  {
    return this.a.hashCode();
  }

  public void setAdCode(String paramString)
  {
    this.d = paramString;
  }

  public void setDistance(int paramInt)
  {
    this.g = paramInt;
  }

  public void setTel(String paramString)
  {
    this.c = paramString;
  }

  public void setTypeCode(String paramString)
  {
    this.b = paramString;
  }

  public void setTypeDes(String paramString)
  {
    this.e = paramString;
  }

  public void setXmlNode(String paramString)
  {
    this.f = paramString;
  }

  public String toString()
  {
    return this.mTitle;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeString(this.a);
    paramParcel.writeString(this.d);
    paramParcel.writeString(this.c);
    paramParcel.writeString(this.b);
    paramParcel.writeString(this.e);
    paramParcel.writeInt(this.g);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.PoiItem
 * JD-Core Version:    0.6.0
 */
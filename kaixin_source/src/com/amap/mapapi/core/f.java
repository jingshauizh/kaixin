package com.amap.mapapi.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class f
  implements Parcelable.Creator<GeoPoint>
{
  public GeoPoint a(Parcel paramParcel)
  {
    return new GeoPoint(paramParcel, null);
  }

  public GeoPoint[] a(int paramInt)
  {
    return new GeoPoint[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.f
 * JD-Core Version:    0.6.0
 */
package com.amap.mapapi.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class l
  implements Parcelable.Creator<PoiItem>
{
  public PoiItem a(Parcel paramParcel)
  {
    return new PoiItem(paramParcel, null);
  }

  public PoiItem[] a(int paramInt)
  {
    return new PoiItem[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.l
 * JD-Core Version:    0.6.0
 */
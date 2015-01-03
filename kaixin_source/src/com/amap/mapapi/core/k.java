package com.amap.mapapi.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class k
  implements Parcelable.Creator<OverlayItem>
{
  public OverlayItem a(Parcel paramParcel)
  {
    return new OverlayItem(paramParcel);
  }

  public OverlayItem[] a(int paramInt)
  {
    return new OverlayItem[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.k
 * JD-Core Version:    0.6.0
 */
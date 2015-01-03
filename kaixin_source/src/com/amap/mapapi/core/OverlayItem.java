package com.amap.mapapi.core;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class OverlayItem
  implements Parcelable
{
  public static final Parcelable.Creator<OverlayItem> CREATOR = new k();
  public static final int ITEM_STATE_FOCUSED_MASK = 4;
  public static final int ITEM_STATE_PRESSED_MASK = 1;
  public static final int ITEM_STATE_SELECTED_MASK = 2;
  protected Drawable mMarker;
  protected final GeoPoint mPoint;
  protected final String mSnippet;
  protected final String mTitle;

  protected OverlayItem(Parcel paramParcel)
  {
    this.mPoint = ((GeoPoint)paramParcel.readValue(GeoPoint.class.getClassLoader()));
    this.mTitle = paramParcel.readString();
    this.mSnippet = paramParcel.readString();
  }

  public OverlayItem(GeoPoint paramGeoPoint, String paramString1, String paramString2)
  {
    this.mPoint = paramGeoPoint.e();
    this.mTitle = paramString1;
    this.mSnippet = paramString2;
    this.mMarker = null;
  }

  private static int[] a(int paramInt)
  {
    int i = 0;
    int[] arrayOfInt1 = new int[3];
    if ((paramInt & 0x1) != 0)
      arrayOfInt1[0] = 16842919;
    for (int j = 1; ; j = 0)
    {
      if ((paramInt & 0x2) != 0)
      {
        arrayOfInt1[j] = 16842913;
        j++;
      }
      if ((paramInt & 0x4) != 0)
      {
        arrayOfInt1[j] = 16842908;
        j++;
      }
      int[] arrayOfInt2 = new int[j];
      while (i < j)
      {
        arrayOfInt2[i] = arrayOfInt1[i];
        i++;
      }
      return arrayOfInt2;
    }
  }

  public static void setState(Drawable paramDrawable, int paramInt)
  {
    int[] arrayOfInt = a(paramInt);
    if (arrayOfInt.length != 0)
      paramDrawable.setState(arrayOfInt);
  }

  public int describeContents()
  {
    return 0;
  }

  public Drawable getMarker(int paramInt)
  {
    if (this.mMarker == null)
      return null;
    if (paramInt == 0)
      return this.mMarker.getCurrent();
    int[] arrayOfInt1 = a(paramInt);
    int[] arrayOfInt2 = this.mMarker.getState();
    this.mMarker.setState(arrayOfInt1);
    Drawable localDrawable = this.mMarker.getCurrent();
    this.mMarker.setState(arrayOfInt2);
    return localDrawable;
  }

  public GeoPoint getPoint()
  {
    return this.mPoint;
  }

  public String getSnippet()
  {
    return this.mSnippet;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public Drawable getmMarker()
  {
    return this.mMarker;
  }

  public String routableAddress()
  {
    return this.mPoint.f();
  }

  public void setMarker(Drawable paramDrawable)
  {
    this.mMarker = paramDrawable;
    if (this.mMarker != null)
      this.mMarker.setState(new int[0]);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeValue(this.mPoint);
    paramParcel.writeString(this.mTitle);
    paramParcel.writeString(this.mSnippet);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.OverlayItem
 * JD-Core Version:    0.6.0
 */
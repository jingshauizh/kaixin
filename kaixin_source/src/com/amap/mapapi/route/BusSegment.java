package com.amap.mapapi.route;

import com.amap.mapapi.core.GeoPoint;

public class BusSegment extends Segment
{
  protected String mFirstStation;
  protected String mLastStation;
  protected String mLine;
  protected String[] mPassStopName;
  protected GeoPoint[] mPassStopPos;

  public String getFirstStationName()
  {
    return this.mFirstStation;
  }

  public String getLastStationName()
  {
    return this.mLastStation;
  }

  public String getLineName()
  {
    return this.mLine;
  }

  public String getOffStationName()
  {
    return this.mPassStopName[(-1 + this.mPassStopName.length)];
  }

  public String getOnStationName()
  {
    return this.mPassStopName[0];
  }

  public String[] getPassStopName()
  {
    return this.mPassStopName;
  }

  public int getStopNumber()
  {
    return this.mPassStopPos.length;
  }

  public void setFirstStationName(String paramString)
  {
    this.mFirstStation = paramString;
  }

  public void setLastStationName(String paramString)
  {
    this.mLastStation = paramString;
  }

  public void setLineName(String paramString)
  {
    this.mLine = paramString;
  }

  public void setPassStopName(String[] paramArrayOfString)
  {
    this.mPassStopName = paramArrayOfString;
  }

  public void setPassStopPos(GeoPoint[] paramArrayOfGeoPoint)
  {
    this.mPassStopPos = paramArrayOfGeoPoint;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.BusSegment
 * JD-Core Version:    0.6.0
 */
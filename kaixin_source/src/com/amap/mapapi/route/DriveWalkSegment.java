package com.amap.mapapi.route;

public class DriveWalkSegment extends Segment
{
  public static final int NoAction = -1;
  protected int mActionCode;
  protected String mActionDes;
  protected String mRoadName;

  public int getActionCode()
  {
    return this.mActionCode;
  }

  public String getActionDescription()
  {
    return this.mActionDes;
  }

  public String getRoadName()
  {
    return this.mRoadName;
  }

  public void setActionCode(int paramInt)
  {
    this.mActionCode = paramInt;
  }

  public void setActionDescription(String paramString)
  {
    this.mActionDes = paramString;
  }

  public void setRoadName(String paramString)
  {
    this.mRoadName = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.DriveWalkSegment
 * JD-Core Version:    0.6.0
 */
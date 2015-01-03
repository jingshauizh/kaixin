package com.kaixin001.item;

public class CheckInItem
{
  public CheckInUser checkInUser;
  public String content;
  public long ctime = 0L;
  public String distance;
  public String large;
  public String mMapUrl;
  public String mSource = null;
  public String poiId;
  public String poiName;
  public String privacy;
  public String thumbnail;
  public String wid;

  public void clear()
  {
    this.checkInUser = null;
    this.poiName = null;
    this.thumbnail = null;
    this.ctime = 0L;
    this.distance = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CheckInItem
 * JD-Core Version:    0.6.0
 */
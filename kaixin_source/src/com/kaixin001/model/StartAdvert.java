package com.kaixin001.model;

public class StartAdvert
{
  private static volatile StartAdvert instance = null;
  private String actionlink;
  private int duration;
  private long endTime;
  private String image;
  private long startTime;

  public static StartAdvert getInstance()
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new StartAdvert();
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getActionlink()
  {
    return this.actionlink;
  }

  public int getDuration()
  {
    return this.duration;
  }

  public long getEndTime()
  {
    return this.endTime;
  }

  public String getImage()
  {
    return this.image;
  }

  public long getStartTime()
  {
    return this.startTime;
  }

  public void setActionlink(String paramString)
  {
    this.actionlink = paramString;
  }

  public void setDuration(int paramInt)
  {
    this.duration = paramInt;
  }

  public void setEndTime(long paramLong)
  {
    this.endTime = paramLong;
  }

  public void setImage(String paramString)
  {
    this.image = paramString;
  }

  public void setStartTime(long paramLong)
  {
    this.startTime = paramLong;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.StartAdvert
 * JD-Core Version:    0.6.0
 */
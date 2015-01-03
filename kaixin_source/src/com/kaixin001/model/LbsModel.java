package com.kaixin001.model;

import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.PoiActivityItem;
import com.kaixin001.item.PoiItem;
import java.util.ArrayList;

public class LbsModel extends KXModel
{
  private static LbsModel sInstance = null;
  private final ArrayList<PoiActivityItem> mActivityList = new ArrayList();
  private int mActivityTotal = 0;
  private final ArrayList<PoiItem> mPoiList = new ArrayList();
  private int mRewardTotal = 0;
  private int mTotal = 0;

  public static LbsModel getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new LbsModel();
      LbsModel localLbsModel = sInstance;
      return localLbsModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mPoiList.clear();
    this.mTotal = 0;
    this.mActivityList.clear();
    this.mActivityTotal = 0;
    if (sInstance != null)
      sInstance = null;
  }

  public ArrayList<PoiActivityItem> getActivityList()
  {
    return this.mActivityList;
  }

  public int getActivityTotal()
  {
    return this.mActivityTotal;
  }

  public final ArrayList<PoiItem> getPoiList()
  {
    return this.mPoiList;
  }

  public int getRewardTotal()
  {
    return this.mRewardTotal;
  }

  public int getTotal()
  {
    return this.mTotal;
  }

  public void setActivityTotal(int paramInt)
  {
    this.mActivityTotal = paramInt;
  }

  public void setRewardTotal(int paramInt)
  {
    this.mRewardTotal = paramInt;
  }

  public void setTotal(int paramInt)
  {
    this.mTotal = paramInt;
  }

  public static class CheckInList extends ArrayList<CheckInItem>
  {
    private boolean isLocalCheckin = true;
    private int mTotal = 0;

    public int getTotal()
    {
      return this.mTotal;
    }

    public boolean isLocalCheckin()
    {
      return this.isLocalCheckin;
    }

    public void setLocalCheckin(boolean paramBoolean)
    {
      this.isLocalCheckin = paramBoolean;
    }

    public void setTotal(int paramInt)
    {
      this.mTotal = paramInt;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.LbsModel
 * JD-Core Version:    0.6.0
 */
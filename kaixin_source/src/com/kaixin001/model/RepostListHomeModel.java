package com.kaixin001.model;

import com.kaixin001.item.RepostHomeInfo;
import java.util.ArrayList;

public class RepostListHomeModel extends KXModel
{
  public static final int ALL_MY_REPOST = 101;
  public static final int MY_PRIVACY_REPOST = 102;
  private static RepostListHomeModel instance;
  protected int mActiveRepostType = 101;
  private ArrayList<RepostHomeInfo> mAllRepostList;
  private ArrayList<RepostHomeInfo> mMyRepostList;
  private int mTotalAll = 0;
  private int mTotalPrivate = 0;

  public static RepostListHomeModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RepostListHomeModel();
      RepostListHomeModel localRepostListHomeModel = instance;
      return localRepostListHomeModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mAllRepostList = null;
    this.mMyRepostList = null;
    this.mTotalAll = 0;
    this.mTotalPrivate = 0;
    this.mActiveRepostType = 101;
  }

  public int getActiveRepostType()
  {
    return this.mActiveRepostType;
  }

  public ArrayList<RepostHomeInfo> getAllRepostList()
  {
    return this.mAllRepostList;
  }

  public int getAllTotal()
  {
    return this.mTotalAll;
  }

  public ArrayList<RepostHomeInfo> getMyRepostList()
  {
    return this.mMyRepostList;
  }

  public int getMyTotal()
  {
    return this.mTotalPrivate;
  }

  public void setActiveRepostType(int paramInt)
  {
    this.mActiveRepostType = paramInt;
  }

  public void setAllRepostList(ArrayList<RepostHomeInfo> paramArrayList)
  {
    if (this.mAllRepostList == null)
      this.mAllRepostList = new ArrayList();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
      this.mAllRepostList.addAll(paramArrayList);
  }

  public void setAllTotal(int paramInt)
  {
    this.mTotalAll = paramInt;
  }

  public void setMyRepostList(ArrayList<RepostHomeInfo> paramArrayList)
  {
    if (this.mMyRepostList == null)
      this.mMyRepostList = new ArrayList();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
      this.mMyRepostList.addAll(paramArrayList);
  }

  public void setMyTotal(int paramInt)
  {
    this.mTotalPrivate = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RepostListHomeModel
 * JD-Core Version:    0.6.0
 */
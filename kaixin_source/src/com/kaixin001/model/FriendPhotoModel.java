package com.kaixin001.model;

import com.kaixin001.item.FriendPhotoInfo;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class FriendPhotoModel extends KXModel
{
  public static final int FLAG_FROM_CACHE = -2;
  public static final int FLAG_GET_FIRST = -1;
  private static final String TAG = "FriendPhotoModel";
  private static FriendPhotoModel instance;
  private int mFriendPhotoTotal = 0;
  private ArrayList<FriendPhotoInfo> mFriendPhotolist = new ArrayList();
  private volatile boolean mHasShutDown = false;
  private int mPlazaPhotoTotal = 0;
  private ArrayList<FriendPhotoInfo> mPlazaPhotolist = new ArrayList();
  private long mTimeStampFirend;
  private long mTimeStampPlaza;

  public static FriendPhotoModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FriendPhotoModel();
      FriendPhotoModel localFriendPhotoModel = instance;
      return localFriendPhotoModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addMoreFriendPhoto(ArrayList<FriendPhotoInfo> paramArrayList, int paramInt)
  {
    KXLog.d("FriendPhotoModel", "-------addMoreFriendPhoto()---- mHasShutDown:" + this.mHasShutDown + "---------");
    if (this.mHasShutDown);
    do
    {
      return;
      KXLog.d("FriendPhotoModel", "add more friend photo to friends photo list>> " + paramArrayList);
    }
    while (paramArrayList == null);
    this.mFriendPhotolist.addAll(paramArrayList);
    this.mFriendPhotoTotal = paramInt;
  }

  public void addMorePlazaPhoto(ArrayList<FriendPhotoInfo> paramArrayList, int paramInt)
  {
    KXLog.d("FriendPhotoModel", "-------addMorePlazaPhoto()---- mHasShutDown:" + this.mHasShutDown + "---------");
    if (this.mHasShutDown);
    do
    {
      return;
      KXLog.d("FriendPhotoModel", "add more plaza photo to plaza photo list>> " + paramArrayList);
    }
    while (paramArrayList == null);
    this.mPlazaPhotolist.addAll(paramArrayList);
    this.mPlazaPhotoTotal = paramInt;
  }

  public void clear()
  {
    this.mFriendPhotolist.clear();
    this.mPlazaPhotolist.clear();
    this.mFriendPhotoTotal = 0;
    this.mPlazaPhotoTotal = 0;
    this.mTimeStampFirend = 0L;
    this.mTimeStampPlaza = 0L;
  }

  public void clear(boolean paramBoolean)
  {
    if (paramBoolean)
      setHasShutDown(true);
    clear();
  }

  public ArrayList<FriendPhotoInfo> getFriendPhotoList()
  {
    return this.mFriendPhotolist;
  }

  public ArrayList<FriendPhotoInfo> getPlazaPhotoList()
  {
    return this.mPlazaPhotolist;
  }

  public int getmFriendPhotoTotal()
  {
    return this.mFriendPhotoTotal;
  }

  public int getmPlazaPhotoTotal()
  {
    return this.mPlazaPhotoTotal;
  }

  public long getmTimeStampFirend()
  {
    return this.mTimeStampFirend;
  }

  public long getmTimeStampPlaza()
  {
    return this.mTimeStampPlaza;
  }

  public void resetNewFriendPhoto(ArrayList<FriendPhotoInfo> paramArrayList, int paramInt, long paramLong)
  {
    KXLog.d("FriendPhotoModel", "-------resetNewFriendPhoto()---- mHasShutDown:" + this.mHasShutDown + "---------");
    if (this.mHasShutDown);
    do
    {
      return;
      KXLog.d("FriendPhotoModel", "reset first batch photo list data to FRIENDS photo list>> " + paramArrayList);
      KXLog.d("FriendPhotoModel", "reset firest batch photo with [totalInServer]=" + paramInt + " [timeStamp]=" + paramLong);
    }
    while (paramArrayList == null);
    if (this.mFriendPhotolist == null)
    {
      this.mFriendPhotolist = new ArrayList();
      this.mFriendPhotolist.addAll(paramArrayList);
      if (paramLong == -2L)
        break label148;
    }
    label148: for (this.mFriendPhotoTotal = paramInt; ; this.mFriendPhotoTotal = 0)
    {
      this.mTimeStampFirend = paramLong;
      return;
      this.mFriendPhotolist.clear();
      break;
    }
  }

  public void resetNewPlazaPhoto(ArrayList<FriendPhotoInfo> paramArrayList, int paramInt, long paramLong)
  {
    KXLog.d("FriendPhotoModel", "-------resetNewPlazaPhoto()---- mHasShutDown:" + this.mHasShutDown + "---------");
    if (this.mHasShutDown);
    do
    {
      return;
      KXLog.d("FriendPhotoModel", "reset first batch photo list data to PLAZA photo list>> " + paramArrayList);
      KXLog.d("FriendPhotoModel", "reset firest batch photo with [totalInServer]=" + paramInt + " [timeStamp]=" + paramLong);
    }
    while (paramArrayList == null);
    if (this.mPlazaPhotolist == null)
    {
      this.mPlazaPhotolist = new ArrayList();
      this.mPlazaPhotolist.addAll(paramArrayList);
      if (paramLong <= 0L)
        break label146;
    }
    label146: for (this.mPlazaPhotoTotal = paramInt; ; this.mPlazaPhotoTotal = 0)
    {
      this.mTimeStampPlaza = paramLong;
      return;
      this.mPlazaPhotolist.clear();
      break;
    }
  }

  public void setHasShutDown(boolean paramBoolean)
  {
    this.mHasShutDown = paramBoolean;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FriendPhotoModel
 * JD-Core Version:    0.6.0
 */
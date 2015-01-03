package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.CloudAlbumPicItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CloudAlbumModel
{
  private static CloudAlbumModel instance;
  private long mFirstLocalPicModifiedTime = -1L;
  private HashMap<String, String> mIgnoreFileList = new HashMap();
  private ArrayList<CloudAlbumPicItem> mLocalPicList = new ArrayList();
  private HashMap<String, Integer> mPicStatusMap = new HashMap();
  private ArrayList<CloudAlbumPicItem> mSyncPicList = new ArrayList();
  public int mTotal = 0;

  public static CloudAlbumModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CloudAlbumModel();
      CloudAlbumModel localCloudAlbumModel = instance;
      return localCloudAlbumModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addPicStatus(String paramString, int paramInt)
  {
    this.mPicStatusMap.put(paramString, Integer.valueOf(paramInt));
  }

  public void clear()
  {
    this.mPicStatusMap.clear();
    this.mSyncPicList.clear();
    this.mLocalPicList.clear();
    this.mTotal = 0;
    this.mFirstLocalPicModifiedTime = -1L;
  }

  public ArrayList<CloudAlbumPicItem> geLocalPicList()
  {
    return this.mLocalPicList;
  }

  public ArrayList<CloudAlbumPicItem> geSyncPicList()
  {
    return this.mSyncPicList;
  }

  public long getFirstLocalPicModifiedTime()
  {
    return this.mFirstLocalPicModifiedTime;
  }

  public HashMap<String, String> getIgnoreFileList()
  {
    return this.mIgnoreFileList;
  }

  public CloudAlbumPicItem getItem(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    Iterator localIterator = this.mLocalPicList.iterator();
    CloudAlbumPicItem localCloudAlbumPicItem;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localCloudAlbumPicItem = (CloudAlbumPicItem)localIterator.next();
    }
    while (!paramString.equals(localCloudAlbumPicItem.mMD5));
    return localCloudAlbumPicItem;
  }

  public int getStatus(String paramString)
  {
    Integer localInteger = (Integer)this.mPicStatusMap.get(paramString);
    if (this.mIgnoreFileList.containsKey(paramString))
      return 4;
    if (localInteger == null)
      return -1;
    return localInteger.intValue();
  }

  public boolean hasMore()
  {
    return this.mTotal > this.mSyncPicList.size();
  }

  public void setFirstLocalPicModifiedTime(long paramLong)
  {
    this.mFirstLocalPicModifiedTime = paramLong;
  }

  public void updateState()
  {
    Iterator localIterator = this.mLocalPicList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      CloudAlbumPicItem localCloudAlbumPicItem = (CloudAlbumPicItem)localIterator.next();
      int i = getStatus(localCloudAlbumPicItem.mMD5);
      if ((i != 1) && (i != 4))
        continue;
      localCloudAlbumPicItem.mState = i;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CloudAlbumModel
 * JD-Core Version:    0.6.0
 */
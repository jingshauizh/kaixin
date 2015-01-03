package com.kaixin001.model;

import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.PoiItem;
import com.kaixin001.item.PoiPhotoesItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class CheckInInfoModel extends KXModel
{
  private static CheckInInfoModel instance;
  private static final long serialVersionUID = -4887228032056386540L;
  public long currentTimestamp;
  public ReentrantLock itemListLock = new ReentrantLock();
  public KaixinModelTemplate<CheckInItem> nearbyFriendCheckin = new KaixinModelTemplate();
  public KaixinModelTemplate<CheckInItem> otherFriendCheckin = new KaixinModelTemplate();
  public String poi = "0";
  public KaixinModelTemplate<PoiPhotoesItem> poiPhotoesList = new KaixinModelTemplate();
  public KaixinModelTemplate<CheckInItem> strangerCheckin = new KaixinModelTemplate();
  public ArrayList<CheckInUser> users = new ArrayList();

  public static CheckInInfoModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CheckInInfoModel();
      CheckInInfoModel localCheckInInfoModel = instance;
      return localCheckInInfoModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addKaixinUser(CheckInUser paramCheckInUser)
  {
    try
    {
      this.itemListLock.lock();
      if (getKaixinUserbyID(paramCheckInUser.user.uid) == null)
        this.users.add(paramCheckInUser);
      return;
    }
    finally
    {
      this.itemListLock.unlock();
    }
    throw localObject;
  }

  public void clear()
  {
    this.poi = null;
    this.users.clear();
    this.nearbyFriendCheckin.clearItemList();
    this.otherFriendCheckin.clearItemList();
    this.strangerCheckin.clearItemList();
    this.poiPhotoesList.clearItemList();
  }

  public CheckInUser getKaixinUserbyID(String paramString)
  {
    try
    {
      this.itemListLock.lock();
      Iterator localIterator = this.users.iterator();
      CheckInUser localCheckInUser;
      boolean bool2;
      do
      {
        do
        {
          boolean bool1 = localIterator.hasNext();
          if (!bool1)
            return null;
          localCheckInUser = (CheckInUser)localIterator.next();
        }
        while (localCheckInUser.user == null);
        bool2 = paramString.equals(localCheckInUser.user.uid);
      }
      while (!bool2);
      return localCheckInUser;
    }
    finally
    {
      this.itemListLock.unlock();
    }
    throw localObject;
  }

  public PoiPhotoesItem getPoiPhotoesItemByPoiid(String paramString)
  {
    this.poiPhotoesList.itemListLock.lock();
    try
    {
      Iterator localIterator = this.poiPhotoesList.getItemList().iterator();
      PoiPhotoesItem localPoiPhotoesItem;
      boolean bool2;
      do
      {
        do
        {
          boolean bool1 = localIterator.hasNext();
          if (!bool1)
            return null;
          localPoiPhotoesItem = (PoiPhotoesItem)localIterator.next();
        }
        while (localPoiPhotoesItem.poi == null);
        bool2 = paramString.equals(localPoiPhotoesItem.poi.poiId);
      }
      while (!bool2);
      return localPoiPhotoesItem;
    }
    finally
    {
      this.poiPhotoesList.itemListLock.unlock();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CheckInInfoModel
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.model;

import com.kaixin001.businesslogic.AddFriendResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FindFriendModel extends KXModel
{
  private static FindFriendModel instance = new FindFriendModel();
  public HashMap<String, AddFriendResult> addFriendApplyChanges = new HashMap();
  public ReentrantLock friendListLock = new ReentrantLock();
  private ArrayList<StrangerModel.Stranger> friends = new ArrayList();
  private ArrayList<StrangerModel.Stranger> mCategoryStars = new ArrayList();
  public boolean mHasmoreCategoryStar = false;
  public boolean mHasmoreMaybeKnow = false;
  public boolean mHasmoreNearbyPerson = false;
  public boolean mHasmoreSearch = false;
  public boolean mHasmoreStar = false;
  private ArrayList<StrangerModel.Stranger> mMaybeKnow = new ArrayList();
  private ArrayList<StrangerModel.Stranger> mNearbyPerson = new ArrayList();
  private ArrayList<StrangerModel.Stranger> mSearchList = new ArrayList();
  private ArrayList<StrangerModel.Stranger> mStars = new ArrayList();
  public String searchText;
  public int totalSearchFriend = 0;
  public int totalSearchFriendByAccount = 0;
  public int totalSearchFriendByName = 0;

  private FindFriendModel()
  {
    if (this.friends == null)
      this.friends = new ArrayList();
  }

  public static void clearData()
  {
    if (instance != null)
      instance.clear();
  }

  public static FindFriendModel getInstance()
  {
    return instance;
  }

  public void clear()
  {
    this.totalSearchFriendByName = 0;
    this.friends.clear();
    this.mStars.clear();
    this.mCategoryStars.clear();
    this.mNearbyPerson.clear();
    this.mMaybeKnow.clear();
    this.mSearchList.clear();
    this.addFriendApplyChanges.clear();
  }

  public void clearFriends()
  {
    try
    {
      this.friendListLock.lock();
      this.friends.clear();
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<StrangerModel.Stranger> getFriends()
  {
    try
    {
      this.friendListLock.lock();
      ArrayList localArrayList = this.friends;
      return localArrayList;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<StrangerModel.Stranger> getMaybeKnow()
  {
    return this.mMaybeKnow;
  }

  public ArrayList<StrangerModel.Stranger> getNearbyPerson()
  {
    return this.mNearbyPerson;
  }

  public ArrayList<StrangerModel.Stranger> getRecommendCategoryStars()
  {
    return this.mCategoryStars;
  }

  public ArrayList<StrangerModel.Stranger> getRecommendStars()
  {
    return this.mStars;
  }

  public ArrayList<StrangerModel.Stranger> getSearchList()
  {
    return this.mSearchList;
  }

  public void setFriends(ArrayList<StrangerModel.Stranger> paramArrayList, String paramString)
  {
    try
    {
      this.friendListLock.lock();
      if (!paramString.equals(this.searchText))
      {
        this.searchText = paramString;
        this.friends.clear();
      }
      if (paramArrayList != null)
        this.friends.addAll(paramArrayList);
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public void setList(ArrayList<StrangerModel.Stranger> paramArrayList)
  {
    if (paramArrayList != null)
    {
      this.friends.clear();
      this.friends.addAll(paramArrayList);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FindFriendModel
 * JD-Core Version:    0.6.0
 */
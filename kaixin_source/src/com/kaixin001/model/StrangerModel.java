package com.kaixin001.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class StrangerModel extends KXModel
{
  private static StrangerModel instance;
  public ReentrantLock friendListLock = new ReentrantLock();
  private ArrayList<Stranger> friends = new ArrayList();
  private ArrayList<Stranger> starFriends = new ArrayList();
  public ReentrantLock starListLock = new ReentrantLock();
  public int totalFriends = 0;
  public String totalStars = "0";
  public String uid = "0";

  public static StrangerModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new StrangerModel();
      StrangerModel localStrangerModel = instance;
      return localStrangerModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.uid = "0";
    this.totalFriends = 0;
    this.friends.clear();
    this.starFriends.clear();
  }

  public void clearFriends()
  {
    try
    {
      this.friendListLock.lock();
      this.totalFriends = 0;
      this.friends.clear();
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public void clearStarFriends()
  {
    try
    {
      this.friendListLock.lock();
      this.starFriends.clear();
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<Stranger> getFriends()
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

  public ArrayList<Stranger> getStarFriends()
  {
    try
    {
      this.starListLock.lock();
      ArrayList localArrayList = this.starFriends;
      return localArrayList;
    }
    finally
    {
      this.starListLock.unlock();
    }
    throw localObject;
  }

  public void setFriends(int paramInt1, ArrayList<Stranger> paramArrayList, int paramInt2)
  {
    try
    {
      this.friendListLock.lock();
      if (paramInt2 == 0)
        this.friends.clear();
      if (paramArrayList != null)
      {
        this.friends.clear();
        this.friends.addAll(paramArrayList);
      }
      this.totalFriends = paramInt1;
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public void setStarFriends(String paramString, ArrayList<Stranger> paramArrayList, int paramInt)
  {
    try
    {
      this.starListLock.lock();
      if (paramInt == 0)
        this.starFriends.clear();
      if (paramArrayList != null)
        this.starFriends.addAll(paramArrayList);
      this.totalStars = paramString;
      return;
    }
    finally
    {
      this.starListLock.unlock();
    }
    throw localObject;
  }

  public static class Stranger
  {
    public String city;
    public String company;
    public String constellation;
    public String distance;
    public String fans;
    public String flogo;
    public String fname;
    public String fuid;
    public int gender;
    public int isMobileFriend;
    public int isMyFriend;
    public int isStar;
    public int isfan;
    public String location;
    public String mobile;
    public String sameFriends;
    public int sameFriendsNum;
    public String school;
    public String state;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.StrangerModel
 * JD-Core Version:    0.6.0
 */
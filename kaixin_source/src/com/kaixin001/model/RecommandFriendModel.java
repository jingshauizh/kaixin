package com.kaixin001.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class RecommandFriendModel extends KXModel
{
  private static RecommandFriendModel instance;
  public ReentrantLock friendListLock = new ReentrantLock();
  private ArrayList<RecommandFriend> friends = new ArrayList();
  private ArrayList<RecommandFriend> starFriends = new ArrayList();
  public ReentrantLock starListLock = new ReentrantLock();
  public int totalFriends = 0;
  public String totalStars = "0";
  public String uid = "0";

  public static RecommandFriendModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecommandFriendModel();
      RecommandFriendModel localRecommandFriendModel = instance;
      return localRecommandFriendModel;
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

  public ArrayList<RecommandFriend> getFriends()
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

  public ArrayList<RecommandFriend> getStarFriends()
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

  public void setFriends(int paramInt1, ArrayList<RecommandFriend> paramArrayList, int paramInt2)
  {
    try
    {
      this.friendListLock.lock();
      if (paramInt2 == 0)
        this.friends.clear();
      if (paramArrayList != null)
        this.friends.addAll(paramArrayList);
      this.totalFriends = paramInt1;
      return;
    }
    finally
    {
      this.friendListLock.unlock();
    }
    throw localObject;
  }

  public void setStarFriends(String paramString, ArrayList<RecommandFriend> paramArrayList, int paramInt)
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

  public static class RecommandFriend
  {
    public String city;
    public String company;
    public String constellation;
    public String fans;
    public String flogo;
    public String fname;
    public String fuid;
    public int gender;
    public int isMobileFriend;
    public int isMyFriend;
    public int isStar;
    public int isfan;
    public String sameFriends;
    public String school;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RecommandFriendModel
 * JD-Core Version:    0.6.0
 */
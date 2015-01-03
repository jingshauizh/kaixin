package com.kaixin001.model;

import com.kaixin001.item.KaixinCircleMember;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class CircleMemberModel extends KXModel
{
  private static CircleMemberModel instance;
  private ArrayList<KaixinCircleMember> circleMemberList = new ArrayList();
  public ReentrantLock circleMemberListLock = new ReentrantLock();
  public int total = 0;
  public String uid = "0";

  public static CircleMemberModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleMemberModel();
      CircleMemberModel localCircleMemberModel = instance;
      return localCircleMemberModel;
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
    this.total = 0;
    this.circleMemberList.clear();
  }

  public void clearFriends()
  {
    try
    {
      this.circleMemberListLock.lock();
      this.total = 0;
      this.circleMemberList.clear();
      return;
    }
    finally
    {
      this.circleMemberListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<KaixinCircleMember> getCircleMembers()
  {
    try
    {
      this.circleMemberListLock.lock();
      ArrayList localArrayList = this.circleMemberList;
      return localArrayList;
    }
    finally
    {
      this.circleMemberListLock.unlock();
    }
    throw localObject;
  }

  public void setCircleMemberList(int paramInt1, ArrayList<KaixinCircleMember> paramArrayList, int paramInt2)
  {
    try
    {
      this.circleMemberListLock.lock();
      if (paramInt2 == 0)
        this.circleMemberList.clear();
      if (paramArrayList != null)
        this.circleMemberList.addAll(paramArrayList);
      this.total = paramInt1;
      return;
    }
    finally
    {
      this.circleMemberListLock.unlock();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleMemberModel
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.model;

import com.kaixin001.item.VisitorItem;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class VisitorModel extends KXModel
{
  private static VisitorModel instance;
  public int totalViewCount = 0;
  public int totalVisitors = 0;
  public String uid = "0";
  public ReentrantLock visitorListLock = new ReentrantLock();
  private ArrayList<VisitorItem> visitors = new ArrayList();

  public static VisitorModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new VisitorModel();
      VisitorModel localVisitorModel = instance;
      return localVisitorModel;
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
    this.totalVisitors = 0;
    this.totalViewCount = 0;
    this.visitors.clear();
  }

  public void clearVisitors()
  {
    try
    {
      this.visitorListLock.lock();
      this.totalVisitors = 0;
      this.totalViewCount = 0;
      this.visitors.clear();
      return;
    }
    finally
    {
      this.visitorListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<VisitorItem> getVisitors()
  {
    try
    {
      this.visitorListLock.lock();
      ArrayList localArrayList = this.visitors;
      return localArrayList;
    }
    finally
    {
      this.visitorListLock.unlock();
    }
    throw localObject;
  }

  public void setVisitors(int paramInt1, int paramInt2, ArrayList<VisitorItem> paramArrayList, String paramString)
  {
    try
    {
      this.visitorListLock.lock();
      if (paramString.equals("0"))
        this.visitors.clear();
      if (paramArrayList != null)
        this.visitors.addAll(paramArrayList);
      this.totalVisitors = paramInt1;
      this.totalViewCount = paramInt2;
      return;
    }
    finally
    {
      this.visitorListLock.unlock();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.VisitorModel
 * JD-Core Version:    0.6.0
 */
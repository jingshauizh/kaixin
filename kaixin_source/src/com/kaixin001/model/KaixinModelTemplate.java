package com.kaixin001.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class KaixinModelTemplate<T>
{
  private ArrayList<T> itemList = new ArrayList();
  public ReentrantLock itemListLock = new ReentrantLock();
  public int total = -1;

  public void clearItemList()
  {
    try
    {
      this.itemListLock.lock();
      this.total = -1;
      this.itemList.clear();
      return;
    }
    finally
    {
      this.itemListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<T> getItemList()
  {
    try
    {
      this.itemListLock.lock();
      ArrayList localArrayList = this.itemList;
      return localArrayList;
    }
    finally
    {
      this.itemListLock.unlock();
    }
    throw localObject;
  }

  public boolean isGetAllData()
  {
    try
    {
      this.itemListLock.lock();
      if (this.total != -1)
      {
        int j = this.itemList.size();
        int k = this.total;
        if (j >= k)
        {
          i = 1;
          return i;
        }
      }
      int i = 0;
    }
    finally
    {
      this.itemListLock.unlock();
    }
  }

  public void setItemList(int paramInt1, ArrayList<T> paramArrayList, int paramInt2)
  {
    try
    {
      this.itemListLock.lock();
      if (paramInt2 == 0)
        this.itemList.clear();
      if (paramArrayList != null)
        this.itemList.addAll(paramArrayList);
      this.total = paramInt1;
      if (this.total < this.itemList.size())
        this.total = this.itemList.size();
      return;
    }
    finally
    {
      this.itemListLock.unlock();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.KaixinModelTemplate
 * JD-Core Version:    0.6.0
 */
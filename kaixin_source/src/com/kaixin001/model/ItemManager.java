package com.kaixin001.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ItemManager<T>
{
  public ArrayList<T> items = new ArrayList();
  ReentrantLock lock = new ReentrantLock();

  public void addItem(T paramT)
  {
    try
    {
      this.lock.lock();
      this.items.add(paramT);
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public void clear()
  {
    try
    {
      this.lock.lock();
      this.items.clear();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public int getSize()
  {
    try
    {
      this.lock.lock();
      int i = this.items.size();
      return i;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public T removeItem(boolean paramBoolean)
  {
    try
    {
      this.lock.lock();
      int i = this.items.size();
      if (i == 0)
        return null;
      if (paramBoolean)
      {
        Object localObject3 = this.items.remove(0);
        return localObject3;
      }
      Object localObject2 = this.items.remove(-1 + this.items.size());
      return localObject2;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ItemManager
 * JD-Core Version:    0.6.0
 */
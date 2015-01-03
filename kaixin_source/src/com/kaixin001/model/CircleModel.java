package com.kaixin001.model;

import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.activity.MessageHandlerHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class CircleModel extends KXModel
{
  private static CircleModel instance;
  public ReentrantLock circleListLock = new ReentrantLock();
  private ArrayList<CircleItem> circles = new ArrayList();
  public int total = 0;
  public String uid = "0";

  public static CircleModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleModel();
      CircleModel localCircleModel = instance;
      return localCircleModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void sendCircleListUpdateMessage()
  {
    Message localMessage = Message.obtain();
    localMessage.what = 90006;
    MessageHandlerHolder.getInstance().fireMessage(localMessage);
  }

  public void addCircleItem(String paramString1, String paramString2, int paramInt)
  {
    try
    {
      this.circleListLock.lock();
      if (searchCircleItem(paramString1) == null)
      {
        CircleItem localCircleItem = new CircleItem();
        localCircleItem.gid = paramString1;
        localCircleItem.gname = paramString2;
        localCircleItem.type = paramInt;
        this.circles.add(localCircleItem);
      }
      return;
    }
    finally
    {
      this.circleListLock.unlock();
    }
    throw localObject;
  }

  public void clear()
  {
    this.uid = "0";
    this.total = 0;
    this.circles.clear();
  }

  public void clearCircles()
  {
    try
    {
      this.circleListLock.lock();
      this.total = 0;
      this.circles.clear();
      return;
    }
    finally
    {
      this.circleListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<CircleItem> getCircles()
  {
    try
    {
      this.circleListLock.lock();
      ArrayList localArrayList = this.circles;
      return localArrayList;
    }
    finally
    {
      this.circleListLock.unlock();
    }
    throw localObject;
  }

  public void removeCircleItem(String paramString)
  {
    try
    {
      this.circleListLock.lock();
      Iterator localIterator = this.circles.iterator();
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
          return;
        CircleItem localCircleItem = (CircleItem)localIterator.next();
        if (!paramString.equalsIgnoreCase(localCircleItem.gid))
          continue;
        this.circles.remove(localCircleItem);
      }
    }
    finally
    {
      this.circleListLock.unlock();
    }
    throw localObject;
  }

  public CircleItem searchCircleItem(String paramString)
  {
    Iterator localIterator;
    if (!TextUtils.isEmpty(paramString))
      localIterator = this.circles.iterator();
    CircleItem localCircleItem;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localCircleItem = (CircleItem)localIterator.next();
    }
    while (!paramString.equalsIgnoreCase(localCircleItem.gid));
    return localCircleItem;
  }

  public void setCircles(int paramInt1, ArrayList<CircleItem> paramArrayList, int paramInt2)
  {
    try
    {
      this.circleListLock.lock();
      if (paramInt2 == 0)
        this.circles.clear();
      if (paramArrayList != null)
        this.circles.addAll(paramArrayList);
      this.total = paramInt1;
      sendCircleListUpdateMessage();
      return;
    }
    finally
    {
      this.circleListLock.unlock();
    }
    throw localObject;
  }

  public static class CircleItem
  {
    public String gid;
    public String gname;
    public int hasnews;
    public String mtime;
    public int type;

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      CircleItem localCircleItem;
      do
        while (true)
        {
          return true;
          if (paramObject == null)
            return false;
          if (getClass() != paramObject.getClass())
            return false;
          localCircleItem = (CircleItem)paramObject;
          if (this.gid != null)
            break;
          if (localCircleItem.gid != null)
            return false;
        }
      while (this.gid.equals(localCircleItem.gid));
      return false;
    }

    public int hashCode()
    {
      if (this.gid == null);
      for (int i = 0; ; i = this.gid.hashCode())
        return i + 31;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleModel
 * JD-Core Version:    0.6.0
 */
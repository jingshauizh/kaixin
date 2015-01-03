package com.kaixin001.model;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class CirclePhotoModel extends KXModel
{
  private static CirclePhotoModel instance;
  public int currentIndex;
  public String gid;
  private ArrayList<CirclePhoto> photoList = new ArrayList();
  public ReentrantLock photoListLock = new ReentrantLock();
  public int total = 0;

  public static CirclePhotoModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CirclePhotoModel();
      CirclePhotoModel localCirclePhotoModel = instance;
      return localCirclePhotoModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private int getPhotoIndexByPid(String paramString)
  {
    int i = this.photoList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        j = -1;
      do
        return j;
      while (paramString.equals(((CirclePhoto)this.photoList.get(j)).pid));
    }
  }

  public void addPhoto(CirclePhoto paramCirclePhoto, String paramString, boolean paramBoolean)
  {
    int j;
    try
    {
      this.photoListLock.lock();
      if ((this.gid == null) || (paramCirclePhoto.gid.equals(this.gid)))
      {
        this.photoList.clear();
        this.gid = paramCirclePhoto.gid;
      }
      int i = getPhotoIndexByPid(paramCirclePhoto.pid);
      if (i != -1)
      {
        this.currentIndex = i;
        this.photoList.add(i, paramCirclePhoto);
        this.photoList.remove(i + 1);
        return;
      }
      j = getPhotoIndexByPid(paramString);
      if (j == -1)
      {
        this.currentIndex = 0;
        this.photoList.add(paramCirclePhoto);
      }
      while (true)
      {
        return;
        if (!paramBoolean)
          break;
        this.currentIndex = j;
        this.photoList.add(j, paramCirclePhoto);
      }
    }
    finally
    {
      this.photoListLock.unlock();
    }
    int k;
    label173: ArrayList localArrayList;
    int m;
    if (j == 0)
    {
      k = 0;
      this.currentIndex = k;
      localArrayList = this.photoList;
      m = 0;
      if (j != 0)
        break label213;
    }
    while (true)
    {
      localArrayList.add(m, paramCirclePhoto);
      break;
      k = j - 1;
      break label173;
      label213: m = j - 1;
    }
  }

  public void clear()
  {
    this.gid = null;
    this.total = 0;
    this.photoList.clear();
  }

  public void clearPhotoes()
  {
    try
    {
      this.photoListLock.lock();
      this.total = 0;
      this.photoList.clear();
      return;
    }
    finally
    {
      this.photoListLock.unlock();
    }
    throw localObject;
  }

  public CirclePhoto getPhotoByPid(String paramString)
  {
    int i = this.photoList.size();
    for (int j = 0; ; j++)
    {
      CirclePhoto localCirclePhoto;
      if (j >= i)
        localCirclePhoto = null;
      do
      {
        return localCirclePhoto;
        localCirclePhoto = (CirclePhoto)this.photoList.get(j);
      }
      while (paramString.equals(localCirclePhoto.pid));
    }
  }

  public ArrayList<CirclePhoto> getPhotoes()
  {
    try
    {
      this.photoListLock.lock();
      ArrayList localArrayList = this.photoList;
      return localArrayList;
    }
    finally
    {
      this.photoListLock.unlock();
    }
    throw localObject;
  }

  public CirclePhoto searchCirclePhotoItem(String paramString)
  {
    Iterator localIterator;
    if (!TextUtils.isEmpty(paramString))
      localIterator = this.photoList.iterator();
    CirclePhoto localCirclePhoto;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localCirclePhoto = (CirclePhoto)localIterator.next();
    }
    while (!paramString.equalsIgnoreCase(localCirclePhoto.gid));
    return localCirclePhoto;
  }

  public static class CirclePhoto
  {
    public String aid;
    public String ctime;
    public String gid;
    public String largePhoto;
    public String pid;
    public String smallPhoto;
    public String tid;
    public String title;
    public String uid;
    public String username;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CirclePhotoModel
 * JD-Core Version:    0.6.0
 */
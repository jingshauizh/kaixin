package com.kaixin001.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CircleNewsModel extends KXModel
{
  private static CircleNewsModel instance;
  private ArrayList<CircleNewsItem> circleNewsList = new ArrayList();
  public ReentrantLock circleNewsListLock = new ReentrantLock();
  public int total = 0;
  public String uid = "0";

  public static CircleNewsModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleNewsModel();
      CircleNewsModel localCircleNewsModel = instance;
      return localCircleNewsModel;
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
    this.circleNewsList.clear();
  }

  public void clearCircleNews()
  {
    try
    {
      this.circleNewsListLock.lock();
      this.total = 0;
      this.circleNewsList.clear();
      return;
    }
    finally
    {
      this.circleNewsListLock.unlock();
    }
    throw localObject;
  }

  public ArrayList<CircleNewsItem> getCircleNews()
  {
    try
    {
      this.circleNewsListLock.lock();
      ArrayList localArrayList = this.circleNewsList;
      return localArrayList;
    }
    finally
    {
      this.circleNewsListLock.unlock();
    }
    throw localObject;
  }

  public void setCircleNewsList(int paramInt1, ArrayList<CircleNewsItem> paramArrayList, int paramInt2)
  {
    try
    {
      this.circleNewsListLock.lock();
      if (paramInt2 == 0)
        this.circleNewsList.clear();
      if (paramArrayList != null)
        this.circleNewsList.addAll(paramArrayList);
      this.total = paramInt1;
      return;
    }
    finally
    {
      this.circleNewsListLock.unlock();
    }
    throw localObject;
  }

  public static class CircleNewsItem
  {
    public static final int TYPE_CREATE_CIRCLE = 2;
    public static final int TYPE_INVITE_CIRCLE_MEMBER = 1;
    public static final int TYPE_TALK;
    public List<CircleNewsModel.CircleNewsTalkContent> content;
    public long ctime;
    public String gid;
    public String lastTid;
    public String location;
    public CircleNewsModel.KXPicture pic;
    public int rnum;
    public String source;
    public String stid;
    public KaixinUser suser;
    public int talkType;
    public String tid;
    public List<KaixinUser> users;
  }

  public static class CircleNewsTalkContent
  {
    public static final int TYPE_AT = 1;
    public static final int TYPE_MUSIC = 4;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_URL = 2;
    public static final int TYPE_VIDEO = 3;
    public String title;
    public String txt;
    public int type;
    public String uid;
    public String uname;
    public String urlImg;
    public String urlSwf;
  }

  public static class KXPicture
  {
    public String aid;
    public String largePic;
    public String pid;
    public String smallPic;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleNewsModel
 * JD-Core Version:    0.6.0
 */
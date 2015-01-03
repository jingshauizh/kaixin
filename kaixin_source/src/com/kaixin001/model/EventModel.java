package com.kaixin001.model;

import com.kaixin001.fragment.NewsFragment.IEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class EventModel extends KXModel
{
  public static final int EVENT_TYPE_PHOTO = 1;
  public static final int EVENT_TYPE_RECORD = 2;
  public static final int EVENT_TYPE_UNKNOWN;
  private static NewsFragment.IEvent event;
  private static boolean isLoadDone;
  private static EventModel sInstance = null;
  public ArrayList<EventData> mList = new ArrayList();

  static
  {
    isLoadDone = false;
  }

  public static EventModel getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new EventModel();
      EventModel localEventModel = sInstance;
      return localEventModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static boolean isLoadDone()
  {
    return isLoadDone;
  }

  public static void setLoadDone(boolean paramBoolean)
  {
    isLoadDone = paramBoolean;
    if (event != null)
      event.initEvent();
  }

  public void clear()
  {
    this.mList.clear();
  }

  public ArrayList<EventData> getList()
  {
    return this.mList;
  }

  public void setEvent(NewsFragment.IEvent paramIEvent)
  {
    event = paramIEvent;
  }

  public static class EventData
    implements Serializable
  {
    private static final long serialVersionUID = 1006L;
    private String mActionTitle = null;
    private boolean mAlwaysShow = false;
    private String mCtime = null;
    private int mEventType = 0;
    private String mId = null;
    private String mOptionTitle = null;
    private String mTitle = null;
    private String mUrl = null;
    private String mWord = null;

    public void clear()
    {
      this.mTitle = null;
      this.mUrl = null;
    }

    public String getActionTitle()
    {
      return this.mActionTitle;
    }

    public String getCTime()
    {
      return this.mCtime;
    }

    public int getEventType()
    {
      return this.mEventType;
    }

    public String getId()
    {
      return this.mId;
    }

    public String getKeyWord()
    {
      return this.mWord;
    }

    public String getOptionTitle()
    {
      return this.mOptionTitle;
    }

    public String getTitle()
    {
      return this.mTitle;
    }

    public String getUrl()
    {
      return this.mUrl;
    }

    public boolean isAlwaysShow()
    {
      return this.mAlwaysShow;
    }

    public void setActionTitle(String paramString)
    {
      this.mActionTitle = paramString;
    }

    public void setAlwaysShow(boolean paramBoolean)
    {
      this.mAlwaysShow = paramBoolean;
    }

    public void setCTime(String paramString)
    {
      this.mCtime = paramString;
    }

    public void setEventType(int paramInt)
    {
      this.mEventType = paramInt;
    }

    public void setId(String paramString)
    {
      this.mId = paramString;
    }

    public void setKeyWord(String paramString)
    {
      this.mWord = paramString;
    }

    public void setOptionTitle(String paramString)
    {
      this.mOptionTitle = paramString;
    }

    public void setTitle(String paramString)
    {
      this.mTitle = paramString;
    }

    public void setUrl(String paramString)
    {
      this.mUrl = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.EventModel
 * JD-Core Version:    0.6.0
 */
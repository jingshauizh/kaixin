package com.kaixin001.model;

import com.kaixin001.item.NewsInfo;
import java.util.ArrayList;

public class TopicModel extends KXModel
{
  private static TopicModel instance;
  private static final long serialVersionUID = 2214282226833021131L;
  public String detail = null;
  public String title = null;
  private ArrayList<NewsInfo> topicList = new ArrayList();
  public int total = 0;

  public static TopicModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new TopicModel();
      TopicModel localTopicModel = instance;
      return localTopicModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.topicList.clear();
  }

  public int getNum()
  {
    return this.topicList.size();
  }

  public ArrayList<NewsInfo> getTopicList()
  {
    return this.topicList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.TopicModel
 * JD-Core Version:    0.6.0
 */
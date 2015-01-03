package com.kaixin001.model;

import com.kaixin001.item.GiftNewsItem;
import java.util.ArrayList;

public class GiftNewsModel extends KXModel
{
  private static GiftNewsModel instance;
  private static final long serialVersionUID = 7110804609109427939L;
  public ArrayList<GiftNewsItem> giftNewsList = new ArrayList();
  public boolean isHasMore = true;
  public int total = 0;

  public static GiftNewsModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GiftNewsModel();
      GiftNewsModel localGiftNewsModel = instance;
      return localGiftNewsModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.giftNewsList.clear();
  }

  public ArrayList<GiftNewsItem> getGiftNewsList()
  {
    return this.giftNewsList;
  }

  public int getTotalNum()
  {
    return this.giftNewsList.size();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.GiftNewsModel
 * JD-Core Version:    0.6.0
 */
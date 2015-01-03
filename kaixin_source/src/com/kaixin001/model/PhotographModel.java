package com.kaixin001.model;

import com.kaixin001.item.PhotoGraphItem;

public class PhotographModel extends KXModel
{
  private static PhotographModel instance;
  public int ctime = 0;
  public PhotoGraphItem[] item = null;
  public int notice = 0;
  public int uid = 0;

  public static PhotographModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new PhotographModel();
      PhotographModel localPhotographModel = instance;
      return localPhotographModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.item = null;
    this.uid = 0;
    this.ctime = 0;
    this.notice = 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.PhotographModel
 * JD-Core Version:    0.6.0
 */
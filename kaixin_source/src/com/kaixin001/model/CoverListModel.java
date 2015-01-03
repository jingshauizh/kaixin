package com.kaixin001.model;

import com.kaixin001.item.CoverItem;
import java.util.ArrayList;

public class CoverListModel extends KXModel
{
  private static CoverListModel instance;
  private String ctime = "";
  private ArrayList<CoverItem> mCoverList = new ArrayList();

  public static CoverListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CoverListModel();
      CoverListModel localCoverListModel = instance;
      return localCoverListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addCover(CoverItem paramCoverItem)
  {
    this.mCoverList.add(paramCoverItem);
  }

  public void clear()
  {
    this.mCoverList.clear();
  }

  public ArrayList<CoverItem> getCoverList()
  {
    return this.mCoverList;
  }

  public String getCtime()
  {
    return this.ctime;
  }

  public void setCtime(String paramString)
  {
    this.ctime = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CoverListModel
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.model;

import com.kaixin001.item.VoteItem;
import java.util.ArrayList;

public class VoteListModel extends KXModel
{
  private static volatile VoteListModel sInstance = null;
  private ArrayList<VoteItem> mVoteList = new ArrayList();

  private static void clearInstance()
  {
    sInstance = null;
  }

  public static VoteListModel getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new VoteListModel();
      VoteListModel localVoteListModel = sInstance;
      return localVoteListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    if (this.mVoteList != null)
      this.mVoteList.clear();
    clearInstance();
  }

  public ArrayList<VoteItem> getVoteList()
  {
    return this.mVoteList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.VoteListModel
 * JD-Core Version:    0.6.0
 */
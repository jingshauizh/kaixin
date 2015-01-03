package com.kaixin001.model;

import com.kaixin001.item.FriendInfo;
import java.util.ArrayList;

public class NewsDetailVisitorsModel extends KXModel
{
  private ArrayList<FriendInfo> mVisitorList = new ArrayList();

  public void addFriend(FriendInfo paramFriendInfo)
  {
    this.mVisitorList.add(paramFriendInfo);
  }

  public void clear()
  {
    this.mVisitorList.clear();
  }

  public ArrayList<FriendInfo> getVisitorList()
  {
    return this.mVisitorList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.NewsDetailVisitorsModel
 * JD-Core Version:    0.6.0
 */
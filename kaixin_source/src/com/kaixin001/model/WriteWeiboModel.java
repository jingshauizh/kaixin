package com.kaixin001.model;

import java.util.ArrayList;
import java.util.HashMap;

public class WriteWeiboModel extends KXModel
{
  private static WriteWeiboModel instance;
  private HashMap<String, ArrayList<String>> userPicPath = new HashMap();

  public static WriteWeiboModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new WriteWeiboModel();
      WriteWeiboModel localWriteWeiboModel = instance;
      return localWriteWeiboModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addPic(String paramString)
  {
    if (this.userPicPath.containsKey(User.getInstance().getUID()))
    {
      ((ArrayList)this.userPicPath.get(User.getInstance().getUID())).add(paramString);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString);
    this.userPicPath.put(User.getInstance().getUID(), localArrayList);
  }

  public void clear()
  {
    if (this.userPicPath.containsKey(User.getInstance().getUID()))
      ((ArrayList)this.userPicPath.get(User.getInstance().getUID())).clear();
  }

  public String getLastPicPath()
  {
    if (this.userPicPath.containsKey(User.getInstance().getUID()));
    for (ArrayList localArrayList = (ArrayList)this.userPicPath.get(User.getInstance().getUID()); localArrayList.size() > 0; localArrayList = new ArrayList())
      return (String)localArrayList.get(-1 + localArrayList.size());
    return null;
  }

  public ArrayList<String> getPicPath()
  {
    if (this.userPicPath.containsKey(User.getInstance().getUID()))
      return (ArrayList)this.userPicPath.get(User.getInstance().getUID());
    return new ArrayList();
  }

  public void removePic(String paramString)
  {
    if (this.userPicPath.containsKey(User.getInstance().getUID()))
      ((ArrayList)this.userPicPath.get(User.getInstance().getUID())).remove(paramString);
  }

  public void setPicPath(ArrayList<String> paramArrayList)
  {
    this.userPicPath.put(User.getInstance().getUID(), paramArrayList);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.WriteWeiboModel
 * JD-Core Version:    0.6.0
 */
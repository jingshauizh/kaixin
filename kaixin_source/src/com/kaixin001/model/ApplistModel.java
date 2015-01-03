package com.kaixin001.model;

import java.util.ArrayList;

public class ApplistModel extends KXModel
{
  private static ApplistModel instance;
  ArrayList<AppItem> applist = new ArrayList();
  private int getDataMode = -1;
  private String oauthToken = "";
  private String oauthTokenOauth2 = "";
  private String oauthTokenOauth2Expire = "";
  private String oauthTokenOauth2Refresh = "";
  private String oauthTokenSecret = "";
  ArrayList<AppItem> pushList = new ArrayList();
  private int ret = 0;
  ArrayList<AppItem> searchList = new ArrayList();
  ArrayList<AppItem> thirdApplist = new ArrayList();
  ArrayList<AppItem> thirdGamelist = new ArrayList();
  private int uid = 0;

  public static ApplistModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ApplistModel();
      ApplistModel localApplistModel = instance;
      return localApplistModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.ret = 0;
    this.uid = 0;
    this.applist.clear();
    this.thirdApplist.clear();
    this.thirdGamelist.clear();
    this.searchList.clear();
    this.pushList.clear();
  }

  public ArrayList<AppItem> getApplist()
  {
    return this.applist;
  }

  public ArrayList<AppItem> getGamelist()
  {
    return this.thirdGamelist;
  }

  public int getGetDataMode()
  {
    return this.getDataMode;
  }

  public String getOauthToken()
  {
    return this.oauthToken;
  }

  public String getOauthTokenOauth2()
  {
    return this.oauthTokenOauth2;
  }

  public String getOauthTokenOauth2Expire()
  {
    return this.oauthTokenOauth2Expire;
  }

  public String getOauthTokenOauth2Refresh()
  {
    return this.oauthTokenOauth2Refresh;
  }

  public String getOauthTokenSecret()
  {
    return this.oauthTokenSecret;
  }

  public ArrayList<AppItem> getPushlist()
  {
    return this.pushList;
  }

  public int getRet()
  {
    return this.ret;
  }

  public ArrayList<AppItem> getSearchlist()
  {
    return this.searchList;
  }

  public ArrayList<AppItem> getThirdApplist()
  {
    return this.thirdApplist;
  }

  public int getUid()
  {
    return this.uid;
  }

  public void setApplist(ArrayList<AppItem> paramArrayList)
  {
    this.applist = paramArrayList;
  }

  public void setGetDataMode(int paramInt)
  {
    this.getDataMode = paramInt;
  }

  public void setOauthToken(String paramString)
  {
    this.oauthToken = paramString;
  }

  public void setOauthTokenOauth2(String paramString)
  {
    this.oauthTokenOauth2 = paramString;
  }

  public void setOauthTokenOauth2Expire(String paramString)
  {
    this.oauthTokenOauth2Expire = paramString;
  }

  public void setOauthTokenOauth2Refresh(String paramString)
  {
    this.oauthTokenOauth2Refresh = paramString;
  }

  public void setOauthTokenSecret(String paramString)
  {
    this.oauthTokenSecret = paramString;
  }

  public void setRet(int paramInt)
  {
    this.ret = paramInt;
  }

  public void setUid(int paramInt)
  {
    this.uid = paramInt;
  }

  public static class AppItem
  {
    private String appEntry = "";
    private String appId = "";
    private String downloadUrl = "";
    private String logo = "";
    private String name = "";
    private String pakageName = "";
    private String url = "";
    private String version = "";

    public String getAppEntry()
    {
      return this.appEntry;
    }

    public String getAppId()
    {
      return this.appId;
    }

    public String getDownloadUrl()
    {
      return this.downloadUrl;
    }

    public String getLogo()
    {
      return this.logo;
    }

    public String getName()
    {
      return this.name;
    }

    public String getPakageName()
    {
      return this.pakageName;
    }

    public String getUrl()
    {
      return this.url;
    }

    public String getVersion()
    {
      return this.version;
    }

    public void setAppEntry(String paramString)
    {
      this.appEntry = paramString;
    }

    public void setAppId(String paramString)
    {
      this.appId = paramString;
    }

    public void setDownloadUrl(String paramString)
    {
      this.downloadUrl = paramString;
    }

    public void setLogo(String paramString)
    {
      this.logo = paramString;
    }

    public void setName(String paramString)
    {
      this.name = paramString;
    }

    public void setPakageName(String paramString)
    {
      this.pakageName = paramString;
    }

    public void setUrl(String paramString)
    {
      this.url = paramString;
    }

    public void setVersion(String paramString)
    {
      this.version = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ApplistModel
 * JD-Core Version:    0.6.0
 */
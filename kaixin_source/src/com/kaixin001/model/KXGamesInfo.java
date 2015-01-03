package com.kaixin001.model;

import java.util.ArrayList;
import java.util.Iterator;

public class KXGamesInfo
{
  private static KXGamesInfo instance;
  private ArrayList<KXGameModel> appsInfo;
  private ArrayList<KXGameModel> clickInfo;
  private ArrayList<KXGameModel> gamesInfo;
  private ArrayList<RecommendGameModel> recommendGameList;

  public static KXGamesInfo getInstance()
  {
    if (instance == null)
      instance = new KXGamesInfo();
    return instance;
  }

  public void addClickItem(KXGameModel paramKXGameModel)
  {
    if (this.clickInfo == null)
    {
      this.clickInfo = new ArrayList();
      this.clickInfo.add(paramKXGameModel);
    }
    label92: 
    while (true)
    {
      return;
      Iterator localIterator = this.clickInfo.iterator();
      boolean bool = localIterator.hasNext();
      int i = 0;
      if (!bool);
      while (true)
      {
        if (i != 0)
          break label92;
        this.clickInfo.add(0, paramKXGameModel);
        return;
        KXGameModel localKXGameModel = (KXGameModel)localIterator.next();
        if (paramKXGameModel.appid != localKXGameModel.appid)
          break;
        i = 1;
      }
    }
  }

  public void addKXAppItem(KXGameModel paramKXGameModel)
  {
    if (this.appsInfo == null)
      this.appsInfo = new ArrayList();
    this.appsInfo.add(paramKXGameModel);
  }

  public void addKXGameItem(KXGameModel paramKXGameModel)
  {
    if (this.gamesInfo == null)
      this.gamesInfo = new ArrayList();
    this.gamesInfo.add(paramKXGameModel);
  }

  public void addRecommendItem(RecommendGameModel paramRecommendGameModel)
  {
    if (this.recommendGameList == null)
      this.recommendGameList = new ArrayList();
    this.recommendGameList.add(paramRecommendGameModel);
  }

  public ArrayList<KXGameModel> getClickInfo()
  {
    if (this.clickInfo == null)
      this.clickInfo = new ArrayList();
    return this.clickInfo;
  }

  public ArrayList<KXGameModel> getKXAppList()
  {
    if (this.appsInfo == null)
      this.appsInfo = new ArrayList();
    return this.appsInfo;
  }

  public ArrayList<KXGameModel> getKXGameList()
  {
    if (this.gamesInfo == null)
      this.gamesInfo = new ArrayList();
    return this.gamesInfo;
  }

  public ArrayList<RecommendGameModel> getRecommendGameList()
  {
    if (this.recommendGameList == null)
      this.recommendGameList = new ArrayList();
    return this.recommendGameList;
  }

  public void removeAllKXAppItem()
  {
    this.appsInfo.clear();
  }

  public void removeAllKXGameItem()
  {
    this.gamesInfo.clear();
  }

  public void removeAllRecommendItem()
  {
    this.recommendGameList.clear();
  }

  public void removeKxGameItem(KXGameModel paramKXGameModel)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.gamesInfo.size())
        return;
      KXGameModel localKXGameModel = (KXGameModel)this.gamesInfo.get(i);
      if (!localKXGameModel.appid.equals(paramKXGameModel.appid))
        continue;
      this.gamesInfo.remove(localKXGameModel);
    }
  }

  public class KXGameModel
  {
    public String appid = "";
    public String des = "";
    public String dev = "";
    public String download = "";
    public String downloadurl = "";
    public String hotend = "";
    public String hotstart = "";
    public String intro = "";
    public String isnew = "";
    public String key = "";
    public String largelogo = "";
    public String link = "";
    public String logo = "";
    public String name = "";
    public String orderid = "";
    public String package_name = "";
    public String packagename = "";
    public String shortDes = "";
    public String size = "";
    public String smalllogo = "";
    public String status = "";
    public String support = "";
    public String time = "";
    public String type = "";
    public String v = "";
    public String wapurl = "";
    public String weixin = "";

    public KXGameModel()
    {
    }
  }

  public class RecommendGameModel
  {
    public String appid = "";
    public String click_type = "";
    public String ctype = "";
    public String des = "";
    public String detailurl = "";
    public String dev = "";
    public String download = "";
    public String hotend = "";
    public String hotstart = "";
    public String intro = "";
    public String isnew = "";
    public String kaixin = "";
    public String key = "";
    public String largelogo = "";
    public String link = "";
    public String name = "";
    public String online = "";
    public String order = "";
    public String package_name = "";
    public String shortDes = "";
    public String size = "";
    public String smalllogo = "";
    public String support = "";
    public String time = "";
    public String type = "";
    public String v = "";
    public String weixin = "";

    public RecommendGameModel()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.KXGamesInfo
 * JD-Core Version:    0.6.0
 */
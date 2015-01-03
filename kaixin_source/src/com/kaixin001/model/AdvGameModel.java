package com.kaixin001.model;

import com.kaixin001.item.AdvGameItem;
import java.util.ArrayList;

public class AdvGameModel
{
  private static final String TAG = "AdvGameModel";
  private static AdvGameModel instance;
  private ArrayList<AdvGameItem> appBannerItems = new ArrayList();
  private int bannerSleepTime = 3;
  public int ctime = 0;
  private ArrayList<AdvGameItem> gameBannerItems = new ArrayList();
  public int notice = 0;
  public int uid = 0;

  public static AdvGameModel getInstance()
  {
    if (instance == null)
      instance = new AdvGameModel();
    return instance;
  }

  public void addAppBannerItem(AdvGameItem paramAdvGameItem)
  {
    this.appBannerItems.add(paramAdvGameItem);
  }

  public void addGameBannerItem(AdvGameItem paramAdvGameItem)
  {
    this.gameBannerItems.add(paramAdvGameItem);
  }

  public void clear()
  {
    this.gameBannerItems.clear();
    this.appBannerItems.clear();
    this.uid = 0;
    this.ctime = 0;
    this.notice = 0;
  }

  public void clearAppBanner()
  {
    this.appBannerItems.clear();
  }

  public void clearGameBanner()
  {
    this.gameBannerItems.clear();
  }

  public ArrayList<AdvGameItem> getAppBannerItems()
  {
    return this.appBannerItems;
  }

  public int getCtime()
  {
    return this.ctime;
  }

  public ArrayList<AdvGameItem> getGameBannerItems()
  {
    return this.gameBannerItems;
  }

  public int getNotice()
  {
    return this.notice;
  }

  public int getSleepTime()
  {
    return this.bannerSleepTime;
  }

  public int getUid()
  {
    return this.uid;
  }

  public void setAppBannerItems(ArrayList<AdvGameItem> paramArrayList)
  {
    this.appBannerItems = paramArrayList;
  }

  public void setCtime(int paramInt)
  {
    this.ctime = paramInt;
  }

  public void setGameBannerItems(ArrayList<AdvGameItem> paramArrayList)
  {
    this.gameBannerItems = paramArrayList;
  }

  public void setNotice(int paramInt)
  {
    this.notice = paramInt;
  }

  public void setSleepTime(int paramInt)
  {
    this.bannerSleepTime = paramInt;
  }

  public void setUid(int paramInt)
  {
    this.uid = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.AdvGameModel
 * JD-Core Version:    0.6.0
 */
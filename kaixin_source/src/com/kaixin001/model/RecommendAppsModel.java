package com.kaixin001.model;

public class RecommendAppsModel
{
  private static RecommendAppsModel instance;
  private int mAllNewAppsCount = 0;
  private boolean mAppsHasNew = false;
  private int mNewAppsCount = 0;
  private int mNewGamesCount = 0;

  public static RecommendAppsModel getInstance()
  {
    if (instance == null)
      instance = new RecommendAppsModel();
    return instance;
  }

  public int getAllNewAppsCount()
  {
    return this.mAllNewAppsCount;
  }

  public int getNewAppsCount()
  {
    return this.mNewAppsCount;
  }

  public int getNewGamesCount()
  {
    return this.mNewGamesCount;
  }

  public boolean isAppsHasNew()
  {
    return this.mAppsHasNew;
  }

  public void setAllNewAppsCount(int paramInt)
  {
    this.mAllNewAppsCount = paramInt;
  }

  public void setAppsHasNew(boolean paramBoolean)
  {
    this.mAppsHasNew = paramBoolean;
  }

  public void setNewAppsCount(int paramInt)
  {
    this.mNewAppsCount = paramInt;
  }

  public void setNewGamesCount(int paramInt)
  {
    this.mNewGamesCount = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RecommendAppsModel
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.model;

public class GameUserInfo
{
  private static GameUserInfo info = new GameUserInfo();
  private String name;
  private String score;
  private String topNum;

  public static GameUserInfo getInstance()
  {
    monitorenter;
    try
    {
      GameUserInfo localGameUserInfo = info;
      monitorexit;
      return localGameUserInfo;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public String getName()
  {
    return this.name;
  }

  public String getScore()
  {
    return this.score;
  }

  public String getTopNum()
  {
    return this.topNum;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setScore(String paramString)
  {
    this.score = paramString;
  }

  public void setTopNum(String paramString)
  {
    this.topNum = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.GameUserInfo
 * JD-Core Version:    0.6.0
 */
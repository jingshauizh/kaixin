package com.kaixin001.model;

import java.util.List;

public class UserInfo
{
  private static UserInfo info = new UserInfo();
  private String curUserTopNum;
  private List<String> names;

  public static UserInfo getInstance()
  {
    return info;
  }

  public String getCurUserTopNum()
  {
    return this.curUserTopNum;
  }

  public List<String> getNames()
  {
    return this.names;
  }

  public void setCurUserTopNum(String paramString)
  {
    this.curUserTopNum = paramString;
  }

  public void setNames(List<String> paramList)
  {
    this.names = paramList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.UserInfo
 * JD-Core Version:    0.6.0
 */
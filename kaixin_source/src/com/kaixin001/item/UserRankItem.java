package com.kaixin001.item;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRankItem
{
  private static UserRankItem instance;
  private int rank;
  private HashMap<String, ArrayList<UserRankModel>> uRankListMap = new HashMap();

  public static UserRankItem getInstance()
  {
    if (instance == null)
      instance = new UserRankItem();
    return instance;
  }

  public void addRankList(String paramString, ArrayList<UserRankModel> paramArrayList)
  {
    this.uRankListMap.put(paramString, paramArrayList);
  }

  public ArrayList<UserRankModel> getRankList(String paramString)
  {
    return (ArrayList)this.uRankListMap.get(paramString);
  }

  public int getUserRank()
  {
    return this.rank;
  }

  public void setUserRank(int paramInt)
  {
    this.rank = paramInt;
  }

  public static enum BackType
  {
    static
    {
      BackType[] arrayOfBackType = new BackType[3];
      arrayOfBackType[0] = BOTTOM_ROUND;
      arrayOfBackType[1] = MIDDLE_LINE;
      arrayOfBackType[2] = OMIT_ROUND;
      ENUM$VALUES = arrayOfBackType;
    }
  }

  public class UserRankModel
  {
    public UserRankItem.BackType backType;
    public String birthday;
    public String city;
    public String exp;
    public String gender;
    public String hidebirthyear;
    public String large;
    public String level;
    public String logo;
    public String logo50;
    public String logo90;
    public String middle;
    public String name;
    public String online;
    public int rank;
    public String small;
    public String star;
    public String state;
    public String title;
    public String uid;

    public UserRankModel()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.UserRankItem
 * JD-Core Version:    0.6.0
 */
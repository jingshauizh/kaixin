package com.kaixin001.model;

import java.util.ArrayList;

public class HomePeopleInfoModel extends KXModel
{
  private static HomePeopleInfoModel instance = null;
  public String mLastUid = "";
  private ArrayList<PeopleInfo> mPeopleInfoList = new ArrayList();
  public int mTotalNum = -1;

  public static HomePeopleInfoModel getInstance()
  {
    
    
      if (instance == null)
        instance = new HomePeopleInfoModel();
      HomePeopleInfoModel localHomePeopleInfoModel = instance;
      return localHomePeopleInfoModel;
   
    
  }

  public void addPeopleInfo(String paramString1, String paramString2, String paramString3)
  {
    PeopleInfo localPeopleInfo = new PeopleInfo(paramString1, paramString2, paramString3);
    this.mPeopleInfoList.add(localPeopleInfo);
  }

  public void clear()
  {
    this.mPeopleInfoList.clear();
    this.mTotalNum = -1;
    this.mLastUid = "";
  }

  public ArrayList<PeopleInfo> getPeopleInfoList()
  {
    return this.mPeopleInfoList;
  }

  public int getTotalNum()
  {
    return this.mTotalNum;
  }

  public static class PeopleInfo
  {
    public String mAvatar = null;
    public String mCity = null;
    public String mConpany = null;
    public String mEducation = null;
    public int mIsFriends = -1;
    public String mName = null;
    public String mUid = null;

    public PeopleInfo()
    {
    }

    public PeopleInfo(String paramString1, String paramString2, String paramString3)
    {
      this.mUid = paramString1;
      this.mName = paramString2;
      this.mAvatar = paramString3;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.HomePeopleInfoModel
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.model;

public class AddFriendModel extends KXModel
{
  public static final int SEARCH_RESULT_NOT_FIND = 2000;
  public static final int SEARCH_RESULT_PRIVACY_LIMIT = 4000;
  public static final int SEARCH_RESULT_SUCCESS = 3000;
  public static final int SEARCH_RESULT_TOO_FRENQUENT = 1000;
  private static AddFriendModel instance = null;
  public int mGender = 0;
  public int mHasLogo = 0;
  public String mLogo = "";
  public String mRealName = "";
  public int mResult_code = 0;
  public String mUid = "";

  public static AddFriendModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AddFriendModel();
      AddFriendModel localAddFriendModel = instance;
      return localAddFriendModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mUid = "";
    this.mRealName = "";
    this.mHasLogo = 0;
    this.mGender = 0;
    this.mLogo = "";
    this.mResult_code = 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.AddFriendModel
 * JD-Core Version:    0.6.0
 */
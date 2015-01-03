package com.kaixin001.model;

public class DiaryModel extends KXModel
{
  private static DiaryModel instance = null;
  private int mCNum = 0;
  private String mContent = "";
  private String mDid = "";
  private int mErrorNum = 0;
  private String mFuid = "";
  private int mRepFlag = 0;

  public static DiaryModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new DiaryModel();
      DiaryModel localDiaryModel = instance;
      return localDiaryModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mContent = "";
    this.mDid = "";
    this.mRepFlag = 0;
    this.mFuid = "";
    this.mCNum = 0;
  }

  public int getCNum()
  {
    return this.mCNum;
  }

  public String getDiaryContent()
  {
    return this.mContent;
  }

  public String getDiaryFuid()
  {
    return this.mFuid;
  }

  public String getDiaryId()
  {
    return this.mDid;
  }

  public int getDiaryRepFlag()
  {
    return this.mRepFlag;
  }

  public int getErrorNum()
  {
    return this.mErrorNum;
  }

  public void setCNum(int paramInt)
  {
    this.mCNum = paramInt;
  }

  public void setDiaryContent(String paramString)
  {
    if (paramString != null)
      this.mContent = paramString;
  }

  public void setDiaryFuid(String paramString)
  {
    if (paramString != null)
      this.mFuid = paramString;
  }

  public void setDiaryId(String paramString)
  {
    if (paramString != null)
      this.mDid = paramString;
  }

  public void setDiaryRepFlag(int paramInt)
  {
    this.mRepFlag = paramInt;
  }

  public void setErrorNum(int paramInt)
  {
    this.mErrorNum = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.DiaryModel
 * JD-Core Version:    0.6.0
 */
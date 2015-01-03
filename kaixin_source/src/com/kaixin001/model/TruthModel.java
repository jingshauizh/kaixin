package com.kaixin001.model;

public class TruthModel extends KXModel
{
  private static TruthModel instance = null;
  private int mCNum = 0;
  private String mContent = "";
  private String mFuid = "";
  private String mMsg = "";
  private int mStatus = 0;
  private String mTid = "";
  private String mTitle = "";
  private int mTruthid = 0;

  public static TruthModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new TruthModel();
      TruthModel localTruthModel = instance;
      return localTruthModel;
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
    this.mTitle = "";
    this.mTid = "";
    this.mStatus = 0;
    this.mFuid = "";
    this.mCNum = 0;
    this.mMsg = "";
  }

  public int getCNum()
  {
    return this.mCNum;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getMsg()
  {
    return this.mMsg;
  }

  public int getStatus()
  {
    return this.mStatus;
  }

  public String getTid()
  {
    return this.mTid;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getTruthContent()
  {
    return this.mContent;
  }

  public int getTruthid()
  {
    return this.mTruthid;
  }

  public void setCNum(int paramInt)
  {
    this.mCNum = paramInt;
  }

  public void setFuid(String paramString)
  {
    if (paramString != null)
      this.mFuid = paramString;
  }

  public void setMsg(String paramString)
  {
    if (paramString != null)
      this.mMsg = paramString;
  }

  public void setStatus(int paramInt)
  {
    this.mStatus = paramInt;
  }

  public void setTid(String paramString)
  {
    if (paramString != null)
      this.mTid = paramString;
  }

  public void setTitle(String paramString)
  {
    if (paramString != null)
      this.mTitle = paramString;
  }

  public void setTruthContent(String paramString)
  {
    if (paramString != null)
      this.mContent = paramString;
  }

  public void setTruthid(int paramInt)
  {
    this.mTruthid = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.TruthModel
 * JD-Core Version:    0.6.0
 */
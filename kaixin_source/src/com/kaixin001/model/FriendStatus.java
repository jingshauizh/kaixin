package com.kaixin001.model;

public class FriendStatus
{
  private String mFuid = "";
  private String mName = "";
  private String mStatus = "";
  private String mTimeStamp = "";

  public FriendStatus()
  {
  }

  public FriendStatus(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (paramString1 != null)
      this.mFuid = paramString1;
    if (paramString2 != null)
      this.mName = paramString2;
    if (paramString3 != null)
      this.mStatus = paramString3;
    if (paramString4 != null)
      this.mTimeStamp = paramString4;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getName()
  {
    return this.mName;
  }

  public String getStatus()
  {
    return this.mStatus;
  }

  public String getTimeStamp()
  {
    return this.mTimeStamp;
  }

  public void setFuid(String paramString)
  {
    if (paramString != null)
      this.mFuid = paramString;
  }

  public void setName(String paramString)
  {
    if (paramString != null)
      this.mName = paramString;
  }

  public void setStatus(String paramString)
  {
    if (paramString != null)
      this.mStatus = paramString;
  }

  public void setTimeStamp(String paramString)
  {
    if (paramString != null)
      this.mTimeStamp = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FriendStatus
 * JD-Core Version:    0.6.0
 */
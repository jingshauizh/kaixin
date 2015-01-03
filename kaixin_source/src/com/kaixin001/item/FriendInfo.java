package com.kaixin001.item;

import java.io.Serializable;

public class FriendInfo
  implements Serializable
{
  private String mFlogo = "";
  private String mFuid = "";
  private String mName = "";
  private String mType = "";

  public FriendInfo(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      this.mName = paramString1;
    if (paramString2 != null)
      this.mFuid = paramString2;
  }

  public FriendInfo(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 != null)
      this.mName = paramString1;
    if (paramString2 != null)
      this.mFuid = paramString2;
    if (paramString3 != null)
      this.mFlogo = paramString3;
  }

  public String getFlogo()
  {
    return this.mFlogo;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getName()
  {
    return this.mName;
  }

  public String getType()
  {
    return this.mType;
  }

  public void setFlogo(String paramString)
  {
    if (paramString != null)
      this.mFlogo = paramString;
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

  public void setType(String paramString)
  {
    this.mType = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.FriendInfo
 * JD-Core Version:    0.6.0
 */
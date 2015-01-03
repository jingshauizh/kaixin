package com.kaixin001.model;

public class ShareInfo
{
  public static ShareInfo info = new ShareInfo();
  private String endTime;
  private String shareImg;
  private String shareMessage;
  private String shareUrl;
  private String startTime;

  public static ShareInfo getInstance()
  {
    return info;
  }

  public String getEndTime()
  {
    return this.endTime;
  }

  public String getShareImg()
  {
    return this.shareImg;
  }

  public String getShareMessage()
  {
    return this.shareMessage;
  }

  public String getShareUrl()
  {
    return this.shareUrl;
  }

  public String getStartTime()
  {
    return this.startTime;
  }

  public void setEndTime(String paramString)
  {
    this.endTime = paramString;
  }

  public void setShareImg(String paramString)
  {
    this.shareImg = paramString;
  }

  public void setShareMessage(String paramString)
  {
    this.shareMessage = paramString;
  }

  public void setShareUrl(String paramString)
  {
    this.shareUrl = paramString;
  }

  public void setStartTime(String paramString)
  {
    this.startTime = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ShareInfo
 * JD-Core Version:    0.6.0
 */
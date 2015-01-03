package com.kaixin001.item;

public class AdvGameItem
{
  private static final String TAG = "GameBarItem";
  private String advertClickUrl = null;
  private String advertImageUrl = null;
  private int displayTime = 3;
  private String id;
  private String title = null;
  private String type;

  public AdvGameItem()
  {
  }

  public AdvGameItem(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.id = paramString1;
    this.advertImageUrl = paramString2;
    this.advertClickUrl = paramString3;
    this.title = paramString4;
  }

  public String getAdvertClickUrl()
  {
    return this.advertClickUrl;
  }

  public String getAdvertImageUrl()
  {
    return this.advertImageUrl;
  }

  public int getDisplayTime()
  {
    return this.displayTime;
  }

  public String getId()
  {
    return this.id;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getType()
  {
    return this.type;
  }

  public void setAdvertClickUrl(String paramString)
  {
    this.advertClickUrl = paramString;
  }

  public void setAdvertImageUrl(String paramString)
  {
    this.advertImageUrl = paramString;
  }

  public void setDisplayTime(int paramInt)
  {
    this.displayTime = paramInt;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.AdvGameItem
 * JD-Core Version:    0.6.0
 */
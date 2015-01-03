package com.kaixin001.item;

public class PhotoGraphItem
{
  public static final String PHOTOGRAGH = "photograph";
  public String advertClickUrl = null;
  public String advertImageUrl = null;
  public String id = null;
  public String title = null;
  public String uploadphoto = null;

  public PhotoGraphItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.title = paramString4;
    this.id = paramString1;
    this.advertImageUrl = paramString2;
    this.advertClickUrl = paramString3;
    this.uploadphoto = paramString5;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.PhotoGraphItem
 * JD-Core Version:    0.6.0
 */
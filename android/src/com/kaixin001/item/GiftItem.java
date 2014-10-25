package com.kaixin001.item;

public class GiftItem
{
  public String defaultPs = null;
  public String gid = null;
  public String gname = null;
  public String pic = null;

  public GiftItem(String paramString1, String paramString2, String paramString3)
  {
    this.gid = paramString1;
    this.gname = paramString2;
    this.pic = paramString3;
  }

  public GiftItem(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.gid = paramString1;
    this.gname = paramString2;
    this.pic = paramString3;
    this.defaultPs = paramString4;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.GiftItem
 * JD-Core Version:    0.6.0
 */
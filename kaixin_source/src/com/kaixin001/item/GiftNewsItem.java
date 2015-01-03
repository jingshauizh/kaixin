package com.kaixin001.item;

import java.util.ArrayList;

public class GiftNewsItem
{
  public String mIntro = null;
  public String mRgender = null;
  public String mRlogo = null;
  public String mRname = null;
  public String mRuid = null;
  public ArrayList<String> mSgiftLogoList = new ArrayList();
  public String mStime = null;

  public GiftNewsItem()
  {
  }

  public GiftNewsItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ArrayList<String> paramArrayList)
  {
    this.mIntro = paramString1;
    this.mRuid = paramString2;
    this.mRlogo = paramString3;
    this.mRname = paramString4;
    this.mRgender = paramString5;
    this.mSgiftLogoList = paramArrayList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.GiftNewsItem
 * JD-Core Version:    0.6.0
 */
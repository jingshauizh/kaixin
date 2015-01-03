package com.kaixin001.model;

import android.graphics.Bitmap;

public class KXAppShareModel extends KXModel
{
  public String mDes;
  public String mGiftInfo;
  public Bitmap mIcon;
  public String mImgUrl;
  public String mLinkUrl;
  public String mResultInfo;
  public String mTitle;

  public void clear()
  {
    if ((this.mIcon != null) && (!this.mIcon.isRecycled()))
    {
      this.mIcon.recycle();
      this.mIcon = null;
    }
  }

  public void init()
  {
    clear();
    this.mTitle = "";
    this.mDes = "";
    this.mImgUrl = "";
    this.mLinkUrl = "";
    this.mGiftInfo = "";
    this.mResultInfo = "";
    this.mIcon = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.KXAppShareModel
 * JD-Core Version:    0.6.0
 */
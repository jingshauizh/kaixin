package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class KXBottomTabButton
{
  Bitmap bmpLeftIcon;
  Bitmap bmpRightIcon;
  Context context;
  String strRightText;
  String strText;

  public KXBottomTabButton(Context paramContext)
  {
    this.context = paramContext;
  }

  public Bitmap getLeftIcon()
  {
    return this.bmpLeftIcon;
  }

  public Bitmap getRightIcon()
  {
    return this.bmpRightIcon;
  }

  public String getRightText()
  {
    return this.strRightText;
  }

  public String getText()
  {
    return this.strText;
  }

  public void setLeftIcon(int paramInt)
  {
    this.bmpLeftIcon = BitmapFactory.decodeResource(this.context.getResources(), paramInt);
  }

  public void setLeftIcon(Bitmap paramBitmap)
  {
    this.bmpLeftIcon = paramBitmap;
  }

  public void setRightIcon(Bitmap paramBitmap)
  {
    this.bmpRightIcon = paramBitmap;
  }

  public void setRightText(String paramString)
  {
    this.strRightText = paramString;
  }

  public void setText(int paramInt)
  {
    this.strText = this.context.getResources().getString(paramInt);
  }

  public void setText(String paramString)
  {
    this.strText = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXBottomTabButton
 * JD-Core Version:    0.6.0
 */
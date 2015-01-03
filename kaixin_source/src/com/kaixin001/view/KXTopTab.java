package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.NinePatch;

public class KXTopTab
{
  private Bitmap bmpLeftIcon;
  private Bitmap bmpRightIcon;
  private Context context;
  private boolean isSelected = false;
  private NinePatch mRightIcon;
  private String strRightText;
  private String text;

  public KXTopTab(Context paramContext)
  {
    this.context = paramContext;
  }

  public KXTopTab(Context paramContext, int paramInt)
  {
    this.context = paramContext;
    this.text = paramContext.getResources().getString(paramInt);
  }

  public KXTopTab(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.text = paramString;
  }

  public Bitmap getBmpLeftIcon()
  {
    return this.bmpLeftIcon;
  }

  public Bitmap getBmpRightIcon()
  {
    return this.bmpRightIcon;
  }

  public NinePatch getRightIcon()
  {
    return this.mRightIcon;
  }

  public String getRightText()
  {
    return this.strRightText;
  }

  public String getText()
  {
    return this.text;
  }

  public boolean isSelected()
  {
    return this.isSelected;
  }

  public void setBmpLeftIcon(Bitmap paramBitmap)
  {
    this.bmpLeftIcon = paramBitmap;
  }

  public void setBmpRightIcon(Bitmap paramBitmap)
  {
    this.bmpRightIcon = paramBitmap;
  }

  public void setRightIcon(NinePatch paramNinePatch)
  {
    this.mRightIcon = paramNinePatch;
  }

  public void setRightText(String paramString)
  {
    this.strRightText = paramString;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.isSelected = paramBoolean;
  }

  public void setText(int paramInt)
  {
    this.text = this.context.getResources().getString(paramInt);
  }

  public void setText(String paramString)
  {
    this.text = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXTopTab
 * JD-Core Version:    0.6.0
 */
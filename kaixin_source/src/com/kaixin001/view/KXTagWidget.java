package com.kaixin001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class KXTagWidget extends TextView
{
  int mBgColor = -1;
  int mPressedBgColor = -1;
  int mPressedTextColor = -16777216;
  String mTagName = "";
  int mTextColor = -1;

  public KXTagWidget(Context paramContext)
  {
    super(paramContext);
    setClickable(true);
  }

  public KXTagWidget(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setClickable(true);
  }

  public KXTagWidget(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setClickable(true);
  }

  public int getMesuredWidth()
  {
    return (int)getPaint().measureText(this.mTagName) + getCompoundPaddingLeft() + getCompoundPaddingRight();
  }

  public int getmBgColor()
  {
    return this.mBgColor;
  }

  public int getmPressedBgColor()
  {
    return this.mPressedBgColor;
  }

  public int getmPressedTextColor()
  {
    return this.mPressedTextColor;
  }

  public int getmTextColor()
  {
    return this.mTextColor;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    localPaint.setColor(this.mBgColor);
    int i = getWidth();
    int j = getHeight();
    paramCanvas.drawLine(0.0F, 0.0F, i - 1, 0.0F, localPaint);
    paramCanvas.drawLine(i - 1, 0.0F, i - 1, j, localPaint);
    paramCanvas.drawLine(i - 1, j - 1, 0.0F, j - 1, localPaint);
    paramCanvas.drawLine(0.0F, j - 1, 0.0F, 0.0F, localPaint);
  }

  public void setBgColor(int paramInt1, int paramInt2)
  {
    this.mBgColor = paramInt1;
    this.mPressedBgColor = paramInt2;
    setBackgroundColor(paramInt1);
  }

  public void setName(String paramString)
  {
    if (paramString != null)
    {
      this.mTagName = paramString;
      setText(this.mTagName);
    }
  }

  public void setTextColor(int paramInt1, int paramInt2)
  {
    this.mTextColor = paramInt1;
    this.mPressedTextColor = paramInt2;
  }

  public void setmBgColor(int paramInt)
  {
    this.mBgColor = paramInt;
  }

  public void setmPressedBgColor(int paramInt)
  {
    this.mPressedBgColor = paramInt;
  }

  public void setmPressedTextColor(int paramInt)
  {
    this.mPressedTextColor = paramInt;
  }

  public void setmTextColor(int paramInt)
  {
    this.mTextColor = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXTagWidget
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class IndexIndicateView extends View
{
  private static final int MAX_POINT = 10;
  private Bitmap mNormalDot;
  private Paint mPaint = new Paint();
  private Bitmap mSelectedDot;
  private int nCurPicIndex = 0;
  private int nDiameter = 6;
  private int nGap = 5;
  private int nMaxPicNum = 1;
  private int nStartX;
  private int nStartY = 0;
  private int nViewWidth;

  public IndexIndicateView(Context paramContext)
  {
    super(paramContext);
    initData();
  }

  public IndexIndicateView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initData();
  }

  private void initData()
  {
    this.mNormalDot = BitmapFactory.decodeResource(getResources(), 2130838176);
    this.mSelectedDot = BitmapFactory.decodeResource(getResources(), 2130838175);
    this.nDiameter = this.mNormalDot.getWidth();
    this.nGap = (-1 + this.nDiameter);
    postInvalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = 0;
    if (i >= this.nMaxPicNum)
      return;
    int j = this.nStartX + i * (this.nDiameter + this.nGap);
    if (i != this.nCurPicIndex)
      paramCanvas.drawBitmap(this.mNormalDot, j, 0.0F, this.mPaint);
    while (true)
    {
      i++;
      break;
      paramCanvas.drawBitmap(this.mSelectedDot, j, 0.0F, this.mPaint);
    }
  }

  public void updateState(int paramInt1, int paramInt2, int paramInt3)
  {
    this.nMaxPicNum = paramInt1;
    this.nCurPicIndex = paramInt2;
    this.nViewWidth = paramInt3;
    this.nMaxPicNum = paramInt1;
    if (this.nMaxPicNum > 10)
      this.nMaxPicNum = 10;
    this.nStartX = ((this.nViewWidth - this.nDiameter * this.nMaxPicNum - this.nGap * (-1 + this.nMaxPicNum)) / 2);
    if (paramInt2 < 9)
      this.nCurPicIndex = paramInt2;
    while (true)
    {
      invalidate();
      return;
      if (paramInt2 < paramInt1)
      {
        this.nCurPicIndex = 8;
        continue;
      }
      this.nCurPicIndex = 9;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.IndexIndicateView
 * JD-Core Version:    0.6.0
 */
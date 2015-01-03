package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class PhotoClipView extends View
{
  private static final int DIP_MARGAIN = 10;
  private int bottom = 0;
  private int dpMargin = 10;
  private boolean isParamsSetted = false;
  private int left = 0;
  private Context mContext;
  private int right = 0;
  private int side = 0;
  private int top = 0;

  public PhotoClipView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public PhotoClipView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }

  public PhotoClipView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  private int dipToPx(float paramFloat)
  {
    return (int)(0.5F + paramFloat * this.mContext.getResources().getDisplayMetrics().density);
  }

  public int getMarginDp()
  {
    return this.dpMargin;
  }

  public int getRectBottom()
  {
    return this.bottom;
  }

  public int getRectLeft()
  {
    return this.left;
  }

  public int getRectRight()
  {
    return this.right;
  }

  public int getRectTop()
  {
    return this.top;
  }

  public int getSidePx()
  {
    return this.side;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    int i = getWidth();
    int j = getHeight();
    if (!this.isParamsSetted)
    {
      this.side = (i - dipToPx(2 * this.dpMargin));
      this.top = ((j - this.side) / 2);
      this.bottom = (this.top + this.side);
      this.left = ((i - this.side) / 2);
      this.right = (this.left + this.side);
    }
    float[] arrayOfFloat = new float[16];
    arrayOfFloat[0] = this.left;
    arrayOfFloat[1] = (-1 + this.top);
    arrayOfFloat[2] = this.right;
    arrayOfFloat[3] = (-1 + this.top);
    arrayOfFloat[4] = this.right;
    arrayOfFloat[5] = this.top;
    arrayOfFloat[6] = this.right;
    arrayOfFloat[7] = this.bottom;
    arrayOfFloat[8] = this.right;
    arrayOfFloat[9] = this.bottom;
    arrayOfFloat[10] = this.left;
    arrayOfFloat[11] = this.bottom;
    arrayOfFloat[12] = (-1 + this.left);
    arrayOfFloat[13] = this.bottom;
    arrayOfFloat[14] = (-1 + this.left);
    arrayOfFloat[15] = this.top;
    localPaint.setColor(-1728053248);
    paramCanvas.drawRect(0.0F, 0.0F, i, this.top, localPaint);
    paramCanvas.drawRect(0.0F, this.top, this.left, this.bottom, localPaint);
    paramCanvas.drawRect(this.right, this.top, i, this.bottom, localPaint);
    paramCanvas.drawRect(0.0F, this.bottom, i, j, localPaint);
    localPaint.setColor(-1);
    paramCanvas.drawLines(arrayOfFloat, localPaint);
    this.isParamsSetted = false;
  }

  public void setMarginDp(int paramInt)
  {
    this.dpMargin = paramInt;
    invalidate();
  }

  public void setParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.left = paramInt2;
    this.right = paramInt3;
    this.top = paramInt1;
    this.bottom = paramInt4;
    this.isParamsSetted = true;
    invalidate();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.PhotoClipView
 * JD-Core Version:    0.6.0
 */
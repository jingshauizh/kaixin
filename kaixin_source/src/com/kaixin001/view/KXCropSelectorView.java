package com.kaixin001.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.kaixin001.util.KXLog;

public class KXCropSelectorView extends KXHighlightView
{
  private static final String TAG = "KXCropSelectorView";
  private Drawable mLeftTopDrawable;
  private Drawable mRightBottomDrawable;

  public KXCropSelectorView(View paramView)
  {
    super(paramView);
  }

  protected void draw(Canvas paramCanvas)
  {
    if (this.mHidden)
      return;
    Path localPath = new Path();
    localPath.addRect(new RectF(this.mDrawRect), Path.Direction.CW);
    if (!this.mIsFocused)
    {
      this.mOutlinePaint.setColor(-65536);
      paramCanvas.drawPath(localPath, this.mOutlinePaint);
      return;
    }
    paramCanvas.save();
    Rect localRect = new Rect();
    this.mContext.getDrawingRect(localRect);
    this.mOutlinePaint.setColor(-1);
    try
    {
      paramCanvas.clipPath(localPath, Region.Op.DIFFERENCE);
      label100: if (this.mIsFocused);
      for (Paint localPaint = this.mFocusPaint; ; localPaint = this.mNoFocusPaint)
      {
        paramCanvas.drawRect(localRect, localPaint);
        paramCanvas.restore();
        paramCanvas.drawPath(localPath, this.mOutlinePaint);
        int i = this.mLeftTopDrawable.getIntrinsicWidth();
        int j = this.mLeftTopDrawable.getIntrinsicHeight();
        int k = this.mDrawRect.left - i / 2;
        int m = this.mDrawRect.top - j / 2;
        this.mLeftTopDrawable.setBounds(k, m, k + i, m + j);
        this.mLeftTopDrawable.draw(paramCanvas);
        int n = this.mDrawRect.right - i / 2;
        int i1 = this.mDrawRect.bottom - j / 2;
        this.mRightBottomDrawable.setBounds(n, i1, n + i, i1 + j);
        this.mRightBottomDrawable.draw(paramCanvas);
        return;
      }
    }
    catch (Exception localException)
    {
      break label100;
    }
  }

  public int getHit(float paramFloat1, float paramFloat2)
  {
    return super.getHit(paramFloat1, paramFloat2);
  }

  protected void init()
  {
    super.init();
    this.mLeftTopDrawable = this.mContext.getResources().getDrawable(2130838781);
    this.mRightBottomDrawable = this.mContext.getResources().getDrawable(2130838781);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mLeftTopDrawable;
    arrayOfObject[1] = this.mRightBottomDrawable;
    KXLog.w("KXCropSelectorView", "-----------------  mLeftTopDrawable=%s  while mRightBottomDrawable=%s ---------", arrayOfObject);
  }

  public void resetButtonState()
  {
    this.mLeftTopDrawable.setState(new int[0]);
    this.mRightBottomDrawable.setState(new int[0]);
  }

  public void setLeftTopClicked()
  {
    this.mLeftTopDrawable.setState(new int[] { 16842919 });
  }

  public void setRightBottomClicked()
  {
    this.mRightBottomDrawable.setState(new int[] { 16842919 });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXCropSelectorView
 * JD-Core Version:    0.6.0
 */
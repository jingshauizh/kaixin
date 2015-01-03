package com.kaixin001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.kaixin001.util.KXLog;

public class KXCropImageView extends ImageView
{
  private static final String TAG = "CorpImageView";
  float mLastX;
  float mLastY;
  int mMotionEdge;
  int mRotation;
  private KXCropSelectorView mSelectorView;

  public KXCropImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXCropImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public int getImageViewHeight()
  {
    if (isOrientationChanged())
      return getWidth();
    return getHeight();
  }

  public int getImageViewWidth()
  {
    if (isOrientationChanged())
      return getHeight();
    return getWidth();
  }

  public boolean isOrientationChanged()
  {
    return this.mRotation / 90 % 2 != 0;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mSelectorView != null)
      this.mSelectorView.draw(paramCanvas);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mSelectorView == null)
      return false;
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return true;
      KXCropSelectorView localKXCropSelectorView = this.mSelectorView;
      int i = localKXCropSelectorView.getHit(paramMotionEvent.getX(), paramMotionEvent.getY());
      if (i == 1)
        continue;
      this.mMotionEdge = i;
      this.mLastX = paramMotionEvent.getX();
      this.mLastY = paramMotionEvent.getY();
      if (i == 32);
      for (KXHighlightView.ModifyMode localModifyMode = KXHighlightView.ModifyMode.Move; ; localModifyMode = KXHighlightView.ModifyMode.Grow)
      {
        localKXCropSelectorView.setMode(localModifyMode);
        if ((i & 0xA) == 0)
          break label128;
        this.mSelectorView.setLeftTopClicked();
        break;
      }
      label128: if ((i & 0x14) == 0)
        continue;
      this.mSelectorView.setRightBottomClicked();
      continue;
      this.mSelectorView.setMode(KXHighlightView.ModifyMode.None);
      this.mMotionEdge = 1;
      this.mSelectorView.resetButtonState();
      continue;
      this.mSelectorView.handleMotion(this.mMotionEdge, paramMotionEvent.getX() - this.mLastX, paramMotionEvent.getY() - this.mLastY);
      this.mLastX = paramMotionEvent.getX();
      this.mLastY = paramMotionEvent.getY();
    }
  }

  public void setHighlightView(KXCropSelectorView paramKXCropSelectorView)
  {
    this.mSelectorView = paramKXCropSelectorView;
  }

  public void setRotation(int paramInt)
  {
    Matrix localMatrix = getImageMatrix();
    int i = getWidth();
    int j = getHeight();
    int k = paramInt % 360;
    this.mRotation = ((k + this.mRotation) % 360);
    int m = i / 2;
    int n = j / 2;
    localMatrix.postRotate(k, m - getPaddingLeft(), n - getPaddingTop());
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(getWidth());
    arrayOfObject1[1] = Integer.valueOf(getHeight());
    KXLog.w("CorpImageView", "--------- width1=%d  height1=%d ------------", arrayOfObject1);
    invalidate();
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = Integer.valueOf(getWidth());
    arrayOfObject2[1] = Integer.valueOf(getHeight());
    KXLog.w("CorpImageView", "--------- width2=%d  height2=%d ------------", arrayOfObject2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXCropImageView
 * JD-Core Version:    0.6.0
 */
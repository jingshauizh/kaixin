package com.kaixin001.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class KXGallery extends Gallery
{
  private static final int DRAG = 1;
  private static final int NONE = 0;
  private static final int ZOOM = 2;
  private long mNextChangePositionTime;
  private PointF mPtLastMove = new PointF();
  private int mode = 0;
  private float oldDist;
  public IOnKXGalleryFling onFlingListener;
  private float ori_ratio = 1.0F;

  public KXGallery(Context paramContext)
  {
    super(paramContext);
  }

  public KXGallery(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXGallery(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private boolean isScrollingLeft(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2)
  {
    return paramMotionEvent2.getX() > paramMotionEvent1.getX();
  }

  private float spacing(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  public KXImageView getImageView()
  {
    LinearLayout localLinearLayout = (LinearLayout)getSelectedView();
    if (localLinearLayout == null)
      return null;
    return (KXImageView)localLinearLayout.findViewById(2131363969);
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2));
    for (int i = 21; ; i = 22)
    {
      onKeyDown(i, null);
      if (this.onFlingListener != null)
        this.onFlingListener.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
      return true;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int j = 0xFF & paramMotionEvent.getAction();
    int k = paramMotionEvent.getPointerCount();
    KXImageView localKXImageView = getImageView();
    if (localKXImageView == null)
      i = super.onTouchEvent(paramMotionEvent);
    label217: float f2;
    do
    {
      return i;
      switch (j)
      {
      case 3:
      case 4:
      default:
      case 0:
      case 1:
      case 6:
      case 5:
      case 2:
      }
      do
      {
        float f1;
        do
        {
          do
          {
            while (true)
            {
              return super.onTouchEvent(paramMotionEvent);
              this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
              this.mode = i;
              continue;
              this.mode = 0;
              continue;
              this.ori_ratio = localKXImageView.getScale();
              this.oldDist = spacing(paramMotionEvent);
              if (this.oldDist <= 10.0F)
                continue;
              this.mode = 2;
            }
            if (this.mode != i)
              continue;
            if (localKXImageView.getScale() > 1.0F)
            {
              if (paramMotionEvent.getEventTime() < this.mNextChangePositionTime)
                break label217;
              this.mNextChangePositionTime = (500L + paramMotionEvent.getEventTime());
            }
            while ((localKXImageView.isLeftBound()) || (localKXImageView.isRightBound()))
            {
              return super.onTouchEvent(paramMotionEvent);
              if (localKXImageView.isNeedDrag())
              {
                localKXImageView.panImage(this.mPtLastMove.x - paramMotionEvent.getX(), this.mPtLastMove.y - paramMotionEvent.getY());
                this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
                continue;
              }
              localKXImageView.panImage(this.mPtLastMove.x - paramMotionEvent.getX(), 0.0F);
              this.mPtLastMove.set(paramMotionEvent.getX(), this.mPtLastMove.y);
            }
            break;
          }
          while (this.mode != 2);
          f1 = spacing(paramMotionEvent);
        }
        while (f1 <= 10.0F);
        f2 = f1 / this.oldDist * this.ori_ratio;
      }
      while (k != 2);
    }
    while (f2 < 1.0F);
    localKXImageView.zoomTo(f2);
    return i;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXGallery
 * JD-Core Version:    0.6.0
 */
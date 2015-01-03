package com.kaixin001.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;

public class KXDragImageView extends KXImageView
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

  public KXDragImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXDragImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private float spacing(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int j = 0xFF & paramMotionEvent.getAction();
    int k = paramMotionEvent.getPointerCount();
    if (this == null)
      i = super.onTouchEvent(paramMotionEvent);
    float f2;
    do
    {
      float f1;
      do
      {
        do
          while (true)
          {
            return i;
            switch (j)
            {
            case 3:
            case 4:
            default:
              return i;
            case 0:
              this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
              this.mode = i;
              return i;
            case 1:
            case 6:
              this.mode = 0;
              return i;
            case 5:
              this.ori_ratio = getScale();
              this.oldDist = spacing(paramMotionEvent);
              if (this.oldDist <= 10.0F)
                continue;
              this.mode = 2;
              return i;
            case 2:
              if (this.mode == i)
              {
                if (getScale() <= 1.0F)
                  continue;
                if (paramMotionEvent.getEventTime() >= this.mNextChangePositionTime)
                {
                  this.mNextChangePositionTime = (500L + paramMotionEvent.getEventTime());
                  return i;
                }
                if (isNeedDrag())
                {
                  panImage(this.mPtLastMove.x - paramMotionEvent.getX(), this.mPtLastMove.y - paramMotionEvent.getY());
                  this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
                  return i;
                }
                panImage(this.mPtLastMove.x - paramMotionEvent.getX(), 0.0F);
                this.mPtLastMove.set(paramMotionEvent.getX(), this.mPtLastMove.y);
                return i;
              }
            }
          }
        while (this.mode != 2);
        f1 = spacing(paramMotionEvent);
      }
      while (f1 <= 10.0F);
      f2 = f1 / this.oldDist * this.ori_ratio;
    }
    while ((k != 2) || (f2 < 1.0F));
    zoomTo(f2);
    return i;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXDragImageView
 * JD-Core Version:    0.6.0
 */
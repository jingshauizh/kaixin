package com.kaixin001.view;

import android.content.Context;
import android.view.MotionEvent;

public class ScaleGestureDetector
{
  private static final float PRESSURE_THRESHOLD = 0.67F;
  private static final String TAG = "ScaleGestureDetector";
  private float mBottomFingerBeginX;
  private float mBottomFingerBeginY;
  private float mBottomFingerCurrX;
  private float mBottomFingerCurrY;
  private Context mContext;
  private MotionEvent mCurrEvent;
  private float mCurrFingerDiffX;
  private float mCurrFingerDiffY;
  private float mCurrLen;
  private float mCurrPressure;
  private float mFocusX;
  private float mFocusY;
  private boolean mGestureInProgress;
  private OnScaleGestureListener mListener;
  private boolean mPointerOneUp;
  private boolean mPointerTwoUp;
  private MotionEvent mPrevEvent;
  private float mPrevFingerDiffX;
  private float mPrevFingerDiffY;
  private float mPrevLen;
  private float mPrevPressure;
  private float mScaleFactor;
  private long mTimeDelta;
  private float mTopFingerBeginX;
  private float mTopFingerBeginY;
  private float mTopFingerCurrX;
  private float mTopFingerCurrY;
  private boolean mTopFingerIsPointer1;

  public ScaleGestureDetector(Context paramContext, OnScaleGestureListener paramOnScaleGestureListener)
  {
    this.mContext = paramContext;
    this.mListener = paramOnScaleGestureListener;
  }

  private void reset()
  {
    if (this.mPrevEvent != null)
    {
      this.mPrevEvent.recycle();
      this.mPrevEvent = null;
    }
    if (this.mCurrEvent != null)
    {
      this.mCurrEvent.recycle();
      this.mCurrEvent = null;
    }
  }

  private void setContext(MotionEvent paramMotionEvent)
  {
    if (this.mCurrEvent != null)
      this.mCurrEvent.recycle();
    this.mCurrEvent = MotionEvent.obtain(paramMotionEvent);
    this.mCurrLen = -1.0F;
    this.mPrevLen = -1.0F;
    this.mScaleFactor = -1.0F;
    MotionEvent localMotionEvent = this.mPrevEvent;
    float f1 = localMotionEvent.getX(0);
    float f2 = localMotionEvent.getY(0);
    float f3 = localMotionEvent.getX(1);
    float f4 = localMotionEvent.getY(1);
    float f5 = paramMotionEvent.getX(0);
    float f6 = paramMotionEvent.getY(0);
    float f7 = paramMotionEvent.getX(1);
    float f8 = paramMotionEvent.getY(1);
    float f9 = f3 - f1;
    float f10 = f4 - f2;
    float f11 = f7 - f5;
    float f12 = f8 - f6;
    this.mPrevFingerDiffX = f9;
    this.mPrevFingerDiffY = f10;
    this.mCurrFingerDiffX = f11;
    this.mCurrFingerDiffY = f12;
    this.mFocusX = (f5 + 0.5F * f11);
    this.mFocusY = (f6 + 0.5F * f12);
    this.mTimeDelta = (paramMotionEvent.getEventTime() - localMotionEvent.getEventTime());
    this.mCurrPressure = (paramMotionEvent.getPressure(0) + paramMotionEvent.getPressure(1));
    this.mPrevPressure = (localMotionEvent.getPressure(0) + localMotionEvent.getPressure(1));
    this.mBottomFingerCurrX = f5;
    this.mBottomFingerCurrY = f6;
    this.mTopFingerCurrX = f7;
    this.mTopFingerCurrY = f8;
  }

  public float getBottomFingerDeltaX()
  {
    if (!this.mTopFingerIsPointer1)
      return this.mTopFingerCurrX - this.mTopFingerBeginX;
    return this.mBottomFingerCurrX - this.mBottomFingerBeginX;
  }

  public float getBottomFingerDeltaY()
  {
    if (!this.mTopFingerIsPointer1)
      return this.mTopFingerCurrY - this.mTopFingerBeginY;
    return this.mBottomFingerCurrY - this.mBottomFingerBeginY;
  }

  public float getBottomFingerX()
  {
    if (!this.mTopFingerIsPointer1)
      return this.mTopFingerCurrX;
    return this.mBottomFingerCurrX;
  }

  public float getBottomFingerY()
  {
    if (!this.mTopFingerIsPointer1)
      return this.mTopFingerCurrY;
    return this.mBottomFingerCurrY;
  }

  public float getCurrentSpan()
  {
    if (this.mCurrLen == -1.0F)
    {
      float f1 = this.mCurrFingerDiffX;
      float f2 = this.mCurrFingerDiffY;
      this.mCurrLen = (float)Math.sqrt(f1 * f1 + f2 * f2);
    }
    return this.mCurrLen;
  }

  public long getEventTime()
  {
    return this.mCurrEvent.getEventTime();
  }

  public float getFocusX()
  {
    return this.mFocusX;
  }

  public float getFocusY()
  {
    return this.mFocusY;
  }

  public float getPreviousSpan()
  {
    if (this.mPrevLen == -1.0F)
    {
      float f1 = this.mPrevFingerDiffX;
      float f2 = this.mPrevFingerDiffY;
      this.mPrevLen = (float)Math.sqrt(f1 * f1 + f2 * f2);
    }
    return this.mPrevLen;
  }

  public float getScaleFactor()
  {
    if (this.mScaleFactor == -1.0F)
      this.mScaleFactor = (getCurrentSpan() / getPreviousSpan());
    return this.mScaleFactor;
  }

  public long getTimeDelta()
  {
    return this.mTimeDelta;
  }

  public float getTopFingerDeltaX()
  {
    if (this.mTopFingerIsPointer1)
      return this.mTopFingerCurrX - this.mTopFingerBeginX;
    return this.mBottomFingerCurrX - this.mBottomFingerBeginX;
  }

  public float getTopFingerDeltaY()
  {
    if (this.mTopFingerIsPointer1)
      return this.mTopFingerCurrY - this.mTopFingerBeginY;
    return this.mBottomFingerCurrY - this.mBottomFingerBeginY;
  }

  public float getTopFingerX()
  {
    if (this.mTopFingerIsPointer1)
      return this.mTopFingerCurrX;
    return this.mBottomFingerCurrX;
  }

  public float getTopFingerY()
  {
    if (this.mTopFingerIsPointer1)
      return this.mTopFingerCurrY;
    return this.mBottomFingerCurrY;
  }

  public boolean isInProgress()
  {
    return this.mGestureInProgress;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int j = paramMotionEvent.getAction();
    if (!this.mGestureInProgress)
      if (((j == 5) || (j == 261)) && (paramMotionEvent.getPointerCount() >= 2))
      {
        this.mBottomFingerBeginX = paramMotionEvent.getX(0);
        this.mBottomFingerBeginY = paramMotionEvent.getY(0);
        this.mTopFingerBeginX = paramMotionEvent.getX(i);
        this.mTopFingerBeginY = paramMotionEvent.getY(i);
        this.mTopFingerCurrX = this.mTopFingerBeginX;
        this.mTopFingerCurrY = this.mTopFingerBeginY;
        this.mBottomFingerCurrX = this.mBottomFingerBeginX;
        this.mBottomFingerCurrY = this.mBottomFingerBeginY;
        this.mPointerOneUp = false;
        this.mPointerTwoUp = false;
        reset();
        if (this.mTopFingerBeginY <= this.mBottomFingerBeginY)
          break label167;
        this.mTopFingerIsPointer1 = false;
        this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
        this.mTimeDelta = 0L;
        setContext(paramMotionEvent);
        this.mGestureInProgress = this.mListener.onScaleBegin(this);
      }
    label167: 
    do
    {
      do
      {
        return true;
        this.mTopFingerIsPointer1 = i;
        break;
        switch (j)
        {
        default:
          return true;
        case 1:
          this.mPointerOneUp = i;
          this.mPointerTwoUp = i;
        case 6:
          if (this.mPointerOneUp)
            this.mPointerTwoUp = i;
          this.mPointerOneUp = i;
        case 262:
          if (j != 262)
            continue;
          if (this.mPointerTwoUp)
            this.mPointerOneUp = i;
          this.mPointerTwoUp = i;
        case 3:
        case 2:
        }
      }
      while ((!this.mPointerOneUp) && (!this.mPointerTwoUp));
      setContext(paramMotionEvent);
      if ((0xFF00 & j) >> 8 == 0);
      while (true)
      {
        this.mFocusX = paramMotionEvent.getX(i);
        this.mFocusY = paramMotionEvent.getY(i);
        this.mListener.onScaleEnd(this, false);
        this.mGestureInProgress = false;
        reset();
        return true;
        i = 0;
      }
      this.mListener.onScaleEnd(this, i);
      this.mGestureInProgress = false;
      reset();
      return true;
      setContext(paramMotionEvent);
    }
    while ((this.mCurrPressure / this.mPrevPressure <= 0.67F) || (!this.mListener.onScale(this)));
    this.mPrevEvent.recycle();
    this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
    return true;
  }

  public static abstract interface OnScaleGestureListener
  {
    public abstract boolean onScale(ScaleGestureDetector paramScaleGestureDetector);

    public abstract boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector);

    public abstract void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector, boolean paramBoolean);
  }

  public class SimpleOnScaleGestureListener
    implements ScaleGestureDetector.OnScaleGestureListener
  {
    public SimpleOnScaleGestureListener()
    {
    }

    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
    {
      return true;
    }

    public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector, boolean paramBoolean)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.ScaleGestureDetector
 * JD-Core Version:    0.6.0
 */
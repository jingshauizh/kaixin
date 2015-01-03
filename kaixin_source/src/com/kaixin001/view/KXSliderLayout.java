package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.kaixin001.util.KXLog;

public class KXSliderLayout extends ViewGroup
{
  private static final int SNAP_VELOCITY = 600;
  private static final String TAG = "KXSliderLayout";
  private static final int TOUCH_STATE_REST = 0;
  private static final int TOUCH_STATE_SCROLLING = 1;
  private int mCurScreen;
  private int mDefaultScreen = 0;
  private float mLastMotionX;
  private float mLastMotionY;
  private OnScreenSlideListener mScreenSlideListener;
  private Scroller mScroller;
  private int mScrollingBounce = 60;
  private int mTouchSlop;
  private int mTouchState = 0;
  private VelocityTracker mVelocityTracker;

  public KXSliderLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public KXSliderLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mScroller = new Scroller(paramContext);
    this.mCurScreen = this.mDefaultScreen;
    this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
  }

  public void computeScroll()
  {
    if (this.mScroller.computeScrollOffset())
    {
      scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
      postInvalidate();
    }
  }

  public int getCurScreen()
  {
    return this.mCurScreen;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    KXLog.d("KXSliderLayout", "onInterceptTouchEvent-slop:" + this.mTouchSlop);
    int i = paramMotionEvent.getAction();
    if ((i == 2) && (this.mTouchState != 0));
    while (true)
    {
      return true;
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      switch (i)
      {
      default:
      case 2:
      case 0:
      case 1:
      case 3:
      }
      while (this.mTouchState == 0)
      {
        return false;
        if ((int)Math.abs(this.mLastMotionX - f1) <= this.mTouchSlop)
          continue;
        this.mTouchState = 1;
        continue;
        this.mLastMotionX = f1;
        this.mLastMotionY = f2;
        if (this.mScroller.isFinished());
        for (int j = 0; ; j = 1)
        {
          this.mTouchState = j;
          break;
        }
        this.mTouchState = 0;
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = 0;
    int j = getChildCount();
    for (int k = 0; ; k++)
    {
      if (k >= j)
        return;
      View localView = getChildAt(k);
      if (localView.getVisibility() == 8)
        continue;
      int m = localView.getMeasuredWidth();
      localView.layout(i, 0, i + m, localView.getMeasuredHeight());
      i += m;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    KXLog.d("KXSliderLayout", "onMeasure");
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    if (View.MeasureSpec.getMode(paramInt1) != 1073741824)
      throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
    if (View.MeasureSpec.getMode(paramInt2) != 1073741824)
      throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
    int j = getChildCount();
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        scrollTo(i * this.mCurScreen, 0);
        return;
      }
      getChildAt(k).measure(paramInt1, paramInt2);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    float f = paramMotionEvent.getX();
    switch (i)
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      return true;
      KXLog.d("KXSliderLayout", "event down!");
      if (!this.mScroller.isFinished())
        this.mScroller.abortAnimation();
      this.mLastMotionX = f;
      continue;
      int k = (int)(this.mLastMotionX - f);
      this.mLastMotionX = f;
      if (k < 0)
      {
        if (getScrollX() <= -this.mScrollingBounce)
          continue;
        scrollBy(Math.min(k, this.mScrollingBounce), 0);
        continue;
      }
      if ((k <= 0) || (getChildAt(-1 + getChildCount()).getRight() - getScrollX() - getWidth() + this.mScrollingBounce <= 0))
        continue;
      scrollBy(k, 0);
      continue;
      KXLog.d("KXSliderLayout", "event : up");
      VelocityTracker localVelocityTracker = this.mVelocityTracker;
      localVelocityTracker.computeCurrentVelocity(1000);
      int j = (int)localVelocityTracker.getXVelocity();
      KXLog.e("KXSliderLayout", "velocityX:" + j);
      if ((j > 600) && (this.mCurScreen > 0))
      {
        KXLog.e("KXSliderLayout", "snap left");
        snapToScreen(-1 + this.mCurScreen);
      }
      while (true)
      {
        if (this.mVelocityTracker != null)
        {
          this.mVelocityTracker.recycle();
          this.mVelocityTracker = null;
        }
        this.mTouchState = 0;
        break;
        if ((j < -600) && (this.mCurScreen < -1 + getChildCount()))
        {
          KXLog.e("KXSliderLayout", "snap right");
          snapToScreen(1 + this.mCurScreen);
          continue;
        }
        if ((this.mCurScreen == -1 + getChildCount()) && (j < -600) && (this.mScreenSlideListener != null))
          this.mScreenSlideListener.onSlideOverTheEnd();
        snapToDestination();
      }
      this.mTouchState = 0;
    }
  }

  public void setOnScreenSliderListener(OnScreenSlideListener paramOnScreenSlideListener)
  {
    this.mScreenSlideListener = paramOnScreenSlideListener;
  }

  public void setToScreen(int paramInt)
  {
    int i = Math.max(0, Math.min(paramInt, -1 + getChildCount()));
    this.mCurScreen = i;
    scrollTo(i * getWidth(), 0);
  }

  public void snapToDestination()
  {
    int i = getWidth();
    snapToScreen((getScrollX() + i / 2) / i);
  }

  public void snapToScreen(int paramInt)
  {
    int i = Math.max(0, Math.min(paramInt, -1 + getChildCount()));
    if (getScrollX() != i * getWidth())
    {
      int j = i * getWidth() - getScrollX();
      this.mScroller.startScroll(getScrollX(), 0, j, 0, 2 * Math.abs(j));
      this.mCurScreen = i;
      invalidate();
    }
    if (this.mScreenSlideListener != null)
      this.mScreenSlideListener.onSlideTo(i);
  }

  public static abstract interface OnScreenSlideListener
  {
    public abstract void onSlideOverTheEnd();

    public abstract void onSlideTo(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXSliderLayout
 * JD-Core Version:    0.6.0
 */
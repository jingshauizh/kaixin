package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import com.kaixin001.util.KXLog;

public class KXSliderLayout2 extends FrameLayout
  implements GestureDetector.OnGestureListener, Animation.AnimationListener
{
  private static final float ANIMATION_MIN_SPEED_PER_MILLISECOND = 0.3F;
  private static final float ANIMATION_SPEED_PER_MILLISECOND = 0.8F;
  private static final int START_SCROLL_OFFSET = 10;
  private static final String TAG = "KXSliderLayout2";
  public static final int TOCENTER = 2;
  public static final int TOLEFT = 1;
  public static final int TORIGHT = 2;
  protected static boolean mCansHorizontalScrolling;
  private static boolean mSlideEnable = true;
  private View mCenterLayout = null;
  private boolean mDebug = false;
  protected GestureDetector mDetector = null;
  private float mDownX = 0.0F;
  private float mDownY = 0.0F;
  private float mFirstSpeed = -1.0F;
  private int mGapX = 0;
  private boolean mHorizontalScrolling = false;
  private View mLeftLayout = null;
  private OnSlideListener mOnSlideListener;
  private boolean mPlayingAnimation = false;
  private OnScreenSlideListener mScreenSlideListener = null;
  private int mScreenWidth = 0;
  private long mTouchEventRetainTime = -1L;
  private long mTouchEventStartTime = -1L;
  private boolean mVerticalScrolling = false;

  static
  {
    mCansHorizontalScrolling = true;
  }

  public KXSliderLayout2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initData(paramContext);
  }

  public KXSliderLayout2(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initData(paramContext);
  }

  private void initData(Context paramContext)
  {
    this.mScreenWidth = getResources().getDisplayMetrics().widthPixels;
    this.mDetector = new GestureDetector(this);
    this.mDetector.setIsLongpressEnabled(true);
  }

  private void moveCenterLayout(int paramInt)
  {
    moveCenterLayoutTo(paramInt + ((KXAbsoluteLayout.LayoutParams)this.mCenterLayout.getLayoutParams()).x);
  }

  private void moveCenterLayoutTo(int paramInt)
  {
    int i = this.mLeftLayout.getWidth();
    if (paramInt < 0)
      paramInt = 0;
    while (true)
    {
      KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)this.mCenterLayout.getLayoutParams();
      localLayoutParams.x = paramInt;
      this.mCenterLayout.setLayoutParams(localLayoutParams);
      if (this.mScreenSlideListener != null)
        this.mScreenSlideListener.onSlideTo(2, localLayoutParams.x);
      return;
      if (paramInt <= i)
        continue;
      paramInt = i;
    }
  }

  private void notifySlided()
  {
    if (this.mOnSlideListener != null)
      this.mOnSlideListener.onSlided();
  }

  private boolean regulateViewPosition()
  {
    int i = getCenterLayoutLeft();
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "regulateViewPosition nCLeft:" + i);
    if ((i > 0) && (i >= this.mLeftLayout.getWidth() / 2))
    {
      this.mCenterLayout.clearAnimation();
      slideToRight();
    }
    do
      return true;
    while (i < 0);
    this.mCenterLayout.clearAnimation();
    slideToCenterFromRight();
    return true;
  }

  public static void setSlideEnable(boolean paramBoolean)
  {
    mSlideEnable = paramBoolean;
  }

  private void slideToCenterFromRight()
  {
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "slideToCenterFromRight...");
    if (this.mScreenSlideListener != null)
      this.mScreenSlideListener.onSlideTo(0, 0);
  }

  private void slideToRight()
  {
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "slideToRight...");
    if (this.mScreenSlideListener != null)
      this.mScreenSlideListener.onSlideTo(1, 0);
  }

  private void startFlingAnimation(float paramFloat1, float paramFloat2, int paramInt)
  {
    KXTranslateAnimation localKXTranslateAnimation = new KXTranslateAnimation(paramFloat1, paramFloat2, 0.0F, 0.0F, 0);
    localKXTranslateAnimation.setDuration(paramInt);
    localKXTranslateAnimation.setRepeatCount(0);
    localKXTranslateAnimation.setAnimationListener(this);
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "startFlingAnimation mCenterLayout.startAnimation");
    this.mPlayingAnimation = true;
    this.mCenterLayout.startAnimation(localKXTranslateAnimation);
  }

  public int getCenterLayoutLeft()
  {
    return ((KXAbsoluteLayout.LayoutParams)this.mCenterLayout.getLayoutParams()).x;
  }

  int getFlingDistance(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1)
      return Math.abs(paramInt2 + -0);
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "getFlingDistance  getCenterLayoutLeft():" + getCenterLayoutLeft());
    return Math.abs(this.mLeftLayout.getWidth() - paramInt2);
  }

  public float getLastSpeed(float paramFloat)
  {
    float f = 0.8F;
    if (paramFloat > 0.0F)
      f = paramFloat / 1000.0F;
    if (this.mTouchEventRetainTime > 0L)
      f = ((KXAbsoluteLayout.LayoutParams)this.mCenterLayout.getLayoutParams()).x / (float)this.mTouchEventRetainTime;
    if (f < 0.3F)
      f = 0.3F;
    return f;
  }

  public void initLayout()
  {
    ViewGroup localViewGroup = (ViewGroup)getChildAt(0);
    if (localViewGroup != null)
    {
      this.mLeftLayout = localViewGroup.getChildAt(0);
      this.mCenterLayout = localViewGroup.getChildAt(1);
    }
  }

  public void onAnimationEnd(Animation paramAnimation)
  {
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "onAnimationEnd");
    this.mPlayingAnimation = false;
    if (regulateViewPosition())
      invalidate();
  }

  public void onAnimationRepeat(Animation paramAnimation)
  {
  }

  public void onAnimationStart(Animation paramAnimation)
  {
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "onAnimationStart");
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (!mSlideEnable)
      bool = super.onInterceptTouchEvent(paramMotionEvent);
    do
      return bool;
    while (this.mPlayingAnimation);
    if (paramMotionEvent.getAction() == 0)
    {
      this.mTouchEventStartTime = System.currentTimeMillis();
      this.mTouchEventRetainTime = -1L;
      this.mFirstSpeed = -1.0F;
    }
    if (paramMotionEvent.getAction() == 0)
    {
      this.mGapX = this.mLeftLayout.getWidth();
      if ((getCenterLayoutLeft() > this.mGapX / 2) && (paramMotionEvent.getX() > this.mGapX))
      {
        slideToCenterFromRight();
        return bool;
      }
      mCansHorizontalScrolling = bool;
      if ((paramMotionEvent.getX() <= getCenterLayoutLeft()) || (paramMotionEvent.getX() >= this.mCenterLayout.getRight()))
        break label318;
      this.mDetector.onTouchEvent(paramMotionEvent);
      this.mDownX = paramMotionEvent.getX();
      this.mDownY = paramMotionEvent.getY();
      this.mVerticalScrolling = false;
      this.mHorizontalScrolling = false;
    }
    while (true)
    {
      if ((paramMotionEvent.getAction() == 2) && (mCansHorizontalScrolling))
      {
        float f1 = paramMotionEvent.getX() - this.mDownX;
        float f2 = paramMotionEvent.getY() - this.mDownY;
        if (((int)this.mDownX != -1) && (Math.abs(f1) > Math.abs(f2)) && (f1 > 10.0F) && (!this.mHorizontalScrolling) && (!this.mVerticalScrolling))
          this.mHorizontalScrolling = bool;
        if (((int)this.mDownX != -1) && (Math.abs(f1) < Math.abs(f2)) && (Math.abs(f2) > 10.0F) && (!this.mHorizontalScrolling) && (!this.mVerticalScrolling))
          this.mVerticalScrolling = bool;
      }
      if (this.mHorizontalScrolling)
        break;
      return super.onInterceptTouchEvent(paramMotionEvent);
      label318: this.mDownX = -1.0F;
      this.mDownY = -1.0F;
    }
  }

  public void onLongPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if (this.mPlayingAnimation)
      return true;
    float f1 = -paramFloat1;
    int i = this.mLeftLayout.getWidth();
    int j = getCenterLayoutLeft();
    if (this.mDebug)
      KXLog.d("KXSliderLayout2", "onScroll leftViewWidth:" + i + ", mCenterView.getLeft():" + j);
    int k = (int)(f1 + j);
    if ((k > 10) && (this.mFirstSpeed < 0.0F))
    {
      this.mFirstSpeed = (k / (float)(paramMotionEvent2.getEventTime() - paramMotionEvent1.getEventTime()));
      if (this.mFirstSpeed > 0.3F)
      {
        float f2 = getFlingDistance(2, j);
        if (f2 != 0.0F)
        {
          float f3 = 0.8F;
          if (this.mFirstSpeed > f3)
            f3 = this.mFirstSpeed;
          int m = (int)(f2 / f3);
          if (this.mDebug)
            KXLog.d("KXSliderLayout2", "onScroll velocityX >= 0 des:" + f2 + ", duration:" + m + ", speed:" + f3 + ", dest pos:" + (f2 + j));
          notifySlided();
          startFlingAnimation(j, f2 + j, m);
        }
        return true;
      }
    }
    if ((getCenterLayoutLeft() >= this.mLeftLayout.getWidth()) && (f1 > 0.0F))
      return true;
    if ((getCenterLayoutLeft() <= 0) && (f1 < 0.0F))
      return true;
    if (f1 + getCenterLayoutLeft() > this.mLeftLayout.getWidth())
      f1 = this.mLeftLayout.getWidth() - getCenterLayoutLeft();
    while (true)
    {
      if (this.mDebug)
        KXLog.d("KXSliderLayout2", "onScroll offsetX:" + f1);
      notifySlided();
      moveCenterLayout((int)f1);
      invalidate();
      return true;
      if (f1 + getCenterLayoutLeft() >= -this.mScreenWidth)
        continue;
      f1 = -this.mScreenWidth - getCenterLayoutLeft();
    }
  }

  public void onShowPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mDownX == -1.0F)
      return true;
    if (paramMotionEvent.getAction() == 1)
    {
      long l = System.currentTimeMillis();
      if ((this.mTouchEventStartTime > 0L) && (l > this.mTouchEventStartTime))
      {
        this.mTouchEventRetainTime = (l - this.mTouchEventStartTime);
        this.mTouchEventStartTime = -1L;
      }
    }
    if ((paramMotionEvent.getAction() == 0) && (this.mDebug))
      KXLog.d("KXSliderLayout2", "onTouchEvent ACTION_DOWN...");
    while (true)
    {
      boolean bool = this.mDetector.onTouchEvent(paramMotionEvent);
      if ((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3))
        break;
      if (this.mDebug)
        KXLog.d("KXSliderLayout2", "onTouchEvent ACTION_UP or ACTION_CANCEL handled:" + bool);
      if ((this.mHorizontalScrolling) && (!this.mPlayingAnimation))
      {
        regulateViewPosition();
        invalidate();
      }
      this.mDownX = -1.0F;
      this.mDownY = -1.0F;
      this.mVerticalScrolling = false;
      this.mHorizontalScrolling = false;
      return true;
      if ((paramMotionEvent.getAction() != 2) || (!this.mDebug))
        continue;
      KXLog.d("KXSliderLayout2", "onTouchEvent ACTION_MOVE...");
    }
  }

  public void setOnScreenSliderListener(OnScreenSlideListener paramOnScreenSlideListener)
  {
    this.mScreenSlideListener = paramOnScreenSlideListener;
  }

  public void setOnSlideListener(OnSlideListener paramOnSlideListener)
  {
    this.mOnSlideListener = paramOnSlideListener;
  }

  private class KXTranslateAnimation extends Animation
  {
    private float mCollect;
    private float mFromXValue = 0.0F;
    private float mLastXValue;
    private float mToXValue = 0.0F;

    public KXTranslateAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramInt, int arg6)
    {
      this.mFromXValue = paramFloat1;
      this.mToXValue = paramFloat2;
    }

    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      float f1 = paramFloat * (this.mToXValue - this.mFromXValue);
      boolean bool = this.mFromXValue < this.mToXValue;
      float f2 = 0.0F;
      if (bool)
      {
        f2 = f1 - this.mLastXValue;
        this.mLastXValue = f1;
      }
      this.mCollect = (f2 + this.mCollect - (int)f2);
      KXSliderLayout2.this.moveCenterLayout((int)f2 + (int)this.mCollect);
      this.mCollect -= (int)this.mCollect;
    }
  }

  public static abstract interface OnScreenSlideListener
  {
    public static final int DIRECT_LEFT = 0;
    public static final int DIRECT_RIGHT = 1;
    public static final int OFFSET_X = 2;

    public abstract void onSlideTo(int paramInt1, int paramInt2);
  }

  public static abstract interface OnSlideListener
  {
    public abstract void onSlided();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXSliderLayout2
 * JD-Core Version:    0.6.0
 */
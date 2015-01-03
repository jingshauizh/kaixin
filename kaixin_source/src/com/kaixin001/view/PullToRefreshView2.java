package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.kaixin001.util.KXLog;

public class PullToRefreshView2 extends PullToRefreshView
{
  private View mActiveView;
  private JPScaleRunnable mBackHeaderRunnable;
  private int mCurStretchHei;
  private int mMaxStretchHei;

  public PullToRefreshView2(Context paramContext)
  {
    super(paramContext);
    onInit();
  }

  public PullToRefreshView2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    onInit();
  }

  private void onInit()
  {
  }

  private void setActiveViewHeight(float paramFloat)
  {
    int i = this.mCurStretchHei;
    this.mCurStretchHei = (int)(paramFloat + this.mCurStretchHei);
    if (this.mCurStretchHei > this.mMaxStretchHei)
      this.mCurStretchHei = this.mMaxStretchHei;
    while (true)
    {
      int j = this.mCurStretchHei - i;
      KXLog.d("TESTAPP", "setActiveViewHeight:" + paramFloat + " delta:" + j + " mCurStretchHei:" + this.mCurStretchHei);
      ViewGroup.LayoutParams localLayoutParams = this.mActiveView.getLayoutParams();
      localLayoutParams.height = (j + localLayoutParams.height);
      KXLog.d("TESTAPP", "setActiveViewHeight new mlp.height=" + localLayoutParams.height);
      this.mActiveView.setLayoutParams(localLayoutParams);
      return;
      if (this.mCurStretchHei >= 0)
        continue;
      this.mCurStretchHei = 0;
    }
  }

  private void setScale(int paramInt)
  {
    ViewGroup.LayoutParams localLayoutParams = this.mActiveView.getLayoutParams();
    int i = localLayoutParams.height;
    localLayoutParams.height = paramInt;
    this.mCurStretchHei += localLayoutParams.height - i;
    KXLog.d("TESTAPP", "setScale: " + paramInt + ", mCurStretchHei:" + this.mCurStretchHei);
    if (this.mCurStretchHei < 0)
    {
      localLayoutParams.height += 0 - this.mCurStretchHei;
      this.mCurStretchHei = 0;
    }
    this.mActiveView.setLayoutParams(localLayoutParams);
  }

  public void backHeader()
  {
    if (this.mBackHeaderRunnable != null)
      this.mBackHeaderRunnable.stop();
    KXLog.d("TESTAPP", "backHeader  from:" + this.mActiveView.getHeight() + " to:" + (this.mActiveView.getHeight() - this.mCurStretchHei));
    JPScaleRunnable localJPScaleRunnable = new JPScaleRunnable(this.mActiveView, this.mActiveView.getHeight(), this.mActiveView.getHeight() - this.mCurStretchHei, this);
    this.mBackHeaderRunnable = localJPScaleRunnable;
    post(localJPScaleRunnable);
  }

  protected boolean move(float paramFloat, boolean paramBoolean)
  {
    KXLog.d("TESTAPP", "move:" + paramFloat + "," + paramBoolean);
    float f1 = paramFloat * 2.0F;
    if (paramBoolean)
    {
      float f2 = -f1;
      if (((f2 > 0.0F) && (this.mCurStretchHei < this.mMaxStretchHei)) || ((f2 < 0.0F) && (this.mCurStretchHei > 0)))
      {
        setActiveViewHeight(f2);
        return true;
      }
    }
    return false;
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if ((paramFloat1 != 0.0F) && (Math.abs(paramFloat2 / paramFloat1) < 2.0F))
      return false;
    return super.onScroll(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
  }

  protected boolean release()
  {
    KXLog.d("TESTAPP", "release:");
    if (this.mCurStretchHei > 0)
    {
      KXLog.d("TESTAPP", "release: backHeader...");
      backHeader();
      return true;
    }
    return false;
  }

  public void setActiveView(View paramView, int paramInt)
  {
    this.mActiveView = paramView;
    int i = getResources().getDisplayMetrics().widthPixels;
    int j = (int)(paramInt * getResources().getDisplayMetrics().density);
    ViewGroup.LayoutParams localLayoutParams = this.mActiveView.getLayoutParams();
    if ((localLayoutParams != null) && (localLayoutParams.width < i))
    {
      float f = i / localLayoutParams.width;
      localLayoutParams.width = i;
      localLayoutParams.height = (int)(f * localLayoutParams.height);
      j = (int)(f * j);
    }
    this.mMaxStretchHei = j;
    this.mCurStretchHei = 0;
  }

  public class JPScaleRunnable
    implements Runnable
  {
    static final int ANIMATION_DURATION_MS = 190;
    static final int ANIMATION_FPS = 16;
    private boolean continueRunning = true;
    private int currentValue = -1;
    private final int fromValue;
    private final Interpolator interpolator;
    private PullToRefreshView2 mListener;
    private final View mTarget;
    private long startTime = -1L;
    private final int toValue;

    public JPScaleRunnable(View paramInt1, int paramInt2, int paramPullToRefreshView2, PullToRefreshView2 arg5)
    {
      this.mTarget = paramInt1;
      this.fromValue = paramInt2;
      this.toValue = paramPullToRefreshView2;
      this.interpolator = new AccelerateDecelerateInterpolator();
      Object localObject;
      this.mListener = localObject;
    }

    public void run()
    {
      if (this.startTime == -1L)
        this.startTime = System.currentTimeMillis();
      while (true)
      {
        if ((this.continueRunning) && (this.toValue != this.currentValue))
          this.mTarget.postDelayed(this, 16L);
        return;
        long l = Math.max(Math.min(1000L * (System.currentTimeMillis() - this.startTime) / 190L, 1000L), 0L);
        int i = Math.round((this.fromValue - this.toValue) * this.interpolator.getInterpolation((float)l / 1000.0F));
        this.currentValue = (this.fromValue - i);
        KXLog.d("TESTAPP", "JPScaleRunnable run() deltaY=" + i + ", normalizedTime:" + l + " fromValue:" + this.fromValue + " toValue:" + this.toValue);
        if (this.mListener == null)
          continue;
        this.mListener.setScale(this.currentValue);
      }
    }

    public void stop()
    {
      this.continueRunning = false;
      this.mTarget.removeCallbacks(this);
    }
  }

  public static abstract interface ViewScaleRunning
  {
    public abstract void setScale(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.PullToRefreshView2
 * JD-Core Version:    0.6.0
 */
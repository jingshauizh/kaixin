package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

public class PullToRefreshForNewsYearView extends FrameLayout
  implements GestureDetector.OnGestureListener
{
  public static final int STATE_CLOSE = 1;
  public static final int STATE_OPEN = 2;
  public static final int STATE_OPEN_RELEASE = 3;
  public static final int STATE_UPDATE = 4;
  private static boolean isCanPull = true;
  public int MAX_LENGTH = 50;
  private Bitmap bitmap;
  public ImageView mArrow;
  private GestureDetector mDetector;
  protected Flinger mFlinger;
  private RotateAnimation mFlipAnimation;
  private int mPading;
  public ProgressBar mProgressBar;
  private View mPullContainer;
  private View mPullHeader;
  private RotateAnimation mReverseFlipAnimation;
  protected int mState;
  private String mTime = null;
  public TextView mTitle;
  private PullToRefreshListener mUpdateHandle;
  public Context parentActivity;

  public PullToRefreshForNewsYearView(Context paramContext)
  {
    super(paramContext);
    this.parentActivity = paramContext;
    this.mDetector = new GestureDetector(this);
    this.mFlinger = new Flinger();
    this.mState = 1;
    init();
    addUpdateBar();
  }

  public PullToRefreshForNewsYearView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.parentActivity = paramContext;
    this.mDetector = new GestureDetector(this);
    this.mFlinger = new Flinger();
    this.mState = 1;
    init();
    addUpdateBar();
  }

  private void addUpdateBar()
  {
    this.MAX_LENGTH = (int)(getResources().getDisplayMetrics().density * this.MAX_LENGTH);
    this.mFlipAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
    this.mFlipAnimation.setInterpolator(new LinearInterpolator());
    this.mFlipAnimation.setDuration(250L);
    this.mFlipAnimation.setFillAfter(true);
    this.mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
    this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
    this.mReverseFlipAnimation.setDuration(250L);
    this.mReverseFlipAnimation.setFillAfter(true);
    this.mPullHeader = LayoutInflater.from(getContext()).inflate(2130903304, null);
    addView(this.mPullHeader);
    this.mTitle = ((TextView)findViewById(2131363482));
    setHintText(2131428273);
    this.mArrow = ((ImageView)findViewById(2131363485));
    this.mArrow.clearAnimation();
    this.mProgressBar = ((ProgressBar)findViewById(2131363486));
    this.bitmap = BitmapFactory.decodeResource(getResources(), 2130838347);
  }

  private void init()
  {
    this.mDetector.setIsLongpressEnabled(true);
  }

  private void scrollToClose()
  {
    int i = -this.mPading;
    this.mFlinger.startUsingDistance(i, 300);
  }

  private void scrollToUpdate()
  {
    int i = -this.mPading - this.MAX_LENGTH;
    this.mFlinger.startUsingDistance(i, 300);
    if ((this.mState == 4) && (this.mUpdateHandle != null))
      this.mUpdateHandle.onUpdate();
  }

  private void setHintText(int paramInt)
  {
    this.mTitle.setText(paramInt);
  }

  public static void setIsCanPull(boolean paramBoolean)
  {
    if (isCanPull != paramBoolean)
      isCanPull = paramBoolean;
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    float f1 = getWidth();
    float f2 = this.bitmap.getWidth();
    float f3 = this.bitmap.getHeight();
    int i = (int)(f1 / 2.0F - f2 / 2.0F);
    (int)(f1 / 2.0F + f2 / 2.0F);
    int j = (int)(25 + (-this.mPading - this.MAX_LENGTH) - f3);
    if (this.mPading < 25 + (0 - this.MAX_LENGTH))
      paramCanvas.drawBitmap(this.bitmap, i, j, new Paint());
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!isCanPull)
      return super.dispatchTouchEvent(paramMotionEvent);
    boolean bool1 = this.mDetector.onTouchEvent(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    if (i == 1)
    {
      release();
      return super.dispatchTouchEvent(paramMotionEvent);
    }
    if (i == 3)
    {
      boolean bool3 = release();
      super.dispatchTouchEvent(paramMotionEvent);
      return bool3;
    }
    if ((bool1) && ((this.mState == 2) || (this.mState == 3) || (this.mState == 1) || (this.mState == 4)))
    {
      paramMotionEvent.setAction(3);
      boolean bool2 = super.dispatchTouchEvent(paramMotionEvent);
      updateView(0, 0);
      return bool2;
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public boolean isFrefrshing()
  {
    return this.mState == 4;
  }

  protected boolean move(float paramFloat, boolean paramBoolean)
  {
    if ((paramFloat > 0.0F) && (this.mPading >= 0))
      return false;
    if ((this.mState == 4) && (paramFloat < 0.0F) && (Math.abs(paramFloat + this.mPading) > this.MAX_LENGTH))
      return true;
    if (paramBoolean)
    {
      this.mPading = (int)(paramFloat + this.mPading);
      if (this.mPading > 0)
        this.mPading = 0;
      if ((this.mState == 1) || (this.mState == 2) || (this.mState == 3))
        if (Math.abs(this.mPading) < this.MAX_LENGTH)
        {
          if (this.mState != 3)
            break label261;
          setHintText(2131428273);
          this.mArrow.clearAnimation();
          this.mArrow.startAnimation(this.mReverseFlipAnimation);
        }
    }
    while (true)
    {
      this.mState = 2;
      if (this.mPading == 0)
      {
        setHintText(2131428273);
        this.mArrow.clearAnimation();
        this.mProgressBar.setVisibility(8);
        this.mArrow.setVisibility(0);
        this.mState = 1;
      }
      if (Math.abs(this.mPading) >= this.MAX_LENGTH)
      {
        if ((this.mState == 2) || (this.mState == 1))
        {
          setHintText(2131428274);
          this.mArrow.clearAnimation();
          this.mArrow.startAnimation(this.mFlipAnimation);
        }
        this.mState = 3;
      }
      invalidate();
      return true;
      label261: setHintText(2131428273);
    }
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mPullHeader.layout(0, -this.mPading - this.MAX_LENGTH, this.mPullHeader.getMeasuredWidth(), -this.mPading);
    this.mPullContainer = getChildAt(1);
    this.mPullContainer.layout(0, -this.mPading, this.mPullContainer.getMeasuredWidth(), this.mPullContainer.getMeasuredHeight() - this.mPading);
  }

  public void onLongPress(MotionEvent paramMotionEvent)
  {
  }

  public void onRefreshComplete()
  {
    this.mState = 1;
    updateView(1, 0);
    ((ListView)getChildAt(1)).invalidateViews();
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    int i;
    if (Math.abs(paramFloat1) > Math.abs(paramFloat2))
      i = 0;
    float f;
    boolean bool;
    do
    {
      int k;
      do
      {
        AdapterView localAdapterView;
        int j;
        do
        {
          do
          {
            return i;
            f = (float)(0.5D * paramFloat2);
            localAdapterView = (AdapterView)getChildAt(1);
            i = 1;
            if ((localAdapterView != null) && (localAdapterView.getCount() != 0))
              continue;
            i = 0;
          }
          while (i == 0);
          j = localAdapterView.getFirstVisiblePosition();
          i = 0;
        }
        while (j != 0);
        k = localAdapterView.getChildAt(0).getTop();
        i = 0;
      }
      while (k < 0);
      bool = Math.abs(paramFloat2) < Math.abs(paramFloat1);
      i = 0;
    }
    while (!bool);
    return move(f, true);
  }

  public void onShowPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }

  protected boolean release()
  {
    if (this.mPading >= 0);
    int i = 1;
    if (this.mState == 4)
      i = 0;
    do
    {
      return i;
      if (Math.abs(this.mPading) >= this.MAX_LENGTH)
        continue;
      this.mState = 1;
      scrollToClose();
    }
    while (Math.abs(this.mPading) < this.MAX_LENGTH);
    this.mState = 4;
    scrollToUpdate();
    return i;
  }

  public void setHeaderBackground(int paramInt)
  {
    setBackgroundColor(paramInt);
  }

  public void setPullToRefreshListener(PullToRefreshListener paramPullToRefreshListener)
  {
    this.mUpdateHandle = paramPullToRefreshListener;
  }

  public void setUpdateTime(String paramString)
  {
    this.mTime = paramString;
  }

  public void showRefreshing()
  {
    if (isFrefrshing())
      return;
    this.mState = 4;
    updateView(1, this.MAX_LENGTH);
  }

  protected void updateView(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1)
      if (this.mState == 4)
      {
        getChildAt(1).offsetTopAndBottom(0 - getChildAt(1).getTop() + this.MAX_LENGTH - paramInt2);
        this.mPullHeader.offsetTopAndBottom(0 - this.mPullHeader.getTop() - paramInt2);
        this.mPading = (paramInt2 + -this.MAX_LENGTH);
        setHintText(2131428275);
        this.mProgressBar.setVisibility(0);
        this.mArrow.clearAnimation();
        this.mArrow.setVisibility(8);
      }
    while (true)
    {
      invalidate();
      return;
      if (this.mState != 1)
        continue;
      this.mPullContainer.offsetTopAndBottom(0 - this.mPullContainer.getTop());
      this.mPullHeader.offsetTopAndBottom(0 - this.mPullHeader.getTop() - this.MAX_LENGTH);
      setHintText(2131428273);
      this.mPading = 0;
      this.mArrow.clearAnimation();
      this.mProgressBar.setVisibility(8);
      this.mArrow.setVisibility(0);
      continue;
      int i = Math.abs(this.mPading) - this.mPullContainer.getTop();
      this.mPullContainer.offsetTopAndBottom(Math.abs(this.mPading) - this.mPullContainer.getTop());
      this.mPullHeader.offsetTopAndBottom(i);
    }
  }

  protected class Flinger
    implements Runnable
  {
    protected int mLastFlingX;
    protected Scroller mScroller = new Scroller(PullToRefreshForNewsYearView.this.getContext());

    public Flinger()
    {
    }

    public void run()
    {
      int i = this.mScroller.getCurrX();
      PullToRefreshForNewsYearView.this.move(this.mLastFlingX - i, false);
      PullToRefreshForNewsYearView.this.updateView(1, 0);
      if (this.mScroller.computeScrollOffset())
      {
        this.mLastFlingX = i;
        PullToRefreshForNewsYearView.this.post(this);
        return;
      }
      PullToRefreshForNewsYearView.this.removeCallbacks(this);
    }

    protected void startCommon()
    {
      PullToRefreshForNewsYearView.this.removeCallbacks(this);
    }

    public void startUsingDistance(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 0)
        paramInt1--;
      startCommon();
      this.mLastFlingX = 0;
      this.mScroller.startScroll(0, 0, -paramInt1, 0, paramInt2);
      PullToRefreshForNewsYearView.this.post(this);
    }
  }

  public static abstract interface PullToRefreshListener
  {
    public abstract void onUpdate();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.PullToRefreshForNewsYearView
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class KXSlidingDrawer extends FrameLayout
  implements View.OnTouchListener
{
  private static final int ANIM_HEIGHT_STEP = 40;
  private static final int ANIM_PERIOD = 10;
  private static final int CLICK_GAP_TIME = 500;
  private View content = null;
  private View handleDown = null;
  private View handleUp = null;
  private Handler handler = new Handler();
  private Runnable hideContentTask = new Runnable()
  {
    public void run()
    {
      int i = KXSlidingDrawer.this.handleUp.getBottom() - KXSlidingDrawer.this.handleUp.getTop();
      if (KXSlidingDrawer.this.content.getBottom() < i + 40)
      {
        KXSlidingDrawer.this.hideContentInternal(false);
        return;
      }
      ViewGroup.LayoutParams localLayoutParams = KXSlidingDrawer.this.content.getLayoutParams();
      localLayoutParams.height = (-40 + KXSlidingDrawer.this.content.getBottom());
      localLayoutParams.width = -1;
      KXSlidingDrawer.this.content.setLayoutParams(localLayoutParams);
      KXSlidingDrawer.this.content.setVisibility(0);
      KXSlidingDrawer.this.handler.postDelayed(KXSlidingDrawer.this.hideContentTask, 10L);
    }
  };
  private long pressTime = 0L;
  private Runnable showContentTask = new Runnable()
  {
    public void run()
    {
      int i = KXSlidingDrawer.this.getBottom() - KXSlidingDrawer.this.getTop();
      if (KXSlidingDrawer.this.content.getBottom() > i - 40)
      {
        KXSlidingDrawer.this.showContentInternal(false);
        return;
      }
      ViewGroup.LayoutParams localLayoutParams = KXSlidingDrawer.this.content.getLayoutParams();
      localLayoutParams.height = (40 + KXSlidingDrawer.this.content.getBottom());
      localLayoutParams.width = -1;
      KXSlidingDrawer.this.content.setLayoutParams(localLayoutParams);
      KXSlidingDrawer.this.content.setVisibility(0);
      KXSlidingDrawer.this.handler.postDelayed(KXSlidingDrawer.this.showContentTask, 10L);
    }
  };
  private State state = State.HIDE;
  private OnStateChangedListener stateChangedListener = null;
  private State touchDownState = State.HIDE;

  public KXSlidingDrawer(Context paramContext)
  {
    super(paramContext);
  }

  public KXSlidingDrawer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXSlidingDrawer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void hideContentInternal(boolean paramBoolean)
  {
    this.state = State.IS_HIDING;
    if (paramBoolean)
    {
      this.handler.postDelayed(this.hideContentTask, 10L);
      return;
    }
    ViewGroup.LayoutParams localLayoutParams = this.content.getLayoutParams();
    localLayoutParams.height = 0;
    localLayoutParams.width = -1;
    this.content.setLayoutParams(localLayoutParams);
    if (this.stateChangedListener != null)
      this.stateChangedListener.onHideContent();
    this.state = State.HIDE;
  }

  private void pullContent(int paramInt)
  {
    int[] arrayOfInt = new int[2];
    this.handleDown.getLocationOnScreen(arrayOfInt);
    int i = this.handleUp.getBottom() - this.handleUp.getTop();
    int j = getBottom() - getTop();
    int k = paramInt - arrayOfInt[1];
    if ((k < i) || (k > j))
      return;
    ViewGroup.LayoutParams localLayoutParams = this.content.getLayoutParams();
    localLayoutParams.height = k;
    localLayoutParams.width = -1;
    this.content.setLayoutParams(localLayoutParams);
    this.content.setVisibility(0);
  }

  private void showContentInternal(boolean paramBoolean)
  {
    this.state = State.IS_SHOWING;
    if (paramBoolean)
    {
      this.handler.postDelayed(this.showContentTask, 10L);
      return;
    }
    ViewGroup.LayoutParams localLayoutParams = this.content.getLayoutParams();
    localLayoutParams.height = -1;
    this.content.setLayoutParams(localLayoutParams);
    this.content.setVisibility(0);
    if (this.stateChangedListener != null)
      this.stateChangedListener.onShowContent();
    this.state = State.SHOW;
  }

  private void showOrHideContent(int paramInt, boolean paramBoolean)
  {
    ViewGroup.LayoutParams localLayoutParams = this.content.getLayoutParams();
    int i = (getBottom() - getTop()) / 2 + getTop();
    if (localLayoutParams.height > i)
    {
      showContentInternal(paramBoolean);
      return;
    }
    hideContentInternal(paramBoolean);
  }

  public State getState()
  {
    return this.state;
  }

  public void hideContent(boolean paramBoolean)
  {
    if ((this.state == State.IS_HIDING) || (this.state == State.HIDE))
      return;
    if (this.stateChangedListener != null)
      this.stateChangedListener.onScrollContent();
    hideContentInternal(paramBoolean);
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 2:
      do
      {
        do
        {
          return true;
          this.pressTime = System.currentTimeMillis();
          this.touchDownState = this.state;
        }
        while (((this.touchDownState != State.HIDE) || (paramView != this.handleDown)) && ((this.touchDownState != State.SHOW) || (paramView != this.handleUp) || (this.stateChangedListener == null)));
        this.stateChangedListener.onScrollContent();
        return true;
      }
      while (((this.touchDownState != State.HIDE) || (paramView != this.handleDown)) && ((this.touchDownState != State.SHOW) || (paramView != this.handleUp)));
      pullContent((int)paramMotionEvent.getRawY());
      return true;
    case 1:
    }
    if (System.currentTimeMillis() - this.pressTime < 500L)
      if (this.state == State.SHOW)
      {
        if ((this.touchDownState == State.SHOW) && (paramView == this.handleDown) && (this.stateChangedListener != null))
          this.stateChangedListener.onScrollContent();
        hideContentInternal(true);
      }
    while (true)
    {
      this.pressTime = 0L;
      return true;
      if (this.state != State.HIDE)
        continue;
      showContentInternal(true);
      continue;
      if (((this.touchDownState != State.HIDE) || (paramView != this.handleDown)) && ((this.touchDownState != State.SHOW) || (paramView != this.handleUp)))
        continue;
      showOrHideContent((int)paramMotionEvent.getRawY(), true);
    }
  }

  public void setHandleAndContent(int paramInt1, int paramInt2, int paramInt3)
  {
    this.handleDown = findViewById(paramInt1);
    this.handleDown.setClickable(true);
    this.handleDown.setOnTouchListener(this);
    this.handleUp = findViewById(paramInt2);
    this.handleUp.setClickable(true);
    this.handleUp.setOnTouchListener(this);
    this.content = findViewById(paramInt3);
    this.content.setClickable(true);
    this.content.setVisibility(8);
  }

  public void setOnStateChangedListener(OnStateChangedListener paramOnStateChangedListener)
  {
    this.stateChangedListener = paramOnStateChangedListener;
  }

  public void showContent(boolean paramBoolean)
  {
    if ((this.state == State.IS_SHOWING) || (this.state == State.SHOW))
      return;
    if (this.stateChangedListener != null)
      this.stateChangedListener.onScrollContent();
    showContentInternal(paramBoolean);
  }

  public static abstract interface OnStateChangedListener
  {
    public abstract void onHideContent();

    public abstract void onScrollContent();

    public abstract void onShowContent();
  }

  public static enum State
  {
    static
    {
      IS_HIDING = new State("IS_HIDING", 2);
      HIDE = new State("HIDE", 3);
      State[] arrayOfState = new State[4];
      arrayOfState[0] = IS_SHOWING;
      arrayOfState[1] = SHOW;
      arrayOfState[2] = IS_HIDING;
      arrayOfState[3] = HIDE;
      ENUM$VALUES = arrayOfState;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXSlidingDrawer
 * JD-Core Version:    0.6.0
 */
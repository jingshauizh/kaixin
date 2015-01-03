package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class KXHorizScrollView extends HorizontalScrollView
{
  public KXHorizScrollView(Context paramContext)
  {
    super(paramContext);
  }

  public KXHorizScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXHorizScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void fling(int paramInt)
  {
    super.fling(paramInt / 4);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
      KXSliderLayout2.mCansHorizontalScrolling = false;
    return super.onInterceptTouchEvent(paramMotionEvent);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (bool)
      KXSliderLayout2.mCansHorizontalScrolling = false;
    return bool;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXHorizScrollView
 * JD-Core Version:    0.6.0
 */
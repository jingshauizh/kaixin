package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class KXHorizontalListView extends HorizontalListView
{
  public KXHorizontalListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
      KXSliderLayout2.mCansHorizontalScrolling = false;
    return super.onInterceptTouchEvent(paramMotionEvent);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (super.onTouchEvent(paramMotionEvent))
      KXSliderLayout2.mCansHorizontalScrolling = false;
    return super.onTouchEvent(paramMotionEvent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXHorizontalListView
 * JD-Core Version:    0.6.0
 */
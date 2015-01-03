package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class KXGameListView extends KXListView
{
  private int endX;
  private int endY;
  private int startX;
  private int startY;

  public KXGameListView(Context paramContext)
  {
    super(paramContext);
  }

  public KXGameListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXGameListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    do
    {
      while (true)
      {
        return super.onInterceptTouchEvent(paramMotionEvent);
        this.startX = (int)paramMotionEvent.getX();
        this.startY = (int)paramMotionEvent.getY();
      }
      this.endX = (int)paramMotionEvent.getX();
      this.endY = (int)paramMotionEvent.getY();
    }
    while (Math.abs(this.endX - this.startX) <= Math.abs(this.endY - this.startY));
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXGameListView
 * JD-Core Version:    0.6.0
 */
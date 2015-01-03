package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import com.kaixin001.activity.KXActivity;

public class KXHorizontalScrollView extends HorizontalScrollView
{
  private Context mContext = null;

  public KXHorizontalScrollView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public KXHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }

  public KXHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      if ((this.mContext != null) && ((this.mContext instanceof KXActivity)))
        ((KXActivity)this.mContext).enableSlideBack(false);
      KXSliderLayout2.mCansHorizontalScrolling = false;
    }
    while (true)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      if (((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3)) || (this.mContext == null) || (!(this.mContext instanceof KXActivity)))
        continue;
      ((KXActivity)this.mContext).enableSlideBack(true);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (bool)
    {
      if ((this.mContext != null) && ((this.mContext instanceof KXActivity)))
        ((KXActivity)this.mContext).enableSlideBack(false);
      KXSliderLayout2.mCansHorizontalScrolling = false;
    }
    if (((paramMotionEvent.getAction() == 1) || (paramMotionEvent.getAction() == 3)) && (this.mContext != null) && ((this.mContext instanceof KXActivity)))
      ((KXActivity)this.mContext).enableSlideBack(true);
    return bool;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXHorizontalScrollView
 * JD-Core Version:    0.6.0
 */
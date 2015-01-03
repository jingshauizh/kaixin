package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class PullToRefreshView3 extends PullToRefreshView
{
  public PullToRefreshView3(Context paramContext)
  {
    super(paramContext);
  }

  public PullToRefreshView3(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void onRefreshComplete()
  {
    this.mState = 1;
    updateView(1, 0);
    getChildAt(1).invalidate();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.PullToRefreshView3
 * JD-Core Version:    0.6.0
 */
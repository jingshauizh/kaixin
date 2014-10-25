package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

public class KaiXinProgressBar extends ProgressBar
{
  Context mContext;

  public KaiXinProgressBar(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public KaiXinProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }

  public KaiXinProgressBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (paramInt == 0)
    {
      setAnimation(AnimationUtils.loadAnimation(this.mContext, 2130968595));
      return;
    }
    clearAnimation();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KaiXinProgressBar
 * JD-Core Version:    0.6.0
 */
package com.kaixin001.ugc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;

public class InOutImageButton extends ImageButton
{
  private Animation animation;

  public InOutImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onAnimationEnd()
  {
    super.onAnimationEnd();
    if (!(this.animation instanceof InOutAnimation));
    do
      return;
    while (((InOutAnimation)this.animation).mDirection != InOutAnimation.Direction.OUT);
    setVisibility(8);
  }

  protected void onAnimationStart()
  {
    super.onAnimationStart();
    if ((this.animation instanceof InOutAnimation))
      setVisibility(0);
  }

  public void startAnimation(Animation paramAnimation)
  {
    super.startAnimation(paramAnimation);
    this.animation = paramAnimation;
    getRootView().postInvalidate();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.ugc.InOutImageButton
 * JD-Core Version:    0.6.0
 */
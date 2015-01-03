package com.kaixin001.ugc;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public abstract class InOutAnimation extends AnimationSet
{
  public final Direction mDirection;

  public InOutAnimation(Direction paramDirection, long paramLong, View paramView)
  {
    super(true);
    this.mDirection = paramDirection;
    if (this.mDirection == Direction.IN)
      addInAnimation(paramView);
    while (true)
    {
      setDuration(paramLong);
      return;
      if (this.mDirection != Direction.OUT)
        continue;
      addOutAnimation(paramView);
    }
  }

  protected abstract void addInAnimation(View paramView);

  protected abstract void addOutAnimation(View paramView);

  public static enum Direction
  {
    static
    {
      Direction[] arrayOfDirection = new Direction[2];
      arrayOfDirection[0] = IN;
      arrayOfDirection[1] = OUT;
      ENUM$VALUES = arrayOfDirection;
    }
  }

  public static final class InOutTranslateup extends InOutAnimation
  {
    public InOutTranslateup(InOutAnimation.Direction paramDirection, long paramLong, View paramView)
    {
      super(paramLong, paramView);
    }

    protected void addInAnimation(View paramView)
    {
      int i = paramView.getHeight();
      int j = paramView.getTop();
      addAnimation(new TranslateAnimation(0.0F, 0.0F, -i - j, 0.0F));
    }

    protected void addOutAnimation(View paramView)
    {
      int i = paramView.getHeight();
      int j = paramView.getTop();
      addAnimation(new TranslateAnimation(0.0F, 0.0F, 0.0F, -i - j));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.ugc.InOutAnimation
 * JD-Core Version:    0.6.0
 */
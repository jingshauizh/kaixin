package com.tencent.mm.sdk.platformtools;

import android.view.View;
import android.view.animation.Animation;

class AnimationHelperImpl21below
  implements BackwardSupportUtil.AnimationHelper.IHelper
{
  public void cancelAnimation(View paramView, Animation paramAnimation)
  {
    if (paramView != null)
      paramView.setAnimation(null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.AnimationHelperImpl21below
 * JD-Core Version:    0.6.0
 */
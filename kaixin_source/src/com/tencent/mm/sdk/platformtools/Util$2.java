package com.tencent.mm.sdk.platformtools;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

final class Util$2
  implements View.OnTouchListener
{
  public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
      return false;
    case 0:
      this.bD.setSelected(true);
      return false;
    case 1:
    case 3:
    case 4:
      this.bD.setSelected(false);
      return false;
    case 2:
    }
    this.bD.setSelected(this.bE.isPressed());
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.Util.2
 * JD-Core Version:    0.6.0
 */
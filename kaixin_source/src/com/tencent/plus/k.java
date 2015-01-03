package com.tencent.plus;

import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

class k
  implements ViewTreeObserver.OnGlobalLayoutListener
{
  k(ImageActivity paramImageActivity)
  {
  }

  public void onGlobalLayout()
  {
    this.a.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    ImageActivity.a(this.a, ImageActivity.a(this.a).a());
    ImageActivity.c(this.a).a(ImageActivity.b(this.a));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.k
 * JD-Core Version:    0.6.0
 */
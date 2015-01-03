package com.tencent.plus;

import android.view.View;
import android.view.View.OnClickListener;

class g
  implements View.OnClickListener
{
  g(ImageActivity paramImageActivity)
  {
  }

  public void onClick(View paramView)
  {
    long l = System.currentTimeMillis() - ImageActivity.i(this.a);
    this.a.a("10656", l);
    this.a.setResult(0);
    ImageActivity.j(this.a);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.g
 * JD-Core Version:    0.6.0
 */
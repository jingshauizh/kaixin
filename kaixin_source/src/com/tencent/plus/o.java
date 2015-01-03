package com.tencent.plus;

import android.widget.Button;
import android.widget.ProgressBar;

class o
  implements Runnable
{
  o(i parami)
  {
  }

  public void run()
  {
    ImageActivity.e(this.a.a).setEnabled(true);
    ImageActivity.e(this.a.a).setTextColor(-1);
    ImageActivity.f(this.a.a).setEnabled(true);
    ImageActivity.f(this.a.a).setTextColor(-1);
    ImageActivity.f(this.a.a).setText("重试");
    ImageActivity.d(this.a.a).setVisibility(8);
    ImageActivity.a(this.a.a, true);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.o
 * JD-Core Version:    0.6.0
 */
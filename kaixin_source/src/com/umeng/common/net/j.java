package com.umeng.common.net;

import android.widget.Toast;
import com.umeng.common.a.c;

class j
  implements Runnable
{
  j(DownloadingService.b paramb)
  {
  }

  public void run()
  {
    Toast.makeText(this.a.a, c.h(DownloadingService.b.a(this.a)), 0).show();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.j
 * JD-Core Version:    0.6.0
 */
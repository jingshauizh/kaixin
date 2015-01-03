package com.umeng.common.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class f extends BroadcastReceiver
{
  f(DownloadingService paramDownloadingService)
  {
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    DownloadingService.a(this.a, paramContext, paramIntent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.f
 * JD-Core Version:    0.6.0
 */
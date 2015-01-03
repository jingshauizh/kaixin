package com.tencent.mm.sdk.platformtools;

import android.os.HandlerThread;

class MMHandlerThread$1
  implements MMHandlerThread.IWaitWorkThread
{
  public boolean doInBackground()
  {
    if (this.aq != null)
      return this.aq.doInBackground();
    MMHandlerThread.a(this.ar).quit();
    MMHandlerThread.b(this.ar);
    return true;
  }

  public boolean onPostExecute()
  {
    if (this.aq != null)
      return this.aq.onPostExecute();
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MMHandlerThread.1
 * JD-Core Version:    0.6.0
 */
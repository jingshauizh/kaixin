package com.tencent.mm.sdk.platformtools;

import android.os.HandlerThread;

class MMHandlerThread$2
  implements MMHandlerThread.IWaitWorkThread
{
  public boolean doInBackground()
  {
    Log.d("MicroMsg.MMHandlerThread", "syncReset doInBackground");
    MMHandlerThread.a(this.ar).quit();
    if (this.as != null)
      this.as.callback();
    MMHandlerThread.b(this.ar);
    synchronized (this.at)
    {
      this.at.notify();
      return true;
    }
  }

  public boolean onPostExecute()
  {
    Log.d("MicroMsg.MMHandlerThread", "syncReset onPostExecute");
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MMHandlerThread.2
 * JD-Core Version:    0.6.0
 */
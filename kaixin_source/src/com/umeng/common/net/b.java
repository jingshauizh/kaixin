package com.umeng.common.net;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

class b
  implements ServiceConnection
{
  b(a parama)
  {
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    Log.d(a.b(), "ServiceConnection.onServiceConnected");
    a.a(this.a, new Messenger(paramIBinder));
    if (a.a(this.a) != null)
      a.a(this.a).a();
    try
    {
      Message localMessage = Message.obtain(null, 4);
      a.a locala = new a.a(a.d(this.a), a.e(this.a), a.f(this.a));
      locala.d = a.g(this.a);
      locala.e = a.h(this.a);
      localMessage.setData(locala.a());
      localMessage.replyTo = this.a.a;
      a.i(this.a).send(localMessage);
      return;
    }
    catch (RemoteException localRemoteException)
    {
    }
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    Log.d(a.b(), "ServiceConnection.onServiceDisconnected");
    a.a(this.a, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.b
 * JD-Core Version:    0.6.0
 */
package com.tencent.mm.sdk.platformtools;

import java.util.Comparator;

class ObserverPool$1
  implements Comparator<ObserverPool.Listener>
{
  public int compare(ObserverPool.Listener paramListener1, ObserverPool.Listener paramListener2)
  {
    return ObserverPool.Listener.a(paramListener2) - ObserverPool.Listener.a(paramListener1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.ObserverPool.1
 * JD-Core Version:    0.6.0
 */
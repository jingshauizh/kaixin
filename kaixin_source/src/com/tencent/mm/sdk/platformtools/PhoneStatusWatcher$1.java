package com.tencent.mm.sdk.platformtools;

import android.telephony.PhoneStateListener;
import java.util.Iterator;
import java.util.List;

class PhoneStatusWatcher$1 extends PhoneStateListener
{
  public void onCallStateChanged(int paramInt, String paramString)
  {
    Iterator localIterator = PhoneStatusWatcher.a(this.aH).iterator();
    while (localIterator.hasNext())
      ((PhoneStatusWatcher.PhoneCallListener)localIterator.next()).onPhoneCall(paramInt);
    super.onCallStateChanged(paramInt, paramString);
    switch (paramInt)
    {
    default:
      return;
    case 0:
      PhoneStatusWatcher.a(false);
      return;
    case 1:
    case 2:
    }
    PhoneStatusWatcher.a(true);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneStatusWatcher.1
 * JD-Core Version:    0.6.0
 */
package com.tencent.mm.sdk.platformtools;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

class PhoneUtil16Impl$1 extends PhoneStateListener
{
  public void onSignalStrengthChanged(int paramInt)
  {
    super.onSignalStrengthChanged(paramInt);
    PhoneUtil16Impl.c(-113 + paramInt * 2);
    if (PhoneUtil16Impl.a(this.aN) != null)
      PhoneUtil16Impl.a(this.aN).listen(PhoneUtil16Impl.b(this.aN), 0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneUtil16Impl.1
 * JD-Core Version:    0.6.0
 */
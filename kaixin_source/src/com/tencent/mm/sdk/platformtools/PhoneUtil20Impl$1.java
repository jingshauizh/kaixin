package com.tencent.mm.sdk.platformtools;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

class PhoneUtil20Impl$1 extends PhoneStateListener
{
  public void onSignalStrengthsChanged(SignalStrength paramSignalStrength)
  {
    super.onSignalStrengthsChanged(paramSignalStrength);
    if (PhoneUtil20Impl.a(this.aP) == 2)
      PhoneUtil20Impl.d(paramSignalStrength.getCdmaDbm());
    if (PhoneUtil20Impl.a(this.aP) == 1)
      PhoneUtil20Impl.d(-113 + 2 * paramSignalStrength.getGsmSignalStrength());
    if (PhoneUtil20Impl.b(this.aP) != null)
      PhoneUtil20Impl.b(this.aP).listen(PhoneUtil20Impl.c(this.aP), 0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneUtil20Impl.1
 * JD-Core Version:    0.6.0
 */
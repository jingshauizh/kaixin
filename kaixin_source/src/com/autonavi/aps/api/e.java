package com.autonavi.aps.api;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import java.util.ArrayList;

final class e extends PhoneStateListener
{
  public final void onCellLocationChanged(CellLocation paramCellLocation)
  {
    if (paramCellLocation == null)
      Utils.writeLogCat("cell location changed, is null");
    while (true)
    {
      if (APSYUNPINGTAI.c() != null);
      try
      {
        APSYUNPINGTAI.a(paramCellLocation, APSYUNPINGTAI.c().getNeighboringCellInfo());
        if (paramCellLocation == null);
      }
      catch (Exception localException2)
      {
        try
        {
          super.onCellLocationChanged(paramCellLocation);
          return;
          Utils.writeLogCat("cell location changed, not null");
          continue;
          localException2 = localException2;
          Utils.printException(localException2);
        }
        catch (Exception localException1)
        {
          Utils.printException(localException1);
        }
      }
    }
  }

  public final void onServiceStateChanged(ServiceState paramServiceState)
  {
    super.onServiceStateChanged(paramServiceState);
  }

  public final void onSignalStrengthsChanged(SignalStrength paramSignalStrength)
  {
    Utils.writeLogCat("cell signal strength change");
    APSYUNPINGTAI.a(-113 + 2 * paramSignalStrength.getGsmSignalStrength());
    if ((APSYUNPINGTAI.d() == 1) && (APSYUNPINGTAI.e().size() > 0))
      ((GsmCellBean)APSYUNPINGTAI.e().get(0)).setSignal(APSYUNPINGTAI.f());
    while (true)
    {
      super.onSignalStrengthsChanged(paramSignalStrength);
      return;
      if ((APSYUNPINGTAI.d() == 2) && (APSYUNPINGTAI.g().size() > 0))
      {
        ((CdmaCellBean)APSYUNPINGTAI.g().get(0)).setSignal(APSYUNPINGTAI.f());
        continue;
      }
      Utils.writeLogCat("unknown phone type");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.e
 * JD-Core Version:    0.6.0
 */
package com.autonavi.aps.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.List;

final class f extends BroadcastReceiver
{
  private f(APSYUNPINGTAI paramAPSYUNPINGTAI, byte paramByte)
  {
  }

  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    Utils.writeLogCat("receive " + paramIntent.getAction());
    if (paramIntent.getAction().equalsIgnoreCase("android.net.wifi.SCAN_RESULTS"))
    {
      APSYUNPINGTAI.a(APSYUNPINGTAI.a().getScanResults());
      if (APSYUNPINGTAI.b() == null)
        APSYUNPINGTAI.a(new ArrayList());
      Utils.writeLogCat("wifi scan " + String.valueOf(APSYUNPINGTAI.b().size()) + " results");
      return;
    }
    if ((paramIntent.getAction().equalsIgnoreCase("android.intent.action.TIME_SET")) || (paramIntent.getAction().equalsIgnoreCase("android.intent.action.TIMEZONE_CHANGED")))
    {
      APSYUNPINGTAI.a(0L);
      return;
    }
    switch (paramIntent.getIntExtra("wifi_state", 4))
    {
    case 2:
    case 3:
    default:
      return;
    case 0:
      APSYUNPINGTAI.b().clear();
      APSYUNPINGTAI.a(null);
      return;
    case 1:
      APSYUNPINGTAI.b().clear();
      APSYUNPINGTAI.a(null);
      return;
    case 4:
    }
    APSYUNPINGTAI.b().clear();
    APSYUNPINGTAI.a(null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.f
 * JD-Core Version:    0.6.0
 */
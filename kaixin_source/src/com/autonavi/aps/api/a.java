package com.autonavi.aps.api;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

final class a
  implements LocationListener
{
  public final void onLocationChanged(Location paramLocation)
  {
    if ((paramLocation != null) && (paramLocation.getProvider().equalsIgnoreCase("gps")))
    {
      Utils.writeLogCat("receive gps provider location");
      APSYUNPINGTAI.a(paramLocation);
    }
  }

  public final void onProviderDisabled(String paramString)
  {
    if (paramString.equals("gps"))
    {
      Utils.writeLogCat("gps provider disabled");
      APSYUNPINGTAI.a(null);
    }
  }

  public final void onProviderEnabled(String paramString)
  {
    if (paramString.equalsIgnoreCase("gps"))
      Utils.writeLogCat("gps provider is enabled");
  }

  public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
    if (paramString.equalsIgnoreCase("gps"))
    {
      Utils.writeLogCat("gps provider status changed to " + String.valueOf(paramInt));
      if (paramInt == 0)
        APSYUNPINGTAI.a(null);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.a
 * JD-Core Version:    0.6.0
 */
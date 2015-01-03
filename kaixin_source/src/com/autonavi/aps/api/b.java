package com.autonavi.aps.api;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

final class b
  implements LocationListener
{
  public final void onLocationChanged(Location paramLocation)
  {
    if ((paramLocation != null) && (paramLocation.getProvider().equalsIgnoreCase("network")))
    {
      Utils.writeLogCat("receive system network provider location");
      APSYUNPINGTAI.b(paramLocation);
    }
  }

  public final void onProviderDisabled(String paramString)
  {
    if (paramString.equals("network"))
    {
      Utils.writeLogCat("system network provider disabled");
      APSYUNPINGTAI.b(null);
    }
  }

  public final void onProviderEnabled(String paramString)
  {
    if (paramString.equalsIgnoreCase("network"))
      Utils.writeLogCat("system network provider is enabled");
  }

  public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
    if (paramString.equalsIgnoreCase("network"))
    {
      Utils.writeLogCat("system network provider status changed to " + String.valueOf(paramInt));
      if (paramInt == 0)
        APSYUNPINGTAI.b(null);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.b
 * JD-Core Version:    0.6.0
 */
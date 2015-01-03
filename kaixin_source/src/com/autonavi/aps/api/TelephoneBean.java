package com.autonavi.aps.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;

public class TelephoneBean
{
  private static String a = null;
  private static TelephoneBean b = null;

  public static TelephoneBean getInstance(TelephonyManager paramTelephonyManager, Context paramContext, String paramString)
  {
    String str;
    if (b == null)
    {
      b = new TelephoneBean();
      a = paramTelephonyManager.getDeviceId();
      str = "";
      if ((paramString != null) && (paramString.length() > 0))
        break label145;
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("APS_Preferences", 2);
      str = localSharedPreferences.getString("imeisalt", "");
      if ((str == null) || (str.length() <= 0) || (str.equalsIgnoreCase("null")))
      {
        str = String.valueOf((int)(10000.0D * Math.random()));
        localSharedPreferences.edit().putString("APS_Preferences", a).commit();
      }
    }
    while (true)
    {
      a = a + "." + str;
      return b;
      label145: if (!paramString.equalsIgnoreCase("lenovodualcard"))
        continue;
      str = String.valueOf(10000);
    }
  }

  public String getDeviceId()
  {
    return a;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.TelephoneBean
 * JD-Core Version:    0.6.0
 */
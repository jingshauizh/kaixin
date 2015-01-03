package com.autonavi.aps.api;

import android.content.Context;

public class APSFactory
{
  private static IAPS a = null;

  public static IAPS getInstance(Context paramContext)
  {
    APSYUNPINGTAI localAPSYUNPINGTAI = APSYUNPINGTAI.getInstance(paramContext);
    a = localAPSYUNPINGTAI;
    return localAPSYUNPINGTAI;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.APSFactory
 * JD-Core Version:    0.6.0
 */
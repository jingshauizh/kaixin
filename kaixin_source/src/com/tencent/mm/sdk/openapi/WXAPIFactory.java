package com.tencent.mm.sdk.openapi;

import android.content.Context;

public class WXAPIFactory
{
  private static WXAPIFactory p = null;

  public static IWXAPI createWXAPI(Context paramContext, String paramString)
  {
    if (p == null)
      p = new WXAPIFactory();
    return new WXApiImplV10(paramContext, paramString);
  }

  public static IWXAPI createWXAPI(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (p == null)
      p = new WXAPIFactory();
    return new WXApiImplV10(paramContext, paramString, paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.openapi.WXAPIFactory
 * JD-Core Version:    0.6.0
 */
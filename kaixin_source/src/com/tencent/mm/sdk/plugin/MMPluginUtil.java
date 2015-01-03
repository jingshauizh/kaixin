package com.tencent.mm.sdk.plugin;

import android.content.Context;

public class MMPluginUtil
{
  public static IMMPluginAPI queryPluginMgr(Context paramContext)
  {
    return new MMPluginAPIImpl(paramContext);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.MMPluginUtil
 * JD-Core Version:    0.6.0
 */
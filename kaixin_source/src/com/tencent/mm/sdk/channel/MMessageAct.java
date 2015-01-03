package com.tencent.mm.sdk.channel;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;

public class MMessageAct
{
  public static boolean send(Context paramContext, String paramString1, String paramString2)
  {
    return send(paramContext, paramString1, paramString1 + ".wxapi.WXEntryActivity", paramString2, null);
  }

  public static boolean send(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return send(paramContext, paramString1, paramString1 + ".wxapi.WXEntryActivity", paramString2, paramBundle);
  }

  public static boolean send(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    if ((paramContext == null) || (paramString1 == null) || (paramString1.length() == 0) || (paramString2 == null) || (paramString2.length() == 0))
    {
      Log.e("MicroMsg.SDK.MMessageAct", "send fail, invalid arguments");
      return false;
    }
    Intent localIntent = new Intent();
    localIntent.setClassName(paramString1, paramString2);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    String str = paramContext.getPackageName();
    localIntent.putExtra("_mmessage_sdkVersion", 553910273);
    localIntent.putExtra("_mmessage_appPackage", str);
    localIntent.putExtra("_mmessage_content", paramString3);
    localIntent.putExtra("_mmessage_checksum", MMessageUtil.a(paramString3, str));
    localIntent.addFlags(268435456).addFlags(134217728);
    try
    {
      paramContext.startActivity(localIntent);
      Log.d("MicroMsg.SDK.MMessageAct", "send mm message, intent=" + localIntent);
      return true;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      Log.e("MicroMsg.SDK.MMessageAct", "send fail, target ActivityNotFound");
    }
    return false;
  }

  public static boolean sendToWx(Context paramContext, String paramString)
  {
    return send(paramContext, "com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity", paramString, null);
  }

  public static boolean sendToWx(Context paramContext, String paramString, Bundle paramBundle)
  {
    return send(paramContext, "com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity", paramString, paramBundle);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.channel.MMessageAct
 * JD-Core Version:    0.6.0
 */
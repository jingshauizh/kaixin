package com.tencent.mm.sdk.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;
import java.util.HashMap;
import java.util.Map;

public class MMessage
{
  public static void send(Context paramContext, String paramString1, String paramString2)
  {
    send(paramContext, paramString1, "com.tencent.mm.sdk.channel.Intent.ACTION_MESSAGE", paramString2);
  }

  public static void send(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    send(paramContext, paramString1, paramString2, paramString3, null);
  }

  public static boolean send(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    String str1 = paramString1 + ".permission.MM_MESSAGE";
    Intent localIntent = new Intent(paramString2);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    String str2 = paramContext.getPackageName();
    localIntent.putExtra("_mmessage_sdkVersion", 553910273);
    localIntent.putExtra("_mmessage_appPackage", str2);
    localIntent.putExtra("_mmessage_content", paramString3);
    localIntent.putExtra("_mmessage_checksum", MMessageUtil.a(paramString3, str2));
    paramContext.sendBroadcast(localIntent, str1);
    Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + localIntent + ", perm=" + str1);
    return true;
  }

  public static abstract interface CallBack
  {
    public abstract void handleMessage(Intent paramIntent);
  }

  public static final class Receiver extends BroadcastReceiver
  {
    public static final Map<String, MMessage.CallBack> callbacks = new HashMap();
    private final MMessage.CallBack o;

    public Receiver()
    {
      this(null);
    }

    public Receiver(MMessage.CallBack paramCallBack)
    {
      this.o = paramCallBack;
    }

    public static void registerCallBack(String paramString, MMessage.CallBack paramCallBack)
    {
      callbacks.put(paramString, paramCallBack);
    }

    public static void unregisterCallBack(String paramString)
    {
      callbacks.remove(paramString);
    }

    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      Log.d("MicroMsg.SDK.MMessage", "receive intent=" + paramIntent);
      if (this.o != null)
      {
        this.o.handleMessage(paramIntent);
        Log.d("MicroMsg.SDK.MMessage", "mm message self-handled");
      }
      MMessage.CallBack localCallBack;
      do
      {
        return;
        localCallBack = (MMessage.CallBack)callbacks.get(paramIntent.getAction());
      }
      while (localCallBack == null);
      localCallBack.handleMessage(paramIntent);
      Log.d("MicroMsg.SDK.MMessage", "mm message handled");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.channel.MMessage
 * JD-Core Version:    0.6.0
 */
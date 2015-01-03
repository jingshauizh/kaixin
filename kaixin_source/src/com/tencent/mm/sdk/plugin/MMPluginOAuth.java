package com.tencent.mm.sdk.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.tencent.mm.sdk.platformtools.Log;
import java.util.HashMap;
import java.util.Map;

public class MMPluginOAuth
{
  private final IResult bG;
  private String bH;
  private String bI;
  private Handler handler;
  private final Context q;

  public MMPluginOAuth(Context paramContext, IResult paramIResult)
  {
    this.q = paramContext;
    this.bG = paramIResult;
  }

  public String getAccessToken()
  {
    return this.bH;
  }

  public String getRequestToken()
  {
    return this.bI;
  }

  public void start()
  {
    start(null);
  }

  public boolean start(Handler paramHandler)
  {
    if (paramHandler == null)
      paramHandler = new Handler();
    this.handler = paramHandler;
    ContentResolver localContentResolver = this.q.getContentResolver();
    Uri localUri = MMPluginProviderConstants.OAuth.CONTENT_URI;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.q.getPackageName();
    arrayOfString[1] = "request_token";
    Cursor localCursor = localContentResolver.query(localUri, null, null, arrayOfString, null);
    if (localCursor != null)
    {
      if ((localCursor.moveToFirst()) && (localCursor.getColumnCount() >= 2))
      {
        this.bI = localCursor.getString(0);
        this.bH = localCursor.getString(1);
      }
      localCursor.close();
    }
    Log.i("MicroMsg.SDK.MMPluginOAuth", "request token = " + this.bI);
    if (this.bI == null)
    {
      Log.e("MicroMsg.SDK.MMPluginOAuth", "request token failed");
      return false;
    }
    if (this.bH != null)
    {
      this.handler.post(new MMPluginOAuth.1(this));
      return true;
    }
    Log.d("MicroMsg.SDK.MMPluginOAuth", "begin to show user oauth page");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.PluginOAuthUI");
    localIntent.putExtra("com.tencent.mm.sdk.plugin.Intent.REQUEST_TOKEN", this.bI);
    localIntent.putExtra("com.tencent.mm.sdk.plugin.Intent.PACKAGE", this.q.getPackageName());
    if (this.q.getPackageManager().resolveActivity(localIntent, 65536) == null)
      Log.e("MicroMsg.SDK.MMPluginOAuth", "show oauth page failed, activity not found");
    for (int i = 0; i != 0; i = 1)
    {
      Receiver.register(this.bI, this);
      return true;
      if (!(this.q instanceof Activity))
        localIntent.setFlags(268435456);
      this.q.startActivity(localIntent);
    }
    return false;
  }

  public static abstract interface IResult
  {
    public abstract void onResult(MMPluginOAuth paramMMPluginOAuth);

    public abstract void onSessionTimeOut();
  }

  public static class Receiver extends BroadcastReceiver
  {
    private static final Map<String, MMPluginOAuth> ah = new HashMap();
    private final MMPluginOAuth bK;

    public Receiver()
    {
      this(null);
    }

    public Receiver(MMPluginOAuth paramMMPluginOAuth)
    {
      this.bK = paramMMPluginOAuth;
    }

    public static void register(String paramString, MMPluginOAuth paramMMPluginOAuth)
    {
      ah.put(paramString, paramMMPluginOAuth);
    }

    public static void unregister(String paramString)
    {
      ah.remove(paramString);
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Log.d("MicroMsg.SDK.MMPluginOAuth", "receive oauth result");
      String str1 = paramIntent.getStringExtra("com.tencent.mm.sdk.plugin.Intent.REQUEST_TOKEN");
      String str2 = paramIntent.getStringExtra("com.tencent.mm.sdk.plugin.Intent.ACCESS_TOKEN");
      MMPluginOAuth localMMPluginOAuth;
      if (this.bK != null)
        localMMPluginOAuth = this.bK;
      while (true)
      {
        new Handler().post(new MMPluginOAuth.Receiver.1(this, localMMPluginOAuth, str2));
        return;
        localMMPluginOAuth = (MMPluginOAuth)ah.get(str1);
        if (localMMPluginOAuth == null)
        {
          Log.e("MicroMsg.SDK.MMPluginOAuth", "oauth unregistered, request token = " + str1);
          return;
        }
        unregister(MMPluginOAuth.a(localMMPluginOAuth));
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.MMPluginOAuth
 * JD-Core Version:    0.6.0
 */
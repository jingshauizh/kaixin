package com.umeng.common.net;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class a
{
  private static final String b = a.class.getName();
  final Messenger a = new Messenger(new b());
  private Context c;
  private k d;
  private Messenger e;
  private String f;
  private String g;
  private String h;
  private String[] i;
  private boolean j = false;
  private ServiceConnection k = new b(this);

  public a(Context paramContext, String paramString1, String paramString2, String paramString3, k paramk)
  {
    this.c = paramContext.getApplicationContext();
    this.f = paramString1;
    this.g = paramString2;
    this.h = paramString3;
    this.d = paramk;
  }

  public void a()
  {
    Intent localIntent = new Intent(this.c, DownloadingService.class);
    this.c.bindService(localIntent, this.k, 1);
  }

  public void a(boolean paramBoolean)
  {
    this.j = paramBoolean;
  }

  public void a(String[] paramArrayOfString)
  {
    this.i = paramArrayOfString;
  }

  static class a
  {
    public String a;
    public String b;
    public String c;
    public String[] d = null;
    public boolean e = false;

    public a(String paramString1, String paramString2, String paramString3)
    {
      this.a = paramString1;
      this.b = paramString2;
      this.c = paramString3;
    }

    public static a a(Bundle paramBundle)
    {
      a locala = new a(paramBundle.getString("mComponentName"), paramBundle.getString("mTitle"), paramBundle.getString("mUrl"));
      locala.d = paramBundle.getStringArray("reporturls");
      locala.e = paramBundle.getBoolean("rich_notification");
      return locala;
    }

    public Bundle a()
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("mComponentName", this.a);
      localBundle.putString("mTitle", this.b);
      localBundle.putString("mUrl", this.c);
      localBundle.putStringArray("reporturls", this.d);
      localBundle.putBoolean("rich_notification", this.e);
      return localBundle;
    }
  }

  class b extends Handler
  {
    b()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      try
      {
        Log.d(a.b(), "DownloadAgent.handleMessage(" + paramMessage.what + "): ");
        switch (paramMessage.what)
        {
        case 4:
        default:
          super.handleMessage(paramMessage);
          return;
        case 3:
          if (a.a(a.this) == null)
            return;
          a.a(a.this).a(paramMessage.arg1);
          return;
        case 5:
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.d(a.b(), "DownloadAgent.handleMessage(" + paramMessage.what + "): " + localException.getMessage());
        return;
      }
      a.c(a.this).unbindService(a.b(a.this));
      if (a.a(a.this) != null)
      {
        if (paramMessage.arg1 == 1)
        {
          a.a(a.this).a(paramMessage.arg1, paramMessage.getData().getString("filename"));
          return;
        }
        a.a(a.this).a(0, null);
        Log.d(a.b(), "DownloadAgent.handleMessage(DownloadingService.DOWNLOAD_COMPLETE_FAIL): ");
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.a
 * JD-Core Version:    0.6.0
 */
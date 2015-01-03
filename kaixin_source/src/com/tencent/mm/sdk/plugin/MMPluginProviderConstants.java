package com.tencent.mm.sdk.plugin;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;
import com.tencent.mm.sdk.platformtools.Log;

public class MMPluginProviderConstants
{
  public static final String AUTHORITY = "com.tencent.mm.sdk.plugin.provider";
  public static final String PLUGIN_PACKAGE_PATTERN = "com.tencent.mm.plugin";
  public static final int TYPE_BOOLEAN = 4;
  public static final int TYPE_DOUBLE = 6;
  public static final int TYPE_FLOAT = 5;
  public static final int TYPE_INT = 1;
  public static final int TYPE_LONG = 2;
  public static final int TYPE_STRING = 3;
  public static final int TYPE_UNKNOWN;

  public static final class OAuth
    implements BaseColumns
  {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String ACTION_REQUEST_TOKEN = "request_token";
    public static final String API_KEY = "apiKey";
    public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/oauth");
    public static final String REQUEST_TOKEN = "requestToken";
    public static final String SECRET = "secret";
  }

  public static final class PluginDB
    implements BaseColumns
  {
    public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/plugindb");
    public static final String KEY = "key";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
  }

  public static class PluginIntent
  {
    public static final String ACCESS_TOKEN = "com.tencent.mm.sdk.plugin.Intent.ACCESS_TOKEN";
    public static final String ACTION_QRCODE_SCANNED = "com.tencent.mm.sdk.plugin.Intent.ACTION_QRCODE_SCANNED";
    public static final String ACTION_REQUEST_TOKEN = "com.tencent.mm.sdk.plugin.Intent.ACTION_REQUEST_TOKEN";
    public static final String ACTION_RESPONSE = "com.tencent.mm.sdk.plugin.Intent.ACTION_RESPONSE";
    public static final String APP_PACKAGE_PATTERN = "com.tencent.mm";
    public static final String AUTH_KEY = "com.tencent.mm.sdk.plugin.Intent.AUTHKEY";
    public static final String NAME = "com.tencent.mm.sdk.plugin.Intent.NAME";
    public static final String PACKAGE = "com.tencent.mm.sdk.plugin.Intent.PACKAGE";
    public static final String PERMISSIONS = "com.tencent.mm.sdk.plugin.Intent.PERMISSIONS";
    public static final String PLUGIN_PACKAGE_PATTERN = "com.tencent.mm.plugin";
    public static final String REQUEST_TOKEN = "com.tencent.mm.sdk.plugin.Intent.REQUEST_TOKEN";
  }

  public static final class Resolver
  {
    public static int getType(Object paramObject)
    {
      if (paramObject == null)
      {
        Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, null value");
        return 0;
      }
      if ((paramObject instanceof Integer))
        return 1;
      if ((paramObject instanceof Long))
        return 2;
      if ((paramObject instanceof String))
        return 3;
      if ((paramObject instanceof Boolean))
        return 4;
      if ((paramObject instanceof Float))
        return 5;
      if ((paramObject instanceof Double))
        return 6;
      Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, unknown type=" + paramObject.getClass().toString());
      return 0;
    }

    public static Object resolveObj(int paramInt, String paramString)
    {
      switch (paramInt)
      {
      default:
      case 1:
      case 2:
      case 4:
      case 5:
      case 6:
        try
        {
          Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unknown type");
          break label82;
          return Integer.valueOf(paramString);
          return Long.valueOf(paramString);
          return Boolean.valueOf(paramString);
          return Float.valueOf(paramString);
          Double localDouble = Double.valueOf(paramString);
          return localDouble;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          label82: paramString = null;
        }
      case 3:
      }
      return paramString;
    }

    public static boolean unresolveObj(ContentValues paramContentValues, Object paramObject)
    {
      int i = getType(paramObject);
      if (i == 0)
        return false;
      paramContentValues.put("type", Integer.valueOf(i));
      paramContentValues.put("value", paramObject.toString());
      return true;
    }
  }

  public static final class SharedPref
    implements BaseColumns
  {
    public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/sharedpref");
    public static final String KEY = "key";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.MMPluginProviderConstants
 * JD-Core Version:    0.6.0
 */
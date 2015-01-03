package com.amap.mapapi.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.text.Html;
import android.text.Spanned;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class e
{
  public static boolean a;
  static float[] b;
  private static String c = null;

  static
  {
    a = true;
    b = new float[9];
  }

  public static double a(long paramLong)
  {
    return paramLong / 1000000.0D;
  }

  public static int a(int paramInt)
  {
    return (int)(1117L * paramInt / 10000L);
  }

  public static long a()
  {
    return 1000L * System.nanoTime() / 1000000000L;
  }

  public static long a(double paramDouble)
  {
    return ()(1000000.0D * paramDouble);
  }

  public static String a(Context paramContext)
  {
    char[] arrayOfChar;
    if (c == null)
      arrayOfChar = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    try
    {
      Signature[] arrayOfSignature = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures;
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(arrayOfSignature[0].toByteArray());
      byte[] arrayOfByte = localMessageDigest.digest();
      localObject = "";
      i = 0;
      if (i < arrayOfByte.length)
      {
        if (arrayOfByte[i] < 0);
        for (int j = 256 + arrayOfByte[i]; ; j = arrayOfByte[i])
        {
          String str1 = (String)localObject + arrayOfChar[(j / 16)];
          str2 = str1 + arrayOfChar[(j % 16)];
          if (i == -1 + arrayOfByte.length)
            break;
          str2 = str2 + ":";
          break;
        }
      }
      c = (String)localObject;
      label283: return c;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label283;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
      {
        int i;
        String str2;
        continue;
        i++;
        Object localObject = str2;
      }
    }
  }

  public static String a(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<font color=").append(paramString2).append(">").append(paramString1).append("</font>");
    return localStringBuffer.toString();
  }

  public static void a(Context paramContext, Location paramLocation)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("last_know_location", 0).edit();
    localEditor.putString("last_know_lat", String.valueOf(paramLocation.getLatitude()));
    localEditor.putString("last_know_lng", String.valueOf(paramLocation.getLongitude()));
    localEditor.commit();
  }

  public static boolean a(double paramDouble1, double paramDouble2)
  {
    int i = 1;
    if ((paramDouble1 < a(1000000L)) || (paramDouble1 > a(65000000L)))
      i = 0;
    if ((paramDouble2 < a(50000000L)) || (paramDouble2 > a(145000000L)))
      i = 0;
    return i;
  }

  public static boolean a(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }

  public static int b(int paramInt)
  {
    return (int)(1000000L * paramInt / 111700L);
  }

  public static Address b()
  {
    Address localAddress = new Address(Locale.CHINA);
    localAddress.setCountryCode("CN");
    localAddress.setCountryName("中国");
    return localAddress;
  }

  public static Spanned b(String paramString)
  {
    if (paramString == null)
      return null;
    return Html.fromHtml(paramString.replace("\n", "<br />"));
  }

  public static java.net.Proxy b(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null)
    {
      int k;
      Object localObject;
      if (localNetworkInfo.getType() == 1)
      {
        String str2 = android.net.Proxy.getHost(paramContext);
        k = android.net.Proxy.getPort(paramContext);
        localObject = str2;
      }
      int i;
      for (int j = k; localObject != null; j = i)
      {
        return new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress((String)localObject, j));
        String str1 = android.net.Proxy.getDefaultHost();
        i = android.net.Proxy.getDefaultPort();
        localObject = str1;
      }
    }
    return (java.net.Proxy)null;
  }

  public static String c()
  {
    return "<br />";
  }

  public static String c(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramInt; i++)
      localStringBuilder.append("&nbsp;");
    return localStringBuilder.toString();
  }

  public static void c(String paramString)
    throws AMapException
  {
    String str;
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      if (localJSONObject.has("status"))
      {
        str = localJSONObject.getString("status");
        if (!str.equals("E6008"))
          break label54;
        throw new AMapException("key为空");
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    label54: 
    do
    {
      return;
      if (!str.equals("E6012"))
        continue;
      throw new AMapException("key不存在");
    }
    while (!str.equals("E6018"));
    throw new AMapException("key被锁定");
  }

  public static boolean c(Context paramContext)
  {
    if (paramContext == null)
      return false;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
      return false;
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return false;
    NetworkInfo.State localState = localNetworkInfo.getState();
    return (localState != null) && (localState != NetworkInfo.State.DISCONNECTED) && (localState != NetworkInfo.State.DISCONNECTING);
  }

  public static Location d(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("last_know_location", 0);
    Location localLocation = new Location("");
    localLocation.setProvider("lbs");
    double d1 = Double.parseDouble(localSharedPreferences.getString("last_know_lat", "0.0"));
    double d2 = Double.parseDouble(localSharedPreferences.getString("last_know_lng", "0.0"));
    localLocation.setLatitude(d1);
    localLocation.setLongitude(d2);
    return localLocation;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.e
 * JD-Core Version:    0.6.0
 */
package com.tencent.sdkutil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class Util
{
  private static final String ENDLINE = "\r\n";

  public static boolean checkMobileQQ(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      PackageInfo localPackageInfo2 = localPackageManager.getPackageInfo("com.tencent.mobileqq", 0);
      localPackageInfo1 = localPackageInfo2;
      i = 0;
      if (localPackageInfo1 != null)
        str = localPackageInfo1.versionName;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      try
      {
        int i;
        String str;
        String[] arrayOfString = str.split("\\.");
        int j = Integer.parseInt(arrayOfString[0]);
        int k = Integer.parseInt(arrayOfString[1]);
        if (j <= 4)
        {
          i = 0;
          if (j == 4)
          {
            i = 0;
            if (k < 1);
          }
        }
        else
        {
          i = 1;
        }
        return i;
        localNameNotFoundException = localNameNotFoundException;
        localNameNotFoundException.printStackTrace();
        PackageInfo localPackageInfo1 = null;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }

  public static boolean checkSd()
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    File localFile = null;
    if (bool)
      localFile = Environment.getExternalStorageDirectory();
    return localFile != null;
  }

  public static int compareVersion(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null))
      return 0;
    if ((paramString1 != null) && (paramString2 == null))
      return 1;
    if ((paramString1 == null) && (paramString2 != null))
      return -1;
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    for (int i = 0; ; i++)
    {
      int k;
      int m;
      try
      {
        if ((i < arrayOfString1.length) && (i < arrayOfString2.length))
        {
          k = Integer.parseInt(arrayOfString1[i]);
          m = Integer.parseInt(arrayOfString2[i]);
          if (k < m)
            return -1;
        }
        else
        {
          if (arrayOfString1.length > i)
            return 1;
          int j = arrayOfString2.length;
          if (j <= i)
            break;
          return -1;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return paramString1.compareTo(paramString2);
      }
      if (k > m)
        return 1;
    }
  }

  public static Bundle decodeUrl(String paramString)
  {
    Bundle localBundle = new Bundle();
    if (paramString != null)
    {
      String[] arrayOfString1 = paramString.split("&");
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split("=");
        if (arrayOfString2.length != 2)
          continue;
        localBundle.putString(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
      }
    }
    return localBundle;
  }

  public static JSONObject decodeUrlToJson(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject == null)
      paramJSONObject = new JSONObject();
    if (paramString != null)
    {
      String[] arrayOfString1 = paramString.split("&");
      int i = arrayOfString1.length;
      int j = 0;
      while (true)
        if (j < i)
        {
          String[] arrayOfString2 = arrayOfString1[j].split("=");
          if (arrayOfString2.length == 2);
          try
          {
            paramJSONObject.put(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
            j++;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }
    }
    return paramJSONObject;
  }

  public static String encodePostBody(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramBundle.size();
    Iterator localIterator = paramBundle.keySet().iterator();
    int j = -1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      int k = j + 1;
      Object localObject = paramBundle.get(str);
      if (!(localObject instanceof String))
      {
        j = k;
        continue;
      }
      localStringBuilder.append("Content-Disposition: form-data; name=\"" + str + "\"" + "\r\n" + "\r\n" + (String)localObject);
      if (k < i - 1)
        localStringBuilder.append("\r\n--" + paramString + "\r\n");
      j = k;
    }
    return localStringBuilder.toString();
  }

  public static String encodeUrl(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramBundle.keySet().iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if ((!(localObject instanceof String)) && (!(localObject instanceof String[])))
        continue;
      label127: int j;
      if ((localObject instanceof String[]))
      {
        String[] arrayOfString;
        int k;
        if (i != 0)
        {
          i = 0;
          localStringBuilder.append(URLEncoder.encode(str) + "=");
          arrayOfString = (String[])paramBundle.getStringArray(str);
          k = 0;
          if (k >= arrayOfString.length)
            break label203;
          if (k != 0)
            break label169;
          localStringBuilder.append(URLEncoder.encode(arrayOfString[k]));
        }
        while (true)
        {
          k++;
          break label127;
          localStringBuilder.append("&");
          break;
          label169: localStringBuilder.append(URLEncoder.encode("," + arrayOfString[k]));
        }
        label203: j = i;
        i = j;
        continue;
      }
      if (i != 0)
        i = 0;
      while (true)
      {
        localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(paramBundle.getString(str)));
        j = i;
        break;
        localStringBuilder.append("&");
      }
    }
    return localStringBuilder.toString();
  }

  public static String encrypt(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      if (arrayOfByte != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = arrayOfByte.length;
        for (int j = 0; j < i; j++)
        {
          int k = arrayOfByte[j];
          localStringBuilder.append(hexDigit(k >>> 4));
          localStringBuilder.append(hexDigit(k));
        }
        String str = localStringBuilder.toString();
        paramString = str;
      }
      return paramString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return paramString;
  }

  public static boolean fileExists(String paramString)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return false;
      localFile = new File(paramString);
    }
    while ((localFile == null) || (!localFile.exists()));
    return true;
  }

  public static String getAppName(Context paramContext)
  {
    return paramContext.getApplicationInfo().loadLabel(paramContext.getPackageManager()).toString();
  }

  // ERROR //
  public static String getAppSignatureMD5(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 233	android/content/Context:getPackageName	()Ljava/lang/String;
    //   4: astore 5
    //   6: aload_0
    //   7: invokevirtual 24	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   10: aload 5
    //   12: bipush 64
    //   14: invokevirtual 32	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   17: getfield 237	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   20: astore 6
    //   22: ldc 180
    //   24: invokestatic 186	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   27: astore 7
    //   29: aload 7
    //   31: aload 6
    //   33: iconst_0
    //   34: aaload
    //   35: invokevirtual 242	android/content/pm/Signature:toByteArray	()[B
    //   38: invokevirtual 194	java/security/MessageDigest:update	([B)V
    //   41: aload 7
    //   43: invokevirtual 197	java/security/MessageDigest:digest	()[B
    //   46: invokestatic 246	com/tencent/sdkutil/Util:toHexString	([B)Ljava/lang/String;
    //   49: astore 8
    //   51: aload 7
    //   53: invokevirtual 249	java/security/MessageDigest:reset	()V
    //   56: aload 7
    //   58: new 117	java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   65: aload 5
    //   67: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: ldc 251
    //   72: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: aload 8
    //   77: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: ldc 251
    //   82: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: aload_1
    //   86: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: ldc 115
    //   91: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   97: invokevirtual 190	java/lang/String:getBytes	()[B
    //   100: invokevirtual 194	java/security/MessageDigest:update	([B)V
    //   103: aload 7
    //   105: invokevirtual 197	java/security/MessageDigest:digest	()[B
    //   108: invokestatic 246	com/tencent/sdkutil/Util:toHexString	([B)Ljava/lang/String;
    //   111: astore 9
    //   113: aload 9
    //   115: astore_3
    //   116: aload 7
    //   118: invokevirtual 249	java/security/MessageDigest:reset	()V
    //   121: aload_3
    //   122: areturn
    //   123: astore_2
    //   124: ldc 115
    //   126: astore_3
    //   127: aload_2
    //   128: astore 4
    //   130: aload 4
    //   132: invokevirtual 55	java/lang/Exception:printStackTrace	()V
    //   135: aload_3
    //   136: areturn
    //   137: astore 4
    //   139: goto -9 -> 130
    //
    // Exception table:
    //   from	to	target	type
    //   0	113	123	java/lang/Exception
    //   116	121	137	java/lang/Exception
  }

  public static String getAppVersion(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return "";
  }

  public static String getAppVersionName(Context paramContext, String paramString)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getPackageInfo(paramString, 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return null;
  }

  public static String getUserIp()
  {
    try
    {
      while (true)
      {
        Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
        while (true)
          if ((localEnumeration1 != null) && (localEnumeration1.hasMoreElements()))
          {
            Enumeration localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
            if (!localEnumeration2.hasMoreElements())
              continue;
            InetAddress localInetAddress = (InetAddress)localEnumeration2.nextElement();
            if (localInetAddress.isLoopbackAddress())
              break;
            String str = localInetAddress.getHostAddress().toString();
            return str;
          }
      }
    }
    catch (SocketException localSocketException)
    {
    }
    return "";
  }

  public static boolean hasActivityForIntent(Context paramContext, Intent paramIntent)
  {
    if (paramIntent != null)
      return isActivityExist(paramContext, paramIntent);
    return false;
  }

  private static char hexDigit(int paramInt)
  {
    int i = paramInt & 0xF;
    if (i < 10)
      return (char)(i + 48);
    return (char)(97 + (i - 10));
  }

  public static boolean isActivityExist(Context paramContext, Intent paramIntent)
  {
    if ((paramContext == null) || (paramIntent == null));
    do
      return false;
    while (paramContext.getPackageManager().queryIntentActivities(paramIntent, 0).size() == 0);
    return true;
  }

  public static boolean isAppSignatureValid(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString1, 64);
      Signature[] arrayOfSignature = localPackageInfo.signatures;
      int i = arrayOfSignature.length;
      for (int j = 0; ; j++)
      {
        int k = 0;
        if (j < i)
        {
          if (!encrypt(arrayOfSignature[j].toCharsString()).equals(paramString2))
            continue;
          k = 1;
        }
        return k;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  public static final boolean isValidUrl(String paramString)
  {
    if (paramString == null);
    do
      return false;
    while ((!paramString.startsWith("http://")) && (!paramString.startsWith("https://")));
    return true;
  }

  public static Bundle parseUrl(String paramString)
  {
    String str = paramString.replace("auth://", "http://");
    try
    {
      URL localURL = new URL(str);
      Bundle localBundle = decodeUrl(localURL.getQuery());
      localBundle.putAll(decodeUrl(localURL.getRef()));
      return localBundle;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new Bundle();
  }

  public static JSONObject parseUrlToJson(String paramString)
  {
    String str = paramString.replace("auth://", "http://");
    try
    {
      URL localURL = new URL(str);
      JSONObject localJSONObject = decodeUrlToJson(null, localURL.getQuery());
      decodeUrlToJson(localJSONObject, localURL.getRef());
      return localJSONObject;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new JSONObject();
  }

  public static String toHexString(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
      if (str.length() == 1)
        str = "0" + str;
      localStringBuilder.append(str);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.Util
 * JD-Core Version:    0.6.0
 */
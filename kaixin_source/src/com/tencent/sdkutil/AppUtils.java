package com.tencent.sdkutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import com.tencent.tauth.Tencent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppUtils
{
  private static final String CONFIG_FILE = "config.json";
  public static final String DEFAULT_PF = "openmobile_android";
  private static final String QZONE_SIGNATURE = "ec96e9ac1149251acbb1b0c5777cae95";
  private static final String TAG = "AppUtils";
  public static AppUtils instance;
  public static int instance_count = 0;
  private Activity activity;
  private QQToken mQQToken;

  static
  {
    instance = null;
  }

  public AppUtils(Activity paramActivity, QQToken paramQQToken)
  {
    this.activity = paramActivity;
    this.mQQToken = paramQQToken;
  }

  private static char hexDigit(int paramInt)
  {
    int i = paramInt & 0xF;
    if (i < 10)
      return (char)(i + 48);
    return (char)(97 + (i - 10));
  }

  private static String[] jsonArrayToStrings(JSONArray paramJSONArray)
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      return null;
    String[] arrayOfString = new String[paramJSONArray.length()];
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayOfString[i] = paramJSONArray.getString(i);
    return arrayOfString;
  }

  public static Bundle parseJsonToBundle(String paramString)
  {
    Bundle localBundle;
    if (paramString == null)
      localBundle = null;
    while (true)
    {
      return localBundle;
      localBundle = new Bundle();
      JSONObject localJSONObject = JsonUtil.parseJson(paramString);
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = localIterator.next().toString();
        localBundle.putString(str, localJSONObject.getString(str));
      }
    }
  }

  public static Intent parseJsonToIntent(String paramString)
  {
    if (paramString == null)
      return null;
    Intent localIntent = new Intent();
    JSONObject localJSONObject1 = JsonUtil.parseJson(paramString);
    if (localJSONObject1.has("integer"))
    {
      JSONObject localJSONObject5 = localJSONObject1.getJSONObject("integer");
      Iterator localIterator4 = localJSONObject5.keys();
      while (localIterator4.hasNext())
      {
        String str4 = (String)localIterator4.next();
        localIntent.putExtra(str4, localJSONObject5.getInt(str4));
      }
    }
    if (localJSONObject1.has("string"))
    {
      JSONObject localJSONObject4 = localJSONObject1.getJSONObject("string");
      Iterator localIterator3 = localJSONObject4.keys();
      while (localIterator3.hasNext())
      {
        String str3 = (String)localIterator3.next();
        localIntent.putExtra(str3, localJSONObject4.getString(str3));
      }
    }
    if (localJSONObject1.has("bundle"))
    {
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("bundle");
      Iterator localIterator1 = localJSONObject2.keys();
      while (localIterator1.hasNext())
      {
        Bundle localBundle = new Bundle();
        String str1 = (String)localIterator1.next();
        JSONObject localJSONObject3 = localJSONObject2.getJSONObject(str1);
        Iterator localIterator2 = localJSONObject3.keys();
        while (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          Object localObject = localJSONObject3.get(str2);
          if ((localObject instanceof String))
          {
            if (str2 == "exitAnim")
              localBundle.putInt("exitAnim", Integer.parseInt(localJSONObject3.getString(str2)));
            localBundle.putString(str2, localJSONObject3.getString(str2));
            continue;
          }
          if (!(localObject instanceof JSONArray))
            continue;
          localBundle.putStringArray(str2, jsonArrayToStrings(localJSONObject3.getJSONArray(str2)));
        }
        localIntent.putExtra(str1, localBundle);
      }
    }
    return localIntent;
  }

  // ERROR //
  public static final String sSubString(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: aload_0
    //   4: invokestatic 164	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   7: ifeq +8 -> 15
    //   10: ldc 166
    //   12: astore_0
    //   13: aload_0
    //   14: areturn
    //   15: aload_2
    //   16: invokestatic 164	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   19: ifne +157 -> 176
    //   22: aload_0
    //   23: aload_2
    //   24: invokevirtual 170	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   27: arraylength
    //   28: iload_1
    //   29: if_icmple -16 -> 13
    //   32: iconst_0
    //   33: istore 6
    //   35: iload 4
    //   37: aload_0
    //   38: invokevirtual 171	java/lang/String:length	()I
    //   41: if_icmpge -28 -> 13
    //   44: aload_0
    //   45: iload 4
    //   47: iload 4
    //   49: iconst_1
    //   50: iadd
    //   51: invokevirtual 175	java/lang/String:substring	(II)Ljava/lang/String;
    //   54: aload_2
    //   55: invokevirtual 170	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   58: arraylength
    //   59: istore 7
    //   61: iload 6
    //   63: iload 7
    //   65: iadd
    //   66: iload_1
    //   67: if_icmple +51 -> 118
    //   70: aload_0
    //   71: iconst_0
    //   72: iload 4
    //   74: invokevirtual 175	java/lang/String:substring	(II)Ljava/lang/String;
    //   77: astore 8
    //   79: aload 8
    //   81: astore 9
    //   83: aload_3
    //   84: invokestatic 164	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   87: ifne +28 -> 115
    //   90: new 177	java/lang/StringBuilder
    //   93: dup
    //   94: invokespecial 178	java/lang/StringBuilder:<init>	()V
    //   97: aload 9
    //   99: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload_3
    //   103: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: astore 11
    //   111: aload 11
    //   113: astore 9
    //   115: aload 9
    //   117: areturn
    //   118: iload 6
    //   120: iload 7
    //   122: iadd
    //   123: istore 6
    //   125: iinc 4 1
    //   128: goto -93 -> 35
    //   131: astore 5
    //   133: getstatic 189	java/lang/System:out	Ljava/io/PrintStream;
    //   136: new 177	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 178	java/lang/StringBuilder:<init>	()V
    //   143: ldc 191
    //   145: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload 5
    //   150: invokevirtual 194	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   153: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokevirtual 200	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   162: aload_0
    //   163: areturn
    //   164: astore 10
    //   166: aload 9
    //   168: astore_0
    //   169: aload 10
    //   171: astore 5
    //   173: goto -40 -> 133
    //   176: ldc 202
    //   178: astore_2
    //   179: goto -157 -> 22
    //
    // Exception table:
    //   from	to	target	type
    //   22	32	131	java/lang/Exception
    //   35	61	131	java/lang/Exception
    //   70	79	131	java/lang/Exception
    //   83	111	164	java/lang/Exception
  }

  @JavascriptInterface
  private boolean validateActivityIntent(Context paramContext, Intent paramIntent)
  {
    ResolveInfo localResolveInfo = paramContext.getPackageManager().resolveActivity(paramIntent, 0);
    if (localResolveInfo == null)
      return false;
    return validateAppSignatureForPackage(paramContext, localResolveInfo.activityInfo.packageName);
  }

  @JavascriptInterface
  private boolean validateAppSignatureForPackage(Context paramContext, String paramString)
  {
    while (true)
    {
      int j;
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 64);
        Signature[] arrayOfSignature = localPackageInfo.signatures;
        int i = arrayOfSignature.length;
        j = 0;
        if (j >= i)
          break;
        String str = encrypt(arrayOfSignature[j].toCharsString());
        if ((paramString.equals(Constants.PACKAGE_QZONE)) && (str.equals("ec96e9ac1149251acbb1b0c5777cae95")))
          return true;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        return false;
      }
      if (paramString.equals(Constants.PACKAGE_QQ))
        continue;
      j++;
    }
    return false;
  }

  @JavascriptInterface
  public void JsAlert(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.activity).setTitle(paramString1).setMessage(paramString2).setPositiveButton(paramString3, new AppUtils.2(this));
    if (paramString5.equals("false"))
      localBuilder.setNegativeButton(paramString4, new AppUtils.3(this));
    localBuilder.create().show();
  }

  @JavascriptInterface
  public String JsSubString(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    return sSubString(paramString, paramInt, null, null);
  }

  @JavascriptInterface
  public void OpenTDialog(String paramString1, String paramString2, int paramInt)
  {
    Log.i("native", "OpenTDialog");
    IUiListener localIUiListener = TemporaryStorage.getListener(paramInt);
    if (localIUiListener != null)
    {
      Log.i("action", paramString1);
      Log.i("listener", "not null");
      this.activity.runOnUiThread(new AppUtils.1(this, paramString1, paramString2, localIUiListener));
    }
  }

  @JavascriptInterface
  public boolean checkMobileQQ()
  {
    PackageManager localPackageManager = this.activity.getPackageManager();
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
        Log.d("MobileQQ verson", str);
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
        Log.d("checkMobileQQ", "error");
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

  @JavascriptInterface
  public boolean checkSd()
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    File localFile = null;
    if (bool)
      localFile = Environment.getExternalStorageDirectory();
    return localFile != null;
  }

  @JavascriptInterface
  public int compareVersion(String paramString1, String paramString2)
  {
    int i = -1;
    Log.d("AppUtils", "compareVersion");
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    for (int j = 0; ; j++)
    {
      int m;
      int n;
      try
      {
        if ((j < arrayOfString1.length) && (j < arrayOfString2.length))
        {
          m = Integer.parseInt(arrayOfString1[j]);
          n = Integer.parseInt(arrayOfString2[j]);
          if (m >= n)
            break label107;
          return i;
        }
        if (arrayOfString1.length > j)
          return 1;
        int k = arrayOfString2.length;
        if (k <= j)
          return 0;
      }
      catch (Exception localException)
      {
        i = 0;
      }
      return i;
      label107: if (m > n)
        return 1;
    }
  }

  @JavascriptInterface
  public String encrypt(String paramString)
  {
    Log.d("AppUtils", "source =" + paramString);
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
      Log.d("AppUtils", "result =" + paramString);
      return paramString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        localNoSuchAlgorithmException.printStackTrace();
    }
  }

  @JavascriptInterface
  public boolean fileExists(String paramString)
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

  @JavascriptInterface
  public Bundle fillParams4BrowserLogin(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("scope", "all");
    if (("action_login".equals(paramString)) || ("action_pay".equals(paramString)))
      if (this.mQQToken != null)
      {
        localBundle.putString("client_id", this.mQQToken.getAppId());
        localBundle.putString("pf", "openmobile_android");
        localBundle.putString("need_pay", "1");
        String str1 = System.currentTimeMillis() / 1000L + "";
        localBundle.putString("sign", getSignValidString(str1 + ""));
        localBundle.putString("time", str1);
      }
    while (true)
    {
      localBundle.putString("sdkv", "2.2");
      localBundle.putString("sdkp", "a");
      return localBundle;
      if (this.mQQToken == null)
        continue;
      localBundle.putString("oauth_consumer_key", this.mQQToken.getAppId());
      if (this.mQQToken.isSessionValid())
        localBundle.putString("access_token", this.mQQToken.getAccessToken());
      String str2 = this.mQQToken.getOpenId();
      if (str2 != null)
        localBundle.putString("openid", str2);
      try
      {
        localBundle.putString("pf", this.mQQToken.getAppContext().getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localBundle.putString("pf", "openmobile_android");
      }
    }
  }

  @JavascriptInterface
  public String getAppName()
  {
    Resources localResources = this.activity.getResources();
    return localResources.getText(localResources.getIdentifier("app_name", "string", this.activity.getPackageName())).toString();
  }

  @JavascriptInterface
  public String getAppVerison(String paramString)
  {
    try
    {
      String str = this.activity.getPackageManager().getPackageInfo(paramString, 0).versionName;
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  @JavascriptInterface
  public String getApplicationLable()
  {
    if (this.activity != null)
    {
      CharSequence localCharSequence = this.activity.getPackageManager().getApplicationLabel(this.activity.getApplicationInfo());
      if (localCharSequence != null)
        return localCharSequence.toString();
    }
    return null;
  }

  @JavascriptInterface
  public String getCurrentTimeSeconds()
  {
    return System.currentTimeMillis() / 1000L + "";
  }

  @JavascriptInterface
  public String getJSVersion()
  {
    String str1 = this.activity.getFilesDir() + File.separator + "tencent/js/" + "config.json";
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(str1);
      localStringBuffer = new StringBuffer();
      Scanner localScanner = new Scanner(localFileInputStream);
      while (localScanner.hasNextLine())
        localStringBuffer.append(localScanner.nextLine()).append("\n");
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      StringBuffer localStringBuffer;
      localFileNotFoundException.printStackTrace();
      return null;
      String str2 = new JSONObject(localStringBuffer.toString()).getString("version");
      return str2;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  @JavascriptInterface
  public String getMachineStatus()
  {
    return Build.MODEL;
  }

  @JavascriptInterface
  public String getOsStatus()
  {
    return Build.VERSION.RELEASE;
  }

  @JavascriptInterface
  public String getQQVersion()
  {
    try
    {
      String str = this.activity.getPackageManager().getPackageInfo(Constants.PACKAGE_QQ, 0).versionName;
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  @JavascriptInterface
  public String getQZoneVersion()
  {
    try
    {
      String str = this.activity.getPackageManager().getPackageInfo(Constants.PACKAGE_QZONE, 0).versionName;
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  // ERROR //
  @JavascriptInterface
  public String getSignValidString(String paramString)
  {
    // Byte code:
    //   0: invokestatic 589	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   3: ldc_w 591
    //   6: ldc_w 593
    //   9: invokevirtual 596	com/tencent/record/debug/WnsClientLog:v	(Ljava/lang/String;Ljava/lang/String;)V
    //   12: aload_0
    //   13: getfield 37	com/tencent/sdkutil/AppUtils:activity	Landroid/app/Activity;
    //   16: invokevirtual 599	android/app/Activity:getApplicationContext	()Landroid/content/Context;
    //   19: invokevirtual 600	android/content/Context:getPackageName	()Ljava/lang/String;
    //   22: astore 5
    //   24: aload_0
    //   25: getfield 37	com/tencent/sdkutil/AppUtils:activity	Landroid/app/Activity;
    //   28: invokevirtual 599	android/app/Activity:getApplicationContext	()Landroid/content/Context;
    //   31: invokevirtual 211	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   34: aload 5
    //   36: bipush 64
    //   38: invokevirtual 238	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   41: getfield 244	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   44: astore 6
    //   46: ldc_w 387
    //   49: invokestatic 393	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   52: astore 7
    //   54: aload 7
    //   56: aload 6
    //   58: iconst_0
    //   59: aaload
    //   60: invokevirtual 603	android/content/pm/Signature:toByteArray	()[B
    //   63: invokevirtual 400	java/security/MessageDigest:update	([B)V
    //   66: aload_0
    //   67: aload 7
    //   69: invokevirtual 403	java/security/MessageDigest:digest	()[B
    //   72: invokevirtual 607	com/tencent/sdkutil/AppUtils:toHexString	([B)Ljava/lang/String;
    //   75: astore 8
    //   77: aload 7
    //   79: invokevirtual 610	java/security/MessageDigest:reset	()V
    //   82: aload 7
    //   84: new 177	java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial 178	java/lang/StringBuilder:<init>	()V
    //   91: aload 5
    //   93: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: ldc_w 612
    //   99: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload 8
    //   104: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: ldc_w 612
    //   110: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload_1
    //   114: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: ldc 166
    //   119: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: invokevirtual 396	java/lang/String:getBytes	()[B
    //   128: invokevirtual 400	java/security/MessageDigest:update	([B)V
    //   131: aload_0
    //   132: aload 7
    //   134: invokevirtual 403	java/security/MessageDigest:digest	()[B
    //   137: invokevirtual 607	com/tencent/sdkutil/AppUtils:toHexString	([B)Ljava/lang/String;
    //   140: astore 9
    //   142: aload 9
    //   144: astore_3
    //   145: aload 7
    //   147: invokevirtual 610	java/security/MessageDigest:reset	()V
    //   150: aload_3
    //   151: areturn
    //   152: astore_2
    //   153: ldc 166
    //   155: astore_3
    //   156: aload_2
    //   157: astore 4
    //   159: aload 4
    //   161: invokevirtual 367	java/lang/Exception:printStackTrace	()V
    //   164: invokestatic 589	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   167: ldc_w 591
    //   170: ldc_w 614
    //   173: aload 4
    //   175: invokevirtual 618	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   178: aload_3
    //   179: areturn
    //   180: astore 4
    //   182: goto -23 -> 159
    //
    // Exception table:
    //   from	to	target	type
    //   12	142	152	java/lang/Exception
    //   145	150	180	java/lang/Exception
  }

  @JavascriptInterface
  public String getStatus_machine()
  {
    return Build.MODEL;
  }

  @JavascriptInterface
  public String getStatus_os()
  {
    return Build.VERSION.RELEASE;
  }

  @JavascriptInterface
  public String getSystemInfo()
  {
    String str1 = new WebView(this.activity).getSettings().getUserAgentString();
    String str2 = "Product: " + Build.PRODUCT;
    String str3 = str2 + ", CPU_ABI: " + Build.CPU_ABI;
    String str4 = str3 + ", TAGS: " + Build.TAGS;
    String str5 = str4 + ", VERSION_CODES.BASE: 1";
    String str6 = str5 + ", MODEL: " + Build.MODEL;
    String str7 = str6 + ", SDK: " + Build.VERSION.SDK;
    String str8 = str7 + ", VERSION.RELEASE: " + Build.VERSION.RELEASE;
    String str9 = str8 + ", DEVICE: " + Build.DEVICE;
    String str10 = str9 + ", DISPLAY: " + Build.DISPLAY;
    String str11 = str10 + ", BRAND: " + Build.BRAND;
    String str12 = str11 + ", BOARD: " + Build.BOARD;
    String str13 = str12 + ", FINGERPRINT: " + Build.FINGERPRINT;
    String str14 = str13 + ", ID: " + Build.ID;
    String str15 = str14 + ", MANUFACTURER: " + Build.MANUFACTURER;
    String str16 = str15 + ", USER: " + Build.USER;
    return str1 + "\n" + str16;
  }

  @JavascriptInterface
  public String getUrlByParams(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("scope", "all");
    localBundle.putString("display", "mobile");
    StringBuilder localStringBuilder = new StringBuilder();
    localBundle.putString("response_type", "token");
    localBundle.putString("redirect_uri", ServerSetting.getInstance().getSettingUrl(this.mQQToken.getAppContext(), 1));
    localBundle.putString("cancel_display", "1");
    localBundle.putString("switch", "1");
    localBundle.putString("sdkp", "a");
    localBundle.putString("sdkv", "2.2");
    localBundle.putString("status_userip", getUserIp());
    localBundle.putString("status_os", Build.VERSION.RELEASE);
    localBundle.putString("status_version", Build.VERSION.SDK);
    localBundle.putString("status_machine", Build.MODEL);
    localStringBuilder.append(ServerSetting.getInstance().getSettingUrl(this.mQQToken.getAppContext(), 2));
    localBundle.putString("client_id", this.mQQToken.getAppId());
    localBundle.putString("pf", "openmobile_android");
    localBundle.putString("need_pay", "1");
    String str = System.currentTimeMillis() / 1000L + "";
    localBundle.putString("sign", getSignValidString(str + ""));
    localBundle.putString("time", str);
    localStringBuilder.append(Util.encodeUrl(localBundle));
    return localStringBuilder.toString();
  }

  @JavascriptInterface
  public String getUserIp()
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

  @JavascriptInterface
  public String getVersionStatus()
  {
    return Build.VERSION.SDK;
  }

  @JavascriptInterface
  public boolean isActivityExist(String paramString)
  {
    Intent localIntent = parseJsonToIntent(paramString);
    if (localIntent == null);
    do
      return false;
    while (this.activity.getPackageManager().queryIntentActivities(localIntent, 0).size() == 0);
    return true;
  }

  @JavascriptInterface
  public boolean isValidUrl(String paramString)
  {
    if (paramString == null);
    do
      return false;
    while ((!paramString.startsWith("http://")) && (!paramString.startsWith("https://")));
    return true;
  }

  @JavascriptInterface
  public int nextRequestCode()
  {
    return TemporaryStorage.nextRequestCode();
  }

  public void setActivity(Activity paramActivity)
  {
    this.activity = paramActivity;
  }

  public void setToken(QQToken paramQQToken)
  {
    this.mQQToken = paramQQToken;
  }

  @JavascriptInterface
  public String toHexString(byte[] paramArrayOfByte)
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

  @JavascriptInterface
  public void updateQQToken()
  {
    Tencent.UpdateQQToken();
  }

  @JavascriptInterface
  public boolean validateActivityIntent4JS(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramString1, paramString2);
    return validateActivityIntent(this.activity, localIntent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AppUtils
 * JD-Core Version:    0.6.0
 */
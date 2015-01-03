package com.tencent.mm.sdk.platformtools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class NetStatusUtil
{
  public static final int CMNET = 6;
  public static final int CMWAP = 5;
  public static final int CTNET = 8;
  public static final int CTWAP = 7;
  public static final int LTE = 10;
  public static final int MOBILE = 9;
  public static final int NET_3G = 4;
  public static final int NON_NETWORK = -1;
  public static final int NO_SIM_OPERATOR = 0;
  public static final int POLICY_NONE = 0;
  public static final int POLICY_REJECT_METERED_BACKGROUND = 1;
  public static final int TBACKGROUND_DATA_LIMITED = 2;
  public static final int TBACKGROUND_NOT_LIMITED = 0;
  public static final int TBACKGROUND_PROCESS_LIMITED = 1;
  public static final int TBACKGROUND_WIFI_LIMITED = 3;
  public static final int UNINET = 1;
  public static final int UNIWAP = 2;
  public static final int WAP_3G = 3;
  public static final int WIFI;

  private static Intent a(Context paramContext, String paramString)
  {
    try
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      List localList1 = localPackageManager.getInstalledPackages(0);
      if ((localList1 != null) && (localList1.size() > 0))
      {
        Log.e("MicroMsg.NetStatusUtil", "package  size" + localList1.size());
        int i = 0;
        while (true)
        {
          int j = localList1.size();
          if (i < j)
            try
            {
              Log.e("MicroMsg.NetStatusUtil", "package " + ((PackageInfo)localList1.get(i)).packageName);
              Intent localIntent1 = new Intent();
              localIntent1.setPackage(((PackageInfo)localList1.get(i)).packageName);
              List localList2 = localPackageManager.queryIntentActivities(localIntent1, 0);
              int m;
              if (localList2 != null)
              {
                int k = localList2.size();
                m = k;
              }
              while (true)
                if (m > 0)
                  try
                  {
                    Log.e("MicroMsg.NetStatusUtil", "activityName count " + m);
                    for (int n = 0; ; n++)
                    {
                      if (n >= m)
                        break label301;
                      ActivityInfo localActivityInfo = ((ResolveInfo)localList2.get(n)).activityInfo;
                      if (!localActivityInfo.name.contains(paramString))
                        continue;
                      Intent localIntent2 = new Intent("/");
                      localIntent2.setComponent(new ComponentName(localActivityInfo.packageName, localActivityInfo.name));
                      localIntent2.setAction("android.intent.action.VIEW");
                      paramContext.startActivity(localIntent2);
                      return localIntent2;
                      m = 0;
                      break;
                    }
                  }
                  catch (Exception localException3)
                  {
                    localException3.printStackTrace();
                  }
              label301: i++;
            }
            catch (Exception localException2)
            {
              while (true)
                localException2.printStackTrace();
            }
        }
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return null;
  }

  public static boolean checkFromXml(int paramInt)
  {
    try
    {
      runRootCommand();
      NodeList localNodeList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(new File("/data/system/netpolicy.xml"))).getDocumentElement().getElementsByTagName("uid-policy");
      for (int i = 0; i < localNodeList.getLength(); i++)
      {
        Element localElement = (Element)localNodeList.item(i);
        String str1 = localElement.getAttribute("uid");
        String str2 = localElement.getAttribute("policy");
        Log.e("MicroMsg.NetStatusUtil", "uid is " + str1 + "  policy is " + str2);
        if (!str1.equals(Integer.toString(paramInt)))
          continue;
        if (Integer.parseInt(str2) == 1)
          return true;
        int j = Integer.parseInt(str2);
        if (j == 0)
          return false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static void dumpNetStatus(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      Log.e("MicroMsg.NetStatusUtil", "isAvailable " + localNetworkInfo.isAvailable());
      Log.e("MicroMsg.NetStatusUtil", "isConnected " + localNetworkInfo.isConnected());
      Log.e("MicroMsg.NetStatusUtil", "isRoaming " + localNetworkInfo.isRoaming());
      Log.e("MicroMsg.NetStatusUtil", "isFailover " + localNetworkInfo.isFailover());
      Log.e("MicroMsg.NetStatusUtil", "getSubtypeName " + localNetworkInfo.getSubtypeName());
      Log.e("MicroMsg.NetStatusUtil", "getExtraInfo " + localNetworkInfo.getExtraInfo());
      Log.e("MicroMsg.NetStatusUtil", "activeNetInfo " + localNetworkInfo.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static int getBackgroundLimitType(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 14)
      try
      {
        Class localClass = Class.forName("android.app.ActivityManagerNative");
        Object localObject = localClass.getMethod("getDefault", new Class[0]).invoke(localClass, new Object[0]);
        int k = ((Integer)localObject.getClass().getMethod("getProcessLimit", new Class[0]).invoke(localObject, new Object[0])).intValue();
        if (k == 0)
          return 1;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    try
    {
      int i = getWifiSleeepPolicy(paramContext);
      if (i != 2)
      {
        int j = getNetType(paramContext);
        if (j == 0);
      }
      else
      {
        return 0;
      }
      if ((i == 1) || (i == 0))
        return 3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return 0;
  }

  public static int getISPCode(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
      return 0;
    String str1 = localTelephonyManager.getSimOperator();
    if ((str1 == null) || (str1.length() < 5))
      return 0;
    String str2 = str1.substring(0, 5);
    Log.d("MicroMsg.NetStatusUtil", "getISPCode MCC_MNC=%s", new Object[] { str2 });
    return Integer.valueOf(str2).intValue();
  }

  public static String getISPName(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null)
      return "";
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localTelephonyManager.getSimOperatorName();
    Log.d("MicroMsg.NetStatusUtil", "getISPName ISPName=%s", arrayOfObject);
    if (localTelephonyManager.getSimOperatorName().length() <= 100)
      return localTelephonyManager.getSimOperatorName();
    return localTelephonyManager.getSimOperatorName().substring(0, 100);
  }

  public static int getNetType(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
      return -1;
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return -1;
    if (localNetworkInfo.getType() == 1)
      return 0;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localNetworkInfo.getExtraInfo();
    arrayOfObject[1] = Integer.valueOf(localNetworkInfo.getType());
    Log.d("MicroMsg.NetStatusUtil", "activeNetInfo extra=%s, type=%d", arrayOfObject);
    if (localNetworkInfo.getExtraInfo() != null)
    {
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("uninet"))
        return 1;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("uniwap"))
        return 2;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("3gwap"))
        return 3;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("3gnet"))
        return 4;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("cmwap"))
        return 5;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("cmnet"))
        return 6;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("ctwap"))
        return 7;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("ctnet"))
        return 8;
      if (localNetworkInfo.getExtraInfo().equalsIgnoreCase("LTE"))
        return 10;
    }
    return 9;
  }

  public static String getNetTypeString(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
      return "NON_NETWORK";
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return "NON_NETWORK";
    if (localNetworkInfo.getType() == 1)
      return "WIFI";
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localNetworkInfo.getExtraInfo();
    arrayOfObject[1] = Integer.valueOf(localNetworkInfo.getType());
    Log.d("MicroMsg.NetStatusUtil", "activeNetInfo extra=%s, type=%d", arrayOfObject);
    if (localNetworkInfo.getExtraInfo() != null)
      return localNetworkInfo.getExtraInfo();
    return "MOBILE";
  }

  public static int getWifiSleeepPolicy(Context paramContext)
  {
    return Settings.System.getInt(paramContext.getContentResolver(), "wifi_sleep_policy", 2);
  }

  public static int guessNetSpeed(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo.getType() == 1)
        return 102400;
      int i = localNetworkInfo.getSubtype();
      switch (i)
      {
      default:
        return 102400;
      case 1:
        return 4096;
      case 2:
        return 8192;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      }
      return 102400;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static boolean is2G(int paramInt)
  {
    return (paramInt == 1) || (paramInt == 2) || (paramInt == 5) || (paramInt == 6) || (paramInt == 7) || (paramInt == 8);
  }

  public static boolean is2G(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo.getType() == 1)
        return false;
      if (localNetworkInfo.getSubtype() != 2)
      {
        int i = localNetworkInfo.getSubtype();
        if (i != 1);
      }
      else
      {
        return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean is3G(int paramInt)
  {
    return (paramInt == 3) || (paramInt == 4);
  }

  public static boolean is3G(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo.getType() == 1)
        return false;
      if (localNetworkInfo.getSubtype() >= 5)
      {
        int i = localNetworkInfo.getSubtype();
        if (i < 13)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean is4G(int paramInt)
  {
    return paramInt == 10;
  }

  public static boolean is4G(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo.getType() == 1)
        return false;
      int i = localNetworkInfo.getSubtype();
      if (i >= 13)
        return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean isConnected(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    try
    {
      boolean bool = localNetworkInfo.isConnected();
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean isImmediatelyDestroyActivities(Context paramContext)
  {
    int i = Settings.System.getInt(paramContext.getContentResolver(), "always_finish_activities", 0);
    int j = 0;
    if (i != 0)
      j = 1;
    return j;
  }

  public static boolean isLimited(int paramInt)
  {
    return (paramInt == 2) || (paramInt == 1) || (paramInt == 3);
  }

  public static boolean isMobile(int paramInt)
  {
    return (is3G(paramInt)) || (is2G(paramInt)) || (is4G(paramInt));
  }

  public static boolean isMobile(Context paramContext)
  {
    int i = getNetType(paramContext);
    return (is3G(i)) || (is2G(i)) || (is4G(i));
  }

  public static boolean isRestrictBacground(Context paramContext)
  {
    int i = paramContext.getApplicationInfo().uid;
    try
    {
      Class localClass1 = Class.forName("android.net.NetworkPolicyManager");
      Object localObject1 = localClass1.getMethod("getSystemService", new Class[] { Context.class }).invoke(localClass1, new Object[] { paramContext });
      Field localField = localClass1.getDeclaredField("mService");
      localField.setAccessible(true);
      Object localObject2 = localField.get(localObject1);
      Class localClass2 = localObject2.getClass();
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      Method localMethod = localClass2.getMethod("getUidPolicy", arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      int j = ((Integer)localMethod.invoke(localObject2, arrayOfObject)).intValue();
      Log.e("MicroMsg.NetStatusUtil", "policy is " + j);
      if (j == 1)
        return true;
      if (j == 0)
        return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return checkFromXml(i);
  }

  public static boolean isWap(int paramInt)
  {
    return (paramInt == 2) || (paramInt == 5) || (paramInt == 7) || (paramInt == 3);
  }

  public static boolean isWap(Context paramContext)
  {
    return isWap(getNetType(paramContext));
  }

  public static boolean isWifi(int paramInt)
  {
    return paramInt == 0;
  }

  public static boolean isWifi(Context paramContext)
  {
    return isWifi(getNetType(paramContext));
  }

  // ERROR //
  public static boolean runRootCommand()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_0
    //   2: invokestatic 476	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   5: ldc_w 478
    //   8: invokevirtual 482	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   11: astore 6
    //   13: new 484	java/io/DataOutputStream
    //   16: dup
    //   17: aload 6
    //   19: invokevirtual 490	java/lang/Process:getOutputStream	()Ljava/io/OutputStream;
    //   22: invokespecial 493	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   25: astore_2
    //   26: aload_2
    //   27: ldc_w 495
    //   30: invokevirtual 498	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   33: aload_2
    //   34: invokevirtual 501	java/io/DataOutputStream:flush	()V
    //   37: aload 6
    //   39: invokevirtual 504	java/lang/Process:waitFor	()I
    //   42: pop
    //   43: aload_2
    //   44: invokevirtual 507	java/io/DataOutputStream:close	()V
    //   47: aload 6
    //   49: ifnull +8 -> 57
    //   52: aload 6
    //   54: invokevirtual 510	java/lang/Process:destroy	()V
    //   57: iconst_1
    //   58: ireturn
    //   59: astore 8
    //   61: aload 8
    //   63: invokevirtual 154	java/lang/Exception:printStackTrace	()V
    //   66: goto -9 -> 57
    //   69: astore 4
    //   71: aconst_null
    //   72: astore_2
    //   73: ldc 64
    //   75: new 66	java/lang/StringBuilder
    //   78: dup
    //   79: ldc_w 512
    //   82: invokespecial 71	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   85: aload 4
    //   87: invokevirtual 515	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   90: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual 79	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   96: invokestatic 517	com/tencent/mm/sdk/platformtools/Log:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   99: aload_2
    //   100: ifnull +7 -> 107
    //   103: aload_2
    //   104: invokevirtual 507	java/io/DataOutputStream:close	()V
    //   107: aload_0
    //   108: ifnull +7 -> 115
    //   111: aload_0
    //   112: invokevirtual 510	java/lang/Process:destroy	()V
    //   115: iconst_0
    //   116: ireturn
    //   117: astore 5
    //   119: aload 5
    //   121: invokevirtual 154	java/lang/Exception:printStackTrace	()V
    //   124: goto -9 -> 115
    //   127: astore_1
    //   128: aconst_null
    //   129: astore_2
    //   130: aload_2
    //   131: ifnull +7 -> 138
    //   134: aload_2
    //   135: invokevirtual 507	java/io/DataOutputStream:close	()V
    //   138: aload_0
    //   139: ifnull +7 -> 146
    //   142: aload_0
    //   143: invokevirtual 510	java/lang/Process:destroy	()V
    //   146: aload_1
    //   147: athrow
    //   148: astore_3
    //   149: aload_3
    //   150: invokevirtual 154	java/lang/Exception:printStackTrace	()V
    //   153: goto -7 -> 146
    //   156: astore_1
    //   157: aload 6
    //   159: astore_0
    //   160: aconst_null
    //   161: astore_2
    //   162: goto -32 -> 130
    //   165: astore_1
    //   166: aload 6
    //   168: astore_0
    //   169: goto -39 -> 130
    //   172: astore_1
    //   173: goto -43 -> 130
    //   176: astore 4
    //   178: aload 6
    //   180: astore_0
    //   181: aconst_null
    //   182: astore_2
    //   183: goto -110 -> 73
    //   186: astore 4
    //   188: aload 6
    //   190: astore_0
    //   191: goto -118 -> 73
    //
    // Exception table:
    //   from	to	target	type
    //   43	47	59	java/lang/Exception
    //   52	57	59	java/lang/Exception
    //   2	13	69	java/lang/Exception
    //   103	107	117	java/lang/Exception
    //   111	115	117	java/lang/Exception
    //   2	13	127	finally
    //   134	138	148	java/lang/Exception
    //   142	146	148	java/lang/Exception
    //   13	26	156	finally
    //   26	43	165	finally
    //   73	99	172	finally
    //   13	26	176	java/lang/Exception
    //   26	43	186	java/lang/Exception
  }

  public static void startSettingItent(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    case 0:
    default:
      return;
    case 2:
      try
      {
        Intent localIntent3 = new Intent("/");
        localIntent3.setComponent(new ComponentName("com.android.providers.subscribedfeeds", "com.android.settings.ManageAccountsSettings"));
        localIntent3.setAction("android.intent.action.VIEW");
        paramContext.startActivity(localIntent3);
        return;
      }
      catch (Exception localException3)
      {
        try
        {
          Intent localIntent4 = new Intent("/");
          localIntent4.setComponent(new ComponentName("com.htc.settings.accountsync", "com.htc.settings.accountsync.ManageAccountsSettings"));
          localIntent4.setAction("android.intent.action.VIEW");
          paramContext.startActivity(localIntent4);
          return;
        }
        catch (Exception localException4)
        {
          a(paramContext, "ManageAccountsSettings");
          return;
        }
      }
    case 1:
      try
      {
        Intent localIntent2 = new Intent("/");
        localIntent2.setComponent(new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings"));
        localIntent2.setAction("android.intent.action.VIEW");
        paramContext.startActivity(localIntent2);
        return;
      }
      catch (Exception localException2)
      {
        a(paramContext, "DevelopmentSettings");
        return;
      }
    case 3:
    }
    try
    {
      Intent localIntent1 = new Intent();
      localIntent1.setAction("android.settings.WIFI_IP_SETTINGS");
      paramContext.startActivity(localIntent1);
      return;
    }
    catch (Exception localException1)
    {
      a(paramContext, "AdvancedSettings");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.NetStatusUtil
 * JD-Core Version:    0.6.0
 */
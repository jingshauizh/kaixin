package com.tencent.mta;

import android.content.Context;
import android.util.Log;
import com.tencent.common.OpenConfig;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatAppMonitor;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;
import com.tencent.tauth.QQToken;
import java.util.Properties;

public class TencentStat
{
  private static final String a = TencentStat.class.getSimpleName();

  public static void a(Context paramContext, QQToken paramQQToken, String paramString, String[] paramArrayOfString)
  {
    b(paramContext, paramQQToken);
    StatService.trackCustomEvent(paramContext, paramString, paramArrayOfString);
  }

  public static void a(Context paramContext, QQToken paramQQToken, boolean paramBoolean, String paramString)
  {
    b(paramContext, paramQQToken);
    String str1 = paramQQToken.getAppId();
    String str2 = "Aqc" + str1;
    StatConfig.setAutoExceptionCaught(paramBoolean);
    StatConfig.setEnableSmartReporting(true);
    StatConfig.setSendPeriodMinutes(1440);
    StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
    StatConfig.setStatReportUrl(paramString);
    try
    {
      StatService.startStatService(paramContext, str2, "1.0.0");
      return;
    }
    catch (MtaSDkException localMtaSDkException)
    {
      Log.e("DEBUG", "MTA init Failed.");
    }
  }

  public static void a(Context paramContext, String paramString)
  {
    StatService.reportError(paramContext, paramString);
  }

  public static void a(Context paramContext, String paramString1, String paramString2)
  {
    StatAppMonitor localStatAppMonitor = new StatAppMonitor(paramString1);
    Runtime localRuntime = Runtime.getRuntime();
    Process localProcess = null;
    try
    {
      long l = System.currentTimeMillis();
      localProcess = localRuntime.exec(paramString2);
      localProcess.waitFor();
      localStatAppMonitor.setMillisecondsConsume(System.currentTimeMillis() - l);
      int i = localProcess.waitFor();
      localStatAppMonitor.setReturnCode(i);
      localStatAppMonitor.setReqSize(1000L);
      localStatAppMonitor.setRespSize(2000L);
      localStatAppMonitor.setSampling(2);
      if (i == 0)
      {
        Log.d(a, "ping连接成功");
        localStatAppMonitor.setResultType(0);
      }
      while (true)
      {
        localProcess.destroy();
        StatService.reportAppMonitorStat(paramContext, localStatAppMonitor);
        return;
        Log.d(a, "ping测试失败");
        localStatAppMonitor.setResultType(2);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e(a, localException.toString());
        localStatAppMonitor.setResultType(1);
        localProcess.destroy();
      }
    }
    finally
    {
      localProcess.destroy();
    }
    throw localObject;
  }

  public static void a(Context paramContext, Properties paramProperties, String paramString)
  {
    StatService.trackCustomKVEvent(paramContext, paramString, paramProperties);
  }

  public static boolean a(Context paramContext, QQToken paramQQToken)
  {
    return OpenConfig.a(paramContext, paramQQToken.getAppId()).d("Common_ta_enable");
  }

  public static void b(Context paramContext, QQToken paramQQToken)
  {
    if (a(paramContext, paramQQToken))
    {
      StatConfig.setEnableStatService(true);
      return;
    }
    StatConfig.setEnableStatService(false);
  }

  public static void b(Context paramContext, Properties paramProperties, String paramString)
  {
    StatService.trackCustomBeginKVEvent(paramContext, paramString, paramProperties);
  }

  public static void c(Context paramContext, QQToken paramQQToken)
  {
    b(paramContext, paramQQToken);
    if (paramQQToken.getOpenId() != null)
      StatService.reportQQ(paramContext, paramQQToken.getOpenId());
  }

  public static void c(Context paramContext, Properties paramProperties, String paramString)
  {
    StatService.trackCustomEndKVEvent(paramContext, paramString, paramProperties);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mta.TencentStat
 * JD-Core Version:    0.6.0
 */
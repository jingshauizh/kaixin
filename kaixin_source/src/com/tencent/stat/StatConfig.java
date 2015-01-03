package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.StatPreferences;
import java.util.Iterator;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig
{
  static String HIBERNATE;
  private static String appkey;
  private static volatile int curSessionStatReportCount;
  private static int currentDaySessionNumbers;
  private static boolean enableSmartReporting;
  private static boolean enableStatService;
  private static String installChannel;
  public static boolean isAutoExceptionCaught;
  private static long lastReportStrategyCheckTime;
  private static StatLogger logger = StatCommonHelper.getLogger();
  private static int maxBatchReportCount;
  private static int maxDaySessionNumbers;
  private static int maxLoadEventCount;
  private static int maxParallelTimmingEvents;
  private static int maxReportEventLength;
  private static long maxReportStrategyCheckTime;
  private static int maxSendRetryCount;
  private static int maxSessionStatReportCount;
  private static int maxStoreEventCount;
  static String qq;
  private static boolean reportTrafficStat;
  static OnlineConfig sdkCfg;
  private static int sendPeriodMinutes;
  private static int sessionTimoutMillis;
  private static String statReportUrl;
  private static StatReportStrategy statSendStrategy;
  private static String ta_http_proxy;
  static OnlineConfig userCfg = new OnlineConfig(2);

  static
  {
    sdkCfg = new OnlineConfig(1);
    statSendStrategy = StatReportStrategy.APP_LAUNCH;
    enableStatService = true;
    sessionTimoutMillis = 30000;
    maxStoreEventCount = 1024;
    maxLoadEventCount = 30;
    maxSendRetryCount = 3;
    maxBatchReportCount = 30;
    HIBERNATE = "__HIBERNATE__";
    ta_http_proxy = null;
    qq = "";
    sendPeriodMinutes = 1440;
    maxParallelTimmingEvents = 1024;
    enableSmartReporting = true;
    lastReportStrategyCheckTime = 0L;
    maxReportStrategyCheckTime = 300000L;
    isAutoExceptionCaught = true;
    statReportUrl = "http://pingma.qq.com:80/mstat/report";
    maxSessionStatReportCount = 0;
    curSessionStatReportCount = 0;
    maxDaySessionNumbers = 20;
    currentDaySessionNumbers = 0;
    reportTrafficStat = false;
    maxReportEventLength = 4096;
  }

  static void checkHibernate(JSONObject paramJSONObject)
  {
    try
    {
      String str = paramJSONObject.getString(HIBERNATE);
      logger.d("hibernateVer:" + str + ", current version:" + "1.0.0");
      long l = StatCommonHelper.getSDKLongVersion(str);
      if (StatCommonHelper.getSDKLongVersion("1.0.0") <= l)
      {
        StatPreferences.putLong(StatDispatcher.getApplicationContext(), HIBERNATE, l);
        setEnableStatService(false);
        logger.warn("MTA has disable for SDK version of " + str + " or lower.");
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      logger.d("__HIBERNATE__ not found.");
    }
  }

  public static String getAppKey(Context paramContext)
  {
    if (appkey != null)
      return appkey;
    if ((paramContext != null) && (appkey == null))
      appkey = StatCommonHelper.getAppKey(paramContext);
    if ((appkey == null) || (appkey.trim().length() == 0))
      logger.error("AppKey can not be null or empty, please read Developer's Guide first!");
    return appkey;
  }

  public static int getCurSessionStatReportCount()
  {
    return curSessionStatReportCount;
  }

  static int getCurrentDaySessionNumbers()
  {
    return currentDaySessionNumbers;
  }

  public static String getCustomProperty(String paramString)
  {
    try
    {
      String str = userCfg.props.getString(paramString);
      return str;
    }
    catch (JSONException localJSONException)
    {
    }
    return null;
  }

  public static String getCustomProperty(String paramString1, String paramString2)
  {
    try
    {
      String str = userCfg.props.getString(paramString1);
      if (str != null)
        paramString2 = str;
      return paramString2;
    }
    catch (JSONException localJSONException)
    {
    }
    return paramString2;
  }

  public static String getInstallChannel(Context paramContext)
  {
    if (installChannel != null)
      return installChannel;
    installChannel = StatCommonHelper.getInstallChannel(paramContext);
    if ((installChannel == null) || (installChannel.trim().length() == 0))
      logger.e("installChannel can not be null or empty, please read Developer's Guide first!");
    return installChannel;
  }

  public static int getMaxBatchReportCount()
  {
    return maxBatchReportCount;
  }

  public static int getMaxDaySessionNumbers()
  {
    return maxDaySessionNumbers;
  }

  static int getMaxLoadEventCount()
  {
    return maxLoadEventCount;
  }

  public static int getMaxParallelTimmingEvents()
  {
    return maxParallelTimmingEvents;
  }

  public static int getMaxReportEventLength()
  {
    return maxReportEventLength;
  }

  public static int getMaxSendRetryCount()
  {
    return maxSendRetryCount;
  }

  public static int getMaxSessionStatReportCount()
  {
    return maxSessionStatReportCount;
  }

  public static int getMaxStoreEventCount()
  {
    return maxStoreEventCount;
  }

  public static String getQQ()
  {
    return qq;
  }

  public static int getSendPeriodMinutes()
  {
    return sendPeriodMinutes;
  }

  public static int getSessionTimoutMillis()
  {
    return sessionTimoutMillis;
  }

  static HttpHost getStatHttpProxy()
  {
    if ((ta_http_proxy != null) && (ta_http_proxy.length() > 0))
    {
      String str = ta_http_proxy;
      String[] arrayOfString = str.split(":");
      int i = 80;
      if (arrayOfString.length == 2)
      {
        str = arrayOfString[0];
        i = Integer.parseInt(arrayOfString[1]);
      }
      return new HttpHost(str, i);
    }
    return null;
  }

  public static String getStatReportUrl()
  {
    return statReportUrl;
  }

  public static StatReportStrategy getStatSendStrategy()
  {
    return statSendStrategy;
  }

  static String getStoredAppkeys(Context paramContext)
  {
    return StatCommonHelper.decode(StatPreferences.getString(paramContext, "_mta_ky_tag_", null));
  }

  static void incCurSessionStatReportCount()
  {
    monitorenter;
    try
    {
      curSessionStatReportCount = 1 + curSessionStatReportCount;
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  static void incCurrentDaySessionNumbers()
  {
    currentDaySessionNumbers = 1 + currentDaySessionNumbers;
  }

  public static boolean isAutoExceptionCaught()
  {
    return isAutoExceptionCaught;
  }

  static boolean isBetween(int paramInt1, int paramInt2, int paramInt3)
  {
    return (paramInt1 >= paramInt2) && (paramInt1 <= paramInt3);
  }

  public static boolean isDebugEnable()
  {
    return StatCommonHelper.getLogger().isDebugEnable();
  }

  public static boolean isEnableSmartReporting()
  {
    return enableSmartReporting;
  }

  public static boolean isEnableStatService()
  {
    return enableStatService;
  }

  public static void setAppKey(Context paramContext, String paramString)
  {
    if (paramContext == null)
      logger.error("ctx in StatConfig.setAppKey() is null");
    do
    {
      return;
      if ((paramString == null) || (paramString.length() > 256))
      {
        logger.error("appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        return;
      }
      if (appkey != null)
        continue;
      appkey = getStoredAppkeys(paramContext);
    }
    while (!(updateAppkeys(paramString) | updateAppkeys(StatCommonHelper.getAppKey(paramContext))));
    storeAppkeys(paramContext, appkey);
  }

  @Deprecated
  public static void setAppKey(String paramString)
  {
    if (paramString == null)
    {
      logger.error("appkey in StatConfig.setAppKey() is null");
      return;
    }
    if (paramString.length() > 256)
    {
      logger.error("The length of appkey cann't exceed 256 bytes.");
      return;
    }
    appkey = paramString;
  }

  public static void setAutoExceptionCaught(boolean paramBoolean)
  {
    isAutoExceptionCaught = paramBoolean;
  }

  static void setConfig(OnlineConfig paramOnlineConfig)
    throws JSONException
  {
    if (paramOnlineConfig.type == sdkCfg.type)
    {
      sdkCfg = paramOnlineConfig;
      updateReportStrategy(sdkCfg.props);
    }
    do
      return;
    while (paramOnlineConfig.type != userCfg.type);
    userCfg = paramOnlineConfig;
  }

  static void setCurSessionStatReportCount(int paramInt)
  {
    monitorenter;
    try
    {
      curSessionStatReportCount = paramInt;
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  static void setCurrentDaySessionNumbers(int paramInt)
  {
    if (paramInt < 0)
      return;
    currentDaySessionNumbers = paramInt;
  }

  public static void setDebugEnable(boolean paramBoolean)
  {
    StatCommonHelper.getLogger().setDebugEnable(paramBoolean);
  }

  public static void setEnableSmartReporting(boolean paramBoolean)
  {
    enableSmartReporting = paramBoolean;
  }

  public static void setEnableStatService(boolean paramBoolean)
  {
    enableStatService = paramBoolean;
    if (!paramBoolean)
      logger.warn("!!!!!!MTA StatService has been disabled!!!!!!");
  }

  public static void setInstallChannel(String paramString)
  {
    if (paramString.length() > 128)
    {
      logger.error("the length of installChannel can not exceed the range of 128 bytes.");
      return;
    }
    installChannel = paramString;
  }

  public static void setMaxBatchReportCount(int paramInt)
  {
    if (!isBetween(paramInt, 2, 1000))
    {
      logger.error("setMaxBatchReportCount can not exceed the range of [2,1000].");
      return;
    }
    maxBatchReportCount = paramInt;
  }

  public static void setMaxDaySessionNumbers(int paramInt)
  {
    if (paramInt <= 0)
    {
      logger.e("maxDaySessionNumbers must be greater than 0.");
      return;
    }
    maxDaySessionNumbers = paramInt;
  }

  public static void setMaxParallelTimmingEvents(int paramInt)
  {
    if (!isBetween(paramInt, 1, 4096))
    {
      logger.error("setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
      return;
    }
    maxParallelTimmingEvents = paramInt;
  }

  public static void setMaxReportEventLength(int paramInt)
  {
    if (paramInt <= 0)
    {
      logger.error("maxReportEventLength on setMaxReportEventLength() must greater than 0.");
      return;
    }
    maxReportEventLength = paramInt;
  }

  public static void setMaxSendRetryCount(int paramInt)
  {
    if (!isBetween(paramInt, 1, 10))
    {
      logger.error("setMaxSendRetryCount can not exceed the range of [1,10].");
      return;
    }
    maxSendRetryCount = paramInt;
  }

  public static void setMaxSessionStatReportCount(int paramInt)
  {
    if (paramInt < 0)
    {
      logger.error("maxSessionStatReportCount cannot be less than 0.");
      return;
    }
    maxSessionStatReportCount = paramInt;
  }

  public static void setMaxStoreEventCount(int paramInt)
  {
    if (!isBetween(paramInt, 0, 500000))
    {
      logger.error("setMaxStoreEventCount can not exceed the range of [0, 500000].");
      return;
    }
    maxStoreEventCount = paramInt;
  }

  public static void setQQ(Context paramContext, String paramString)
  {
    StatService.reportQQ(paramContext, paramString);
  }

  public static void setSendPeriodMinutes(int paramInt)
  {
    if (!isBetween(paramInt, 1, 10080))
    {
      logger.error("setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
      return;
    }
    sendPeriodMinutes = paramInt;
  }

  public static void setSessionTimoutMillis(int paramInt)
  {
    if (!isBetween(paramInt, 1000, 86400000))
    {
      logger.error("setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
      return;
    }
    sessionTimoutMillis = paramInt;
  }

  public static void setStatReportUrl(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      logger.error("statReportUrl cannot be null or empty.");
      return;
    }
    statReportUrl = paramString;
  }

  public static void setStatSendStrategy(StatReportStrategy paramStatReportStrategy)
  {
    statSendStrategy = paramStatReportStrategy;
    logger.d("Change to statSendStrategy: " + paramStatReportStrategy);
  }

  static void storeAppkeys(Context paramContext, String paramString)
  {
    if (paramString != null)
      StatPreferences.putString(paramContext, "_mta_ky_tag_", StatCommonHelper.encode(paramString));
  }

  private static boolean updateAppkeys(String paramString)
  {
    if (paramString == null);
    do
    {
      return false;
      if (appkey != null)
        continue;
      appkey = paramString;
      return true;
    }
    while (appkey.contains(paramString));
    appkey = appkey + "|" + paramString;
    return true;
  }

  static void updateCfg(OnlineConfig paramOnlineConfig, JSONObject paramJSONObject)
  {
    do
    {
      while (true)
      {
        String str1;
        try
        {
          Iterator localIterator = paramJSONObject.keys();
          if (!localIterator.hasNext())
            break;
          str1 = (String)localIterator.next();
          if (!str1.equalsIgnoreCase("v"))
            continue;
          paramOnlineConfig.version = paramJSONObject.getInt(str1);
          continue;
        }
        catch (JSONException localJSONException)
        {
          logger.e(localJSONException);
          return;
          if (str1.equalsIgnoreCase("c"))
          {
            String str2 = paramJSONObject.getString("c");
            if (str2.length() <= 0)
              continue;
            paramOnlineConfig.props = new JSONObject(str2);
            continue;
          }
        }
        catch (Throwable localThrowable)
        {
          logger.e(localThrowable);
          return;
        }
        if (!str1.equalsIgnoreCase("m"))
          continue;
        paramOnlineConfig.md5sum = paramJSONObject.getString("m");
      }
      StatStore localStatStore = StatStore.getInstance(StatDispatcher.getApplicationContext());
      if (localStatStore == null)
        continue;
      localStatStore.storeCfg(paramOnlineConfig);
    }
    while (paramOnlineConfig.type != sdkCfg.type);
    updateReportStrategy(paramOnlineConfig.props);
    checkHibernate(paramOnlineConfig.props);
  }

  static void updateOnlineConfig(JSONObject paramJSONObject)
  {
    while (true)
    {
      String str;
      try
      {
        Iterator localIterator = paramJSONObject.keys();
        if (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          if (!str.equalsIgnoreCase(Integer.toString(sdkCfg.type)))
            break label67;
          JSONObject localJSONObject2 = paramJSONObject.getJSONObject(str);
          updateCfg(sdkCfg, localJSONObject2);
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        logger.e(localJSONException);
      }
      label67: 
      do
      {
        return;
        if (!str.equalsIgnoreCase(Integer.toString(userCfg.type)))
          continue;
        JSONObject localJSONObject1 = paramJSONObject.getJSONObject(str);
        updateCfg(userCfg, localJSONObject1);
        break;
      }
      while (!str.equalsIgnoreCase("rs"));
      StatReportStrategy localStatReportStrategy = StatReportStrategy.getStatReportStrategy(paramJSONObject.getInt(str));
      if (localStatReportStrategy == null)
        continue;
      statSendStrategy = localStatReportStrategy;
      logger.d("Change to ReportStrategy:" + localStatReportStrategy.name());
    }
  }

  static void updateReportStrategy(JSONObject paramJSONObject)
  {
    try
    {
      StatReportStrategy localStatReportStrategy = StatReportStrategy.getStatReportStrategy(paramJSONObject.getInt("rs"));
      if (localStatReportStrategy != null)
      {
        setStatSendStrategy(localStatReportStrategy);
        logger.debug("Change to ReportStrategy: " + localStatReportStrategy.name());
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      logger.d("rs not found.");
    }
  }

  static class OnlineConfig
  {
    String md5sum = "";
    JSONObject props = new JSONObject();
    int type;
    int version = 0;

    public OnlineConfig(int paramInt)
    {
      this.type = paramInt;
    }

    String toJsonString()
    {
      return this.props.toString();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatConfig
 * JD-Core Version:    0.6.0
 */
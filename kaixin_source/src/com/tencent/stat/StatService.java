package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.Env;
import com.tencent.stat.common.SdkProtection;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.StatPreferences;
import com.tencent.stat.common.User;
import com.tencent.stat.event.AdditionEvent;
import com.tencent.stat.event.CustomEvent;
import com.tencent.stat.event.CustomEvent.Key;
import com.tencent.stat.event.ErrorEvent;
import com.tencent.stat.event.Event;
import com.tencent.stat.event.EventType;
import com.tencent.stat.event.MonitorStatEvent;
import com.tencent.stat.event.PageView;
import com.tencent.stat.event.SessionEnv;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class StatService
{
  private static boolean firstSession;
  private static Handler handler;
  private static volatile long lastActivityTimestamp;
  private static volatile String lastReferPageId;
  private static volatile String last_pageId;
  private static StatLogger logger;
  private static volatile long nextDayStartTimestamp;
  private static Thread.UncaughtExceptionHandler originalExceptionHandler;
  private static volatile int sessionId;
  private static Map<CustomEvent.Key, Long> timedEventMap = new WeakHashMap();
  private static Map<String, Long> timedPageEventMap;

  static
  {
    lastActivityTimestamp = 0L;
    nextDayStartTimestamp = 0L;
    sessionId = 0;
    last_pageId = "";
    lastReferPageId = "";
    timedPageEventMap = new WeakHashMap();
    logger = StatCommonHelper.getLogger();
    originalExceptionHandler = null;
    firstSession = true;
  }

  public static void commitEvents(Context paramContext, int paramInt)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.commitEvents() can not be null!");
      return;
    }
    if ((paramInt < -1) || (paramInt == 0))
    {
      logger.error("The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
      return;
    }
    try
    {
      StatStore.getInstance(paramContext).loadEvents(paramInt);
      return;
    }
    catch (Throwable localThrowable)
    {
      reportSdkSelfException(paramContext, localThrowable);
    }
  }

  static JSONObject getEncodeConfig()
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      JSONObject localJSONObject2 = new JSONObject();
      if (StatConfig.sdkCfg.version != 0)
        localJSONObject2.put("v", StatConfig.sdkCfg.version);
      localJSONObject1.put(Integer.toString(StatConfig.sdkCfg.type), localJSONObject2);
      JSONObject localJSONObject3 = new JSONObject();
      if (StatConfig.userCfg.version != 0)
        localJSONObject3.put("v", StatConfig.userCfg.version);
      localJSONObject1.put(Integer.toString(StatConfig.userCfg.type), localJSONObject3);
      return localJSONObject1;
    }
    catch (JSONException localJSONException)
    {
      logger.e(localJSONException);
    }
    return localJSONObject1;
  }

  private static Handler getHandler(Context paramContext)
  {
    init(paramContext);
    return handler;
  }

  static int getSessionID(Context paramContext, boolean paramBoolean)
  {
    int i = 1;
    long l = System.currentTimeMillis();
    if ((paramBoolean) && (l - lastActivityTimestamp >= StatConfig.getSessionTimoutMillis()));
    for (int j = i; ; j = 0)
    {
      lastActivityTimestamp = l;
      if (nextDayStartTimestamp == 0L)
        nextDayStartTimestamp = StatCommonHelper.getTomorrowStartMilliseconds();
      if (l >= nextDayStartTimestamp)
      {
        nextDayStartTimestamp = StatCommonHelper.getTomorrowStartMilliseconds();
        if (StatStore.getInstance(paramContext).getUser(paramContext).getType() != i)
          StatStore.getInstance(paramContext).getUser(paramContext).setType(i);
        StatConfig.setCurrentDaySessionNumbers(0);
        j = i;
      }
      if (firstSession);
      while (true)
      {
        if (i != 0)
        {
          if (StatConfig.getCurrentDaySessionNumbers() >= StatConfig.getMaxDaySessionNumbers())
            break label133;
          sendNewSessionEnv(paramContext);
        }
        while (true)
        {
          if (firstSession)
          {
            SdkProtection.endCheck(paramContext);
            firstSession = false;
          }
          return sessionId;
          label133: logger.e("Exceed StatConfig.getMaxDaySessionNumbers().");
        }
        i = j;
      }
    }
  }

  static void init(Context paramContext)
  {
    if (paramContext == null);
    do
      return;
    while ((handler != null) || (!isServiceStatActive(paramContext)));
    if (!SdkProtection.beginCheck(paramContext))
    {
      logger.error("ooh, Compatibility problem was found in this device!");
      logger.error("If you are on debug mode, please delete apk and try again.");
      StatConfig.setEnableStatService(false);
      return;
    }
    StatStore.getInstance(paramContext);
    HandlerThread localHandlerThread = new HandlerThread("StatService");
    localHandlerThread.start();
    StatDispatcher.setApplicationContext(paramContext);
    handler = new Handler(localHandlerThread.getLooper());
    originalExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    if (StatConfig.isAutoExceptionCaught())
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(paramContext.getApplicationContext())
      {
        public void uncaughtException(Thread paramThread, Throwable paramThrowable)
        {
          if (!StatConfig.isEnableStatService())
            return;
          StatStore.getInstance(this.val$tempContext).storeEvent(new ErrorEvent(this.val$tempContext, StatService.getSessionID(this.val$tempContext, false), 2, paramThrowable), null);
          StatService.logger.debug("MTA has caught the following uncaught exception:");
          StatService.logger.error(paramThrowable);
          if (StatService.originalExceptionHandler != null)
          {
            StatService.logger.debug("Call the original uncaught exception handler.");
            StatService.originalExceptionHandler.uncaughtException(paramThread, paramThrowable);
            return;
          }
          StatService.logger.debug("Original uncaught exception handler not set.");
        }
      });
    while (true)
    {
      if ((StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH) && (StatCommonHelper.isNetworkAvailable(paramContext)))
        StatStore.getInstance(paramContext).loadEvents(-1);
      logger.d("Init MTA StatService success.");
      return;
      logger.warn("MTA SDK AutoExceptionCaught is disable");
    }
  }

  static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  static boolean isServiceStatActive(Context paramContext)
  {
    long l = StatPreferences.getLong(paramContext, StatConfig.HIBERNATE, 0L);
    if (StatCommonHelper.getSDKLongVersion("1.0.0") <= l)
    {
      StatConfig.setEnableStatService(false);
      return false;
    }
    return true;
  }

  public static void onPause(Context paramContext)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.onPause() can not be null!");
      return;
    }
    String str;
    try
    {
      str = StatCommonHelper.getActivityName(paramContext);
      Long localLong1 = (Long)timedPageEventMap.remove(str);
      if (localLong1 != null)
      {
        Long localLong2 = Long.valueOf((System.currentTimeMillis() - localLong1.longValue()) / 1000L);
        if (localLong2.longValue() == 0L)
          localLong2 = Long.valueOf(1L);
        if (lastReferPageId.equals(str) == true)
          lastReferPageId = "-";
        PageView localPageView = new PageView(paramContext, lastReferPageId, getSessionID(paramContext, false), localLong2);
        if (!localPageView.getPageId().equals(last_pageId))
          logger.warn("Invalid invocation since previous onResume on diff page.");
        if (getHandler(paramContext) != null)
          getHandler(paramContext).post(new StatTask(localPageView));
        lastReferPageId = str;
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      reportSdkSelfException(paramContext, localThrowable);
      return;
    }
    logger.e("Starttime for PageID:" + str + " not found, lost onResume()?");
  }

  public static void onResume(Context paramContext)
  {
    if (!StatConfig.isEnableStatService());
    do
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.onResume() can not be null!");
        return;
      }
      try
      {
        if (timedPageEventMap.size() >= StatConfig.getMaxParallelTimmingEvents())
        {
          logger.error("The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
        return;
      }
      last_pageId = StatCommonHelper.getActivityName(paramContext);
    }
    while (last_pageId == null);
    if (timedPageEventMap.containsKey(last_pageId))
    {
      logger.e("Duplicate PageID : " + last_pageId + ", onResume() repeated?");
      return;
    }
    timedPageEventMap.put(last_pageId, Long.valueOf(System.currentTimeMillis()));
    getSessionID(paramContext, true);
  }

  public static void reportAppMonitorStat(Context paramContext, StatAppMonitor paramStatAppMonitor)
  {
    if (!StatConfig.isEnableStatService());
    while (true)
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.reportAppMonitorStat() can not be null!");
        return;
      }
      if (paramStatAppMonitor == null)
      {
        logger.error("The StatAppMonitor of StatService.reportAppMonitorStat() can not be null!");
        return;
      }
      if (paramStatAppMonitor.getInterfaceName() == null)
      {
        logger.error("The interfaceName of StatAppMonitor on StatService.reportAppMonitorStat() can not be null!");
        return;
      }
      try
      {
        MonitorStatEvent localMonitorStatEvent = new MonitorStatEvent(paramContext, getSessionID(paramContext, false), paramStatAppMonitor);
        if (getHandler(paramContext) == null)
          continue;
        getHandler(paramContext).post(new StatTask(localMonitorStatEvent));
        return;
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
      }
    }
  }

  public static void reportError(Context paramContext, String paramString)
  {
    if (!StatConfig.isEnableStatService());
    while (true)
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.reportError() can not be null!");
        return;
      }
      if (isEmpty(paramString))
      {
        logger.error("Error message in StatService.reportError() is empty.");
        return;
      }
      try
      {
        ErrorEvent localErrorEvent = new ErrorEvent(paramContext, getSessionID(paramContext, false), paramString);
        if (getHandler(paramContext) == null)
          continue;
        getHandler(paramContext).post(new StatTask(localErrorEvent));
        return;
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
      }
    }
  }

  public static void reportException(Context paramContext, Throwable paramThrowable)
  {
    if (!StatConfig.isEnableStatService());
    ErrorEvent localErrorEvent;
    do
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.reportException() can not be null!");
        return;
      }
      if (paramThrowable == null)
      {
        logger.error("The Throwable error message of StatService.reportException() can not be null!");
        return;
      }
      localErrorEvent = new ErrorEvent(paramContext, getSessionID(paramContext, false), 1, paramThrowable);
    }
    while (getHandler(paramContext) == null);
    getHandler(paramContext).post(new StatTask(localErrorEvent));
  }

  public static void reportQQ(Context paramContext, String paramString)
  {
    if (paramString == null)
      paramString = "";
    if (!StatConfig.qq.equals(paramString))
    {
      StatConfig.qq = paramString;
      sendAdditionEvent(paramContext, null);
    }
  }

  static void reportSdkSelfException(Context paramContext, Throwable paramThrowable)
  {
    try
    {
      if (!StatConfig.isEnableStatService())
        return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.reportSdkSelfException() can not be null!");
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      logger.e("reportSdkSelfException error: " + localThrowable);
      return;
    }
    ErrorEvent localErrorEvent = new ErrorEvent(paramContext, getSessionID(paramContext, false), 99, paramThrowable);
    if (getHandler(paramContext) != null)
      getHandler(paramContext).post(new StatTask(localErrorEvent));
  }

  static void sendAdditionEvent(Context paramContext, Map<String, ?> paramMap)
  {
    if (!StatConfig.isEnableStatService());
    while (true)
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.sendAdditionEvent() can not be null!");
        return;
      }
      try
      {
        AdditionEvent localAdditionEvent = new AdditionEvent(paramContext, getSessionID(paramContext, false), paramMap);
        if (getHandler(paramContext) == null)
          continue;
        getHandler(paramContext).post(new StatTask(localAdditionEvent));
        return;
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
      }
    }
  }

  static void sendNewSessionEnv(Context paramContext)
  {
    if (getHandler(paramContext) != null)
    {
      logger.d("start new session.");
      sessionId = StatCommonHelper.getNextSessionID();
      StatConfig.setCurSessionStatReportCount(0);
      StatConfig.incCurrentDaySessionNumbers();
      getHandler(paramContext).post(new StatTask(new SessionEnv(paramContext, sessionId, getEncodeConfig())));
    }
  }

  public static void setEnvAttributes(Context paramContext, Map<String, String> paramMap)
  {
    if ((paramMap == null) || (paramMap.size() > 512))
    {
      logger.error("The map in setEnvAttributes can't be null or its size can't exceed 512.");
      return;
    }
    try
    {
      Env.appendEnvAttr(paramContext, paramMap);
      return;
    }
    catch (JSONException localJSONException)
    {
      logger.e(localJSONException);
    }
  }

  public static void startNewSession(Context paramContext)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.startNewSession() can not be null!");
      return;
    }
    try
    {
      stopSession();
      getSessionID(paramContext, true);
      return;
    }
    catch (Throwable localThrowable)
    {
      reportSdkSelfException(paramContext, localThrowable);
    }
  }

  public static boolean startStatService(Context paramContext, String paramString1, String paramString2)
    throws MtaSDkException
  {
    if (!StatConfig.isEnableStatService())
    {
      logger.error("MTA StatService is disable.");
      return false;
    }
    logger.d("MTA SDK version, current: " + "1.0.0" + " ,required: " + paramString2);
    if ((paramContext == null) || (paramString2 == null))
    {
      logger.error("Context or mtaSdkVersion in StatService.startStatService() is null, please check it!");
      StatConfig.setEnableStatService(false);
      throw new MtaSDkException("Context or mtaSdkVersion in StatService.startStatService() is null, please check it!");
    }
    if (StatCommonHelper.getSDKLongVersion("1.0.0") < StatCommonHelper.getSDKLongVersion(paramString2))
    {
      String str2 = "MTA SDK version conflicted, current: " + "1.0.0" + ",required: " + paramString2;
      String str3 = str2 + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/";
      logger.error(str3);
      StatConfig.setEnableStatService(false);
      throw new MtaSDkException(str3);
    }
    try
    {
      String str1 = StatConfig.getInstallChannel(paramContext);
      if ((str1 == null) || (str1.length() == 0))
        StatConfig.setInstallChannel("-");
      StatConfig.setAppKey(paramContext, paramString1);
      getHandler(paramContext);
      return true;
    }
    catch (Throwable localThrowable)
    {
      logger.e(localThrowable);
    }
    return false;
  }

  public static void stopSession()
  {
    lastActivityTimestamp = 0L;
  }

  public static void trackCustomBeginEvent(Context paramContext, String paramString, String[] paramArrayOfString)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.trackCustomBeginEvent() can not be null!");
      return;
    }
    if (isEmpty(paramString))
    {
      logger.error("The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
      return;
    }
    CustomEvent.Key localKey;
    try
    {
      CustomEvent localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
      localCustomEvent.setArgs(paramArrayOfString);
      localKey = localCustomEvent.getKey();
      if (timedEventMap.containsKey(localKey))
      {
        logger.error("Duplicate CustomEvent key: " + localKey.toString() + ", trackCustomBeginEvent() repeated?");
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      reportSdkSelfException(paramContext, localThrowable);
      return;
    }
    if (timedEventMap.size() <= StatConfig.getMaxParallelTimmingEvents())
    {
      timedEventMap.put(localKey, Long.valueOf(System.currentTimeMillis()));
      return;
    }
    logger.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
  }

  public static void trackCustomBeginKVEvent(Context paramContext, String paramString, Properties paramProperties)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.trackCustomBeginEvent() can not be null!");
      return;
    }
    if (isEmpty(paramString))
    {
      logger.error("The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
      return;
    }
    CustomEvent.Key localKey;
    try
    {
      CustomEvent localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
      localCustomEvent.setProperties(paramProperties);
      localKey = localCustomEvent.getKey();
      if (timedEventMap.containsKey(localKey))
      {
        logger.error("Duplicate CustomEvent key: " + localKey.toString() + ", trackCustomBeginKVEvent() repeated?");
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      reportSdkSelfException(paramContext, localThrowable);
      return;
    }
    if (timedEventMap.size() <= StatConfig.getMaxParallelTimmingEvents())
    {
      timedEventMap.put(localKey, Long.valueOf(System.currentTimeMillis()));
      return;
    }
    logger.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
  }

  public static void trackCustomEndEvent(Context paramContext, String paramString, String[] paramArrayOfString)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.trackCustomEndEvent() can not be null!");
      return;
    }
    if (isEmpty(paramString))
    {
      logger.error("The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
      return;
    }
    CustomEvent localCustomEvent;
    while (true)
    {
      Long localLong2;
      try
      {
        localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
        localCustomEvent.setArgs(paramArrayOfString);
        Long localLong1 = (Long)timedEventMap.remove(localCustomEvent.getKey());
        if (localLong1 == null)
          break label166;
        localLong2 = Long.valueOf((System.currentTimeMillis() - localLong1.longValue()) / 1000L);
        if (localLong2.longValue() == 0L)
        {
          l = 1L;
          localCustomEvent.setDuration(Long.valueOf(l).longValue());
          if (getHandler(paramContext) == null)
            break;
          getHandler(paramContext).post(new StatTask(localCustomEvent));
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
        return;
      }
      long l = localLong2.longValue();
    }
    label166: logger.error("No start time found for custom event: " + localCustomEvent.getKey().toString() + ", lost trackCustomBeginEvent()?");
  }

  public static void trackCustomEndKVEvent(Context paramContext, String paramString, Properties paramProperties)
  {
    if (!StatConfig.isEnableStatService())
      return;
    if (paramContext == null)
    {
      logger.error("The Context of StatService.trackCustomEndEvent() can not be null!");
      return;
    }
    if (isEmpty(paramString))
    {
      logger.error("The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
      return;
    }
    CustomEvent localCustomEvent;
    while (true)
    {
      Long localLong2;
      try
      {
        localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
        localCustomEvent.setProperties(paramProperties);
        Long localLong1 = (Long)timedEventMap.remove(localCustomEvent.getKey());
        if (localLong1 == null)
          break label166;
        localLong2 = Long.valueOf((System.currentTimeMillis() - localLong1.longValue()) / 1000L);
        if (localLong2.longValue() == 0L)
        {
          l = 1L;
          localCustomEvent.setDuration(Long.valueOf(l).longValue());
          if (getHandler(paramContext) == null)
            break;
          getHandler(paramContext).post(new StatTask(localCustomEvent));
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
        return;
      }
      long l = localLong2.longValue();
    }
    label166: logger.error("No start time found for custom event: " + localCustomEvent.getKey().toString() + ", lost trackCustomBeginKVEvent()?");
  }

  public static void trackCustomEvent(Context paramContext, String paramString, String[] paramArrayOfString)
  {
    if (!StatConfig.isEnableStatService());
    while (true)
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.trackCustomEvent() can not be null!");
        return;
      }
      if (isEmpty(paramString))
      {
        logger.error("The event_id of StatService.trackCustomEvent() can not be null or empty.");
        return;
      }
      try
      {
        CustomEvent localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
        localCustomEvent.setArgs(paramArrayOfString);
        if (getHandler(paramContext) == null)
          continue;
        getHandler(paramContext).post(new StatTask(localCustomEvent));
        return;
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
      }
    }
  }

  public static void trackCustomKVEvent(Context paramContext, String paramString, Properties paramProperties)
  {
    if (!StatConfig.isEnableStatService());
    while (true)
    {
      return;
      if (paramContext == null)
      {
        logger.error("The Context of StatService.trackCustomEvent() can not be null!");
        return;
      }
      if (isEmpty(paramString))
      {
        logger.error("The event_id of StatService.trackCustomEvent() can not be null or empty.");
        return;
      }
      try
      {
        CustomEvent localCustomEvent = new CustomEvent(paramContext, getSessionID(paramContext, false), paramString);
        localCustomEvent.setProperties(paramProperties);
        if (getHandler(paramContext) == null)
          continue;
        getHandler(paramContext).post(new StatTask(localCustomEvent));
        return;
      }
      catch (Throwable localThrowable)
      {
        reportSdkSelfException(paramContext, localThrowable);
      }
    }
  }

  static class StatTask
    implements Runnable
  {
    private Event ev;
    private StatReportStrategy strategy = null;

    public StatTask(Event paramEvent)
    {
      this.ev = paramEvent;
      this.strategy = StatConfig.getStatSendStrategy();
    }

    private void sendEvents()
    {
      if (StatStore.getInstance().getNumStoredEvents() > 0)
      {
        StatStore.getInstance().storeEvent(this.ev, null);
        StatStore.getInstance().loadEvents(-1);
        return;
      }
      sendEventsNow(true);
    }

    private void sendEventsNow(boolean paramBoolean)
    {
      1 local1 = null;
      if (paramBoolean)
        local1 = new StatDispatchCallback()
        {
          public void onDispatchFailure()
          {
            StatStore.getInstance().storeEvent(StatService.StatTask.this.ev, null);
          }

          public void onDispatchSuccess()
          {
            StatStore.getInstance().loadEvents(-1);
          }
        };
      StatDispatcher.getInstance().send(this.ev, local1);
    }

    public void run()
    {
      if (!StatConfig.isEnableStatService());
      Context localContext;
      do
        while (true)
        {
          return;
          if ((this.ev.getType() != EventType.ERROR) && (this.ev.toJsonString().length() > StatConfig.getMaxReportEventLength()))
          {
            StatService.logger.e("Event length exceed StatConfig.getMaxReportEventLength(): " + StatConfig.getMaxReportEventLength());
            return;
          }
          if (StatConfig.getMaxSessionStatReportCount() > 0)
          {
            if (StatConfig.getCurSessionStatReportCount() >= StatConfig.getMaxSessionStatReportCount())
            {
              StatService.logger.e("Times for reporting events has reached the limit of StatConfig.getMaxSessionStatReportCount() in current session.");
              return;
            }
            StatConfig.incCurSessionStatReportCount();
          }
          StatService.logger.i("Lauch stat task in thread:" + Thread.currentThread().getName());
          localContext = this.ev.getContext();
          if (!StatCommonHelper.isNetworkAvailable(localContext))
          {
            StatStore.getInstance(localContext).storeEvent(this.ev, null);
            return;
          }
          if ((StatConfig.isEnableSmartReporting()) && (this.strategy != StatReportStrategy.ONLY_WIFI_NO_CACHE) && (StatCommonHelper.isWifiNet(localContext)))
            this.strategy = StatReportStrategy.INSTANT;
          switch (StatService.2.$SwitchMap$com$tencent$stat$StatReportStrategy[this.strategy.ordinal()])
          {
          default:
            StatService.logger.error("Invalid stat strategy:" + StatConfig.getStatSendStrategy());
            return;
          case 1:
            sendEvents();
            return;
          case 2:
            if (StatCommonHelper.isWiFiActive(localContext))
            {
              sendEvents();
              return;
            }
            StatStore.getInstance(localContext).storeEvent(this.ev, null);
            return;
          case 3:
          case 4:
            StatStore.getInstance(localContext).storeEvent(this.ev, null);
            return;
          case 5:
            if (StatStore.getInstance(this.ev.getContext()) == null)
              continue;
            StatStore.getInstance(localContext).storeEvent(this.ev, new StatDispatchCallback()
            {
              public void onDispatchFailure()
              {
              }

              public void onDispatchSuccess()
              {
                if (StatStore.getInstance().getNumStoredEvents() >= StatConfig.getMaxBatchReportCount())
                  StatStore.getInstance().loadEvents(StatConfig.getMaxBatchReportCount());
              }
            });
            return;
          case 6:
            try
            {
              StatStore.getInstance(localContext).storeEvent(this.ev, null);
              Long localLong1 = Long.valueOf(StatPreferences.getLong(localContext, "last_period_ts", 0L));
              Long localLong2 = Long.valueOf(System.currentTimeMillis());
              if (Long.valueOf(Long.valueOf(localLong2.longValue() - localLong1.longValue()).longValue() / 60000L).longValue() <= StatConfig.getSendPeriodMinutes())
                continue;
              StatStore.getInstance(localContext).loadEvents(-1);
              StatPreferences.putLong(localContext, "last_period_ts", localLong2.longValue());
              return;
            }
            catch (Exception localException)
            {
              StatService.logger.e(localException);
              return;
            }
          case 7:
          }
        }
      while (!StatCommonHelper.isWiFiActive(localContext));
      sendEventsNow(false);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatService
 * JD-Core Version:    0.6.0
 */
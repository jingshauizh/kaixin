package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.umeng.common.Log;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;

public class MobclickAgent
{
  private static final b a = new b();

  static b a()
  {
    return a;
  }

  public static void flush(Context paramContext)
  {
    a.d(paramContext);
  }

  public static String getConfigParams(Context paramContext, String paramString)
  {
    return h.b(paramContext).getString(paramString, "");
  }

  public static void onError(Context paramContext)
  {
    a.b(paramContext);
  }

  public static void onError(Context paramContext, String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      Log.b("MobclickAgent", "unexpected empty appkey in onError");
      return;
    }
    a.b = paramString;
    onError(paramContext);
  }

  public static void onEvent(Context paramContext, String paramString)
  {
    a.a(paramContext, paramString, null, -1L, 1);
  }

  public static void onEvent(Context paramContext, String paramString, int paramInt)
  {
    a.a(paramContext, paramString, null, -1L, paramInt);
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2))
    {
      Log.a("MobclickAgent", "label is null or empty");
      return;
    }
    a.a(paramContext, paramString1, paramString2, -1L, 1);
  }

  public static void onEvent(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    if (TextUtils.isEmpty(paramString2))
    {
      Log.a("MobclickAgent", "label is null or empty");
      return;
    }
    a.a(paramContext, paramString1, paramString2, -1L, paramInt);
  }

  public static void onEvent(Context paramContext, String paramString, Map<String, String> paramMap)
  {
    a.a(paramContext, paramString, paramMap, -1L);
  }

  public static void onEventBegin(Context paramContext, String paramString)
  {
    a.b(paramContext, paramString);
  }

  public static void onEventBegin(Context paramContext, String paramString1, String paramString2)
  {
    a.a(paramContext, paramString1, paramString2);
  }

  public static void onEventDuration(Context paramContext, String paramString, long paramLong)
  {
    if (paramLong <= 0L)
    {
      Log.a("MobclickAgent", "duration is not valid in onEventDuration");
      return;
    }
    a.a(paramContext, paramString, null, paramLong, 1);
  }

  public static void onEventDuration(Context paramContext, String paramString1, String paramString2, long paramLong)
  {
    if (TextUtils.isEmpty(paramString2))
    {
      Log.a("MobclickAgent", "label is null or empty");
      return;
    }
    if (paramLong <= 0L)
    {
      Log.a("MobclickAgent", "duration is not valid in onEventDuration");
      return;
    }
    a.a(paramContext, paramString1, paramString2, paramLong, 1);
  }

  public static void onEventDuration(Context paramContext, String paramString, Map<String, String> paramMap, long paramLong)
  {
    if (paramLong <= 0L)
    {
      Log.a("MobclickAgent", "duration is not valid in onEventDuration");
      return;
    }
    a.a(paramContext, paramString, paramMap, paramLong);
  }

  public static void onEventEnd(Context paramContext, String paramString)
  {
    a.c(paramContext, paramString);
  }

  public static void onEventEnd(Context paramContext, String paramString1, String paramString2)
  {
    a.b(paramContext, paramString1, paramString2);
  }

  public static void onKVEventBegin(Context paramContext, String paramString1, Map<String, String> paramMap, String paramString2)
  {
    a.a(paramContext, paramString1, paramMap, paramString2);
  }

  public static void onKVEventEnd(Context paramContext, String paramString1, String paramString2)
  {
    a.c(paramContext, paramString1, paramString2);
  }

  public static void onPause(Context paramContext)
  {
    a.a(paramContext);
  }

  public static void onResume(Context paramContext)
  {
    a.c(paramContext);
  }

  public static void onResume(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      Log.b("MobclickAgent", "unexpected empty appkey in onResume");
      return;
    }
    a.b = paramString1;
    a.a = paramString2;
    a.c(paramContext);
  }

  public static void openActivityDurationTrack(boolean paramBoolean)
  {
    e.h = paramBoolean;
  }

  public static void reportError(Context paramContext, String paramString)
  {
    a.a(paramContext, paramString);
  }

  public static void setAutoLocation(boolean paramBoolean)
  {
    e.g = paramBoolean;
  }

  public static void setDebugMode(boolean paramBoolean)
  {
    Log.LOG = paramBoolean;
  }

  public static void setDefaultReportPolicy(Context paramContext, int paramInt)
  {
    Log.e("MobclickAgent", "此方法不再使用，请使用在线参数配置，发送策略");
  }

  public static void setEnableEventBuffer(boolean paramBoolean)
  {
    e.k = paramBoolean;
  }

  public static void setOnlineConfigureListener(UmengOnlineConfigureListener paramUmengOnlineConfigureListener)
  {
    a.c = paramUmengOnlineConfigureListener;
  }

  public static void setOpenGLContext(GL10 paramGL10)
  {
    if (paramGL10 != null)
    {
      String[] arrayOfString = com.umeng.common.b.a(paramGL10);
      if (arrayOfString.length == 2)
      {
        a.d = arrayOfString[0];
        a.e = arrayOfString[1];
      }
    }
  }

  public static void setSessionContinueMillis(long paramLong)
  {
    e.d = paramLong;
  }

  public static void updateOnlineConfig(Context paramContext)
  {
    a.e(paramContext);
  }

  public static void updateOnlineConfig(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      Log.b("MobclickAgent", "unexpected empty appkey in onResume");
      return;
    }
    a.b = paramString1;
    a.a = paramString2;
    a.e(paramContext);
  }

  public void setAge(Context paramContext, int paramInt)
  {
    SharedPreferences localSharedPreferences = h.a(paramContext);
    if ((paramInt < 0) || (paramInt > 200))
    {
      Log.a("MobclickAgent", "not a valid age!");
      return;
    }
    localSharedPreferences.edit().putInt("age", paramInt).commit();
  }

  public void setGender(Context paramContext, Gender paramGender)
  {
    SharedPreferences localSharedPreferences = h.a(paramContext);
    int i = 1.a[paramGender.ordinal()];
    int j = 0;
    switch (i)
    {
    case 3:
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      localSharedPreferences.edit().putInt("gender", j).commit();
      return;
      j = 1;
      continue;
      j = 2;
    }
  }

  public void setUserID(Context paramContext, String paramString1, String paramString2)
  {
    SharedPreferences localSharedPreferences = h.a(paramContext);
    if (TextUtils.isEmpty(paramString1))
    {
      Log.a("MobclickAgent", "userID is null or empty");
      return;
    }
    localSharedPreferences.edit().putString("user_id", paramString1).commit();
    if (TextUtils.isEmpty(paramString2))
    {
      Log.a("MobclickAgent", "id source is null or empty");
      return;
    }
    localSharedPreferences.edit().putString("id_source", paramString2).commit();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.MobclickAgent
 * JD-Core Version:    0.6.0
 */
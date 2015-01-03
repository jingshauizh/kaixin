package com.kaixin001.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ActivityUtil
{
  public static boolean checkActivityExist(Context paramContext, Class<?> paramClass)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(40).iterator();
    ActivityManager.RunningTaskInfo localRunningTaskInfo;
    do
    {
      if (!localIterator.hasNext())
      {
        KXLog.d("TEST", paramClass.getCanonicalName() + " does not exist");
        return false;
      }
      localRunningTaskInfo = (ActivityManager.RunningTaskInfo)localIterator.next();
    }
    while (!paramClass.getCanonicalName().equals(localRunningTaskInfo.baseActivity.getClassName()));
    KXLog.d("TEST", paramClass.getCanonicalName() + " exists");
    return true;
  }

  public static boolean checkKXActivityExist(Context paramContext)
  {
    List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(40);
    if (localList == null)
      return false;
    Iterator localIterator = localList.iterator();
    ActivityManager.RunningTaskInfo localRunningTaskInfo;
    do
    {
      if (!localIterator.hasNext())
      {
        KXLog.d("TEST", "com.kaixin001.activity.*Activity does not exist");
        return false;
      }
      localRunningTaskInfo = (ActivityManager.RunningTaskInfo)localIterator.next();
    }
    while ((localRunningTaskInfo == null) || (-1 == localRunningTaskInfo.baseActivity.getClassName().indexOf("com.kaixin001.activity")) || (localRunningTaskInfo.baseActivity.getClassName().equals("com.kaixin001.activity.MessagePushDialogActivity")));
    KXLog.d("TEST", localRunningTaskInfo.baseActivity.getClassName() + " exists");
    return true;
  }

  public static void hideInputMethod(Activity paramActivity)
  {
    View localView = paramActivity.getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)paramActivity.getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  public static boolean isServiceStarted(Context paramContext, String paramString)
  {
    try
    {
      Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(1000).iterator();
      boolean bool;
      do
      {
        if (!localIterator.hasNext())
          return false;
        bool = ((ActivityManager.RunningServiceInfo)localIterator.next()).service.getPackageName().equals(paramString);
      }
      while (!bool);
      return true;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return false;
  }

  public static boolean isTopActivity(Context paramContext, String paramString1, String paramString2)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(40).iterator();
    ActivityManager.RunningTaskInfo localRunningTaskInfo;
    do
    {
      if (!localIterator.hasNext())
      {
        KXLog.d("TEST", paramString2 + "not on top");
        return false;
      }
      localRunningTaskInfo = (ActivityManager.RunningTaskInfo)localIterator.next();
    }
    while ((!localRunningTaskInfo.baseActivity.getClassName().equals(paramString1)) || (!localRunningTaskInfo.topActivity.getClassName().equals(paramString2)));
    KXLog.d("TEST", paramString2 + "is on top");
    return true;
  }

  public static boolean needNotification(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext.getApplicationContext());
    boolean bool1 = localSharedPreferences.getBoolean("notification_message_logout_preference", true);
    boolean bool2 = localSharedPreferences.getBoolean("no_notification_evening_preference", true);
    int i = 1;
    if (!checkKXActivityExist(paramContext.getApplicationContext()))
    {
      if (bool1)
        break label51;
      i = 0;
    }
    label51: int j;
    do
    {
      do
        return i;
      while (!bool2);
      j = Calendar.getInstance().get(11);
    }
    while ((j >= 9) && (j <= 22));
    return false;
  }

  public static void showInputMethod(Activity paramActivity)
  {
    View localView = paramActivity.getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)paramActivity.getSystemService("input_method")).showSoftInput(localView, 0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ActivityUtil
 * JD-Core Version:    0.6.0
 */
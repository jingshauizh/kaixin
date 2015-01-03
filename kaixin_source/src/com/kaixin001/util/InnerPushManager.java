package com.kaixin001.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import com.kaixin001.engine.InnerPushEngine;
import com.kaixin001.model.User;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class InnerPushManager
{
  public static final int NOTIFICATION_ID = 4387;
  public static final String SHARED_HOROSCOPE = "horoscope_push";
  public static final String SHARED_PREFE_LOGIN = "loginNum";
  public static final String SP_IS_LOGED = "isLoged";
  public static final String SP_LOGOUT_TIME = "logoutTime";
  public static final String SP_TODAY_CHECK = "isChecked";
  public static final String SP_TODAY_HOROSCOPE_ED = "isObtained";
  public static final String TAG = "InnerPushManager";
  private static InnerPushManager instance;
  private String action = "com.kaixin001.VIEW_NEWS";
  private Context mContext;
  private String txtPush;

  public static InnerPushManager getInstance(Context paramContext)
  {
    if (instance == null)
    {
      instance = new InnerPushManager();
      instance.setContext(paramContext);
    }
    return instance;
  }

  private int getNoLoginDays()
  {
    long l1 = this.mContext.getSharedPreferences("loginNum", 0).getLong("logoutTime", 0L);
    if (l1 == 0L)
    {
      setLoginTime();
      return 0;
    }
    InnerPushEngine.getInstance().runEngine(this.mContext);
    String str = InnerPushEngine.getInstance().getLatestTime();
    if (!str.equals(""));
    try
    {
      long l2 = TimeConvertUtil.strToLong(str);
      if (l2 > l1)
      {
        setLoginTime(l2);
        l1 = l2;
      }
      return TimeUtil.getDaysBetween(Calendar.getInstance(), TimeConvertUtil.millisToCalendar(l1));
    }
    catch (ParseException localParseException)
    {
      while (true)
        localParseException.printStackTrace();
    }
  }

  private void initPushText()
  {
    switch ((int)Math.round(7.0D * Math.random()))
    {
    default:
      return;
    case 0:
      this.txtPush = this.mContext.getResources().getString(2131428644);
      this.action = "com.kaixin001.VIEW_NEWS";
      return;
    case 1:
      this.txtPush = this.mContext.getResources().getString(2131428639);
      this.action = "com.kaixin001.VIEW_REPOST";
      return;
    case 2:
      this.txtPush = this.mContext.getResources().getString(2131428640);
      this.action = "com.kaixin001.VIEW_HOME_DETAIL";
      return;
    case 3:
      this.txtPush = this.mContext.getResources().getString(2131428641);
      this.action = "com.kaixin001.VIEW_REPOST";
      return;
    case 4:
      this.txtPush = this.mContext.getResources().getString(2131428642);
      this.action = "com.kaixin001.VIEW_HOME_DETAIL";
      return;
    case 5:
      this.txtPush = this.mContext.getResources().getString(2131428643);
      this.action = "com.kaixin001.VIEW_REPOST";
      return;
    case 6:
    }
    this.txtPush = this.mContext.getResources().getString(2131428638);
    this.action = "com.kaixin001.VIEW_NEWS";
  }

  private boolean isAtTime(int paramInt1, int paramInt2)
  {
    int i = TimeUtil.getIntHour();
    return (i >= paramInt1) && (i <= paramInt2);
  }

  private boolean isDaysTrue(int paramInt)
  {
    if ((paramInt == 2) || (paramInt == 6) || (paramInt == 10) || (paramInt == 15));
    while (true)
    {
      return true;
      if (paramInt <= 15)
        break;
      if ((paramInt - 15) % 6 != 0)
        return false;
    }
    return false;
  }

  private boolean isNeedCheck()
  {
    return (!isTodayChecked()) && (isAtTime(16, 20)) && (isLoged()) && (getNoLoginDays() != 0);
  }

  private boolean isTodayChecked()
  {
    return this.mContext.getSharedPreferences("loginNum", 0).getBoolean("isChecked" + TimeUtil.getStringDateShort(), false);
  }

  private void sendInnerPush(int paramInt)
  {
    if (isDaysTrue(paramInt))
      sendNotification();
  }

  private void sendNotification()
  {
    initPushText();
    String str = this.mContext.getResources().getString(2131427329);
    Notification localNotification = new Notification();
    if (User.getInstance().GetLookAround())
      this.action = "com.kaixin001.VIEW_LOGIN";
    Intent localIntent = new Intent(this.action);
    localIntent.putExtra("InnerPushManager", true);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 0);
    localNotification.icon = 2130838989;
    localNotification.tickerText = this.txtPush;
    localNotification.when = System.currentTimeMillis();
    localNotification.defaults = 1;
    localNotification.defaults = -1;
    localNotification.flags = 16;
    localNotification.setLatestEventInfo(this.mContext, str, this.txtPush, localPendingIntent);
    ((NotificationManager)this.mContext.getSystemService("notification")).notify(4387, localNotification);
  }

  private void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void addCount()
  {
    UserHabitUtils.getInstance(this.mContext).addUserHabit("inner_push_enter");
  }

  public void beginInnerPush()
  {
    if (isNeedCheck())
    {
      sendInnerPush(getNoLoginDays());
      setTodayChecked(true);
    }
  }

  public void cancenNotify()
  {
    ((NotificationManager)this.mContext.getSystemService("notification")).cancel(4387);
  }

  public long getLogoutTime()
  {
    return this.mContext.getSharedPreferences("loginNum", 0).getLong("logoutTime", new Date().getTime());
  }

  public boolean isLoged()
  {
    return this.mContext.getSharedPreferences("loginNum", 0).getBoolean("isLoged", false);
  }

  public boolean isTodayGetHorosd()
  {
    return this.mContext.getSharedPreferences("horoscope_push", 0).getBoolean("isObtained" + TimeUtil.getStringDateShort(), false);
  }

  public void setLoged(boolean paramBoolean)
  {
    this.mContext.getSharedPreferences("loginNum", 0).edit().putBoolean("isLoged", paramBoolean).commit();
  }

  public void setLoginTime()
  {
    this.mContext.getSharedPreferences("loginNum", 0).edit().putLong("logoutTime", new Date().getTime()).commit();
  }

  public void setLoginTime(long paramLong)
  {
    this.mContext.getSharedPreferences("loginNum", 0).edit().putLong("logoutTime", paramLong).commit();
  }

  public void setTodayChecked(boolean paramBoolean)
  {
    this.mContext.getSharedPreferences("loginNum", 0).edit().putBoolean("isChecked" + TimeUtil.getStringDateShort(), paramBoolean).commit();
  }

  public void setTodayGetHorosd(boolean paramBoolean)
  {
    this.mContext.getSharedPreferences("horoscope_push", 0).edit().putBoolean("isObtained" + TimeUtil.getStringDateShort(), paramBoolean).commit();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.InnerPushManager
 * JD-Core Version:    0.6.0
 */